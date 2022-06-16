package com.alzios.api.services;

import com.alzios.api.domain.Exercise;
import com.alzios.api.domain.File;
import com.alzios.api.exceptions.FileFormatException;
import com.alzios.api.exceptions.ResourceNotFoundException;
import com.alzios.api.repositories.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ExerciseFilesService {
    @Autowired
    ExerciseRepository exerciseRepository;

    @Autowired
    FileStorageService fileStorageService;

    protected Exercise verifyExercise(Long exerciseId) throws ResourceNotFoundException {
        Optional<Exercise> exercise = exerciseRepository.findById(exerciseId);
        if(!exercise.isPresent()){
            throw new ResourceNotFoundException("Exercise with id " + exerciseId + " not found.");
        }
        return exercise.get();
    }
    public Exercise attach(Long exerciseId, Long fileId) {
        Exercise exercise = verifyExercise(exerciseId);
        File file = fileStorageService.getFile(fileId);

        if(file.getType().equals(File.TYPE_PICTURE)){
            fileStorageService.detachPictureFromExerciseId(exerciseId);
        } else if(file.getType().equals(File.TYPE_VIDEO)){
            fileStorageService.detachVideoFromExerciseId(exerciseId);
        } else {
            throw new FileFormatException(file.getType() + " format not supported");
        }
        file.setExercise(exercise);
        exerciseRepository.save(exercise);

        return exercise;
    }

}

