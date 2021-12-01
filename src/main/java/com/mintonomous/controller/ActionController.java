package com.mintonomous.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mintonomous.model.Action;
import com.mintonomous.repository.ActionRepository;

@RestController
@RequestMapping("action")
public class ActionController {
	@Autowired
	private ActionRepository actionRepository;

	@PostMapping(path = "/add")
	public @ResponseBody ResponseEntity<Action> addNewAction(@RequestParam String name, @RequestParam String description) {

		Action action = new Action();
		action.setName(name);
		action.setDescription(description);
		action.setLastUpdatedDate(LocalDateTime.now());
		action = actionRepository.save(action);
		return new ResponseEntity<Action>(action, HttpStatus.OK);
	}

	@PutMapping(path = "/update") // Map ONLY POST Requests
	public @ResponseBody String updateAction(@RequestParam String name, @RequestParam String description) {

		Action action = actionRepository.findByName(name).get(0);
		action.setDescription(description);
		action.setLastUpdatedDate(LocalDateTime.now());
		actionRepository.save(action);
		return "Updated";
	}

	@GetMapping(path = "/all")
	public @ResponseBody Iterable<Action> getAllPlants() {
		// This returns a JSON
		return actionRepository.findAll();
	}

	@GetMapping
	public @ResponseBody Iterable<Action> getAction(@RequestParam String name) {
		// This returns a JSON
		return actionRepository.findByName(name);
	}

	@DeleteMapping
	public @ResponseBody String deleteAction(@RequestParam String name) {
		Integer deletedRow = actionRepository.deleteByName(name);
		return "deleted:" + deletedRow;
	}

	@DeleteMapping(path = "/all")
	public @ResponseBody String deleteAll() {
		actionRepository.deleteAll();
		return "deleted";
	}
}
