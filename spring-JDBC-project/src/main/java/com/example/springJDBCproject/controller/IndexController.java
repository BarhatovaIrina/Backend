package com.example.springJDBCproject.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {
    @GetMapping("/")
    public String hello() {
        return String.format("index controllerhasdkjah kjsh fkjhfkjs!");
    }
}
