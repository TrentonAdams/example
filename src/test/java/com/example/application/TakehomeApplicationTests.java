package com.example.application;

import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.endpoint.ApiVersion;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnableCaching
class TakehomeApplicationTests
{
    // TODO: 2023-03-22 tag:docker prometheus docker for metrics
    // TODO: 2023-03-22 tag:docker grafana if there's enough time.
    // https://wkrzywiec.medium.com/create-and-configure-keycloak-oauth-2-0-authorization-server-f75e2f6f6046
    // TODO: 2023-03-22 tag:db country service for db access
    // TODO: 2023-03-22 tag:db country service db metrics
    // TODO: 2023-03-22 tag:sync country synchronization service (graphql + db dependency)
    // TODO: 2023-03-22 tag:sync use guava for map diffing
    // TODO: 2023-03-22 tag:sync country sync endpoint
    // TODO: 2023-03-22 tag:sync country sync endpoint metrics

    // TODO: 2023-03-22 tag:bonus implement oauth2 and users
    // TODO: 2023-03-22 tag:bonus keycloak oauth2 docker server

    // optional
    // TODO: 2023-03-22 tag:gradle task for IT tests
    // TODO: 2023-03-22 tag:gradle task for unit tests
    // TODO: 2023-03-22 tag:gradle task for e2e tests

    @Test
    void contextLoads()
    {
        assertThat(ApiVersion.LATEST)
            .as("matching spring boot version")
            .isEqualTo(ApiVersion.V3);
    }
}
