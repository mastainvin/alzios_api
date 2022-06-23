package com.alzios.api.v1.controllers;

import com.alzios.api.domain.SerieDivision;
import com.alzios.api.exceptions.ResourceNotFoundException;
import com.alzios.api.repositories.SerieDivisionRepository;
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
@RequestMapping("/v1/seriedivision")
@Tag(name="Serie divisions", description = "Serie divisions api")
public class SerieDivisionController {

    @Autowired
    private SerieDivisionRepository serieDivisionRepository;

    protected SerieDivision verifySerieDivision(Long serieDivisionId) throws ResourceNotFoundException {
        Optional<SerieDivision> serieDivision = serieDivisionRepository.findById(serieDivisionId);
        if(!serieDivision.isPresent()){
            throw new ResourceNotFoundException("SerieDivision with id " + serieDivisionId + " not found.");
        }
        return serieDivision.get();
    }

    @GetMapping("/{serieDivisionId}")
    @Operation(summary = "Get serieDivision from id")
    @ApiResponse(responseCode = "200", description = "SerieDivision found.")
    @ApiResponse(responseCode = "404", description = "SerieDivision not found.")
    public ResponseEntity<?> getSerieDivision(@PathVariable Long serieDivisionId) {
        return new ResponseEntity<>(this.verifySerieDivision(serieDivisionId), HttpStatus.OK);
    }

    @GetMapping("/")
    @Operation(summary = "Get every serieDivision (work with paging)")
    @ApiResponse(responseCode = "200", description = "Getting successful.")
    public ResponseEntity<Page<SerieDivision>> getAllSerieDivisions(Pageable pageable) {
        Page<SerieDivision> allSerieDivisions = serieDivisionRepository.findAll(pageable);
        return new ResponseEntity<>(allSerieDivisions, HttpStatus.OK);
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @Operation(summary = "Add new serieDivision in database", description = "The newly created serieDivision ID will be sent in the location response.")
    @ApiResponse(responseCode = "201", description = "SerieDivision created successfully")
    @ApiResponse(responseCode = "500", description = "Error creating serieDivision")
    public ResponseEntity<?> createSerieDivision(@Valid @RequestBody SerieDivision serieDivision) {
        serieDivision = serieDivisionRepository.save(serieDivision);

        // Set the location in the header
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newSerieDivisionUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(serieDivision.getId())
                .toUri();
        responseHeaders.setLocation(newSerieDivisionUri);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{serieDivisionId}")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @Operation(summary = "Update an serieDivision.")
    @ApiResponse(responseCode = "200", description = "SerieDivision updated successfully")
    @ApiResponse(responseCode = "500", description = "Error update serieDivision")
    public ResponseEntity<?> updateSerieDivision(@PathVariable Long serieDivisionId, @Valid @RequestBody SerieDivision serieDivision) {
        verifySerieDivision(serieDivisionId);
        serieDivision = serieDivisionRepository.save(serieDivision);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{serieDivisionId}")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @Operation(summary = "Delete serieDivision from id")
    @ApiResponse(responseCode = "200", description = "SerieDivision deleted successfully")
    @ApiResponse(responseCode = "500", description = "Error delete serieDivision")
    public ResponseEntity<?> deleteSerieDivision(@PathVariable Long serieDivisionId) {
        verifySerieDivision(serieDivisionId);
        serieDivisionRepository.deleteById(serieDivisionId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
