package com.example.db.components;

import com.example.application.TakehomeApplication;
import com.example.db.model.Continent;
import com.example.db.model.Country;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = TakehomeApplication.class)
class CountryDataProviderIntegrationTest
{
    @Autowired
    CountryProvider countryProvider;

    @Test
    void queryContinentsByCountryCode()
    {
        final List<Continent> results =
            countryProvider.queryContinentsByCountryCode(
                List.of("CA", "US"));
        assertThat(results)
            .isNotNull()
            .hasSize(1);
        assertThat(results.get(0).getCountries())
            .isNotNull()
            .hasSizeGreaterThan(1)
            .contains(new Country("CA", "Canada"));
    }
}