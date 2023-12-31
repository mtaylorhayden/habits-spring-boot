package com.goalsapi.goalsapi.web;

import java.util.List;

import org.springdoc.core.annotations.RouterOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.goalsapi.goalsapi.entity.Goal;
import com.goalsapi.goalsapi.service.GoalService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import javax.validation.Valid;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@SecurityRequirement(name = "Bearer Authentication")
@Controller
@RequestMapping(path = "/goal")
public class GoalController {

    @Autowired
    private GoalService goalService;

    @Operation(description = "Gets all of the goals")
    @GetMapping(path = "/all")
    public ResponseEntity<List<Goal>> getAllGoals() {
        return new ResponseEntity<>(goalService.getGoals(), HttpStatus.OK);
    }

    @Operation(description = "Gets a goal by the goalId")
    @GetMapping(path = "/{goalId}")
    public ResponseEntity<Goal> getGoalById(@PathVariable Long goalId) {
        return new ResponseEntity<>(goalService.getGoalById(goalId), HttpStatus.OK);
    }

    @Operation(description = "Add a goal")
    @PostMapping(path = "/add")
    public ResponseEntity<Goal> addGoal(@Valid @RequestBody Goal goal) {
        return new ResponseEntity<>(goalService.saveGoal(goal), HttpStatus.CREATED);
    }

    @Operation(description = "Update a goal by the goalId")
    @PutMapping("/{goalId}")
    public ResponseEntity<Goal> updateGoal(@PathVariable Long goalId, @Valid @RequestBody Goal goal) {
        return new ResponseEntity<>(goalService.updateGoal(goalId, goal), HttpStatus.OK);
    }

    @Operation(description = "Delete a goal my the goalId")
    @DeleteMapping("/{goalId}")
    public ResponseEntity<HttpStatus> deleteGoal(@PathVariable Long goalId) {
        goalService.deleteGoal(goalId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
