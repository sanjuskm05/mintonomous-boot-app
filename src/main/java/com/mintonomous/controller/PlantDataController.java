package com.mintonomous.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mintonomous.model.Plant;
import com.mintonomous.model.PlantData;
import com.mintonomous.model.PlantDataReportPayload;
import com.mintonomous.repository.PlantDataReportRepository;
import com.mintonomous.repository.PlantDataRepository;
import com.mintonomous.repository.PlantRepository;
import com.mintonomous.service.PlantDataService;

@RestController
@RequestMapping("plant-data")
public class PlantDataController {
	@Autowired
	private PlantDataRepository plantDataRepository;

	@Autowired
	private PlantRepository plantRepository;
	
	@Autowired
	private PlantDataReportRepository plantDataReportRepository;

	@Autowired
	private PlantDataService plantDataService;

	@PostMapping(path = "/add")
	public @ResponseBody ResponseEntity<PlantData> addNewPlantData(@RequestParam Float temperature,
			@RequestParam Float moisture, @RequestParam Float humidity, @RequestParam Float light,
			@RequestParam String name) {

		PlantData plantData = plantDataService.savePlantData(temperature, moisture, humidity, light, name);
		return new ResponseEntity<PlantData>(plantData, HttpStatus.OK);
	}

	@GetMapping(path = "/all")
	public @ResponseBody Iterable<PlantData> getAllPlants() {
		// This returns a JSON
		return plantDataRepository.findAll();
	}

	@GetMapping(path = "/all/plant")
	public @ResponseBody Iterable<PlantData> getPlantData(@RequestParam Integer plantId) {
		// This returns a JSON
		return plantDataRepository.findByPlantIdOrderByLastUpdatedDateDesc(plantId);
	}

	@CrossOrigin
	@GetMapping(path = "/plant")
	public @ResponseBody PlantData getPlantLastReading(@RequestParam String name) {
		List<Plant> plants = plantRepository.findByName(name);
		if (plants != null && !plants.isEmpty()) {
			Integer plantId = plants.get(0).getPlantId();
			List<PlantData> plantData = plantDataRepository.findByPlantIdOrderByLastUpdatedDateDesc(plantId);
			if (plantData != null && !plantData.isEmpty()) {
				return plantData.get(0);
			}
		}
		return new PlantData();
	}
	
	@CrossOrigin
	@GetMapping(path = "/plant-daily-data")
	public @ResponseBody List<PlantDataReportPayload> getPlantDailyData(@RequestParam String name) {
		return plantDataReportRepository.findAvgPlantDataByEveryHourForLast1Day(name);
	}
}
