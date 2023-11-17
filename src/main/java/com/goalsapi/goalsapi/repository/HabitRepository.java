package com.goalsapi.goalsapi.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.goalsapi.goalsapi.entity.Habit;

public interface HabitRepository extends CrudRepository<Habit, Long> {

    List<Habit> findByGoalId(Long goalId);

}
