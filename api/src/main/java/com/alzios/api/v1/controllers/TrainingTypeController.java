package com.alzios.api.v1.controllers;

import com.alzios.api.domain.TrainingType;
import com.alzios.api.exceptions.ResourceNotFoundException;
import com.alzios.api.repositories.TrainingTypeRepository;
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
@RequestMapping("/v1/trainingtype")
@Tag(name="Training types", description = "Training types api")
public class TrainingTypeController {

    @Autowired
    private TrainingTypeRepository trainingTypeRepository;

    protected TrainingType verifyTrainingType(Long trainingTypeId) throws ResourceNotFoundException {
        Optional<TrainingType> trainingType = trainingTypeRepository.findById(trainingTypeId);
        if(!trainingType.isPresent()){
            throw new ResourceNotFoundException("TrainingType with id " + trainingTypeId + " not found.");
        }
        return trainingType.get();
    }

    @GetMapping("/{trainingTypeId}")
    @Operation(summary = "Get trainingType from id")
    @ApiResponse(responseCode = "200", description = "TrainingType found.")
    @ApiResponse(responseCode = "404", description = "TrainingType not found.")
    public ResponseEntity<?> getTrainingType(@PathVariable Long trainingTypeId) {
        return new ResponseEntity<>(this.verifyTrainingType(trainingTypeId), HttpStatus.OK);
    }

    @GetMapping("/")
    @Operation(summary = "Get every trainingType (work with paging)")
    @ApiResponse(responseCode = "200", description = "Getting successful.")
    public ResponseEntity<Page<TrainingType>> getAllTrainingTypes(Pageable pageable) {
        Page<TrainingType> allTrainingTypes = trainingTypeRepository.findAll(pageable);
        return new ResponseEntity<>(allTrainingTypes, HttpStatus.OK);
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @Operation(summary = "Add new trainingType in database", description = "The newly created trainingType ID will be sent in the location response.")
    @ApiResponse(responseCode = "201", description = "TrainingType created successfully")
    @ApiResponse(responseCode = "500", description = "Error creating trainingType")
    public ResponseEntity<?> createTrainingType(@Valid @RequestBody TrainingType trainingType) {
        trainingType = trainingTypeRepository.save(trainingType);

        // Set the location in the header
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newTrainingTypeUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(trainingType.getId())
                .toUri();
        responseHeaders.setLocation(newTrainingTypeUri);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{trainingTypeId}")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @Operation(summary = "Update an trainingType.")
    @ApiResponse(responseCode = "200", description = "TrainingType updated successfully")
    @ApiResponse(responseCode = "500", description = "Error update trainingType")
    public ResponseEntity<?> updateTrainingType(@PathVariable Long trainingTypeId, @Valid @RequestBody TrainingType trainingType) {
        verifyTrainingType(trainingTypeId);
        trainingType = trainingTypeRepository.save(trainingType);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{trainingTypeId}")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @Operation(summary = "Delete trainingType from id")
    @ApiResponse(responseCode = "200", description = "TrainingType deleted successfully")
    @ApiResponse(responseCode = "500", description = "Error delete trainingType")
    public ResponseEntity<?> deleteTrainingType(@PathVariable Long trainingTypeId) {
        verifyTrainingType(trainingTypeId);
        trainingTypeRepository.deleteById(trainingTypeId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
