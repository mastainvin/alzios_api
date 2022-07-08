package com.alzios.api.v1.controllers;

import com.alzios.api.services.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/v1/file")
@Tag(name="Files", description = "Files api")
public class FileController {

    @Autowired
    FileService fileService;

    @GetMapping("/{fileName}")
    @Operation(summary = "Get a file.")
    @ApiResponse(responseCode = "200", description = "File found.")
    @ApiResponse(responseCode = "500", description = "File not found.")
    public ResponseEntity<Object> findByName(HttpServletRequest request, @PathVariable String fileName) {
        final String path = request.getServletPath();
        return new ResponseEntity<>(fileService.findByName(fileName), HttpStatus.OK);
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @Operation(summary = "Upload a file.")
    @ApiResponse(responseCode = "200", description = "File uploaded.")
    @ApiResponse(responseCode = "500", description = "File not uploaded.")
    public ResponseEntity<Object> save(@RequestParam("file") MultipartFile multipartFile, @RequestParam("name") String name) {
        fileService.save(multipartFile, name);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/{fileName}")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @Operation(summary = "Delete a file.")
    @ApiResponse(responseCode = "200", description = "File deleted.")
    @ApiResponse(responseCode = "500", description = "File not deleted.")
    public ResponseEntity<Object> delete(@PathVariable String fileName) {
        fileService.delete(fileName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}