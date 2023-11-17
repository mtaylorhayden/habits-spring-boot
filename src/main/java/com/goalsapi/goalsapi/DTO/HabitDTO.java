package com.goalsapi.goalsapi.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class HabitDTO {
    private Long habitId;
    private String name;
    private Long goalId;
}
