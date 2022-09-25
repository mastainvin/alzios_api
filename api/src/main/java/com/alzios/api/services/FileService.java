package com.alzios.api.services;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

@Service
public class FileService {

    private static final Logger LOG = LoggerFactory.getLogger(FileService.class);

    @Autowired
    private AmazonS3 amazonS3;

    @Value("alziosappfiles")
    private String s3BucketName;

    private File convertMultiPartFileToFile(final MultipartFile multipartFile) {
        final File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try (final FileOutputStream outputStream = new FileOutputStream(file)) {
            outputStream.write(multipartFile.getBytes());
        } catch (IOException e) {
            LOG.error("Error {} occurred while converting the multipart file", e.getLocalizedMessage());
        }
        return file;
    }

    // @Async annotation ensures that the method is executed in a different thread
    private String generateUrl(String fileName, HttpMethod get) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 1); // Generated URL will be valid for 24 hours
        return amazonS3.generatePresignedUrl(s3BucketName, fileName, calendar.getTime(), HttpMethod.GET).toString();
    }

    @Async
    public String findByName(String fileName) {
        if (!amazonS3.doesObjectExist(s3BucketName, fileName))
            return "File does not exist";
        LOG.info("Generating signed URL for file name {}", fileName);
        return generateUrl(fileName, HttpMethod.GET);
    }


    @Async
    public void delete(String fileName) {
        LOG.info("Deleting file with name {}", fileName);
        amazonS3.deleteObject(s3BucketName, fileName);
    }

    @Async
    public void save(final MultipartFile multipartFile, String fileName) {
        try {
            final File file = convertMultiPartFileToFile(multipartFile);
            LOG.info("Uploading file with name {}", fileName);
            final PutObjectRequest putObjectRequest = new PutObjectRequest(s3BucketName, fileName, file);
            amazonS3.putObject(putObjectRequest);
            Files.delete(file.toPath()); // Remove the file locally created in the project folder
        } catch (AmazonServiceException e) {
            LOG.error("Error {} occurred while uploading file", e.getLocalizedMessage());
        } catch (IOException ex) {
            LOG.error("Error {} occurred while deleting temporary file", ex.getLocalizedMessage());
        }
    }

}