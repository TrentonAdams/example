package com.example.db.components.impl;


import com.example.db.components.CountryProvider;
import com.example.db.model.Continent;
import com.example.db.model.Country;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.stream.StreamSupport;


@Service
public class CountryDataProvider implements CountryProvider
{
    final WebClient countryGraphQLClient;

    public CountryDataProvider(final WebClient countryGraphQLClient)
    {
        this.countryGraphQLClient = countryGraphQLClient;
    }

    @Cacheable("continents")
    @Override
    public List<Continent> queryContinentsByCountryCode(List<String> codes)
    {
        final JsonNode jsonNode = queryCountryService(
            createCountryQuery(codes));

        final List<String> continentCodes = retrieveContinentCodes(jsonNode);
        return retrieveContinents(continentCodes);
    }

    protected List<Continent> retrieveContinents(
        final List<String> continentCodes)
    {
        final String continentQuery = createContinentQuery(continentCodes);
        final JsonNode continentNodes = queryCountryService(continentQuery);

        return StreamSupport
            .stream(continentNodes
                .path("data")
                .path("continents")
                .spliterator(), false)
            .map(CountryDataProvider::mapToContinent)
            .toList();
    }

    private static Continent mapToContinent(JsonNode node)
    {
        final String code = node
            .path("code")
            .asText(null);
        final String name = node
            .path("name")
            .asText(null);
        Objects.requireNonNull(code, "code must not be null");
        Objects.requireNonNull(code, "name must not be null");
        final Continent continent = new Continent(code, name);
        StreamSupport
            .stream(node
                .path("countries")
                .spliterator(), false)
            .map(n -> new Country(n
                .path("code")
                .asText(null), n
                .path("name")
                .asText()))
            .forEach(continent::addCountry);
        return continent.done();
    }

    private static List<String> retrieveContinentCodes(final JsonNode jsonNode)
    {
        final JsonNode countries = jsonNode
            .path("data")
            .path("countries");
        return StreamSupport
            .stream(countries.spliterator(), false)
            .map(node -> node
                .path("continent")
                .path("code")
                .asText())
            .distinct()
            .toList();
    }

    // TODO: 2023-03-27 tag:extract to a service of it's own.  This is a client detail, not a business object, and should be separate
    // TODO: 2023-03-27 tag:extract perhaps take the createCountryQuery and createContinentQuery with it, as those are graphQL client details as well.
    private JsonNode queryCountryService(final String query)
    {
        return countryGraphQLClient
            .post()
            .uri(uriBuilder -> uriBuilder
                .path("/")
                .build())
            .header("Content-Type", "application/json")
            .body(Mono.just(query), String.class)
            .retrieve()
            .bodyToMono(JsonNode.class)
            .block();
    }

    private static String createCountryQuery(final List<String> codes)
    {
        final String graphQLQuery = String.format(
            "query { countries(filter: {code: {in: [%s]}}) { code continent { code } }}",
            String.join(",", codes
                .stream()
                .map(code -> ("\\\"" + code + "\\\""))
                .toList())

        );
        return "{  \"query\": \"" + graphQLQuery + "\"}";
    }

    private static String createContinentQuery(final List<String> codes)
    {
        final String graphQLQuery = String.format(
            "query { continents (filter: { code: {in: [%s]} }) { code name countries { code name } } }",
            String.join(",", codes
                .stream()
                .map(code -> ("\\\"" + code + "\\\""))
                .toList())

        );
        return "{  \"query\": \"" + graphQLQuery + "\"}";
    }

}
