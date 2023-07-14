package com.example.demo.name;

import com.example.demo.feature.FeatureToggles;
import com.example.demo.feature.Features;

public interface NameService {
    String getNameLegacy();
    String getNameVersion1();
    String getNameVersion2();
    default String getNameGate() {
        if ("version1".equals(FeatureToggles.getActiveVersion(Features.NAME_SERVICE.getKey()))) {
            return getNameVersion1();
        } else if ("version2".equals(FeatureToggles.getActiveVersion(Features.NAME_SERVICE.getKey()))) {
            return getNameVersion2();
        } else {
            return getNameLegacy();
        }
    }
}
