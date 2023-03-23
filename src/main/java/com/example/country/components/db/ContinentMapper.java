package com.example.country.components.db;

import com.example.country.model.Continent;
import com.example.db.model.Country;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.BiFunction;

@Component
public class ContinentMapper implements
    BiFunction<com.example.db.model.Continent, List<String>, Continent>
{
    final CountryMapper countryMapper;

    public ContinentMapper(final CountryMapper countryMapper)
    {
        this.countryMapper = countryMapper;
    }

    public Continent apply(com.example.db.model.Continent input,
        List<String> inputCountries)
    {

        final Continent continent = new Continent(input.getCode(),
            input.getName(), input
            .getCountries()
            .stream()
            .map(Country::getCode)
            .filter(inputCountries::contains)
            .toList());
        input
            .getCountries()
            .stream()
            .map(countryMapper)
            .forEach(continent::addCountry);
        return continent;
    }
}