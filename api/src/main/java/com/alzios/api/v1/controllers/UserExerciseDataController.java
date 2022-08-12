package com.alzios.api.v1.controllers;

import com.alzios.api.domain.Exercise;
import com.alzios.api.domain.User;
import com.alzios.api.domain.UserExerciseData;
import com.alzios.api.domain.embeddedIds.UserExerciseDataId;
import com.alzios.api.exceptions.ResourceNotFoundException;
import com.alzios.api.repositories.ExerciseRepository;
import com.alzios.api.repositories.UserExerciseDataRepository;
import com.alzios.api.repositories.UserRepository;
import com.alzios.api.utils.JsonXMLUtils;
import com.fasterxml.jackson.databind.node.ObjectNode;
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
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/v1/userexercisedata")
@Tag(name="User exercise datas", description = "Use exercise datas api")
public class UserExerciseDataController {

    @Autowired
    private UserExerciseDataRepository userExerciseDataRepository;

    @Autowired
    private ExerciseRepository exerciseRepository;

    @Autowired
    private UserRepository userRepository;

    protected UserExerciseData verifyUserExerciseData(UserExerciseDataId userExerciseDataId) throws ResourceNotFoundException {
        Optional<UserExerciseData> userExerciseData = userExerciseDataRepository.findByUserExerciseDataIdExerciseIdAndUserExerciseDataIdUserId(userExerciseDataId.getExercise().getId(), userExerciseDataId.getUser().getId());
        if(!userExerciseData.isPresent()){
            throw new ResourceNotFoundException("UserExerciseData with id " + userExerciseDataId + " not found.");
        }
        return userExerciseData.get();
    }

    @GetMapping("/")
    @Operation(summary = "Get userExerciseData from id")
    @ApiResponse(responseCode = "200", description = "UserExerciseData found.")
    @ApiResponse(responseCode = "404", description = "UserExerciseData not found.")
    public ResponseEntity<?> getUserExerciseData(@Valid @RequestBody UserExerciseDataId userExerciseDataId) {
        return new ResponseEntity<>(this.verifyUserExerciseData(userExerciseDataId), HttpStatus.OK);
    }

    @GetMapping("/all")
    @Operation(summary = "Get every userExerciseData (work with paging)")
    @ApiResponse(responseCode = "200", description = "Getting successful.")
    public ResponseEntity<Page<UserExerciseData>> getAllUserExerciseDatas(Pageable pageable) {
        Page<UserExerciseData> allUserExerciseDatas = userExerciseDataRepository.findAll(pageable);
        return new ResponseEntity<>(allUserExerciseDatas, HttpStatus.OK);
    }

    @PostMapping("/")
    @Operation(summary = "Add new userExerciseData in database", description = "The newly created userExerciseData ID will be sent in the body response.")
    @ApiResponse(responseCode = "201", description = "UserExerciseData created successfully")
    @ApiResponse(responseCode = "500", description = "Error creating userExerciseData")
    public ResponseEntity<?> createUserExerciseData(@Valid @RequestBody UserExerciseData userExerciseData) {
        userExerciseData = userExerciseDataRepository.save(userExerciseData);
        return new ResponseEntity<>(userExerciseData.getUserExerciseDataId(), HttpStatus.CREATED);
    }

    @PutMapping("/")
    @Operation(summary = "Update an userExerciseData.")
    @ApiResponse(responseCode = "200", description = "UserExerciseData updated successfully")
    @ApiResponse(responseCode = "500", description = "Error update userExerciseData")
    public ResponseEntity<?> updateUserExerciseData(@RequestBody Map<String, Object> models) {
        UserExerciseDataId  userExerciseDataId = null;
        String userId = null;
        Long exerciseId = null;
        try {
            userId = (String) models.get("userId");
            exerciseId = Long.valueOf((Integer) models.get("exerciseId"));
            Optional<User> userOptional = userRepository.findById(userId);
            Optional<Exercise> exerciseOptional= exerciseRepository.findById(exerciseId);

            if(userOptional.isEmpty()){
                throw new ResourceNotFoundException("User with id " + userId + " not found.");
            }

            if(exerciseOptional.isEmpty()){
                throw new ResourceNotFoundException("Exercise with id " + exerciseId + " not found.");
            }


            userExerciseDataId = new UserExerciseDataId(exerciseOptional.get(), userOptional.get());
        } catch (Exception ignored) {
            throw ignored;
        }
        UserExerciseData userExerciseData = null;
        try {
            userExerciseData = JsonXMLUtils.map2obj((Map<String, Object>)models.get("userExerciseData"), UserExerciseData.class);
        } catch (Exception ignored) {
        }
        assert userExerciseDataId != null;
        verifyUserExerciseData(userExerciseDataId);
        assert userExerciseData != null;
        userExerciseData.setUserExerciseDataId(userExerciseDataId);
        userExerciseData = userExerciseDataRepository.save(userExerciseData);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @Operation(summary = "Delete userExerciseData from id")
    @ApiResponse(responseCode = "200", description = "UserExerciseData deleted successfully")
    @ApiResponse(responseCode = "500", description = "Error delete userExerciseData")
    public ResponseEntity<?> deleteUserExerciseData(@Valid @RequestBody UserExerciseDataId userExerciseDataId) {
        verifyUserExerciseData(userExerciseDataId);
        userExerciseDataRepository.deleteById(userExerciseDataId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
