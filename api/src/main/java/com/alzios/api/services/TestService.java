package com.alzios.api.services;

import com.alzios.api.dtos.ProgramDto;
import com.alzios.api.dtos.TestDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.alzios.api.repositories.*;
import com.alzios.api.domain.*;

import java.util.Optional;

@Service
public class TestService {

    @Autowired
    private BusinessLogicService businessLogicService;

    @Autowired
    private UserRepository userRepository;

    /*
     * This method is used to test the business logic service.
     * Here we create a program for the user test.
     */
    public void testCreateProgram(TestDto testDto) {
        User userTest = userRepository.findByUsername("test");
        if(userTest == null) {
            userTest = new User();
            userTest.setUsername("test");
            userTest.setEmail("test@test.com");
            userTest.setId("test");
        }

        userTest.setGoal(testDto.getGoal());
        userTest.setMorphology(testDto.getMorphology());
        userTest.setAvailabilities(testDto.getAvailabilities());
        userTest.setEquipments(testDto.getEquipments());
        userRepository.save(userTest);

        businessLogicService.createTraining(userTest.getId());
    }

    /*
     * This method is used to test the business logic service.
     * Here we return the program of the user test.
     */

    public ProgramDto testGetProgram() {
        User userTest = userRepository.findByUsername("test");
        return businessLogicService.getTrainingSuperSetByUserId(userTest.getId());
    }
}
