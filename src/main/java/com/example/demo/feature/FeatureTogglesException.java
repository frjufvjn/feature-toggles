package com.example.demo.feature;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeatureTogglesException extends RuntimeException {
    private final String message;

    public FeatureTogglesException(String message) {
        this.message = message;
        log.error(message);
    }
}
