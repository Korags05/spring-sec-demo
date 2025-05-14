package org.kunal.spring_sec_demo.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("hello")
    public String greet(HttpServletRequest httpServletRequest) {
        return "Hello World ";
    }

    @GetMapping("about")
    public String about(HttpServletRequest httpServletRequest) {
        return "<h1>Kunal</h1> "+httpServletRequest.getSession().getId();
    }

}
