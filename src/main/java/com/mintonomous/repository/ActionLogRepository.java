package com.mintonomous.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.repository.CrudRepository;

import com.mintonomous.model.Action;
import com.mintonomous.model.ActionLog;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface ActionLogRepository extends CrudRepository<ActionLog, Integer> {

}
