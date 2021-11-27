package com.mintonomous.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mintonomous.model.Plant;
import com.mintonomous.model.PlantData;
import com.mintonomous.repository.PlantDataRepository;
import com.mintonomous.repository.PlantRepository;

@RestController
@RequestMapping("plant-data")
public class PlantDataController {
	@Autowired
	private PlantDataRepository plantDataRepository;
	
	@Autowired
	private PlantRepository plantRepository;

	@PostMapping(path = "/add")
	public @ResponseBody String addNewPlantData(@RequestParam Float temperature,
			@RequestParam Float mositure,
			@RequestParam Float humidity,
			@RequestParam Float light,
			@RequestParam String name) {

		Plant plant = plantRepository.findByName(name).get(0);
		PlantData plantData = new PlantData();
		plantData.setHumidity(humidity);
		plantData.setLight(light);
		plantData.setTemperature(temperature);
		plantData.setMositure(mositure);
		plantData.setPlantId(plant.getPlantId());
		plantData.setLastUpdatedDate(LocalDate.now());
		
		
		// TODO before saving validate the data against the plant_thresold_map
		
		plantDataRepository.save(plantData);
		return "Saved";
	}


	@GetMapping(path = "/all")
	public @ResponseBody Iterable<PlantData> getAllPlants() {
		// This returns a JSON
		return plantDataRepository.findAll();
	}

}
