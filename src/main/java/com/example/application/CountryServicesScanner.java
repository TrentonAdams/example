package com.example.application;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * I like to build clean boundaries between services, so if we decided we can
 * always fork into microservices with copy/paste of the project, delete
 * everything not included in said service, add a new CI/CD pipeline, et voila,
 * new microservice.  So, we assume here that the country piece is only one
 * piece of the entire app, and there may be other endpoints we start adding.
 * As such, we isolate the package scanning here, and we isolate the java
 * packages.
 */
@Configuration
@ComponentScan("com.example.country")
public class CountryServicesScanner
{

}
