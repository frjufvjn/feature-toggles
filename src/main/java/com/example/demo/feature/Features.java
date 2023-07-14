package com.example.demo.feature;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum Features {
    MEMBER_SERVICE("member-service", "전략패턴을 사용한 feature toggles 멤버서비스"),
    NAME_SERVICE("name-service", "interface default 메서드를 사용한 이름서비스");

    private final String key;
    private final String desc;

    public static void check(String feature) {
        Arrays.stream(Features.values())
                .filter(f -> feature.equals(f.getKey()))
                .findFirst()
                .orElseThrow(() -> new FeatureTogglesException("[feature-toggles] 정의되어 있지 않은 feature name입니다. Features Enum에 정의해주십시오."));
    }
}
