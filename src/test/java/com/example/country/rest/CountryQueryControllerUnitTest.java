package com.example.country.rest;

import com.example.application.TakehomeApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
                classes = TakehomeApplication.class)
class CountryQueryControllerUnitTest
{
    @Autowired
    private MockMvc mockMvc;

    @Test
    void allowsOnlyTwoCharacterCountryCodes() throws Exception
    {
        this.mockMvc
            .perform(get("/continent_search").param("country_code", "AAA"))
            .andDo(print())
            .andExpect(status().is4xxClientError())
            .andExpect(jsonPath("$.errors").isArray())
            .andExpect(jsonPath("$.errors[0]").value("Only 2 character country codes are supported"));
        this.mockMvc
            .perform(get("/continent_search").param("country_code", "A"))
            .andDo(print())
            .andExpect(status().is4xxClientError())
            .andExpect(jsonPath("$.errors").isArray())
            .andExpect(jsonPath("$.errors[0]").value("Only 2 character country codes are supported"));

    }

    @Test
    void allowsOnlyUppercaseCountryCodes() throws Exception
    {
        this.mockMvc
            .perform(get("/continent_search").param("country_code", "aa"))
            .andDo(print())
            .andExpect(status().is4xxClientError())
            .andExpect(jsonPath("$.errors").isArray())
            .andExpect(jsonPath("$.errors[0]").value("Only uppercase country codes are supported"));
    }

    @Test
    void requireAtLeastOneCode() throws Exception
    {
        this.mockMvc
            .perform(get("/continent_search"))
            .andDo(print())
            .andExpect(status().is4xxClientError())
            .andExpect(jsonPath("$.errors").isArray())
            .andExpect(jsonPath("$.errors[0]").value("At least one country code is required"));
    }
}