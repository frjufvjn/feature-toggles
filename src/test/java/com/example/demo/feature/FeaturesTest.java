package com.example.demo.feature;

import org.junit.jupiter.api.Test;

import static com.example.demo.feature.Features.check;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FeaturesTest {
    @Test
    void featuresEnumIterateTest() {
        String unknownFeature = "unknown";
        assertThatThrownBy(() -> check(unknownFeature));
    }
}
