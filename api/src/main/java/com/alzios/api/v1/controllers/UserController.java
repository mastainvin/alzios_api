package com.alzios.api.v1.controllers;

import com.alzios.api.domain.User;
import com.alzios.api.exceptions.ResourceNotFoundException;
import com.alzios.api.repositories.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/v1/user")
@Tag(name="Users", description = "Users api")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    protected User verifyUser(String userId) throws ResourceNotFoundException {
        Optional<User> user = userRepository.findById(userId);
        if(!user.isPresent()){
            throw new ResourceNotFoundException("User with id " + userId + " not found.");
        }
        return user.get();
    }

    @GetMapping("/{userId}")
    @PostAuthorize("#userId.equals(#principal.getName())")
    @Operation(summary = "Get user from id")
    @ApiResponse(responseCode = "200", description = "User found.")
    @ApiResponse(responseCode = "404", description = "User not found.")
    public ResponseEntity<?> getUser(@PathVariable String userId, Principal principal) {
        return new ResponseEntity<>(this.verifyUser(userId), HttpStatus.OK);
    }

    @GetMapping("/")
    @Operation(summary = "Get every user (work with paging)")
    @ApiResponse(responseCode = "200", description = "Getting successful.")
    public ResponseEntity<Page<User>> getAllUsers(Pageable pageable) {
        Page<User> allUsers = userRepository.findAll(pageable);
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @PostMapping("/")
    @Operation(summary = "Add new user in database", description = "The newly created user ID will be sent in the location response.")
    @ApiResponse(responseCode = "201", description = "User created successfully")
    @ApiResponse(responseCode = "500", description = "Error creating user")
    public ResponseEntity<URI> createUser(@Valid @RequestBody User user) {
        user = userRepository.save(user);

        URI newUserUri = ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(user.getId())
            .toUri();

        return new ResponseEntity<>(newUserUri, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    @PostAuthorize("#userId.equals(#principal.getName())")
    @Operation(summary = "Update an user.")
    @ApiResponse(responseCode = "200", description = "User updated successfully")
    @ApiResponse(responseCode = "500", description = "Error update user")
    public ResponseEntity<?> updateUser(@PathVariable String userId, @Valid @RequestBody User user, Principal principal) {
        verifyUser(userId);
        user = userRepository.save(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{userId}/updatelevel")
    @PostAuthorize("#userId.equals(#principal.getName())")
    @Operation(summary = "Update level of an user, check the difference between level to know if the level has been incremented.")
    @ApiResponse(responseCode = "200", description = "User updated successfully")
    @ApiResponse(responseCode = "500", description = "Error update user")
    public ResponseEntity<User> updateUserLevel(@PathVariable String userId, Principal principal) {
        User user = verifyUser(userId);
        user.updateLevel();
        user = userRepository.save(user);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @Operation(summary = "Delete user from id")
    @ApiResponse(responseCode = "200", description = "User deleted successfully")
    @ApiResponse(responseCode = "500", description = "Error delete user")
    public ResponseEntity<?> deleteUser(@PathVariable String userId) {
        verifyUser(userId);
        userRepository.deleteById(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
