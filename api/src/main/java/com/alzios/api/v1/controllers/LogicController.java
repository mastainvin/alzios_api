package com.alzios.api.v1.controllers;

import com.alzios.api.dtos.ProgramDto;
import com.alzios.api.services.BusinessLogicService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@Tag(name = "Logic", description = "Logic of the application (create, update and get trainings)")
@RequestMapping("/v1/logic")
public class LogicController {

    @Autowired
    BusinessLogicService businessLogicService;

    @PostMapping("/training/create/{userId}")
    @PostAuthorize("#userId.equals(#principal.getName())")
    @ApiResponse(responseCode = "200", description = "Creation succeed.")
    @ApiResponse(responseCode = "404", description = "user not found.")
    public ResponseEntity<?> createUserTraining(@PathVariable String userId, Principal principal) {
        businessLogicService.createTraining(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/training/update/{userId}")
    @PostAuthorize("#userId.equals(#principal.getName())")
    @ApiResponse(responseCode = "200", description = "Update succeed.")
    @ApiResponse(responseCode = "404", description = "user not found.")
    public ResponseEntity<?> updateUserTraining(@PathVariable String userId, @RequestBody @Valid ProgramDto userProgram, Principal principal) {
        businessLogicService.updateTraining(userId, userProgram);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/training/{userId}")
    @PostAuthorize("#userId.equals(#principal.getName())")
    @ApiResponse(responseCode = "200", description = "Training getting succeed.")
    @ApiResponse(responseCode = "404", description = "user not found.")
    public ResponseEntity<ProgramDto> getUserTraining(@PathVariable String userId, Principal principal) {
        ProgramDto program = businessLogicService.getTrainingByUserId(userId);
        return new ResponseEntity<>(program, HttpStatus.OK);
    }

    @GetMapping("/training/superset/{userId}")
    @PostAuthorize("#userId.equals(#principal.getName())")
    @ApiResponse(responseCode = "200", description = "Training getting succeed.")
    @ApiResponse(responseCode = "404", description = "user not found.")
    public ResponseEntity<ProgramDto> getUserTrainingSuperSet(@PathVariable String userId, Principal principal) {
        ProgramDto program = businessLogicService.getTrainingSuperSetByUserId(userId);
        return new ResponseEntity<>(program, HttpStatus.OK);
    }
}
