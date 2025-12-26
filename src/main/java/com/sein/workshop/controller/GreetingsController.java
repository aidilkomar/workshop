package com.sein.workshop.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/Greetings")
public class GreetingsController {

    @GetMapping("/")
    public GreetingResponse greeting() {
        GreetingResponse greetingResponse = new GreetingResponse(
                "Hello",
                List.of("Java", "C#"),
                new Person("Sein", 1, 100000000)
        );
        return greetingResponse;
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
