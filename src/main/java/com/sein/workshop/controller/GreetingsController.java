package com.sein.workshop.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/greetings")
public class GreetingsController {

    @GetMapping("/")
    @PreAuthorize("@authz.hasAnyPermission('USER_READ', 'ADMIN_READ')")
    public GreetingResponse greeting() {
        GreetingResponse greetingResponse = new GreetingResponse(
                "Hello",
                List.of("Java", "C#"),
                new Person("Sein", 1, 10000000)
        );
        return greetingResponse;
    }

    @GetMapping("/unauthorize")
    @PreAuthorize("@authz.hasPermission('ADMIN_READ')")
    public Error error() {
        return new Error("This unauthorized test");
    }

    record Error(String message){
    }

    record Person(String name, int age, double savings){
    }

    record GreetingResponse(
            String greeting,
            List<String> favProgrammingLanguages,
            Person person
    ) {
    }
}
