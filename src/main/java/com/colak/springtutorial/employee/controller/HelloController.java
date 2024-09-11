package com.colak.springtutorial.employee.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/hello")
@CacheConfig(cacheNames = "studentCache")

@Slf4j
public class HelloController {

    // http://localhost:8080/api/hello/findById/1
    @GetMapping(path = "/findById/{id}")
    // Setting sync to true means that consecutive hits which happened before the cache was properly populated,
    // will wait for the cache to actually be populated, instead of performing another request to the database.
    @Cacheable(key = "#id", condition = "#id != null", sync = true, cacheManager = "springCacheManager")
    public String findById(@PathVariable Long id) {
        log.info("findById is called with : {}", id);
        return "Hello " + id;
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleNoSuchElementException(Exception exception) {
        // Return 404
        return ResponseEntity.notFound().build();
    }
}
