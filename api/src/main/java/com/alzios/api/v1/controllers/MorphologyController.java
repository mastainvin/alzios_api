package com.alzios.api.v1.controllers;

import com.alzios.api.domain.Morphology;
import com.alzios.api.exceptions.ResourceNotFoundException;
import com.alzios.api.repositories.MorphologyRepository;
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
@RequestMapping("/v1/morphology")
@Tag(name="Morphologies", description = "Morphologies api")
public class MorphologyController {

    @Autowired
    private MorphologyRepository morphologyRepository;

    protected Morphology verifyMorphology(Long morphologyId) throws ResourceNotFoundException {
        Optional<Morphology> morphology = morphologyRepository.findById(morphologyId);
        if(!morphology.isPresent()){
            throw new ResourceNotFoundException("Morphology with id " + morphologyId + " not found.");
        }
        return morphology.get();
    }

    @GetMapping("/{morphologyId}")
    @Operation(summary = "Get morphology from id")
    @ApiResponse(responseCode = "200", description = "Morphology found.")
    @ApiResponse(responseCode = "404", description = "Morphology not found.")
    public ResponseEntity<?> getMorphology(@PathVariable Long morphologyId) {
        return new ResponseEntity<>(this.verifyMorphology(morphologyId), HttpStatus.OK);
    }

    @GetMapping("/")
    @Operation(summary = "Get every morphology (work with paging)")
    @ApiResponse(responseCode = "200", description = "Getting successful.")
    public ResponseEntity<Page<Morphology>> getAllMorphologys(Pageable pageable) {
        Page<Morphology> allMorphologys = morphologyRepository.findAll(pageable);
        return new ResponseEntity<>(allMorphologys, HttpStatus.OK);
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @Operation(summary = "Add new morphology in database", description = "The newly created morphology ID will be sent in the location response.")
    @ApiResponse(responseCode = "201", description = "Morphology created successfully")
    @ApiResponse(responseCode = "500", description = "Error creating morphology")
    public ResponseEntity<?> createMorphology(@Valid @RequestBody Morphology morphology) {
        morphology = morphologyRepository.save(morphology);

        // Set the location in the header
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newMorphologyUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(morphology.getId())
                .toUri();
        responseHeaders.setLocation(newMorphologyUri);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{morphologyId}")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @Operation(summary = "Update an morphology.")
    @ApiResponse(responseCode = "200", description = "Morphology updated successfully")
    @ApiResponse(responseCode = "500", description = "Error update morphology")
    public ResponseEntity<?> updateMorphology(@PathVariable Long morphologyId, @Valid @RequestBody Morphology morphology) {
        verifyMorphology(morphologyId);
        morphology = morphologyRepository.save(morphology);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{morphologyId}")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @Operation(summary = "Delete morphology from id")
    @ApiResponse(responseCode = "200", description = "Morphology deleted successfully")
    @ApiResponse(responseCode = "500", description = "Error delete morphology")
    public ResponseEntity<?> deleteMorphology(@PathVariable Long morphologyId) {
        verifyMorphology(morphologyId);
        morphologyRepository.deleteById(morphologyId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
