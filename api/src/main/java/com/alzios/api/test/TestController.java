package com.alzios.api.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/fill")
public class TestController {
    @Autowired
    FillDatabase fillDatabase;
    @PostMapping
    public ResponseEntity<?> fillDb() {
        fillDatabase.basic();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
