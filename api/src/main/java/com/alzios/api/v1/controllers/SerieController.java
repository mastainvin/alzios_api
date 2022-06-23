package com.alzios.api.v1.controllers;

import com.alzios.api.domain.Serie;
import com.alzios.api.exceptions.ResourceNotFoundException;
import com.alzios.api.repositories.SerieRepository;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/serie")
@Tag(name="Series", description = "Series api")
public class SerieController {

    @Autowired
    private SerieRepository serieRepository;

    protected Serie verifySerie(Long serieId) throws ResourceNotFoundException {
        Optional<Serie> serie = serieRepository.findById(serieId);
        if(!serie.isPresent()){
            throw new ResourceNotFoundException("Serie with id " + serieId + " not found.");
        }
        return serie.get();
    }

    @GetMapping("/{serieId}")
    @Operation(summary = "Get serie from id")
    @ApiResponse(responseCode = "200", description = "Serie found.")
    @ApiResponse(responseCode = "404", description = "Serie not found.")
    public ResponseEntity<?> getSerie(@PathVariable Long serieId) {
        return new ResponseEntity<>(this.verifySerie(serieId), HttpStatus.OK);
    }

    @GetMapping("/")
    @Operation(summary = "Get every serie (work with paging)")
    @ApiResponse(responseCode = "200", description = "Getting successful.")
    public ResponseEntity<Page<Serie>> getAllSeries(Pageable pageable) {
        Page<Serie> allSeries = serieRepository.findAll(pageable);
        return new ResponseEntity<>(allSeries, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    @PostAuthorize("#userId.equals(#principal.getName())")
    @Operation(summary = "Get every serie of an user(work with paging)")
    @ApiResponse(responseCode = "200", description = "Getting successful.")
    public ResponseEntity<List<Serie>> getAllUserSeries(@PathVariable String userId, Principal principal) {
        List<Serie> allSeries = serieRepository.findByUserId(userId);
        return new ResponseEntity<>(allSeries, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/week")
    @PostAuthorize("#userId.equals(#principal.getName())")
    @Operation(summary = "Get every serie of an user in the actual week.")
    @ApiResponse(responseCode = "200", description = "Getting successful.")
    public ResponseEntity<List<Serie>> getUserWeekSeries(@PathVariable String userId, Principal principal) {
        List<Serie> allSeries = serieRepository.findActualWeekByUserId(userId);
        return new ResponseEntity<>(allSeries, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/next")
    @PostAuthorize("#userId.equals(#principal.getName())")
    @Operation(summary = "Get every serie of an user for the next training.")
    @ApiResponse(responseCode = "200", description = "Getting successful.")
    public ResponseEntity<List<Serie>> getUserNextTraining(@PathVariable String userId, Principal principal) {
        List<Serie> allSeries = serieRepository.findNextTrainingByUserId(userId);
        return new ResponseEntity<>(allSeries, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/best/{exerciseId}")
    @PostAuthorize("#userId.equals(#principal.getName())")
    @Operation(summary = "Get best serie of an user for an exercise.")
    @ApiResponse(responseCode = "200", description = "Getting successful.")
    public ResponseEntity<List<Serie>> getUserBestExerciseSeries(@PathVariable String userId, @PathVariable Long exerciseId, Principal principal) {
        List<Serie> allSeries = serieRepository.findPreviousBestSeries(userId,exerciseId);
        return new ResponseEntity<>(allSeries, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/date")
    @PostAuthorize("#userId.equals(#principal.getName())")
    @Operation(summary = "Get previous trainings dates.")
    @ApiResponse(responseCode = "200", description = "Getting successful.")
    public ResponseEntity<List<String>> getUserBestExerciseSeries(@PathVariable String userId, Principal principal) {
        List<String> allSeries = serieRepository.findPreviousTrainingDates(userId);
        return new ResponseEntity<>(allSeries, HttpStatus.OK);
    }
    @GetMapping("/user/{userId}/date/{dateStr}")
    @PostAuthorize("#userId.equals(#principal.getName())")
    @Operation(summary = "Get previous trainings dates.")
    @ApiResponse(responseCode = "200", description = "Getting successful.")
    public ResponseEntity<List<Serie>> getUserBestExerciseSeries(@PathVariable String userId,@PathVariable String dateStr, Principal principal) {
        List<Serie> allSeries = serieRepository.findSeriesByDate(userId, dateStr);
        return new ResponseEntity<>(allSeries, HttpStatus.OK);
    }


    @PostMapping("/")
    @Operation(summary = "Add new serie in database", description = "The newly created serie ID will be sent in the location response.")
    @ApiResponse(responseCode = "201", description = "Serie created successfully")
    @ApiResponse(responseCode = "500", description = "Error creating serie")
    public ResponseEntity<?> createSerie(@Valid @RequestBody Serie serie) {
        serie = serieRepository.save(serie);

        // Set the location in the header
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newSerieUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(serie.getId())
                .toUri();
        responseHeaders.setLocation(newSerieUri);

        return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{serieId}")
    @Operation(summary = "Update an serie.")
    @ApiResponse(responseCode = "200", description = "Serie updated successfully")
    @ApiResponse(responseCode = "500", description = "Error update serie")
    public ResponseEntity<?> updateSerie(@PathVariable Long serieId, @Valid @RequestBody Serie serie) {
        verifySerie(serieId);
        serie = serieRepository.save(serie);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/user/{userId}/finish")
    @PostAuthorize("#userId.equals(#principal.getName())")
    @Operation(summary = "Finish every series of an user.")
    @ApiResponse(responseCode = "200", description = "Serie updated successfully")
    @ApiResponse(responseCode = "500", description = "Error update serie")
    public ResponseEntity<?> finishEveryUserSeries(@PathVariable String userId, Principal principal) {
        serieRepository.finishAllByUserId(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{serieId}")
    @Operation(summary = "Delete serie from id")
    @ApiResponse(responseCode = "200", description = "Serie deleted successfully")
    @ApiResponse(responseCode = "500", description = "Error delete serie")
    public ResponseEntity<?> deleteSerie(@PathVariable Long serieId) {
        verifySerie(serieId);
        serieRepository.deleteById(serieId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/user/{userId}/clear")
    @PostAuthorize("#userId.equals(#principal.getName())")
    @Operation(summary = "Delete series that user didn't trains.")
    @ApiResponse(responseCode = "200", description = "Serie deleted successfully")
    @ApiResponse(responseCode = "500", description = "Error delete serie")
    public ResponseEntity<?> deleteUserSeriesNotDone(@PathVariable String userId, Principal principal) {
        serieRepository.deleteNotDoneByUserId(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
