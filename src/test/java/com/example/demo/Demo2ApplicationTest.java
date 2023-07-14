package com.example.demo;

import com.example.demo.feature.FeatureToggles;
import com.example.demo.feature.Features;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Demo2ApplicationTest {
    @Autowired
    private CallerService callerService;

    @Test
    void notFoundToggleInstanceTest() {
        FeatureToggles.setActiveVersion(Features.MEMBER_SERVICE.getKey(), "unknownVersion");
        var result = callerService.memberService();
        Assertions.assertThat(result).isEqualTo("default-version");
    }
}
