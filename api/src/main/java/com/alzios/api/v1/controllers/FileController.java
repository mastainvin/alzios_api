package com.alzios.api.v1.controllers;

import com.alzios.api.domain.Exercise;
import com.alzios.api.domain.File;
import com.alzios.api.dtos.FileResponse;
import com.alzios.api.exceptions.ResourceNotFoundException;
import com.alzios.api.exceptions.UploadFailException;
import com.alzios.api.services.FileStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@Tag(name="File", description = "File API")
@RequestMapping("/v1/file")
public class FileController {
    @Autowired
    FileStorageService fileStorageService;

    protected File verifyFile(Long fileId) throws ResourceNotFoundException {
        File file = fileStorageService.getFile(fileId);
        return file;
    }
    @GetMapping("/{fileId}")
    @Operation(summary = "Get a file from an Id")
    @ApiResponse(responseCode = "200", description = "File found.")
    @ApiResponse(responseCode = "404", description = "File not found.")
    public ResponseEntity<File> getFileById(@PathVariable("fileId") Long fileId) {
        File file = verifyFile(fileId);
        HttpHeaders headersResponse = new HttpHeaders();
        ContentDisposition contentDisposition = ContentDisposition.builder("inline")
                .filename(file.getName())
                .build();
        headersResponse.setContentDisposition(contentDisposition);

        return new ResponseEntity<>(file, headersResponse, HttpStatus.OK);
    }

    @GetMapping("/exercise/{exerciseId}/picture")
    public ResponseEntity<File> getPictureByExerciseId(@PathVariable Long exerciseId) {
        Optional<File> picture = fileStorageService.getPictureByExerciseId(exerciseId);
        if(!picture.isPresent()) {
            throw new ResourceNotFoundException("Exercise with id : " + exerciseId + " has no picture");
        }
        return new ResponseEntity<>(picture.get(),HttpStatus.OK);
    }

    @GetMapping("/exercise/{exerciseId}/video")
    public ResponseEntity<File> getVideoByExerciseId(@PathVariable Long exerciseId) {
        Optional<File> video = fileStorageService.getVideoByExerciseId(exerciseId);
        if(!video.isPresent()) {
            throw new ResourceNotFoundException("Exercise with id : " + exerciseId + " has no video");
        }
        return new ResponseEntity<>(video.get(),HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Get every files (work with paging)")
    @ApiResponse(responseCode = "200", description = "Getting successful.")
    public ResponseEntity<List<FileResponse>> getAllFiles() {
        List<FileResponse> files = fileStorageService.getAllFiles().map(file -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(file.getId().toString())
                    .toUriString();
            return new FileResponse(
                    file.getName(),
                    fileDownloadUri,
                    file.getType(),
                    file.getData().length);
        }).collect(Collectors.toList());
        return new ResponseEntity<>(files, HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @Operation(summary = "Add new files in database",description = "The newly created file ID will be sent in the location response.")
    @ApiResponse(responseCode = "201", description = "Exercise created successfully")
    @ApiResponse(responseCode = "417", description = "Error creating exercise")
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        File fileStored = null;
        try {
            fileStored = fileStorageService.store(file);
        } catch (Exception e) {
            throw new UploadFailException("Could not upload the file: " + file.getOriginalFilename() + "!");
        }

        // Set the location in the header
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newExerciseUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(fileStored.getId())
                .toUri();
        responseHeaders.setLocation(newExerciseUri);

        return new ResponseEntity<>(responseHeaders,HttpStatus.OK);
    }

    @PutMapping("/{fileId}")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @Operation(summary = "Update a file.")
    @ApiResponse(responseCode = "200", description = "File updated successfully")
    @ApiResponse(responseCode = "500", description = "Error update file")
    public ResponseEntity<?> updateExercise(@PathVariable Long fileId, @Valid @RequestBody MultipartFile file) {
        verifyFile(fileId);
        File fileUpdated = null;
        try {
            fileUpdated = fileStorageService.store(file);
        } catch (Exception e) {
            throw new UploadFailException("Could not update the file: " + file.getOriginalFilename() + "!");
        }
        // Set the location in the header
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newExerciseUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(fileUpdated.getId())
                .toUri();
        responseHeaders.setLocation(newExerciseUri);

        return new ResponseEntity<>(responseHeaders,HttpStatus.OK);
    }

    @DeleteMapping("/{fileId}")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @Operation(summary = "Delete file from id")
    @ApiResponse(responseCode = "200", description = "File deleted successfully")
    @ApiResponse(responseCode = "500", description = "Error delete file")
    public ResponseEntity<?> deleteExercise(@PathVariable Long fileId) {
        verifyFile(fileId);
        fileStorageService.removeFile(fileId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
