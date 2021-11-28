package com.mintonomous.scheduler;

import java.time.LocalDate;
import java.util.List;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.mintonomous.model.Action;
import com.mintonomous.model.ActionLog;
import com.mintonomous.model.Plant;
import com.mintonomous.model.PlantData;
import com.mintonomous.model.PlantThresoldMap;
import com.mintonomous.repository.ActionLogRepository;
import com.mintonomous.repository.ActionRepository;
import com.mintonomous.repository.PlantDataRepository;
import com.mintonomous.repository.PlantRepository;
import com.mintonomous.repository.PlantThresoldMapRepository;
import com.mintonomous.service.MintMqttService;

/**
 * Task class to check on orders
 */
@Component
public class MintMqttScheduler {

	private static final Logger logger = LoggerFactory.getLogger(MintMqttScheduler.class);

	@Autowired
	MintMqttService messagingService;

	@Autowired
	PlantDataRepository plantDataRepository;

	@Autowired
	PlantRepository plantRepository;

	@Autowired
	ActionRepository actionRepository;

	@Autowired
	ActionLogRepository actionLogRepository;

	@Autowired
	PlantThresoldMapRepository mapRepository;

	@Scheduled(fixedRateString = "300000", initialDelayString = "300000")
	public void performAnalysisAndPublishCmd() throws MqttPersistenceException, MqttException {
		logger.info("performAnalysisAndPublishCmd");
		List<PlantData> plantsData = plantDataRepository.findByIsAnalyzed(false);

		for (PlantData plantData : plantsData) {


			// fetch the action
			Action fetchActionIfMatches = fetchActionIfMatches(plantData);
			if (fetchActionIfMatches != null && fetchActionIfMatches.getActionId() != 0) {
				Plant plant = plantRepository.findById(plantData.getPlantId()).get();
				// publish action over mqtt
				messagingService.publish(
						"plantName:" + plant.getName() + ", actionName:" + fetchActionIfMatches.getName(), 0, true);
				// update is_analyzed true
				plantData.setIsAnalyzed(true);
				plantDataRepository.save(plantData);
				// insert action_log
				ActionLog actionLog = new ActionLog(null, fetchActionIfMatches.getActionId(), true, LocalDate.now());
				actionLogRepository.save(actionLog);
			}

		}

		// TODO performAnalysisAndPublishCmd
		logger.info("done performAnalysisAndPublishCmd");
	}

	private Action fetchActionIfMatches(PlantData plantData) {
		Action action = null;
		Integer actionId = 0;
		List<PlantThresoldMap> thresoldsMap = mapRepository.findByPlantId(plantData.getPlantId());
		for (PlantThresoldMap thresoldMap : thresoldsMap) {
			if (plantData.getLight() <= thresoldMap.getMaxLight() && plantData.getLight() >= thresoldMap.getMinLight()
					&& plantData.getMoisture() <= thresoldMap.getMaxMoisture()
					&& plantData.getMoisture() >= thresoldMap.getMinMoisture()
					&& plantData.getTemperature() <= thresoldMap.getMaxTemperature()
					&& plantData.getTemperature() >= thresoldMap.getMinTemperature()
					&& plantData.getHumidity() <= thresoldMap.getMaxHumidity()
					&& plantData.getHumidity() >= thresoldMap.getMinHumidity()) {
				actionId = thresoldMap.getConfiguredActionId();
				break;
			}
		}
		if (actionId != 0) {
			action = actionRepository.findById(actionId).get();
		}
		return action;
	}
}