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
    private List<Goal> goals = new ArrayList<>();

    @Mock
    private HabitRepository habitRepository;

    @InjectMocks
    private HabitServiceImpl habitService;

    @Before
    public void setUp() {
        // Create a Goal for the Habit
        goal = new Goal();
        goal.setId(1L);
        goal.setTitle("Do yoga everyday for a month");

        goals.add(goal);

        // Create a Habit
        habit = new Habit();
        habit.setId(1L);
        habit.setName("Yoga");
        habit.setType("Yin-yoga");
        habit.setDescription("45 minutes of yin-yoga");
        habit.setCreatedAt(LocalDateTime.now());
        habit.setGoal(goal);

        habits.add(habit);

        habit = new Habit();
        habit.setId(2L);
        habit.setName("Yoga");
        habit.setType("Ashtanga Primary Series");
        habit.setDescription("Completed full ashtanga primary series");
        habit.setCreatedAt(LocalDateTime.now());
        habit.setGoal(goal);

        habits.add(habit);

        // Create a Goal for the Habit
        goal = new Goal();
        goal.setId(2L);
        goal.setTitle("Learn GraphQL");

        goals.add(goal);

        // Create a Habit
        habit = new Habit();
        habit.setId(3L);
        habit.setName("Code");
        habit.setType("Schemas");
        habit.setDescription("Create schemas for my goals-api data");
        habit.setCreatedAt(LocalDateTime.now());
        habit.setGoal(goal);

        habits.add(habit);

        habit = new Habit();
        habit.setId(4L);
        habit.setName("Code");
        habit.setType("Resolvers");
        habit.setDescription("Create resolvers to get my data");
        habit.setCreatedAt(LocalDateTime.now());
        habit.setGoal(goal);

        habits.add(habit);

    }

    @After
    public void tearDown() {
        habitRepository.delete(habit);
    }

    @Test
    public void shouldReturnHabitsWhenHabitsExists() {
        when(habitRepository.findAll()).thenReturn(habits);

        List<Habit> results = habitService.getHabits();

        assertEquals("Yoga", results.get(0).getName());
        assertEquals("Yin-yoga", results.get(0).getType());
    }

    @Test
    public void shouldReturnHabitWhenIdExists() {
        when(habitRepository.findById(1L)).thenReturn(Optional.of(habits.get(1)));

        Habit result = habitService.getHabitById(1L);

        assertEquals("Yoga", result.getName());
    }

    @Test
    public void shouldThrowExceptionWhenIdNotExists() {
        when(habitRepository.findById(habit.getId())).thenReturn(Optional.empty());

        assertThrows(HabitNotFoundException.class, () -> {
            habitService.getHabitById(habit.getId());
        });
    }

    @Test
    public void shouldReturnHabitsByGoalIdWhenHabitsExists() {
        when(habitRepository.findByGoalId(goals.get(0).getId())).thenReturn(habits);

        List<Habit> results = habitService.getHabitsByGoalId(goals.get(0).getId());

        assertEquals("Do yoga everyday for a month", results.get(0).getGoal().getTitle());
        assertEquals("Yoga", results.get(0).getName());
    }
}
