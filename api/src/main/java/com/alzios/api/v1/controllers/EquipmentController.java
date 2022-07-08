package com.alzios.api.v1.controllers;

import com.alzios.api.domain.Equipment;
import com.alzios.api.exceptions.ResourceNotFoundException;
import com.alzios.api.repositories.EquipmentRepository;
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
@RequestMapping("/v1/equipment")
@Tag(name="Equipments", description = "Equipments api")
public class EquipmentController {

    @Autowired
    private EquipmentRepository equipmentRepository;

    protected Equipment verifyEquipment(Long equipmentId) throws ResourceNotFoundException {
        Optional<Equipment> equipment = equipmentRepository.findById(equipmentId);
        if(!equipment.isPresent()){
            throw new ResourceNotFoundException("Equipment with id " + equipmentId + " not found.");
        }
        return equipment.get();
    }

    @GetMapping("/{equipmentId}")
    @Operation(summary = "Get equipment from id")
    @ApiResponse(responseCode = "200", description = "Equipment found.")
    @ApiResponse(responseCode = "404", description = "Equipment not found.")
    public ResponseEntity<?> getEquipment(@PathVariable Long equipmentId) {
        return new ResponseEntity<>(this.verifyEquipment(equipmentId), HttpStatus.OK);
    }

    @GetMapping("/")
    @Operation(summary = "Get every equipment (work with paging)")
    @ApiResponse(responseCode = "200", description = "Getting successful.")
    public ResponseEntity<Page<Equipment>> getAllEquipments(Pageable pageable) {
        Page<Equipment> allEquipments = equipmentRepository.findAll(pageable);
        return new ResponseEntity<>(allEquipments, HttpStatus.OK);
    }

    @PostMapping("/")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @Operation(summary = "Add new equipment in database", description = "The newly created equipment ID will be sent in the location response.")
    @ApiResponse(responseCode = "201", description = "Equipment created successfully")
    @ApiResponse(responseCode = "500", description = "Error creating equipment")
    public ResponseEntity<URI> createEquipment(@Valid @RequestBody Equipment equipment) {
        equipment = equipmentRepository.save(equipment);

        URI newEquipmentUri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(equipment.getId())
                .toUri();

        return new ResponseEntity<>(newEquipmentUri, HttpStatus.CREATED);
    }

    @PutMapping("/{equipmentId}")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @Operation(summary = "Update an equipment.")
    @ApiResponse(responseCode = "200", description = "Equipment updated successfully")
    @ApiResponse(responseCode = "500", description = "Error update equipment")
    public ResponseEntity<?> updateEquipment(@PathVariable Long equipmentId, @Valid @RequestBody Equipment equipment) {
        verifyEquipment(equipmentId);
        equipment = equipmentRepository.save(equipment);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{equipmentId}")
    @PreAuthorize("hasAuthority('SCOPE_admin')")
    @Operation(summary = "Delete equipment from id")
    @ApiResponse(responseCode = "200", description = "Equipment deleted successfully")
    @ApiResponse(responseCode = "500", description = "Error delete equipment")
    public ResponseEntity<?> deleteEquipment(@PathVariable Long equipmentId) {
        verifyEquipment(equipmentId);
        equipmentRepository.deleteById(equipmentId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
