package com.goalsapi.goalsapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goalsapi.goalsapi.DTO.DTOConverter;
import com.goalsapi.goalsapi.DTO.HabitDTO;
import com.goalsapi.goalsapi.entity.Goal;
import com.goalsapi.goalsapi.entity.Habit;
import com.goalsapi.goalsapi.exception.GoalNotFoundException;
import com.goalsapi.goalsapi.exception.HabitNotFoundException;
import com.goalsapi.goalsapi.repository.GoalRepository;
import com.goalsapi.goalsapi.repository.HabitRepository;

import javax.transaction.*;

@Service
public class HabitServiceImpl implements HabitService {
    @Autowired
    HabitRepository habitRepository;

    @Autowired
    GoalRepository goalRepository;

    @Override
    public List<Habit> getHabits() {
        return (List<Habit>) habitRepository.findAll();
    }

    @Override
    public Habit getHabitById(Long habitId) {
        Optional<Habit> habit = habitRepository.findById(habitId);

        if (habit.isPresent()) {
            return habit.get();
        } else {
            throw new HabitNotFoundException(habitId);
        }
    }

    @Override
    public Habit saveHabit(Habit habit, Long goalId) {
        Optional<Goal> goal = goalRepository.findById(goalId);

        if (goal.isPresent()) {
            habit.setGoal(goal.get());
            return habitRepository.save(habit);
        } else {
            throw new GoalNotFoundException(goalId);
        }
    }

    @Override
    public List<Habit> getHabitsByGoalId(Long goalId) {
        return habitRepository.findByGoalId(goalId);
    }

    @Transactional
    @Override
    public void deleteHabit(Long habitId) {
        Optional<Habit> habit = habitRepository.findById(habitId);

        if (habit.isPresent()) {
            habitRepository.deleteById(habitId);
        } else {
            throw new HabitNotFoundException(habitId);
        }
    }

    @Override
    public Habit updateHabit(Long habitId, Habit habit) {

        Optional<Habit> optionalHabit = habitRepository.findById(habitId);

        if (optionalHabit.isPresent()) {

            Habit habitToUpdate = optionalHabit.get();
            habitToUpdate.setName(habit.getName());
            habitToUpdate.setDescription(habit.getDescription());
            habitToUpdate.setType(habit.getType());

            return habitRepository.save(habitToUpdate);

        } else {
            throw new HabitNotFoundException(habitId);
        }
    }

    // DTO
    @Override
    public HabitDTO getHabitDTOById(Long habitId) {
        Optional<Habit> habit = habitRepository.findById(habitId);

        if (habit.isPresent()) {
            HabitDTO habitDTO = DTOConverter.convertHabitToDTO(habit.get());
            return habitDTO;
        } else {
            throw new HabitNotFoundException(habitId);
        }
    }

}
