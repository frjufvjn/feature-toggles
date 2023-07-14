package com.example.demo.member;

import org.springframework.stereotype.Service;

@Service
public class Version2MemberService implements MemberService {
    @Override
    public String getName() {
        return "NEW-VER-2";
    }

    @Override
    public String getMemberName() {
        return "NEW-VER-2";
    }
}
