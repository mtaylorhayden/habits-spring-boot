package com.goalsapi.goalsapi.exception;

public class HabitNotFoundException extends RuntimeException {
    public HabitNotFoundException(Long habitId) {
        super("The habit id " + habitId + " does not exist in our records.");
    }
}
