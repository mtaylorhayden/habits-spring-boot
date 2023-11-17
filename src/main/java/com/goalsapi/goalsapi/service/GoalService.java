package com.goalsapi.goalsapi.service;

import java.util.List;

import com.goalsapi.goalsapi.entity.Goal;

public interface GoalService {
    Goal saveGoal(Goal goal);

    List<Goal> getGoals();

    Goal getGoalById(Long goalId);

    void deleteGoal(Long goalId);

    Goal updateGoal(Long goalId, Goal goal);
}
