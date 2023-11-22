package com.goalsapi.goalsapi;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.goalsapi.goalsapi.entity.Goal;
import com.goalsapi.goalsapi.entity.Habit;
import com.goalsapi.goalsapi.exception.HabitNotFoundException;
import com.goalsapi.goalsapi.repository.HabitRepository;
import com.goalsapi.goalsapi.service.HabitServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class HabitServiceTest {

    private Goal goal;
    private Habit habit;
    private List<Habit> habits = new ArrayList<>();

    @Mock
    private HabitRepository habitRepository;

    @InjectMocks
    private HabitServiceImpl habitService;

    @Before
    public void setUp() {
        // Create a Goal for the Habit
        goal = new Goal();
        goal.setId(1L);

        // Create a Habit
        habit = new Habit();
        habit.setId(1L);
        habit.setName("Habit Name");
        habit.setType("Ashtanga Primary Series");
        habit.setDescription("Habit Description");
        habit.setCreatedAt(LocalDateTime.now());
        habit.setGoal(goal);

        habits.add(habit);
    }

    @After
    public void tearDown() {
        habitRepository.delete(habit);
    }

    @Test
    public void getHabitsTest() {
        when(habitRepository.findAll()).thenReturn(habits);

        List<Habit> results = habitService.getHabits();

        assertEquals("Habit Name", results.get(0).getName());
        assertEquals("Ashtanga Primary Series", results.get(0).getType());
    }

    @Test
    public void getHabitByIdTest() {
        when(habitRepository.findById(1L)).thenReturn(Optional.of(habit));

        Habit result = habitService.getHabitById(1L);

        assertEquals("Habit Name", result.getName());
    }

    @Test
    public void GetHabitbyIdFailTest() {
        when(habitRepository.findById(habit.getId())).thenReturn(Optional.empty());

        assertThrows(HabitNotFoundException.class, () -> {
            habitService.getHabitById(habit.getId());
        });
    }
}
