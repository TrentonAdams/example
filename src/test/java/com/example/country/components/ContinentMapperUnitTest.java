package com.example.country.components;

import com.example.country.components.db.ContinentMapper;
import com.example.country.components.db.CountryMapper;
import com.example.country.model.Continent;
import com.example.country.model.Country;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ContinentMapperUnitTest
{

    @Test
    void mapsContinentCodeAndName()
    {
        final com.example.db.model.Continent dbContinent =
            new com.example.db.model.Continent("EG", "Example");
        final Continent webContinent = new ContinentMapper(
            new CountryMapper()).apply(dbContinent, List.of("CA", "US", "BR"));
        assertThat(webContinent.getCode()).isEqualTo("EG");
        assertThat(webContinent.getName()).isEqualTo("Example");
    }

    @Test
    void mapsCountries()
    {
        final com.example.db.model.Continent dbContinent =
            new com.example.db.model.Continent("EG", "Example")
                .addCountry(
                    new com.example.db.model.Country("AA", "Some Country"))
                .addCountry(
                    new com.example.db.model.Country("AB", "Some Country B"))
                .done();

        final Continent webContinent = new ContinentMapper(
            new CountryMapper()).apply(dbContinent, List.of("AA", "AB", "CC"));
        assertThat(webContinent.getCode()).isEqualTo("EG");
        assertThat(webContinent.getName()).isEqualTo("Example");
        assertThat(webContinent.getCountries())
            .hasSize(2)
            .contains(new Country("AA", "Some Country"))
            .contains(new Country("AB", "Some Country B"));
        assertThat(webContinent.getMatchedCountries()).isEqualTo(
            List.of("AA", "AB"));
    }

}