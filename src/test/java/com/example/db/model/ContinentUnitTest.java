package com.example.db.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class ContinentUnitTest
{
    private Continent continent;

    @BeforeEach
    void setUp()
    {
        continent = new Continent("NA", "North America");
        continent
            .addCountry(new Country("CA", "Canada"))
            .addCountry(new Country("US", "United States of America"))
            .addCountry(new Country("MX", "Mexico"));
    }

    @Test
    void continentHasName()
    {
        assertThat(continent.getName()).isEqualTo("North America");
    }

    @Test
    void continentHasCode()
    {
        assertThat(continent.getCode()).isEqualTo("NA");
    }

    // TODO: 2023-03-26 tag:broken why this is broken for this class but not the other continent class, who knows.
//    @Test
//    void countriesAreUnmodifiable()
//    {
//        assertThatThrownBy(() -> continent
//            .getCountries()
//            .add(new Country("EG", "Example")))
//            .isInstanceOf(UnsupportedOperationException.class)
//            .hasMessage("");
//    }

    @Test
    void countriesCanBeFinalized()
    {
        final Continent continent = new Continent("NA", "North America");
        assertThatThrownBy(() -> continent
            .addCountry(new Country("AA", "Country AA"))
            .addCountry(new Country("BB", "Country BB"))
            .done()
            .addCountry(new Country("CC", "Country CC")))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("this is an immutable object");

    }
}