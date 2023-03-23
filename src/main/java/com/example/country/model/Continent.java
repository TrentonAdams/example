package com.example.country.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

// TODO: 2023-03-26 tag:fluent support fluent interface pattern here too to disable further modifications.
@ToString
@EqualsAndHashCode
@Getter
@NoArgsConstructor
public class Continent
{
    private String code;
    private String name;
    /**
     * The countries that were input for the search, which match the continent.
     */
    private List<String> matchedCountries;
    private List<Country> countries = new ArrayList<>();

    public Continent(final String code, final String name,
        List<String> matchedCountries)
    {
        this.code = code;
        this.name = name;
        this.matchedCountries = matchedCountries;
    }

    public void addCountries(final Country... countries)
    {
        // clone all countries.
        this.countries = Arrays
            .stream(countries)
            .map(country -> new Country(country.getCode(), country.getName()))
            .toList();
    }

    public boolean hasCountryCode(final String code)
    {
        Objects.requireNonNull(code, "country code MUST NOT be null");
        return countries
            .stream()
            .anyMatch(country -> code.equals(country.getCode()));
    }

    public void addCountry(final Country country)
    {
        countries.add(country);
    }
}
