package com.goalsapi.goalsapi.repository;

import org.springframework.data.repository.CrudRepository;

import com.goalsapi.goalsapi.entity.Goal;

public interface GoalRepository extends CrudRepository<Goal, Long> {

}
