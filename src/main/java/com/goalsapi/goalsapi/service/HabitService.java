package com.goalsapi.goalsapi.service;

import java.util.List;

import com.goalsapi.goalsapi.DTO.HabitDTO;
import com.goalsapi.goalsapi.entity.Habit;

import jakarta.transaction.Transactional;

public interface HabitService {
    List<Habit> getHabits();

    HabitDTO getHabitDTOById(Long id);

    Habit getHabitById(Long id);

    List<Habit> getHabitsByGoalId(Long goalId);

    Habit saveHabit(Habit habit, Long goalId);

    @Transactional
    void deleteHabit(Long habitId);

    Habit updateHabit(Long habitId, Habit habit);
}
