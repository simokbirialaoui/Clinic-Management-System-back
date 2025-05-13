package com.clinic.gatewayservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
@Configuration
public class GatewayCorsConfig {
    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.addAllowedOrigin("http://localhost:4200"); // Origine autorisée (Angular)
        corsConfig.addAllowedMethod("*"); // Toutes les méthodes HTTP autorisées (GET, POST, etc.)
        corsConfig.addAllowedHeader("*"); // Tous les en-têtes autorisés
        corsConfig.setAllowCredentials(true); // Autoriser les cookies et les identifiants

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig); // Appliquer à tous les chemins de votre Gateway

        return new CorsWebFilter(source);
    }
}
