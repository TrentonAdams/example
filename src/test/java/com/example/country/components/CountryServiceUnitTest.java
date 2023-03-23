package com.example.country.components;

import com.example.application.TakehomeApplication;
import com.example.country.model.Continent;
import com.example.country.model.Country;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: 2023-03-26 tag:test supposed to be a unit test, convert to unit test
//  with spock mocking
@SpringBootTest(classes = TakehomeApplication.class)
class CountryServiceUnitTest
{
    @Autowired
    CountryService countryService;

    @Test
    void retrievesNorthAmericaForCA()
    {
        final List<Continent> continents =
            countryService.getContinentsByCountryCodes(List.of("CA"));
        assertThat(continents)
            .isNotNull()
            .hasSize(1);
        final Continent continent = continents.get(0);
        assertThat(continent.getCode()).isEqualTo("NA");
        assertThat(continent.getName()).isEqualTo("North America");
        assertThat(continent.getCountries())
            .isNotNull()
            .hasSizeGreaterThan(1)
            .contains(new Country("CA", "Canada"));
    }
}