package com.mintonomous.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.mintonomous.model.PlantData;
import com.mintonomous.model.PlantThresoldMap;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface PlantThresoldMapRepository extends CrudRepository<PlantThresoldMap, Integer> {
	
	List<PlantThresoldMap> findByPlantId(int plantId);
}
