package com.example.country.config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.context.annotation.Bean;

import java.time.Duration;

@org.springframework.context.annotation.Configuration
public class Configuration
{
    @Bean
    public Bucket authenticatedLimit()
    {
        final Bandwidth limit = Bandwidth.classic(20,
            Refill.greedy(20, Duration.ofSeconds(1)));
        return Bucket
        .builder()
        .addLimit(limit)
        .build();
    }

    @Bean
    public Bucket unauthenticatedLimit()
    {
        final Bandwidth limit = Bandwidth.classic(5,
            Refill.greedy(5, Duration.ofSeconds(1)));
        return Bucket
            .builder()
            .addLimit(limit)
            .build();
    }
}
