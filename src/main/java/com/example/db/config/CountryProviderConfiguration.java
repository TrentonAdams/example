package com.example.db.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Configuration
public class CountryProviderConfiguration
{
    @Bean
    WebClient countryGraphQLClient(
        @Value("${country.provider.trevor_blades.url}")
        String url)
    {
        return WebClient.create(url);
    }

}