package com.example.gatewayserver;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

@Component
public class GatewayFiltersFactory
    extends AbstractGatewayFilterFactory<GatewayFiltersFactory.Config> {

  public static class Config {
    private String tokenHeaderName = HttpHeaders.AUTHORIZATION;
    private String tokenPrefix = "Bearer ";
    private boolean relayEnabled = true;

    public String getTokenHeaderName() {
      return tokenHeaderName;
    }

    public void setTokenHeaderName(String tokenHeaderName) {
      this.tokenHeaderName = tokenHeaderName;
    }

    public String getTokenPrefix() {
      return tokenPrefix;
    }

    public void setTokenPrefix(String tokenPrefix) {
      this.tokenPrefix = tokenPrefix;
    }

    public boolean isRelayEnabled() {
      return relayEnabled;
    }

    public void setRelayEnabled(boolean relayEnabled) {
      this.relayEnabled = relayEnabled;
    }
  }

  public GatewayFiltersFactory() {
    super(Config.class);
  }

  @Override
  public GatewayFilter apply(Config config) {
    return (exchange, chain) -> {
      if (!config.isRelayEnabled()) {
        return chain.filter(exchange);
      }

      String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
      if (token != null) {
        exchange =
            exchange
                .mutate()
                .request(request -> request.header(HttpHeaders.AUTHORIZATION, token))
                .build();
      }
      return chain.filter(exchange);
    };
  }
}
