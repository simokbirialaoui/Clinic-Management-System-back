package com.clinic.gatewayservice.config;

import com.clinic.gatewayservice.filter.AuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfiguration {

    private final AuthenticationFilter authenticationFilter;

    public ApiGatewayConfiguration(AuthenticationFilter authenticationFilter) {
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        System.out.println("Initializing Gateway Routes...");
        return builder.routes()
                // Route pour patient-ms
                .route("patient-ms", r -> r.path("/patient-ms/**")
                        .filters(f -> f.filter(authenticationFilter.apply(new AuthenticationFilter.Config()))
                                .rewritePath("/patient-ms/(?<segment>.*)", "/${segment}"))
                        .uri("lb://PATIENT-MS")) // Load balancer vers patient-ms

                // Route pour identity-service
                .route("identity-service", r -> r.path("/identity-service/**")
                        .filters(f -> f.filter(authenticationFilter.apply(new AuthenticationFilter.Config()))
                                .rewritePath("/identity-service/(?<segment>.*)", "/${segment}"))
                        .uri("lb://IDENTITY-SERVICE")) // Load balancer vers identity-service
                // Route pour doctor-ms
                .route("doctor-ms", r -> r.path("/doctor-ms/**")
                        .filters(f -> f.filter(authenticationFilter.apply(new AuthenticationFilter.Config()))
                                .rewritePath("/doctor-ms/(?<segment>.*)", "/${segment}"))
                        .uri("lb://DOCTOR-MS")) // Load balancer vers doctor-ms
                // Route pour appointment-ms
                .route("appointment-ms", r -> r.path("/appointment-ms/**")
                        .filters(f -> f.filter(authenticationFilter.apply(new AuthenticationFilter.Config()))
                                .rewritePath("/appointment-ms/(?<segment>.*)", "/${segment}"))
                        .uri("lb://APPOINTMENT-MS")) // Load balancer vers appointment-ms

                // Route pour MedicalRecord-ms
                .route("medicalrecord-ms", r -> r.path("/medicalrecord-ms/**")
                        .filters(f -> f.filter(authenticationFilter.apply(new AuthenticationFilter.Config()))
                                .rewritePath("/medicalrecord-ms/(?<segment>.*)", "/${segment}"))
                        .uri("lb://MEDICALRECORD-MS")) // Load balancer vers MedicalRecord-ms
                .build();
    }
}