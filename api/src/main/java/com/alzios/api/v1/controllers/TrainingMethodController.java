package com.alzios.api.v1.controllers;

import com.alzios.api.domain.TrainingMethod;
import com.alzios.api.exceptions.ResourceNotFoundException;
import com.alzios.api.repositories.TrainingMethodRepository;
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
@RequestMapping("/v1/trainingmethod")
@Tag(name="Training methods", description = "Training methods api")
public class TrainingMethodController {

    @Autowired
    private TrainingMethodRepository trainingMethodRepository;

    protected TrainingMethod verifyTrainingMethod(Long trainingMethodId) throws ResourceNotFoundException {
        Optional<TrainingMethod> trainingMethod = trainingMethodRepository.findById(trainingMethodId);
        if(!trainingMethod.isPresent()){
            throw new ResourceNotFoundException("TrainingMethod with id " + trainingMethodId + " not found.");
        }
        return trainingMethod.get();
    }

    @GetMapping("/{trainingMethodId}")
    @Operation(summary = "Get trainingMethod from id")
    @ApiResponse(responseCode = "200", description = "TrainingMethod found.")
    @ApiResponse(responseCode = "404", description = "TrainingMethod not found.")
    public ResponseEntity<?> getTrainingMethod(@PathVariable Long trainingMethodId) {
        return new ResponseEntity<>(this.verifyTrainingMethod(trainingMethodId), HttpStatus.OK);
    }

    @GetMapping("/")
    @Operation(summary = "Get every trainingMethod (work with paging)")
    @ApiResponse(responseCode = "200", description = "Getting successful.")
    public ResponseEntity<Page<TrainingMethod>> getAllTrainingMethods(Pageable pageable) {
        Page<TrainingMethod> allTrainingMethods = trainingMethodRepository.findAll(pageable);
        return new ResponseEntity<>(allTrainingMethods, HttpStatus.OK);
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @Operation(summary = "Add new trainingMethod in database", description = "The newly created trainingMethod ID will be sent in the location response.")
    @ApiResponse(responseCode = "201", description = "TrainingMethod created successfully")
    @ApiResponse(responseCode = "500", description = "Error creating trainingMethod")
    public ResponseEntity<URI> createTrainingMethod(@Valid @RequestBody TrainingMethod trainingMethod) {
        trainingMethod = trainingMethodRepository.save(trainingMethod);

        URI newTrainingMethodUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(trainingMethod.getId())
                .toUri();

        return new ResponseEntity<>(newTrainingMethodUri, HttpStatus.CREATED);
    }

    @PutMapping("/{trainingMethodId}")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @Operation(summary = "Update an trainingMethod.")
    @ApiResponse(responseCode = "200", description = "TrainingMethod updated successfully")
    @ApiResponse(responseCode = "500", description = "Error update trainingMethod")
    public ResponseEntity<?> updateTrainingMethod(@PathVariable Long trainingMethodId, @Valid @RequestBody TrainingMethod trainingMethod) {
        verifyTrainingMethod(trainingMethodId);
        trainingMethod = trainingMethodRepository.save(trainingMethod);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{trainingMethodId}")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @Operation(summary = "Delete trainingMethod from id")
    @ApiResponse(responseCode = "200", description = "TrainingMethod deleted successfully")
    @ApiResponse(responseCode = "500", description = "Error delete trainingMethod")
    public ResponseEntity<?> deleteTrainingMethod(@PathVariable Long trainingMethodId) {
        verifyTrainingMethod(trainingMethodId);
        trainingMethodRepository.deleteById(trainingMethodId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
