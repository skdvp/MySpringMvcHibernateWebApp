package ru.skdvp.app.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloWorldController {
    @GetMapping("/")
    public String world() {
        return "test/helloworld_controller/helloworld";
    }

}
