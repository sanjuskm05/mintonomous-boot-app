package com.mintonomous.service;

import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mintonomous.model.Plant;
import com.mintonomous.model.PlantData;
import com.mintonomous.repository.PlantDataRepository;
import com.mintonomous.repository.PlantRepository;

@Service
public class PlantDataService {

	@Autowired
	private PlantDataRepository plantDataRepository;
	
	@Autowired
	private PlantRepository plantRepository;
	
	@Transactional
	public PlantData savePlantData( Float temperature, Float moisture, Float humidity,
			Float light, String name) {
		List<Plant> plants = plantRepository.findByName(name);
		Plant plant = null;
		if(plants != null && !plants.isEmpty()) {
			plant = plants.get(0);
		} else {
			plant = new Plant();
			plant.setName(name);
			plant.setLastUpdatedDate(LocalDateTime.now());
			plantRepository.save(plant);
			plant = plantRepository.findByName(name).get(0);
		}
		PlantData plantData = new PlantData();
		plantData.setHumidity(humidity);
		plantData.setLight(light);
		plantData.setTemperature(temperature);
		plantData.setMoisture(moisture);
		plantData.setPlantId(plant.getPlantId());
		plantData.setLastUpdatedDate(LocalDateTime.now());
		
		return plantDataRepository.save(plantData);
	}

}
