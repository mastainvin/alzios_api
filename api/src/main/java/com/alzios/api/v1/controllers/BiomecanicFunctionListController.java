package com.alzios.api.v1.controllers;

import com.alzios.api.domain.BiomecanicFunctionList;
import com.alzios.api.domain.User;
import com.alzios.api.exceptions.ResourceNotFoundException;
import com.alzios.api.repositories.BiomecanicFunctionListRepository;
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
@RequestMapping("/v1/biomecanicfunctionlist")
@Tag(name = "Biomecanic function lists",description = "Biomecanic function lists API")
public class BiomecanicFunctionListController {

    @Autowired
    BiomecanicFunctionListRepository biomecanicFunctionListRepository;

    protected BiomecanicFunctionList verifyBiomecanicFunctionList(Long biomecanicFunctionListId) throws ResourceNotFoundException {
        Optional<BiomecanicFunctionList> biomecanicFunctionList = biomecanicFunctionListRepository.findById(biomecanicFunctionListId);
        if(!biomecanicFunctionList.isPresent()){
            throw new ResourceNotFoundException("Biomecanic function list with id " + biomecanicFunctionListId + " not found.");
        }
        return biomecanicFunctionList.get();
    }

    @GetMapping("/{biomecanicfunctionlistId}")
    @Operation(summary = "Get biomecanic function list from id")
    @ApiResponse(responseCode = "200", description = "Biomecanic function list found.")
    @ApiResponse(responseCode = "404", description = "Biomecanic function list not found.")
    public ResponseEntity<?> getUser(@PathVariable Long biomecanicfunctionlistId) {
        return new ResponseEntity<>(this.verifyBiomecanicFunctionList(biomecanicfunctionlistId), HttpStatus.OK);
    }

    @GetMapping("/")
    @Operation(summary = "Get every biomecanic function list (work with paging)")
    @ApiResponse(responseCode = "200", description = "Getting successful.")
    public ResponseEntity<Page<BiomecanicFunctionList>> getAllBiomecanicFunctionList(Pageable pageable) {
        Page<BiomecanicFunctionList> allBiomecanicFunctionLists = biomecanicFunctionListRepository.findAll(pageable);
        return new ResponseEntity<>(allBiomecanicFunctionLists, HttpStatus.OK);
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @Operation(summary = "Add new biomecanic function list in database", description = "The newly created biomecanic function list ID will be sent in the location response.")
    @ApiResponse(responseCode = "201", description = "Biomecanic function list created successfully")
    @ApiResponse(responseCode = "500", description = "Error creating biomecanic function list")
    public ResponseEntity<URI> createBiomecanicFunctionList(@Valid @RequestBody BiomecanicFunctionList biomecanicFunctionList) {
        biomecanicFunctionList = biomecanicFunctionListRepository.save(biomecanicFunctionList);

        URI newUserUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(biomecanicFunctionList.getId())
                .toUri();

        return new ResponseEntity<>(newUserUri, HttpStatus.CREATED);
    }

    @PutMapping("/{biomecanicfunctionlistId}")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @Operation(summary = "Update an biomecanic function list.")
    @ApiResponse(responseCode = "200", description = "Biomecaninc function list updated successfully")
    @ApiResponse(responseCode = "500", description = "Error update biomecanic function list")
    public ResponseEntity<?> updateBiomecanicFunctionList(@PathVariable Long biomecanicfunctionlistId, @Valid @RequestBody BiomecanicFunctionList biomecanicFunctionList) {
        verifyBiomecanicFunctionList(biomecanicfunctionlistId);
        biomecanicFunctionList = biomecanicFunctionListRepository.save(biomecanicFunctionList);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{biomecanicfunctionlistId}")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @Operation(summary = "Delete biomecanic function list from id")
    @ApiResponse(responseCode = "200", description = "Biomecanic function list deleted successfully")
    @ApiResponse(responseCode = "500", description = "Error delete biomecanic function list")
    public ResponseEntity<?> deleteBiomecanicFunctionList(@PathVariable Long biomecanicfunctionlistId) {
        verifyBiomecanicFunctionList(biomecanicfunctionlistId);
        biomecanicFunctionListRepository.deleteById(biomecanicfunctionlistId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
