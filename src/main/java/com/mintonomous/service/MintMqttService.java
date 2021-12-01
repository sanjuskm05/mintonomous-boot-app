package com.mintonomous.service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mintonomous.model.PlantDataPayload;

@Service
public class MintMqttService {

	private static final Logger logger = LoggerFactory.getLogger(MintMqttService.class);

	@Autowired
	IMqttClient mqttClient;

	@Value("${mqtt.subtopic}")
	String subTopic;

	@Value("${mqtt.pubtopic}")
	String pubTopic;

	@Autowired
	PlantDataService plantDataService;

	public void subscribe() throws MqttException, InterruptedException {
		logger.info("Messages received:" + subTopic);
		CountDownLatch countDownLatch = new CountDownLatch(10);

		mqttClient.subscribeWithResponse(subTopic, (tpic, msg) -> {
			logger.info(msg.getId() + " -> " + new String(msg.getPayload()));
			ObjectMapper mapper = new ObjectMapper();
			try {
				PlantDataPayload plantDataPayload = mapper.readValue(msg.getPayload(), PlantDataPayload.class);
				plantDataService.savePlantData(plantDataPayload.getTemperature(), plantDataPayload.getMoisture(),
						plantDataPayload.getHumidity(), plantDataPayload.getLight(), plantDataPayload.getPlantName());
			} catch (Exception e) {
				logger.warn("Exception happened why ???? ", e.getMessage(), e);
			}
			countDownLatch.countDown();
		});
		countDownLatch.await(1000, TimeUnit.MILLISECONDS);

	}

	public void publish(final String payload, int qos, boolean retained)
			throws MqttPersistenceException, MqttException {
		MqttMessage mqttMessage = new MqttMessage();
		mqttMessage.setPayload(payload.getBytes());
		mqttMessage.setQos(qos);
		mqttMessage.setRetained(retained);
		System.out.println("publishing...in publish.......");
		mqttClient.publish(pubTopic, mqttMessage);
	}

}
