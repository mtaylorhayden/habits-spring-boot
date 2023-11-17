package com.goalsapi.goalsapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goalsapi.goalsapi.entity.Goal;
import com.goalsapi.goalsapi.exception.GoalNotFoundException;
import com.goalsapi.goalsapi.repository.GoalRepository;

@Service
public class GoalServiceImpl implements GoalService {

    @Autowired
    private GoalRepository goalRepository;

    @Override
    public List<Goal> getGoals() {
        return (List<Goal>) goalRepository.findAll();
    }

    @Override
    public Goal getGoalById(Long goalId) {
        Optional<Goal> goal = goalRepository.findById(goalId);

        if (goal.isPresent()) {
            return goal.get();
        } else {
            throw new GoalNotFoundException(goalId);
        }
    }

    @Override
    public Goal saveGoal(Goal goal) {
        return goalRepository.save(goal);
    }

    @Override
    public void deleteGoal(Long goalId) {
        Optional<Goal> goal = goalRepository.findById(goalId);

        if (goal.isPresent()) {
            goalRepository.delete(goal.get());
        } else {
            throw new GoalNotFoundException(goalId);
        }
    }

    @Override
    public Goal updateGoal(Long goalId, Goal updatedGoal) {
        Optional<Goal> optionalGoal = goalRepository.findById(goalId);

        if (optionalGoal.isPresent()) {
            Goal goalToUpdate = optionalGoal.get();
            goalToUpdate.setTitle(updatedGoal.getTitle());
            goalToUpdate.setDescription(updatedGoal.getDescription());
            return goalRepository.save(goalToUpdate);
        } else {
            throw new GoalNotFoundException(goalId);
        }
    }
}
