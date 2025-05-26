package com.audion;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping("/")
    public String main() {
        return "Hello World";
    }

    @GetMapping("/authTest")
    public String authTest() {
        return "Hello Authentificated World";
    }

}
