package com.example.country.components;

import com.example.country.components.db.CountryMapper;
import com.example.country.model.Country;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CountryMapperUnitTest
{
    @Test
    void mapsCountryCodeAndName()
    {
        final com.example.db.model.Country dbCountry =
            new com.example.db.model.Country("EG", "Example");
        final Country webContinent = new CountryMapper().apply(dbCountry);
        assertThat(webContinent.getCode()).isEqualTo("EG");
        assertThat(webContinent.getName()).isEqualTo("Example");
    }
}