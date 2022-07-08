package com.alzios.api.v1.controllers;

import com.alzios.api.domain.TrainingComponent;
import com.alzios.api.domain.embeddedIds.TrainingComponentId;
import com.alzios.api.exceptions.ResourceNotFoundException;
import com.alzios.api.repositories.TrainingComponentRepository;
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
@RequestMapping("/v1/trainingcomponent")
@Tag(name="Training components", description = "Training components api")
public class TrainingComponentController {

    @Autowired
    private TrainingComponentRepository trainingComponentRepository;

    protected TrainingComponent verifyTrainingComponent(TrainingComponentId trainingComponentId) throws ResourceNotFoundException {
        Optional<TrainingComponent> trainingComponent = trainingComponentRepository.findById(trainingComponentId);
        if(!trainingComponent.isPresent()){
            throw new ResourceNotFoundException("TrainingComponent with id " + trainingComponentId + " not found.");
        }
        return trainingComponent.get();
    }

    @GetMapping("/")
    @Operation(summary = "Get trainingComponent from id")
    @ApiResponse(responseCode = "200", description = "TrainingComponent found.")
    @ApiResponse(responseCode = "404", description = "TrainingComponent not found.")
    public ResponseEntity<?> getTrainingComponent(@Valid @RequestBody TrainingComponentId trainingComponentId) {
        return new ResponseEntity<>(this.verifyTrainingComponent(trainingComponentId), HttpStatus.OK);
    }

    @GetMapping("/all")
    @Operation(summary = "Get every trainingComponent (work with paging)")
    @ApiResponse(responseCode = "200", description = "Getting successful.")
    public ResponseEntity<Page<TrainingComponent>> getAllTrainingComponents(Pageable pageable) {
        Page<TrainingComponent> allTrainingComponents = trainingComponentRepository.findAll(pageable);
        return new ResponseEntity<>(allTrainingComponents, HttpStatus.OK);
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @Operation(summary = "Add new trainingComponent in database", description = "The newly created trainingComponent ID will be sent in the location response.")
    @ApiResponse(responseCode = "201", description = "TrainingComponent created successfully")
    @ApiResponse(responseCode = "500", description = "Error creating trainingComponent")
    public ResponseEntity<?> createTrainingComponent(@Valid @RequestBody TrainingComponent trainingComponent) {
        trainingComponent = trainingComponentRepository.save(trainingComponent);
        return new ResponseEntity<>(trainingComponent.getTrainingComponentId(), HttpStatus.CREATED);
    }

    @PutMapping("/")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @Operation(summary = "Update an trainingComponent.")
    @ApiResponse(responseCode = "200", description = "TrainingComponent updated successfully")
    @ApiResponse(responseCode = "500", description = "Error update trainingComponent")
    public ResponseEntity<?> updateTrainingComponent(@Valid @RequestBody TrainingComponent trainingComponent) {
        verifyTrainingComponent(trainingComponent.getTrainingComponentId());
        trainingComponent = trainingComponentRepository.save(trainingComponent);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @Operation(summary = "Delete trainingComponent from id")
    @ApiResponse(responseCode = "200", description = "TrainingComponent deleted successfully")
    @ApiResponse(responseCode = "500", description = "Error delete trainingComponent")
    public ResponseEntity<?> deleteTrainingComponent(@Valid @RequestBody TrainingComponentId trainingComponentId) {
        verifyTrainingComponent(trainingComponentId);
        trainingComponentRepository.deleteById(trainingComponentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/notused")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @Operation(summary = "Delete all trainingComponent not used in any training")
    @ApiResponse(responseCode = "200", description = "TrainingComponents deleted successfully")
    @ApiResponse(responseCode = "500", description = "Error delete trainingComponent")
    public ResponseEntity<?> deleteTrainingComponentNotUsed() {
        trainingComponentRepository.deleteByInTrainingIsFalse();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
