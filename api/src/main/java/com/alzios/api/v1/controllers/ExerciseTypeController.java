package com.alzios.api.v1.controllers;

import com.alzios.api.domain.ExerciseType;
import com.alzios.api.exceptions.ResourceNotFoundException;
import com.alzios.api.repositories.ExerciseTypeRepository;
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

@RestController
@RequestMapping("/v1/exercisetype")
@Tag(name="Exercise types", description = "Exercise types api")
public class ExerciseTypeController {

    @Autowired
    private ExerciseTypeRepository exerciseTypeRepository;

    protected ExerciseType verifyExerciseType(Long exerciseTypeId) throws ResourceNotFoundException {
        Optional<ExerciseType> exerciseType = exerciseTypeRepository.findById(exerciseTypeId);
        if(!exerciseType.isPresent()){
            throw new ResourceNotFoundException("ExerciseType with id " + exerciseTypeId + " not found.");
        }
        return exerciseType.get();
    }

    @GetMapping("/{exerciseTypeId}")
    @Operation(summary = "Get exerciseType from id")
    @ApiResponse(responseCode = "200", description = "ExerciseType found.")
    @ApiResponse(responseCode = "404", description = "ExerciseType not found.")
    public ResponseEntity<?> getExerciseType(@PathVariable Long exerciseTypeId) {
        return new ResponseEntity<>(this.verifyExerciseType(exerciseTypeId), HttpStatus.OK);
    }

    @GetMapping("/")
    @Operation(summary = "Get every exerciseType (work with paging)")
    @ApiResponse(responseCode = "200", description = "Getting successful.")
    public ResponseEntity<Page<ExerciseType>> getAllExerciseTypes(Pageable pageable) {
        Page<ExerciseType> allExerciseTypes = exerciseTypeRepository.findAll(pageable);
        return new ResponseEntity<>(allExerciseTypes, HttpStatus.OK);
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @Operation(summary = "Add new exerciseType in database", description = "The newly created exerciseType ID will be sent in the location response.")
    @ApiResponse(responseCode = "201", description = "ExerciseType created successfully")
    @ApiResponse(responseCode = "500", description = "Error creating exerciseType")
    public ResponseEntity<URI> createExerciseType(@Valid @RequestBody ExerciseType exerciseType) {
        exerciseType = exerciseTypeRepository.save(exerciseType);

        URI newExerciseTypeUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(exerciseType.getId())
                .toUri();

        return new ResponseEntity<>(newExerciseTypeUri, HttpStatus.CREATED);
    }

    @PutMapping("/{exerciseTypeId}")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @Operation(summary = "Update an exerciseType.")
    @ApiResponse(responseCode = "200", description = "ExerciseType updated successfully")
    @ApiResponse(responseCode = "500", description = "Error update exerciseType")
    public ResponseEntity<?> updateExerciseType(@PathVariable Long exerciseTypeId, @Valid @RequestBody ExerciseType exerciseType) {
        verifyExerciseType(exerciseTypeId);
        exerciseType = exerciseTypeRepository.save(exerciseType);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{exerciseTypeId}")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @Operation(summary = "Delete exerciseType from id")
    @ApiResponse(responseCode = "200", description = "ExerciseType deleted successfully")
    @ApiResponse(responseCode = "500", description = "Error delete exerciseType")
    public ResponseEntity<?> deleteExerciseType(@PathVariable Long exerciseTypeId) {
        verifyExerciseType(exerciseTypeId);
        exerciseTypeRepository.deleteById(exerciseTypeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
