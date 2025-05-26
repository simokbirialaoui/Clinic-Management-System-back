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
        corsConfig.addAllowedOrigin("*"); // Origine autorisée (Angular)
        corsConfig.addAllowedMethod("*"); // Toutes les méthodes HTTP autorisées (GET, POST, etc.)
        corsConfig.addAllowedHeader("*"); // Tous les en-têtes autorisés
        corsConfig.setAllowCredentials(false); // Autoriser les cookies et les identifiants

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig); // Appliquer à tous les chemins de votre Gateway

        return new CorsWebFilter(source);
    }
}



/* CODE HAMID
@Configuration
public class CorsConfig {

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();

        // Allow all origins
        corsConfig.addAllowedOriginPattern("*");  // Use addAllowedOriginPattern instead of addAllowedOrigin for more flexibility

        // Allow all methods (GET, POST, etc.)
        corsConfig.addAllowedMethod("*");

        // Allow all headers
        corsConfig.addAllowedHeader("*");

        // Optional: Enable credentials (Cookies, Authorization headers, etc.)
        corsConfig.setAllowCredentials(true);  // If using '*', CORS security won't allow credentials, so you can enable this for specific trusted origins instead

        // Apply to all routes
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }
}*/
