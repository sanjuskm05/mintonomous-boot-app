package com.mintonomous.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.mintonomous.model.Plant;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface PlantRepository extends CrudRepository<Plant, Integer> {

	List<Plant> findByName(String name);
	@Transactional
	Integer deleteByName(String name);
	
}
