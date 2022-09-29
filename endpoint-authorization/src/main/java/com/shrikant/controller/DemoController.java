package com.shrikant.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/hello")
    public String demo(){
        return "hello";
    }

    @GetMapping("/demo")
    public String hello(){
        return "hello";
    }
}
