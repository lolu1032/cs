package com.example.cs.twoWeek.Singleton;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
//@Scope("prototype")
@Scope("session")
public class TestController {

    private final DemoBean demo;

    @GetMapping("/test")
    public String test() {
        return demo.getId();
    }
}