package com.mintonomous.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mintonomous.model.PlantData;
import com.mintonomous.repository.PlantDataRepository;
import com.mintonomous.repository.PlantRepository;
import com.mintonomous.service.PlantDataService;

@RestController
@RequestMapping("plant-data")
public class PlantDataController {
	@Autowired
	private PlantDataRepository plantDataRepository;
	
	
	@Autowired
	private PlantDataService plantDataService;

	@PostMapping(path = "/add")
	public @ResponseBody ResponseEntity<PlantData> addNewPlantData(@RequestParam Float temperature,
			@RequestParam Float moisture,
			@RequestParam Float humidity,
			@RequestParam Float light,
			@RequestParam String name) {

		PlantData plantData = plantDataService.savePlantData(temperature, moisture, humidity, light, name);
		return new ResponseEntity<PlantData>(plantData, HttpStatus.OK);
	}


	@GetMapping(path = "/all")
	public @ResponseBody Iterable<PlantData> getAllPlants() {
		// This returns a JSON
		return plantDataRepository.findAll();
	}

}
