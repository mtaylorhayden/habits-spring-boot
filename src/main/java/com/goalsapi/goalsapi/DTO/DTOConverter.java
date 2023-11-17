package com.goalsapi.goalsapi.DTO;

import com.goalsapi.goalsapi.entity.Habit;

public class DTOConverter {
    public static HabitDTO convertHabitToDTO(Habit habit) {
        HabitDTO habitDTO = new HabitDTO();
        habitDTO.setHabitId(habit.getId());
        habitDTO.setName(habit.getName());
        habitDTO.setGoalId(habit.getGoal().getId());

        return habitDTO;
    }
}
