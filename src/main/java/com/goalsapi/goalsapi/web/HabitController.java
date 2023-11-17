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

import jakarta.validation.Valid;

// add swagger docs and authentication
@Controller
@RequestMapping(path = "/habit")
public class HabitController {
    @Autowired
    HabitService habitService;

    @GetMapping("/all")
    public ResponseEntity<List<Habit>> getAllHabits() {
        return new ResponseEntity<>(habitService.getHabits(), HttpStatus.OK);
    }

    @GetMapping("/{habitId}")
    public ResponseEntity<Habit> getHabitById(@PathVariable Long habitId) {
        return new ResponseEntity<>(habitService.getHabitById(habitId), HttpStatus.OK);
    }

    @GetMapping("/goal/{goalId}")
    public ResponseEntity<List<Habit>> getHabitsByGoalId(@PathVariable Long goalId) {
        return new ResponseEntity<>(habitService.getHabitsByGoalId(goalId),
                HttpStatus.OK);
    }

    @PostMapping("/goal/{goalId}/add")
    public ResponseEntity<Habit> addHabit(@Valid @RequestBody Habit habit, @PathVariable Long goalId) {
        return new ResponseEntity<>(habitService.saveHabit(habit, goalId), HttpStatus.CREATED);
    }

    @PutMapping("/{habitId}")
    public ResponseEntity<Habit> updateHabit(@PathVariable Long habitId, @Valid @RequestBody Habit habit) {
        return new ResponseEntity<>(habitService.updateHabit(habitId, habit), HttpStatus.OK);
    }

    @DeleteMapping("/{habitId}")
    public ResponseEntity<HttpStatus> deleteHabit(@PathVariable Long habitId) {
        habitService.deleteHabit(habitId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // DTO
    @GetMapping("/{habitId}/dto")
    public ResponseEntity<HabitDTO> getHabiDTOtById(@PathVariable Long habitId) {
        return new ResponseEntity<>(habitService.getHabitDTOById(habitId), HttpStatus.OK);
    }
}
