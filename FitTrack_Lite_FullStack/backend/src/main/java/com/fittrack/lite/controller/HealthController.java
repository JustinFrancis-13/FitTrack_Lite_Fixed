package com.fittrack.lite.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HealthController {
    /** Simple "is the backend up?" check — open http://localhost:8080/ in the browser. */
    @GetMapping("/")
    public Map<String, String> health() {
        return Map.of("status", "UP", "app", "FitTrack Lite API");
    }
}
