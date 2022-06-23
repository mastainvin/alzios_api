package com.alzios.api.v1.controllers;

import com.alzios.api.domain.Program;
import com.alzios.api.exceptions.ResourceNotFoundException;
import com.alzios.api.repositories.ProgramRepository;
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
@RequestMapping("/v1/program")
@Tag(name="Programs", description = "Programs api")
public class ProgramController {

    @Autowired
    private ProgramRepository programRepository;

    protected Program verifyProgram(Long programId) throws ResourceNotFoundException {
        Optional<Program> program = programRepository.findById(programId);
        if(!program.isPresent()){
            throw new ResourceNotFoundException("Program with id " + programId + " not found.");
        }
        return program.get();
    }

    @GetMapping("/{programId}")
    @Operation(summary = "Get program from id")
    @ApiResponse(responseCode = "200", description = "Program found.")
    @ApiResponse(responseCode = "404", description = "Program not found.")
    public ResponseEntity<?> getProgram(@PathVariable Long programId) {
        return new ResponseEntity<>(this.verifyProgram(programId), HttpStatus.OK);
    }

    @GetMapping("/")
    @Operation(summary = "Get every program (work with paging)")
    @ApiResponse(responseCode = "200", description = "Getting successful.")
    public ResponseEntity<Page<Program>> getAllPrograms(Pageable pageable) {
        Page<Program> allPrograms = programRepository.findAll(pageable);
        return new ResponseEntity<>(allPrograms, HttpStatus.OK);
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @Operation(summary = "Add new program in database", description = "The newly created program ID will be sent in the location response.")
    @ApiResponse(responseCode = "201", description = "Program created successfully")
    @ApiResponse(responseCode = "500", description = "Error creating program")
    public ResponseEntity<?> createProgram(@Valid @RequestBody Program program) {
        program = programRepository.save(program);

        // Set the location in the header
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newProgramUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(program.getId())
                .toUri();
        responseHeaders.setLocation(newProgramUri);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{programId}")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @Operation(summary = "Update an program.")
    @ApiResponse(responseCode = "200", description = "Program updated successfully")
    @ApiResponse(responseCode = "500", description = "Error update program")
    public ResponseEntity<?> updateProgram(@PathVariable Long programId, @Valid @RequestBody Program program) {
        verifyProgram(programId);
        program = programRepository.save(program);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{programId}")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @Operation(summary = "Delete program from id")
    @ApiResponse(responseCode = "200", description = "Program deleted successfully")
    @ApiResponse(responseCode = "500", description = "Error delete program")
    public ResponseEntity<?> deleteProgram(@PathVariable Long programId) {
        verifyProgram(programId);
        programRepository.deleteById(programId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
