package com.alzios.api.v1.controllers;

import com.alzios.api.domain.Exercise;
import com.alzios.api.exceptions.ResourceNotFoundException;
import com.alzios.api.repositories.ExerciseRepository;
import com.alzios.api.services.ExerciseFilesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/v1/exercise")
@Tag(name="Exercises", description = "Exercises api")
public class ExerciseController {

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private ExerciseFilesService exerciseFilesService;

    protected Exercise verifyExercise(Long exerciseId) throws ResourceNotFoundException {
        Optional<Exercise> exercise = exerciseRepository.findById(exerciseId);
        if(!exercise.isPresent()){
            throw new ResourceNotFoundException("Exercise with id " + exerciseId + " not found.");
        }
        return exercise.get();
    }

    @GetMapping("/{exerciseId}")
    @Operation(summary = "Get exercise from id")
    @ApiResponse(responseCode = "200", description = "Exercise found.")
    @ApiResponse(responseCode = "404", description = "Exercise not found.")
    public ResponseEntity<?> getExercise(@PathVariable Long exerciseId) {
        Exercise exercise = verifyExercise(exerciseId);
        updateExerciseResource(exercise);
        return new ResponseEntity<>(exercise, HttpStatus.OK);
    }

    @GetMapping("/")
    @Operation(summary = "Get every exercise (work with paging)")
    @ApiResponse(responseCode = "200", description = "Getting successful.")
    public ResponseEntity<Page<Exercise>> getAllExercises(Pageable pageable) {
        Page<Exercise> allExercises = exerciseRepository.findAll(pageable);
        for(Exercise exercise : allExercises) {
            updateExerciseResource(exercise);
        }
        return new ResponseEntity<>(allExercises, HttpStatus.OK);
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @Operation(summary = "Add new exercise in database", description = "The newly created exercise ID will be sent in the location response.")
    @ApiResponse(responseCode = "201", description = "Exercise created successfully")
    @ApiResponse(responseCode = "500", description = "Error creating exercise")
    public ResponseEntity<?> createExercise(@Valid @RequestBody Exercise exercise) {
        exercise = exerciseRepository.save(exercise);

        // Set the location in the header
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newExerciseUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(exercise.getId())
                .toUri();
        responseHeaders.setLocation(newExerciseUri);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{exerciseId}")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @Operation(summary = "Update an exercise.")
    @ApiResponse(responseCode = "200", description = "Exercise updated successfully")
    @ApiResponse(responseCode = "500", description = "Error update exercise")
    public ResponseEntity<?> updateExercise(@PathVariable Long exerciseId, @Valid @RequestBody Exercise exercise) {
        verifyExercise(exerciseId);
        exercise = exerciseRepository.save(exercise);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{exerciseId}")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @Operation(summary = "Delete exercise from id")
    @ApiResponse(responseCode = "200", description = "Exercise deleted successfully")
    @ApiResponse(responseCode = "500", description = "Error delete exercise")
    public ResponseEntity<?> deleteExercise(@PathVariable Long exerciseId) {
        verifyExercise(exerciseId);
        exerciseRepository.deleteById(exerciseId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{exerciseId}/attach/{fileId}")
    @Operation(summary = "Choose the picture of an exercise.")
    @ApiResponse(responseCode = "200", description = "Picture attach successfully")
    @ApiResponse(responseCode = "500", description = "Error attach picture")
    public ResponseEntity<Exercise> attach(@PathVariable Long exerciseId, @PathVariable Long fileId){
        Exercise exercise = exerciseFilesService.attach(exerciseId, fileId);
        return new ResponseEntity<>(exercise, HttpStatus.OK);
    }

    protected void updateExerciseResource(Exercise exercise) {
        exercise.add(linkTo(methodOn(FileController.class).getPictureByExerciseId(exercise.getId())).withRel("picture"));
        exercise.add(linkTo(methodOn(FileController.class).getVideoByExerciseId(exercise.getId())).withRel("video"));
    }


}
