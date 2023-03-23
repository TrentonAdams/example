package com.example.country.components;

import com.example.country.components.db.ContinentMapper;
import com.example.country.model.Continent;
import com.example.db.components.CountryProvider;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CountryService
{
    private final CountryProvider countryProvider;
    private final ContinentMapper continentMapper;


    public CountryService(CountryProvider countryProvider,
        ContinentMapper continentMapper)
    {
        this.countryProvider = countryProvider;
        this.continentMapper = continentMapper;
    }

    public List<Continent> getContinentsByCountryCodes(
        List<String> countryCodes)
    {
        return countryProvider
            .queryContinentsByCountryCode(countryCodes)
            .stream()
            .map(c -> continentMapper.apply(c, countryCodes))
            .toList();
    }
}
