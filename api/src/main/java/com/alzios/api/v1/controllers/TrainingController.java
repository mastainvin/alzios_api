package com.alzios.api.v1.controllers;

import com.alzios.api.domain.Training;
import com.alzios.api.exceptions.ResourceNotFoundException;
import com.alzios.api.repositories.TrainingRepository;
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
@RequestMapping("/v1/training")
@Tag(name="Trainings", description = "Trainings api")
public class TrainingController {

    @Autowired
    private TrainingRepository trainingRepository;

    protected Training verifyTraining(Long trainingId) throws ResourceNotFoundException {
        Optional<Training> training = trainingRepository.findById(trainingId);
        if(!training.isPresent()){
            throw new ResourceNotFoundException("Training with id " + trainingId + " not found.");
        }
        return training.get();
    }

    @GetMapping("/{trainingId}")
    @Operation(summary = "Get training from id")
    @ApiResponse(responseCode = "200", description = "Training found.")
    @ApiResponse(responseCode = "404", description = "Training not found.")
    public ResponseEntity<?> getTraining(@PathVariable Long trainingId) {
        return new ResponseEntity<>(this.verifyTraining(trainingId), HttpStatus.OK);
    }

    @GetMapping("/")
    @Operation(summary = "Get every training (work with paging)")
    @ApiResponse(responseCode = "200", description = "Getting successful.")
    public ResponseEntity<Page<Training>> getAllTrainings(Pageable pageable) {
        Page<Training> allTrainings = trainingRepository.findAll(pageable);
        return new ResponseEntity<>(allTrainings, HttpStatus.OK);
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @Operation(summary = "Add new training in database", description = "The newly created training ID will be sent in the location response.")
    @ApiResponse(responseCode = "201", description = "Training created successfully")
    @ApiResponse(responseCode = "500", description = "Error creating training")
    public ResponseEntity<?> createTraining(@Valid @RequestBody Training training) {
        training = trainingRepository.save(training);

        // Set the location in the header
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newTrainingUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(training.getId())
                .toUri();
        responseHeaders.setLocation(newTrainingUri);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{trainingId}")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @Operation(summary = "Update an training.")
    @ApiResponse(responseCode = "200", description = "Training updated successfully")
    @ApiResponse(responseCode = "500", description = "Error update training")
    public ResponseEntity<?> updateTraining(@PathVariable Long trainingId, @Valid @RequestBody Training training) {
        verifyTraining(trainingId);
        training = trainingRepository.save(training);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{trainingId}")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @Operation(summary = "Delete training from id")
    @ApiResponse(responseCode = "200", description = "Training deleted successfully")
    @ApiResponse(responseCode = "500", description = "Error delete training")
    public ResponseEntity<?> deleteTraining(@PathVariable Long trainingId) {
        verifyTraining(trainingId);
        trainingRepository.deleteById(trainingId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
