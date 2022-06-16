package com.alzios.api.v1.controllers;

import com.alzios.api.domain.Goal;
import com.alzios.api.exceptions.ResourceNotFoundException;
import com.alzios.api.repositories.GoalRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/v1/goal")
@Tag(name="Goals", description = "Goals api")
public class GoalController {

    @Autowired
    private GoalRepository goalRepository;

    protected Goal verifyGoal(Long goalId) throws ResourceNotFoundException {
        Optional<Goal> goal = goalRepository.findById(goalId);
        if(!goal.isPresent()){
            throw new ResourceNotFoundException("Goal with id " + goalId + " not found.");
        }
        return goal.get();
    }

    @GetMapping("/{goalId}")
    @Operation(summary = "Get goal from id")
    @ApiResponse(responseCode = "200", description = "Goal found.")
    @ApiResponse(responseCode = "404", description = "Goal not found.")
    public ResponseEntity<?> getGoal(@PathVariable Long goalId) {
        return new ResponseEntity<>(this.verifyGoal(goalId), HttpStatus.OK);
    }

    @GetMapping("/")
    @Operation(summary = "Get every goal (work with paging)")
    @ApiResponse(responseCode = "200", description = "Getting successful.")
    public ResponseEntity<Page<Goal>> getAllGoals(Pageable pageable) {
        Page<Goal> allGoals = goalRepository.findAll(pageable);
        return new ResponseEntity<>(allGoals, HttpStatus.OK);
    }

    @PostMapping("/")
    @Operation(summary = "Add new goal in database", description = "The newly created goal ID will be sent in the location response.")
    @ApiResponse(responseCode = "201", description = "Goal created successfully")
    @ApiResponse(responseCode = "500", description = "Error creating goal")
    public ResponseEntity<?> createGoal(@Valid @RequestBody Goal goal) {
        goal = goalRepository.save(goal);

        // Set the location in the header
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newGoalUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(goal.getId())
                .toUri();
        responseHeaders.setLocation(newGoalUri);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{goalId}")
    @Operation(summary = "Update an goal.")
    @ApiResponse(responseCode = "200", description = "Goal updated successfully")
    @ApiResponse(responseCode = "500", description = "Error update goal")
    public ResponseEntity<?> updateGoal(@PathVariable Long goalId, @Valid @RequestBody Goal goal) {
        verifyGoal(goalId);
        goal = goalRepository.save(goal);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{goalId}")
    @Operation(summary = "Delete goal from id")
    @ApiResponse(responseCode = "200", description = "Goal deleted successfully")
    @ApiResponse(responseCode = "500", description = "Error delete goal")
    public ResponseEntity<?> deleteGoal(@PathVariable Long goalId) {
        verifyGoal(goalId);
        goalRepository.deleteById(goalId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

