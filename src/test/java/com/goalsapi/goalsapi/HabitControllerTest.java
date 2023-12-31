package com.goalsapi.goalsapi;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import com.goalsapi.goalsapi.entity.Goal;
import com.goalsapi.goalsapi.entity.Habit;
import com.goalsapi.goalsapi.service.HabitServiceImpl;
import com.goalsapi.goalsapi.web.HabitController;

@RunWith(SpringRunner.class)
@WebMvcTest(HabitController.class)
public class HabitControllerTest {

    private Goal goal;
    private Habit habit;
    private List<Habit> habits = new ArrayList<>();
    private List<Goal> goals = new ArrayList<>();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
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

    @WithMockUser(username = "max.taylor")
    @Test
    public void testGetAllHabits() throws Exception {
        when(habitService.getHabits()).thenReturn(habits);            

        mockMvc.perform(get("/habit/all"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("[0].id").value(1));
    }

    @Test
    public void testNotAuthenticated() throws Exception {
        mockMvc.perform(get("/habit/all"))
                .andExpect(status().isUnauthorized())
                .andExpect(header().doesNotExist("Authorization"));
    }

    @WithMockUser(username = "max.taylor")
    @Test
    public void testGetHabitById() throws Exception {
        when(habitService.getHabitById(habits.get(0).getId())).thenReturn(habits.get(0));            

        mockMvc.perform(get("/habit/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("id").value(1));
    }

    @WithMockUser(username = "max.taylor")
    @Test
    public void testGetHabitsByGoalId() throws Exception {

        when(habitService.getHabitsByGoalId(goals.get(0).getId()))
            .thenReturn(
                habits.stream().filter
                    (h -> 
                        h.getGoal().getId().equals
                            (goals.get(0).getId()))
                                .collect(Collectors.toList()));            

        mockMvc.perform(get("/habit/goal/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("[0].id").value(1))
            .andExpect(jsonPath("[0].goal.id").value(1));
    }
}
