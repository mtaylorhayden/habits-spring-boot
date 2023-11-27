package com.goalsapi.goalsapi.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.goalsapi.goalsapi.DTO.HabitDTO;
import com.goalsapi.goalsapi.entity.Habit;
import com.goalsapi.goalsapi.service.HabitService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import javax.validation.Valid;

@SecurityRequirement(name = "Bearer Authentication")
@Controller
@RequestMapping(path = "/habit")
public class HabitController {

    @Autowired
    HabitService habitService;

    @Operation(description = "Gets all of the habits")
    @GetMapping("/all")
    public ResponseEntity<List<Habit>> getAllHabits() {
        return new ResponseEntity<>(habitService.getHabits(), HttpStatus.OK);
    }

    @Operation(description = "Gets a single by habit by the habitId")
    @GetMapping("/{habitId}")
    public ResponseEntity<Habit> getHabitById(@PathVariable Long habitId) {
        return new ResponseEntity<>(habitService.getHabitById(habitId), HttpStatus.OK);
    }

    @Operation(description = "Gets a list of habits associated to a single goal by the goalId")
    @GetMapping("/goal/{goalId}")
    public ResponseEntity<List<Habit>> getHabitsByGoalId(@PathVariable Long goalId) {
        return new ResponseEntity<>(habitService.getHabitsByGoalId(goalId),
                HttpStatus.OK);
    }

    @Operation(description = "Creates a habit and adds it to a specific goal by goalId")
    @PostMapping("/goal/{goalId}/add")
    public ResponseEntity<Habit> addHabit(@Valid @RequestBody Habit habit,
            @Parameter(description = "The habit will be associated with this goal") @PathVariable Long goalId) {
        return new ResponseEntity<>(habitService.saveHabit(habit, goalId), HttpStatus.CREATED);
    }

    @Operation(summary = "Update habit", description = "Updates a habit by the habitId", security = @SecurityRequirement(name = "Bearer Authentication"))
    @PutMapping("/{habitId}")
    public ResponseEntity<Habit> updateHabit(@PathVariable Long habitId, @Valid @RequestBody Habit habit) {
        return new ResponseEntity<>(habitService.updateHabit(habitId, habit), HttpStatus.OK);
    }

    @Operation(summary = "Delete habit", description = "Delete a habit by the habitId", security = @SecurityRequirement(name = "Bearer Authentication"))
    @DeleteMapping("/{habitId}")
    public ResponseEntity<HttpStatus> deleteHabit(@PathVariable Long habitId) {
        habitService.deleteHabit(habitId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // DTO
    @Operation(summary = "Get habit DTO", description = "Gets the habitDTO which only includes habitId, habit name, and goalId", security = @SecurityRequirement(name = "Bearer Authentication"))
    @GetMapping("/{habitId}/dto")
    public ResponseEntity<HabitDTO> getHabiDTOtById(@PathVariable Long habitId) {
        return new ResponseEntity<>(habitService.getHabitDTOById(habitId), HttpStatus.OK);
    }
}
