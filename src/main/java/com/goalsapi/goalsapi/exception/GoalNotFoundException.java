package com.goalsapi.goalsapi.exception;

public class GoalNotFoundException extends RuntimeException {
    public GoalNotFoundException(Long goalId) {
        super("The goal id " + goalId + " does not exist in our records.");
    }
}
