package com.example.demo.member;

import com.example.demo.feature.FeatureOrchestration;
import com.example.demo.feature.Features;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class MemberServiceGate {
    private final Map<String, MemberService> memberServices;

    public MemberServiceGate(Set<MemberService> memberServices) {
        this.memberServices = FeatureOrchestration.compose(memberServices);
    }

    private MemberService getActiveInstance() {
        return FeatureOrchestration.getInstance(Features.MEMBER_SERVICE.getKey(), memberServices);
    }

    public List<String> getFeatureNames() {
        return FeatureOrchestration.getFeatureNamesInInstances(memberServices);
    }

    public String getName() {
        return getActiveInstance().getName();
    }

    public String getMemberName() {
        return getActiveInstance().getMemberName();
    }
}
