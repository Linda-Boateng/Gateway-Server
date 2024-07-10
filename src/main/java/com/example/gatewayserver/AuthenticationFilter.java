package com.example.gatewayserver;

import java.net.URI;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter implements GlobalFilter, Ordered {

  private final JwtService jwtService;

  public AuthenticationFilter(JwtService jwtService) {
    this.jwtService = jwtService;
  }

  @Override
  public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
    String token = exchange.getRequest().getHeaders().getFirst("Authorization");

    if (token == null || !jwtService.validateToken(token)) {
      URI uri =
          UriComponentsBuilder.fromUri(exchange.getRequest().getURI())
              .path("/login")
              .build()
              .toUri();
      exchange.getResponse().setStatusCode(HttpStatus.SEE_OTHER);
      exchange.getResponse().getHeaders().setLocation(uri);
      return exchange.getResponse().setComplete();
    }

    return chain.filter(exchange);
  }

  //    @Override
  //    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
  //        String token = exchange.getRequest().getHeaders().getFirst("Authorization");
  //
  //        if (token == null || !jwtService.validateToken(token)) {
  //            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
  //            return exchange.getResponse().setComplete();
  //        }
  //
  //        return chain.filter(exchange);
  //    }

  @Override
  public int getOrder() {
    return -1;
  }
}
