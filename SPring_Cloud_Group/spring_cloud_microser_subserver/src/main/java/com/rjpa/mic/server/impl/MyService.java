package com.rjpa.mic.server.impl;

import org.springframework.stereotype.Service;

@Service
public class MyService {

    public String secure() {
        return "Hello Security";
    }
}