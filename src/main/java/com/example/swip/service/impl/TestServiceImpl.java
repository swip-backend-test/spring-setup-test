package com.example.swip.service.impl;

import com.example.swip.service.TestService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TestServiceImpl implements TestService {
    @Override
    public Map<String, Object> getTestData() {
        Map<String, Object> testData = new HashMap<>();
        testData.put("label1", "test1");
        testData.put("label2", "test2");
        return testData;
    }
}
