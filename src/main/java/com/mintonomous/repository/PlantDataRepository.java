package com.mintonomous.repository;

import org.springframework.data.repository.CrudRepository;

import com.mintonomous.model.PlantData;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface PlantDataRepository extends CrudRepository<PlantData, Integer> {
}
