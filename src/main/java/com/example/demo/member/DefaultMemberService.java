package com.example.demo.member;

import com.example.demo.feature.FeatureOption;
import org.springframework.stereotype.Service;

@FeatureOption(defaultFeature = true)
@Service
public class DefaultMemberService implements MemberService {
    @Override
    public String getName() {
        return "default-version";
    }

    @Override
    public String getMemberName() {
        return "default-version";
    }
}
