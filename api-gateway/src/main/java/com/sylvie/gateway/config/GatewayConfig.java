package com.sylvie.gateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

@Configuration
public class GatewayConfig {

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("ms-productos", r -> r.path("/api/productos/**")
                .uri("http://ms-productos:8081"))
            .route("ms-usuarios", r -> r.path("/api/usuarios/**")
                .uri("http://ms-usuarios:8082"))
            .route("ms-analisis", r -> r.path("/api/analisis/**")
                .uri("http://ms-analisis:8083"))
            .route("ms-recomendaciones", r -> r.path("/api/recomendaciones/**")
                .uri("http://ms-recomendaciones:8084"))
            .build();
    }

    @Bean
    public CorsWebFilter corsWebFilter() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.addAllowedOrigin("*");
        corsConfig.addAllowedMethod("*");
        corsConfig.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);

        return new CorsWebFilter(source);
    }
}
