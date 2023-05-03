package com.bmo.common.gateway.route.infra;

import java.util.List;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder.Builder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
public class GatewayConfig {

  @Bean
  public RouteLocator customRouteLocator(RouteLocatorBuilder builder, List<GatewayRoute> routes) {
    Builder routeBuilder = builder.routes();
    routes.forEach(gatewayRoute -> {
      routeBuilder.route(p -> p
          .path(gatewayRoute.getPathPatterns().toArray(new String[0]))
          .and()
          .method(gatewayRoute.getMethods().toArray(new HttpMethod[0]))
          .filters(gatewayRoute.getFilter())
          .uri(gatewayRoute.getUri())
      );
    });
    return routeBuilder.build();
  }

}
