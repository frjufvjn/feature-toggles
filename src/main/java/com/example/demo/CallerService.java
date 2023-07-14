package com.example.demo;

import com.example.demo.member.MemberServiceGate;
import com.example.demo.name.NameService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CallerService {
    private final NameService nameService;
    private final MemberServiceGate memberServiceGate;

    public CallerService(NameService nameService,
                         MemberServiceGate memberServiceGate) {
        this.nameService = nameService;
        this.memberServiceGate = memberServiceGate;
    }

    public String nameService() {
        return nameService.getNameGate();
    }

    public String memberService() {
        return memberServiceGate.getName();
    }

    public String memberNameService() {
        return memberServiceGate.getMemberName();
    }

    public List<String> getFeatureNames() {
        return memberServiceGate.getFeatureNames();
    }
}
