package com.example.country.rest;

import com.example.application.TakehomeApplication;
import com.example.country.model.Continent;
import com.example.country.model.Country;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.ExchangeFilterFunctions;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;

import static org.assertj.core.api.Assertions.assertThat;

// TODO: 2023-03-28 tag:wiremock this should be a real test with wiremock responses.  The CountryDataProviderIntegrationTest provides enough "real" interaction with the graphql service to suffice for real testing.
// TODO: 2023-03-28 tag:real  this should be hitting the real service in a prod like environment though.
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
                classes = TakehomeApplication.class)
class CountryQueryControllerE2ETest
{
    @LocalServerPort
    private int port;

    @Test
    void searchForNorthAndSouthAmerica()
    {
        ResponseEntity<List<Continent>> httpResponse = getContinents();
        assertThat(httpResponse.getStatusCode()).isEqualTo(HttpStatus.OK);

        final List<Continent> response = httpResponse.getBody();

        assertThat(response).hasSize(2);
        final Continent northAmerica = response.get(0);
        assertThat(northAmerica.getName()).isEqualTo("North America");
        assertThat(northAmerica.getCode()).isEqualTo("NA");
        assertThat(northAmerica.getMatchedCountries()).isEqualTo(List.of("CA"));
        assertThat(northAmerica.getCountries())
            .isNotNull()
            .hasSizeGreaterThan(1)
            .contains(new Country("CA", "Canada"));
        final Continent southAmerica = response.get(1);
        assertThat(southAmerica.getName()).isEqualTo("South America");
        assertThat(southAmerica.getCode()).isEqualTo("SA");
        assertThat(southAmerica.getMatchedCountries()).isEqualTo(List.of("BR"));
        assertThat(southAmerica.getCountries())
            .isNotNull()
            .hasSizeGreaterThan(1)
            .contains(new Country("BR", "Brazil"));
    }

    @Test
    void performanceTesting()
    {
        searchForNorthAndSouthAmerica();
        for (int i = 0; i < 10; i++)
        {
            long before;
            long after;

            before = System.currentTimeMillis();
            searchForNorthAndSouthAmerica();
            after = System.currentTimeMillis();
            assertThat(after - before).isLessThan(500);

        }
    }

    @Test
    void rateLimitShouldBeExceeded() throws InterruptedException
    {
        // Running once just ensures caching happens so that we don't hammer
        // the third party service by having multiple requests already in flight
        // before caching has happened, until we get wiremock/spock mocking in
        // place.
        getContinents();
        final ScheduledExecutorService threadPool =
            Executors.newScheduledThreadPool(30);
        List<Future<ResponseEntity<List<Continent>>>> resultList =
            new ArrayList<>();

        for (int i = 0; i < 30; i++)
        {
            Future<ResponseEntity<List<Continent>>> future = threadPool.submit(
                this::getContinents);
            resultList.add(future);
        }

        int failedCount = 0;
        for (Future<ResponseEntity<List<Continent>>> result : resultList)
        {
            try
            {
                result.get();
            }
            catch (ExecutionException e)
            {
                System.err.printf("expected exception: %s%n", e.getMessage());
                WebClientResponseException webClientException =
                    (WebClientResponseException) e.getCause();
                if (webClientException.getStatusCode() ==
                    HttpStatus.TOO_MANY_REQUESTS)
                {
                    final JsonNode jsonNode =
                        webClientException.getResponseBodyAs(JsonNode.class);

                    assertThat(jsonNode).isNotNull();
                    assertThat(jsonNode
                        .path("errors")
                        .isArray())
                        .as("response must have errors array")
                        .isTrue();
                    assertThat(jsonNode
                        .path("errors")
                        .get(0)
                        .asText())
                        .as("rate limit message should have been shown in errors array")
                        .isEqualTo("you have exceeded your rate limit");
                    failedCount++;
                }
            }
        }
        assertThat(failedCount > 0)
            .as("no rate limited responses")
            .isTrue();
        threadPool.shutdown();
    }


    private ResponseEntity<List<Continent>> getContinents()
    {
        final WebClient webClient = WebClient
            .builder()
            .filter(
                ExchangeFilterFunctions.basicAuthentication("user", "password"))
            .baseUrl("http://localhost:" + port)
            .build();
        return webClient
            .get()
            .uri(uriBuilder -> uriBuilder
                .path("/continent_search")
                .queryParam("country_code", Arrays.asList("CA", "BR"))
                .build())
            .retrieve()
            .toEntityList(Continent.class)
            .block();
    }

}