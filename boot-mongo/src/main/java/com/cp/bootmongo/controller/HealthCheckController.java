package com.cp.bootmongo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health-check")
public class HealthCheckController {

    @GetMapping(value = "/sayhi", produces = "application/json")
    public @ResponseBody String verifyConfig() {
        return " Hi from server.";
    }
}
