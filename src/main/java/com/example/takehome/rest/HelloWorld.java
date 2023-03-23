package com.example.takehome.rest;

import com.example.takehome.model.Country;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class HelloWorld
{
    @GetMapping("/")
    List<Country> all()
    {
        return List.of(new Country("CA", "Canada"),
            new Country("US", "United States of America"));
    }
}
