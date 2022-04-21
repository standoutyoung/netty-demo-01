package com.aha.netty.discardserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @GetMapping("sayHello/{arg}")
    public String sayHello(@PathVariable String arg) {
        return "hello," + arg;
    }
}
