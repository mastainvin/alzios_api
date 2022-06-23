package com.alzios.api.v1.controllers;

import com.alzios.api.domain.BodyLimb;
import com.alzios.api.domain.User;
import com.alzios.api.exceptions.ResourceNotFoundException;
import com.alzios.api.repositories.BodyLimbRepository;
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
@RequestMapping("/v1/bodylimb")
@Tag(name="Body limbs", description = "Body limb API")
public class BodyLimbController {
    @Autowired
    BodyLimbRepository bodyLimbRepository;

    protected BodyLimb verifyBodyLimb(Long bodyLimbId) throws ResourceNotFoundException {
        Optional<BodyLimb> bodyLimb = bodyLimbRepository.findById(bodyLimbId);
        if(!bodyLimb.isPresent()){
            throw new ResourceNotFoundException("Body limb with id " + bodyLimbId + " not found.");
        }
        return bodyLimb.get();
    }

    @GetMapping("/{bodylimbId}")
    @Operation(summary = "Get body limb from id")
    @ApiResponse(responseCode = "200", description = "Body limb found.")
    @ApiResponse(responseCode = "404", description = "Body limb not found.")
    public ResponseEntity<?> getUser(@PathVariable Long bodylimbId) {
        return new ResponseEntity<>(this.verifyBodyLimb(bodylimbId), HttpStatus.OK);
    }

    @GetMapping("/")
    @Operation(summary = "Get every body limbs (work with paging)")
    @ApiResponse(responseCode = "200", description = "Getting successful.")
    public ResponseEntity<Page<BodyLimb>> getAllBodyLimbs(Pageable pageable) {
        Page<BodyLimb> allBodyLimbs = bodyLimbRepository.findAll(pageable);
        return new ResponseEntity<>(allBodyLimbs, HttpStatus.OK);
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @Operation(summary = "Add new body limb in database", description = "The newly created body limb ID will be sent in the location response.")
    @ApiResponse(responseCode = "201", description = "Body limb created successfully")
    @ApiResponse(responseCode = "500", description = "Error creating body limb")
    public ResponseEntity<?> createUser(@Valid @RequestBody BodyLimb bodyLimb) {
        bodyLimb = bodyLimbRepository.save(bodyLimb);

        // Set the location in the header
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newUserUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(bodyLimb.getId())
                .toUri();
        responseHeaders.setLocation(newUserUri);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{bodylimbId}")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @Operation(summary = "Update a body limb.")
    @ApiResponse(responseCode = "200", description = "Body limb updated successfully")
    @ApiResponse(responseCode = "500", description = "Error update body limb")
    public ResponseEntity<?> updateUser(@PathVariable Long bodylimbId, @Valid @RequestBody BodyLimb bodyLimb) {
        verifyBodyLimb(bodylimbId);
        bodyLimb = bodyLimbRepository.save(bodyLimb);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{bodylimbId}")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @Operation(summary = "Delete body limb from id")
    @ApiResponse(responseCode = "200", description = "Body limb deleted successfully")
    @ApiResponse(responseCode = "500", description = "Error delete body limb")
    public ResponseEntity<?> deleteUser(@PathVariable Long bodylimbId) {
        verifyBodyLimb(bodylimbId);
        bodyLimbRepository.deleteById(bodylimbId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
