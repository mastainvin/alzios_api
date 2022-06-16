package com.alzios.api.v1.controllers;

import com.alzios.api.domain.Availability;
import com.alzios.api.domain.User;
import com.alzios.api.exceptions.ResourceNotFoundException;
import com.alzios.api.repositories.AvailabilityRepository;
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
@RequestMapping("/v1/availability")
@Tag(name="Availabilities", description = "Availabilities API")
public class AvailabilityController {
    @Autowired
    AvailabilityRepository availabilityRepository;

    protected Availability verifyAvailability(Long availabilityId) throws ResourceNotFoundException {
        Optional<Availability> availability = availabilityRepository.findById(availabilityId);
        if(!availability.isPresent()){
            throw new ResourceNotFoundException("Availability with id " + availabilityId + " not found.");
        }
        return availability.get();
    }

    @GetMapping("/{availabilityId}")
    @Operation(summary = "Get availability from id")
    @ApiResponse(responseCode = "200", description = "Availability found.")
    @ApiResponse(responseCode = "404", description = "Availability not found.")
    public ResponseEntity<?> getAvailability(@PathVariable Long availabilityId) {
        return new ResponseEntity<>(this.verifyAvailability(availabilityId), HttpStatus.OK);
    }

    @GetMapping("/")
    @Operation(summary = "Get every availabilities (work with paging)")
    @ApiResponse(responseCode = "200", description = "Getting successful.")
    public ResponseEntity<Page<Availability>> getAllAvailabilities(Pageable pageable) {
        Page<Availability> allAvailabilities = availabilityRepository.findAll(pageable);
        return new ResponseEntity<>(allAvailabilities, HttpStatus.OK);
    }

    @PostMapping("/")
    @Operation(summary = "Add new availability in database", description = "The newly created availability ID will be sent in the location response.")
    @ApiResponse(responseCode = "201", description = "Availability created successfully")
    @ApiResponse(responseCode = "500", description = "Error creating availability")
    public ResponseEntity<?> createUser(@Valid @RequestBody Availability availability) {
        availability = availabilityRepository.save(availability);

        // Set the location in the header
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(availability.getId())
                .toUri();
        responseHeaders.setLocation(newUserUri);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{availabilityId}")
    @Operation(summary = "Update an availability.")
    @ApiResponse(responseCode = "200", description = "User updated successfully")
    @ApiResponse(responseCode = "500", description = "Error update user")
    public ResponseEntity<?> updateUser(@PathVariable Long availabilityId, @Valid @RequestBody Availability availability) {
        verifyAvailability(availabilityId);
        availability = availabilityRepository.save(availability);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{availabilityId}")
    @Operation(summary = "Delete availability from id")
    @ApiResponse(responseCode = "200", description = "Availability deleted successfully")
    @ApiResponse(responseCode = "500", description = "Error delete availability")
    public ResponseEntity<?> deleteUser(@PathVariable Long availabilityId) {
        verifyAvailability(availabilityId);
        availabilityRepository.deleteById(availabilityId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
