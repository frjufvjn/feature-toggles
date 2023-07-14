package com.example.demo.feature;

import java.util.concurrent.ConcurrentHashMap;

public class FeatureToggles {
    private static final ConcurrentHashMap<String,String> features = new ConcurrentHashMap<>();

    public FeatureToggles() {
    }

    /**
     * 현재 토글대상기능(feature)의 버전을 반환
     * @param feature 토글대상기능명
     * @return 토글버전
     */
    public static String getActiveVersion(String feature) {
        return features.get(feature);
    }

    /**
     * 토글대상기능(feature)의 버전을 정의/변경한다.
     * @param feature 토글대상기능명
     * @param version 토글버전
     */
    public static void setActiveVersion(String feature, String version) {
        features.put(feature, version);
    }

    /**
     * feature toggle 설정정보 초기화
     */
    public static void clear() {
        features.clear();
    }
}
