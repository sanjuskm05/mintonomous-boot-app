package com.mintonomous.controller;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mintonomous.model.Plant;
import com.mintonomous.repository.PlantRepository;
import com.mintonomous.service.MintMqttService;

@RestController
@RequestMapping("plant")
public class PlantController {
	@Autowired
	private PlantRepository plantRepository;

	
	@Autowired
	MintMqttService messagingService;
	
	
	@PostMapping(path = "/add")
	public @ResponseBody String addNewPlant(@RequestParam String name, @RequestParam String description) {

		Plant plant = new Plant();
		plant.setName(name);
		plant.setDescription(description);
		plant.setLastUpdatedDate(LocalDateTime.now());
		plantRepository.save(plant);
		return "Saved";
	}

	@PutMapping(path = "/update") // Map ONLY POST Requests
	public @ResponseBody String updatePlant(@RequestParam String name, @RequestParam String description) throws MqttPersistenceException, MqttException, InterruptedException {

		Plant plant = plantRepository.findByName(name).get(0);
		plant.setDescription(description);
		plant.setLastUpdatedDate(LocalDateTime.now());
		plantRepository.save(plant);
		return "Updated";
	}

	@CrossOrigin
	@GetMapping(path = "/all")
	public @ResponseBody Iterable<Plant> getAllPlants() {
		// This returns a JSON
		return plantRepository.findAll();
	}

	@GetMapping
	public @ResponseBody Iterable<Plant> getPlant(@RequestParam String name) {
		// This returns a JSON
		return plantRepository.findByName(name);
	}

	@DeleteMapping
	public @ResponseBody String deletePlant(@RequestParam String name) {
		Integer deletedRow = plantRepository.deleteByName(name);
		return "deleted:" + deletedRow;
	}

	@DeleteMapping(path = "/all")
	public @ResponseBody String deleteAll() {
		plantRepository.deleteAll();
		return "deleted";
	}
}
