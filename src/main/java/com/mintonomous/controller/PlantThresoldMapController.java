package com.mintonomous.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mintonomous.model.Action;
import com.mintonomous.model.Plant;
import com.mintonomous.model.PlantThresoldMap;
import com.mintonomous.repository.ActionRepository;
import com.mintonomous.repository.PlantRepository;
import com.mintonomous.repository.PlantThresoldMapRepository;

@RestController
@RequestMapping("plant-thresold-map")
public class PlantThresoldMapController {
	@Autowired
	private PlantThresoldMapRepository plantThresoldMapRepository;

	@Autowired
	private ActionRepository actionRepository;

	@Autowired
	private PlantRepository plantRepository;

	@CrossOrigin
	@PostMapping(path = "/add")
	public @ResponseBody ResponseEntity<PlantThresoldMap> addNewPlantThresoldMap(@RequestParam Float minTemperature,
			@RequestParam Float minMoisture,
			@RequestParam Float minHumidity,
			@RequestParam Float minLight,
			@RequestParam Float maxTemperature,
			@RequestParam Float maxMoisture,
			@RequestParam Float maxHumidity,
			@RequestParam Float maxLight,
			@RequestParam String plantName,
			@RequestParam String actionName
			) {

		PlantThresoldMap plantThresoldMap = savePlantThresholdMap(minTemperature, minMoisture, minHumidity, minLight,
				maxTemperature, maxMoisture, maxHumidity, maxLight, plantName, actionName);

		return new ResponseEntity<PlantThresoldMap>(plantThresoldMap, HttpStatus.OK);
	}

	@CrossOrigin
	@PutMapping(path = "/update")
	public @ResponseBody ResponseEntity<PlantThresoldMap> updatePlantThresoldMap(@RequestParam Float minTemperature,
			@RequestParam Float minMoisture,
			@RequestParam Float minHumidity,
			@RequestParam Float minLight,
			@RequestParam Float maxTemperature,
			@RequestParam Float maxMoisture,
			@RequestParam Float maxHumidity,
			@RequestParam Float maxLight,
			@RequestParam String plantName,
			@RequestParam String actionName
			) {

		PlantThresoldMap plantThresoldMap = savePlantThresholdMap(minTemperature, minMoisture, minHumidity, minLight,
				maxTemperature, maxMoisture, maxHumidity, maxLight, plantName, actionName);

		return new ResponseEntity<PlantThresoldMap>(plantThresoldMap, HttpStatus.OK);
	}

	private PlantThresoldMap savePlantThresholdMap(Float minTemperature, Float minMoisture, Float minHumidity,
			Float minLight, Float maxTemperature, Float maxMoisture, Float maxHumidity, Float maxLight,
			String plantName, String actionName) {
		List<Action> actions = actionRepository.findByName(actionName);
		Action action = null;
		if(actions != null && !actions.isEmpty()) {
			action = actions.get(0);
		} else {
			action = new Action();
			action.setName(actionName);
			action.setLastUpdatedDate(LocalDateTime.now());
			actionRepository.save(action);
			action = actionRepository.findByName(actionName).get(0);
		}
		List<Plant> plants = plantRepository.findByName(plantName);
		Plant plant = null;
		if(plants != null && !plants.isEmpty()) {
			plant = plants.get(0);
		} else {
			plant = new Plant();
			plant.setName(plantName);
			plant.setLastUpdatedDate(LocalDateTime.now());
			plantRepository.save(plant);
			plant = plantRepository.findByName(plantName).get(0);
		}
		List<PlantThresoldMap> plantData = plantThresoldMapRepository.findByPlantId(plant.getPlantId());
		PlantThresoldMap plantThresoldMap = new PlantThresoldMap(null, plant.getPlantId(), 
				minTemperature, minLight, minHumidity, minMoisture, 
				maxTemperature, maxLight, maxHumidity, maxMoisture, LocalDateTime.now(), 
				action.getActionId());
		if (plantData != null && !plantData.isEmpty()) {
			plantThresoldMap.setPlantThresoldMapId(plantData.get(0).getPlantThresoldMapId());
		}
		
		plantThresoldMap = plantThresoldMapRepository.save(plantThresoldMap);
		return plantThresoldMap;
	}
	
	@GetMapping(path = "/all")
	public @ResponseBody Iterable<PlantThresoldMap> getAllPlantThresoldMap() {
		return plantThresoldMapRepository.findAll();
	}
	
	@CrossOrigin
	@GetMapping(path = "/plant")
	public @ResponseBody PlantThresoldMap getPlantLastReading(@RequestParam String name) {
		List<Plant> plants = plantRepository.findByName(name);
		if (plants != null && !plants.isEmpty()) {
			Integer plantId = plants.get(0).getPlantId();
			List<PlantThresoldMap> plantData = plantThresoldMapRepository.findByPlantId(plantId);
			if (plantData != null && !plantData.isEmpty()) {
				return plantData.get(0);
			}
		}
		return new PlantThresoldMap();
	}

}
