package com.example.demo.web;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Component
public class HelloController {
    @RequestMapping("/hello")
    public String index() {
        return "LP,you are amazing";
    }
}
