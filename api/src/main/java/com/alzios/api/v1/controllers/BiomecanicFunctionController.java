package com.alzios.api.v1.controllers;

import com.alzios.api.domain.BiomecanicFunction;
import com.alzios.api.domain.User;
import com.alzios.api.exceptions.ResourceNotFoundException;
import com.alzios.api.repositories.BiomecanicFunctionRepository;
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
@RequestMapping("/v1/biomecanicfunction")
@Tag(name="Biomecanic functions", description = "Biomecanic functions API")
public class BiomecanicFunctionController {
    @Autowired
    BiomecanicFunctionRepository biomecanicFunctionRepository;

    protected BiomecanicFunction verifyBiomecanicFunction(Long biomecanicFunctionId) throws ResourceNotFoundException {
        Optional<BiomecanicFunction> biomecanicFunction = biomecanicFunctionRepository.findById(biomecanicFunctionId);
        if(!biomecanicFunction.isPresent()){
            throw new ResourceNotFoundException("Biomecanic function with id " + biomecanicFunctionId + " not found.");
        }
        return biomecanicFunction.get();
    }

    @GetMapping("/{biomecanicfunctionId}")
    @Operation(summary = "Get biomecanic function from id")
    @ApiResponse(responseCode = "200", description = "Biomecanic function found.")
    @ApiResponse(responseCode = "404", description = "Biomecanic function not found.")
    public ResponseEntity<?> getUser(@PathVariable Long biomecanicfunctionId) {
        return new ResponseEntity<>(this.verifyBiomecanicFunction(biomecanicfunctionId), HttpStatus.OK);
    }

    @GetMapping("/")
    @Operation(summary = "Get every biomecanic funciton (work with paging)")
    @ApiResponse(responseCode = "200", description = "Getting successful.")
    public ResponseEntity<Page<BiomecanicFunction>> getAllUsers(Pageable pageable) {
        Page<BiomecanicFunction> allBiomecanicFunctions = biomecanicFunctionRepository.findAll(pageable);
        return new ResponseEntity<>(allBiomecanicFunctions, HttpStatus.OK);
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @Operation(summary = "Add new biomecanic function in database", description = "The newly created biomecanic function ID will be sent in the location response.")
    @ApiResponse(responseCode = "201", description = "Biomecanic function created successfully")
    @ApiResponse(responseCode = "500", description = "Error creating biomecanic function")
    public ResponseEntity<?> createUser(@Valid @RequestBody BiomecanicFunction biomecanicFunction) {
        biomecanicFunction = biomecanicFunctionRepository.save(biomecanicFunction);

        // Set the location in the header
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(biomecanicFunction.getId())
                .toUri();
        responseHeaders.setLocation(newUserUri);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{biomecanicfunctionId}")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @Operation(summary = "Update a biomecanic function.")
    @ApiResponse(responseCode = "200", description = "Biomecanic function updated successfully")
    @ApiResponse(responseCode = "500", description = "Error update biomecanic function")
    public ResponseEntity<?> updateUser(@PathVariable Long biomecanicfunctionId, @Valid @RequestBody BiomecanicFunction biomecanicFunction) {
        verifyBiomecanicFunction(biomecanicfunctionId);
        biomecanicFunction = biomecanicFunctionRepository.save(biomecanicFunction);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{biomecanicFunction}")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @Operation(summary = "Delete biomecanic function from id")
    @ApiResponse(responseCode = "200", description = "Biomecanic function deleted successfully.")
    @ApiResponse(responseCode = "500", description = "Error delete biomecanic function.")
    public ResponseEntity<?> deleteUser(@PathVariable Long biomecanicFunction) {
        verifyBiomecanicFunction(biomecanicFunction);
        biomecanicFunctionRepository.deleteById(biomecanicFunction);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
