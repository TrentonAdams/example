package com.example.country.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class ContinentUnitTest
{

    @BeforeEach
    void setUp()
    {
        final Continent continent = new Continent("NA", "North America",
            List.of("CA", "US"));
        continent.addCountries(new Country("CA", "Canada"),
            new Country("US", "United States of America"),
            new Country("MX", "Mexico"));
    }

    @Test
    void hasCountryCodeIsTrueForCountryInContinent()
    {
        final Continent continent = new Continent("NA", "North America",
            List.of("CA", "US"));
        continent.addCountries(new Country("CA", "Canada"),
            new Country("US", "United States of America"),
            new Country("MX", "Mexico"));
        assertThat(continent.hasCountryCode("CA")).isTrue();
        assertThat(continent.hasCountryCode("US")).isTrue();
        assertThat(continent.hasCountryCode("MX")).isTrue();
    }

    @Test
    void continentHasName()
    {
        final Continent continent = new Continent("NA", "North America",
            List.of("CA", "US"));
        continent.addCountries(new Country("CA", "Canada"),
            new Country("US", "United States of America"),
            new Country("MX", "Mexico"));
        assertThat(continent.getName()).isEqualTo("North America");
    }

    @Test
    void hasCountryCodeIsFalseForCountryNotInContinent()
    {
        final Continent continent = new Continent("NA", "North America",
            List.of("CA", "US"));
        continent.addCountries(new Country("CA", "Canada"),
            new Country("US", "United States of America"),
            new Country("MX", "Mexico"));
        assertThat(continent.hasCountryCode("AU")).isFalse();
    }

    @Test
    void hasCountryCodeCannotBeNull()
    {
        final Continent continent = new Continent("NA", "North America",
            List.of("CA", "US"));
        continent.addCountries(new Country("CA", "Canada"),
            new Country("US", "United States of America"),
            new Country("MX", "Mexico"));
        assertThatThrownBy(() -> continent.hasCountryCode(null)).hasMessage(
            "country code MUST NOT be null");
    }

    @Test
    void countriesAreUnmodifiable()
    {
        final Continent continent = new Continent("NA", "North America",
            List.of("CA", "US"));
        continent.addCountries(new Country("CA", "Canada"),
            new Country("US", "United States of America"),
            new Country("MX", "Mexico"));
        assertThatThrownBy(() -> continent
            .getCountries()
            .add(new Country("EG", "Example"))).isInstanceOf(
            UnsupportedOperationException.class);
    }

    @Test
    void matchedCountriesInContinentAreListed()
    {
        final Continent continent = new Continent("NA", "North America",
            List.of("CA", "US"));
        continent.addCountries(new Country("CA", "Canada"),
            new Country("US", "United States of America"),
            new Country("MX", "Mexico"));
        assertThat(continent.getMatchedCountries()).isEqualTo(
            List.of("CA", "US"));
    }
}