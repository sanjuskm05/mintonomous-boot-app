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
import com.mintonomous.repository.ActionRepository;
import com.mintonomous.repository.PlantDataRepository;
import com.mintonomous.repository.PlantRepository;
import com.mintonomous.repository.PlantThresoldMapRepository;

@RestController
@RequestMapping("plant-thresold-config")
public class PlantThresoldMapController {
	@Autowired
	private PlantThresoldMapRepository plantThresoldMapRepository;
	
	@Autowired
	private ActionRepository actionRepository;
	
	@Autowired
	private PlantRepository plantRepository;

	@PostMapping(path = "/add")
	public @ResponseBody String addNewPlant(@RequestParam Float minTemperature,
			@RequestParam Float minMositure,
			@RequestParam Float minHumidity,
			@RequestParam Float minLight,
			@RequestParam Float maxTemperature,
			@RequestParam Float maxMositure,
			@RequestParam Float maxHhumidity,
			@RequestParam Float maxLight,
			@RequestParam String name) {

		// TODO save the thresold mapping
		
		
		// TODO before saving validate the data against the plant_thresold_map
		

		return "Saved";
	}


	@GetMapping(path = "/all")
	public @ResponseBody Iterable<PlantData> getAllPlants() {
		// This returns a JSON
		// TODO return all the thresold
		return null;
	}

}
