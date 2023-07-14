package com.example.demo;

import com.example.demo.feature.FeatureToggles;
import com.example.demo.feature.Features;
import com.example.demo.member.LegacyMemberService;
import com.example.demo.member.Version1MemberService;
import com.example.demo.member.Version2MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.type.AnnotatedTypeMetadata;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class Demo2Application {

    private final CallerService callerService;

    public static void main(String[] args) {
        SpringApplication.run(Demo2Application.class, args);
    }

    /**
     * 애플리케이션 준비 완료 후 실행될 로직을 작성합니다.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady(ApplicationReadyEvent event) {
        log.info("Application is ready!\n");

        log.info("##################################################################");
        log.info("- 인터페이스의 default메소드를 통한 토글 예시");
        log.info("> {}", callerService.nameService());

        log.info("change feature version 1");
        FeatureToggles.setActiveVersion(Features.NAME_SERVICE.getKey(), "version1");
        log.info("> {}", callerService.nameService());

        log.info("change feature version 2");
        FeatureToggles.setActiveVersion(Features.NAME_SERVICE.getKey(), "version2");
        log.info("> {}", callerService.nameService());
        log.info("##################################################################");

        log.info("##################################################################");
        log.info("- Strategy Pattern을 사용한 예시 - 클래스명으로 버전을 부여");
        FeatureToggles.clear();

        log.info("callerService.getFeatureNames() : {}", callerService.getFeatureNames().toString());

        log.info("--------------------------------------------------------");
        log.info("featrue에 대한 버전이 정의되지 않았을때 디폴트 인스턴스로 반환");
        log.info("> {}", callerService.memberService());
        log.info("> {}", callerService.memberNameService());
        log.info("--------------------------------------------------------");

        log.info("@FeatureOption에 name을 지정하였을때 name=legacy");
        FeatureToggles.setActiveVersion(Features.MEMBER_SERVICE.getKey(), "legacy");
        log.info("> {}", callerService.memberService());
        log.info("> {}", callerService.memberNameService());

        log.info("change feature version 1");
        FeatureToggles.setActiveVersion(Features.MEMBER_SERVICE.getKey(), Version1MemberService.class.getSimpleName());
        log.info("> {}", callerService.memberService());
        log.info("> {}", callerService.memberNameService());

        log.info("change feature version 2");
        FeatureToggles.setActiveVersion(Features.MEMBER_SERVICE.getKey(), Version2MemberService.class.getSimpleName());
        log.info("> {}", callerService.memberService());
        log.info("> {}", callerService.memberNameService());
        log.info("##################################################################");
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public static class MyCustomCondition implements Condition {
        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            return false;
        }
    }

    @Configuration
    public class MyConfiguration {

        @Bean
        public MyBeanPostProcessor myBeanPostProcessor() {
            return new MyBeanPostProcessor();
        }
    }

    public class MyBeanPostProcessor implements BeanPostProcessor {

        @Override
        public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
            log.info("before -> {}", beanName);
            // 빈 초기화 전에 실행되는 로직 작성
            // 필요한 경우 빈을 변경하거나 다른 빈을 추가로 등록할 수 있습니다.
            return bean;
        }

        @Override
        public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
            log.info("after -> {}", beanName);
            // 빈 초기화 후에 실행되는 로직 작성
            // 필요한 경우 빈을 변경하거나 다른 빈을 추가로 등록할 수 있습니다.
            return bean;
        }
    }
}
