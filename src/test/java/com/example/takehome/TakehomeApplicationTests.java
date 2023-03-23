package com.example.takehome;

import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.endpoint.ApiVersion;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class TakehomeApplicationTests
{

    // TODO: 2023-03-22 tag:docker h2 database in docker for country storage
    // TODO: 2023-03-22 tag:docker prometheus docker for metrics
    // TODO: 2023-03-22 tag:docker grafana if there's enough time.
    // https://wkrzywiec.medium.com/create-and-configure-keycloak-oauth-2-0-authorization-server-f75e2f6f6046
    // TODO: 2023-03-22 tag:db country service for db access
    // TODO: 2023-03-22 tag:db country service metrics
    // TODO: 2023-03-22 tag:graphql country service for graphql access
    // TODO: 2023-03-22 tag:graphql country service for graphql metrics
    // TODO: 2023-03-22 tag:sync country synchronization service (graphql + db dependency)
    // TODO: 2023-03-22 tag:sync use guava for map diffing
    // TODO: 2023-03-22 tag:sync country sync endpoint
    // TODO: 2023-03-22 tag:sync country sync endpoint metrics
    // TODO: 2023-03-22 tag:service country service as defined
    // TODO: 2023-03-22 tag:service country service endpoint
    // TODO: 2023-03-22 tag:gradle task for IT tests
    // TODO: 2023-03-22 tag:gradle task for unit tests
    // TODO: 2023-03-22 tag:gradle task for e2e tests
    // TODO: 2023-03-22 tag:bonus implement oauth2 and users
    // TODO: 2023-03-22 tag:bonus keycloak oauth2 docker server
    // TODO: 2023-03-22 tag:polish separate scanning packages for spring
    @Test
    void contextLoads()
    {
        assertTrue(ApiVersion.LATEST.equals(ApiVersion.V3));
    }

    @Test
    void returnsCountries()
    {

    }
}
