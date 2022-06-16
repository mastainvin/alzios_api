package com.alzios.api.v1.controllers;

import com.alzios.api.dtos.ProgramDto;
import com.alzios.api.logic.ProgramLogic;
import com.alzios.api.services.BusinessLogicService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Tag(name = "Logic", description = "Logic of the application (create, update and get trainings)")
@RequestMapping("/v1/logic")
public class LogicController {

    @Autowired
    BusinessLogicService businessLogicService;

    @PostMapping("/training/create/{userId}")
    @ApiResponse(responseCode = "200", description = "Creation succeed.")
    @ApiResponse(responseCode = "404", description = "user not found.")
    public ResponseEntity<?> createUserTraining(@PathVariable Long userId) {
        businessLogicService.createTraining(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/training/update/{userId}")
    @ApiResponse(responseCode = "200", description = "Update succeed.")
    @ApiResponse(responseCode = "404", description = "user not found.")
    public ResponseEntity<?> updateUserTraining(@PathVariable Long userId, @RequestBody @Valid ProgramDto userProgram) {
        businessLogicService.updateTraining(userId, userProgram);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/training/{userId}")
    @ApiResponse(responseCode = "200", description = "Training getting succeed.")
    @ApiResponse(responseCode = "404", description = "user not found.")
    public ResponseEntity<ProgramDto> getUserTraining(@PathVariable Long userId) {
        ProgramDto program = businessLogicService.getTrainingByUserId(userId);
        return new ResponseEntity<>(program, HttpStatus.OK);
    }

    @GetMapping("/training/superset/{userId}")
    @ApiResponse(responseCode = "200", description = "Training getting succeed.")
    @ApiResponse(responseCode = "404", description = "user not found.")
    public ResponseEntity<ProgramLogic> getUserTrainingSuperSet(@PathVariable Long userId) {
        ProgramLogic program = businessLogicService.getTrainingSuperSetByUserId(userId);
        return new ResponseEntity<>(program, HttpStatus.OK);
    }
}
