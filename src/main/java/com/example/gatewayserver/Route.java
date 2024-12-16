package com.example.gatewayserver;

import org.springframework.cloud.gateway.server.mvc.filter.CircuitBreakerFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestClient;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

import static com.example.gatewayserver.util.ConstantStrings.*;
import static org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions.route;
import static org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions.http;


import java.net.URI;


@Configuration
public class Route {

  @Bean
  public RouterFunction<ServerResponse> fallbackRoute() {
    return route("fallbackRoute")
        .GET(
            FALLBACK_ROUTE,
            request -> ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE).body(FALLBACK_MESSAGE))
        .POST(
            FALLBACK_ROUTE,
            request -> ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE).body(FALLBACK_MESSAGE))
        .PUT(
            FALLBACK_ROUTE,
            request -> ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE).body(FALLBACK_MESSAGE))
        .DELETE(
            FALLBACK_ROUTE,
            request -> ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE).body(FALLBACK_MESSAGE))
        .PATCH(
            FALLBACK_ROUTE,
            request -> ServerResponse.status(HttpStatus.SERVICE_UNAVAILABLE).body(FALLBACK_MESSAGE))
        .build();
  }

  @Bean
  public RouterFunction<ServerResponse> userServiceRoute() {
    return GatewayRouterFunctions.route("user-service")
        .route(RequestPredicates.path(USER_SERVICE_ROUTE), http(USER_SERVICE))
        .filter(
            CircuitBreakerFilterFunctions.circuitBreaker(
                USER_SERVICE_CIRCUITBREAKER, URI.create(FALLBACK_URL)))
        .build();
  }

  @Bean
  public RouterFunction<ServerResponse> orderServiceRoute() {
    return GatewayRouterFunctions.route("order-service")
        .route(RequestPredicates.path("/api/v1/**"), http(ORDER_SERVICE))
        .filter(
            CircuitBreakerFilterFunctions.circuitBreaker(
                ORDER_SERVICE_CIRCUITBREAKER, URI.create(FALLBACK_URL)))
        .build();
  }

  @Bean
  public RouterFunction<ServerResponse> bookServiceRoute() {
    return GatewayRouterFunctions.route("book-service")
        .route(RequestPredicates.path("/api/v1/user/**"), http(BOOK_SERVICE))
        .route(RequestPredicates.path("/api/v1/admin/**"), http(BOOK_SERVICE))
        .route(RequestPredicates.path("/api/public/books"), http(BOOK_SERVICE))
        .filter(
            CircuitBreakerFilterFunctions.circuitBreaker(
                BOOK_SERVICE_CIRCUITBREAKER, URI.create(FALLBACK_URL)))
        .build();
  }

  @Bean
  public RestClient.Builder restClientBuilder() {
    return RestClient.builder();
  }
}
