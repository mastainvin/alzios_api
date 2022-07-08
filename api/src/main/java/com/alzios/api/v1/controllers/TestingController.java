package com.alzios.api.v1.controllers;

import com.alzios.api.dtos.TestDto;
import com.alzios.api.services.TestService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.alzios.api.dtos.ProgramDto;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@Tag(name = "Test controller")
@RequestMapping("/v1/test")
public class TestingController {
    @Autowired
    TestService testService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @ApiResponse(responseCode = "200", description = "Creation succeed.")
    @ApiResponse(responseCode = "404", description = "user not found.")
    public ResponseEntity<?> createTestTraining(Principal principal, @RequestBody @Valid TestDto testDto) {
        testService.testCreateProgram(testDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/training")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @ApiResponse(responseCode = "200", description = "Creation succeed.")
    @ApiResponse(responseCode = "404", description = "user not found.")
    public ResponseEntity<ProgramDto> getTestTraining(Principal principal) {
        ProgramDto program = testService.testGetProgram();
        return new ResponseEntity<>(program, HttpStatus.OK);
    }
}
