package com.example.demo.member;

import com.example.demo.feature.FeatureOption;
import org.springframework.stereotype.Service;

@FeatureOption(name = "legacy")
@Service
public class LegacyMemberService implements MemberService {
    @Override
    public String getName() {
        return "LEGACY";
    }

    @Override
    public String getMemberName() {
        return "LEGACY";
    }
}
