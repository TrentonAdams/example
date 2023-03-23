package com.example.country.components.db;

import com.example.country.model.Country;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class CountryMapper
    implements Function<com.example.db.model.Country, Country>
{
    public Country apply(com.example.db.model.Country country)
    {
        return new Country(country.getCode(), country.getName());
    }
}