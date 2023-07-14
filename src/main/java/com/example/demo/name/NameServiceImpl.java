package com.example.demo.name;

import org.springframework.stereotype.Service;

@Service
public class NameServiceImpl implements NameService {
    @Override
    public String getNameLegacy() {
        return "LEGACY";
    }

    @Override
    public String getNameVersion1() {
        return "NEW-VER-1";
    }

    @Override
    public String getNameVersion2() {
        return "NEW-VER-2";
    }
}
