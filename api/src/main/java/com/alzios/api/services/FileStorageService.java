package com.alzios.api.services;

import com.alzios.api.domain.File;
import com.alzios.api.exceptions.FileFormatException;
import com.alzios.api.exceptions.ResourceNotFoundException;
import com.alzios.api.repositories.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class FileStorageService {
    @Autowired
    private FileRepository fileRepository;
    public File store(MultipartFile file) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        File File = new File(fileName, file.getContentType(), file.getBytes());
        return fileRepository.save(File);
    }
    public File getFile(Long id) {
        Optional<File> file = fileRepository.findById(id);
        if(!file.isPresent()) {
            throw new ResourceNotFoundException("File with id " + id + " not found.");
        }
        return file.get();
    }

    public Stream<File> getAllFiles() {
        return fileRepository.findAll().stream();
    }


    public void removeFile(Long fileId) {
        fileRepository.deleteById(fileId);
    }

    public Optional<File> getPictureByExerciseId(Long exerciseId) {
        File file = fileRepository.findPictureByExerciseId(exerciseId);
        Optional<File> picture = Optional.empty();
        if(file != null) {
            picture = Optional.ofNullable(file);
        }
        return picture;
    }

    public Optional<File> getVideoByExerciseId(Long exerciseId) {
        File file = fileRepository.findVideoByExerciseId(exerciseId);
        Optional<File> video = Optional.empty();
        if(file != null) {
            video = Optional.ofNullable(file);
        }
        return video;
    }

    public void detachFileFromExercise(File file){
        file.setExercise(null);
        fileRepository.save(file);
    }

    public void detachPictureFromExerciseId(Long exerciseId){
        fileRepository.detachPictureByExerciseId(exerciseId);
    }

    public void detachVideoFromExerciseId(Long exerciseId){
        fileRepository.detachVideoByExerciseId(exerciseId);
    }

}
