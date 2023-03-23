package com.example.country.rest;

import com.example.country.components.CountryService;
import com.example.country.exceptions.RateLimitExceededException;
import com.example.country.model.Continent;
import io.github.bucket4j.Bucket;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@Validated
@Slf4j
public class CountryQueryController
{
    public static final String CODE_LENGTH =
        "Only 2 character country codes are supported";
    public static final String REQUIRED =
        "At least one country code is required";
    public static final String UPPERCASE_REQUIRED =
        "Only uppercase country codes are supported";
    public static final String UPPERCASE_COUNTRY_REGEX = "^[A-Z]*$";
    final CountryService continentService;
    private final Bucket authenticatedLimit;
    private final Bucket unauthenticatedLimit;


    public CountryQueryController(final CountryService continentService,
        Bucket authenticatedLimit, Bucket unauthenticatedLimit)
    {
        this.continentService = continentService;
        this.authenticatedLimit = authenticatedLimit;
        this.unauthenticatedLimit = unauthenticatedLimit;
    }

    @GetMapping("/continent_search")
    ResponseEntity<List<Continent>> continentSearch(
        @RequestParam(name = "country_code", required = false)
        @NotNull(message = REQUIRED) List<@Size(min = 2,
                                                max = 2,
                                                message = CODE_LENGTH) @Pattern(
            regexp = UPPERCASE_COUNTRY_REGEX,
            message = UPPERCASE_REQUIRED) String> countryCodes)
    {
        // TODO: 2023-03-28 tag:cleanup wrap the bucket in a class with a description of the limit so clients can know why they were limited and how to rectify it
        // TODO: 2023-03-28 tag:cleanup possibly add request filter or use AOP to handle rate limits.

        Bucket bucket = getBucket();
        if (bucket.tryConsume(1))
        {
            return ResponseEntity
                .status(HttpStatus.OK)
                .body(
                    continentService.getContinentsByCountryCodes(countryCodes));
        }
        else
        {
            throw new RateLimitExceededException(
                "you have exceeded your rate limit");
        }
    }

    private Bucket getBucket()
    {
        Authentication authentication = SecurityContextHolder
            .getContext()
            .getAuthentication();
        Bucket bucket = authentication.isAuthenticated() ? authenticatedLimit :
            unauthenticatedLimit;
        String currentPrincipalName = authentication.getName();
        log.info(String.format("serving /continent_search request for %s.",
            currentPrincipalName));
        return bucket;
    }

}
