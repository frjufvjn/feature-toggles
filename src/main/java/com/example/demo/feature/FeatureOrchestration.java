package com.example.demo.feature;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class FeatureOrchestration {

    /**
     * [Feature Toggles] 인스턴스 컴포지션
     * <pre>
     * Gate기능을 수행하는 클래스에서 토글 대상 인스턴스들의 컴포지션을 수행한다.
     * 토글대상 클래스의 명칭별로 클래스의 인스턴스를 맵으로 관리할 수 있도록 해준다.
     * </pre>
     * <pre>
     * - 'default'인스턴스를 지정하는 방법 제공
     *   Service, Component 애노테이션과 더불어 @FeatureOption 애노테이션을 추가한다.
     *   feature의 인스턴스들 중에 default인스턴스를 1개 지정 필요, 그렇지 않다면 예외 발생함.
     *   {@code @FeatureOption(defaultFeature = true)}
     *
     * - feature 버전 식별이름은 기본적으로 클래스명으로 제공되나 별도로 이름을 지정할 수 있다.
     *   {@code @FeatureOption(name = "member-v2")}
     * </pre>
     */
    public static <T> Map<String, T> compose(final Set<T> instances) {
        checkDefaultInstance(instances);
        return instances.stream()
                .collect(Collectors.toMap(
                        FeatureOrchestration::getInstanceKey,
                        instance -> instance));
    }

    /**
     * [Feature Toggles] 현재 토글대상 인스턴스 반환
     * <pre>
     * 설정에 의해 정의된 혹은 런타임중 변경된 토글된 대상 인스턴스를 반환한다.
     * 런타임중에 변동이 될 수 있으므로 생성자에서 호출하지 않는다.
     * </pre>
     * @param feature 토글 대상 기능
     * @param instances 인스턴스 맵
     * @return 인스턴스
     * @param <T>
     */
    public static <T> T getInstance(final String feature, Map<String, T> instances) {
        return Optional.ofNullable(instances.get(FeatureToggles.getActiveVersion(feature)))
                .orElseGet(() -> getDefaultInstance(instances));
    }

    public static <T> List<String> getFeatureNamesInInstances(Map<String, T> instances) {
        return instances.keySet().stream()
                .toList();
    }

    /**
     * Feature 인스턴스들 중에 디폴트로 정의되어 있는 인스턴스가 있는지 체크
     */
    private static <T> void checkDefaultInstance(Set<T> instances) {
        long defaultCount = instances.stream()
                .filter(FeatureOrchestration::hasDefaultInstance)
                .count();
        if (defaultCount != 1) {
            throw new FeatureTogglesException("[feature-toggles] 해당 feature에 디폴트로 정의된 인스턴스 1개가 필요합니다.");
        }
    }

    /**
     * 디폴트로 정의된 인스턴스인지 확인
     */
    private static <T> boolean hasDefaultInstance(T instance) {
        return Optional.ofNullable(instance.getClass().getAnnotation(FeatureOption.class))
                .map(FeatureOption::defaultFeature)
                .orElse(false);
    }

    /**
     * 인스턴스를 가져오기 위한 key를 규칙에 따라 반환
     */
    private static <T> String getInstanceKey(T instance) {
        FeatureOption featureOption = instance.getClass().getAnnotation(FeatureOption.class);
        if (featureOption != null) {
            // @FeatureOption 애노테이션의 name이 정의되어 있으면 name으로 정의
            String featureName = StringUtils.isBlank(featureOption.name()) ? instance.getClass().getSimpleName() : featureOption.name();
            // @FeatureOption 애노테이션의 defaultFeature가 true이면 'default'로 정의
            if (featureOption.defaultFeature()) {
                featureName = Feature.DEFAULT;
            }
            return featureName;
        }
        return instance.getClass().getSimpleName();
    }

    /**
     * 디폴트 인스턴스 반환
     */
    private static <T> T getDefaultInstance(Map<String, T> instances) {
        String defaultKey = instances.keySet().stream()
                .filter(Feature.DEFAULT::equals)
                .findFirst()
                .orElseThrow(() -> new FeatureTogglesException("[feature-toggles] feature에 해당되는 인스턴스를 찾을 수 없습니다."));
        log.warn("[feature-toggles] 지정한 feature에 해당되는 인스턴스를 찾을 수 없어서 디폴트로 정의된 인스턴스를 반환합니다.");
        return instances.get(defaultKey);
    }
}
