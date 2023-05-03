package com.bmo.common.gateway.route.infra;

import com.bmo.common.auth_service.client.AuthServiceReactiveClient;
import com.bmo.common.auth_service.model.TokenBody;
import com.bmo.common.auth_service.model.ValidateTokenRequestBody;
import java.net.URI;
import java.util.Collections;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.route.builder.UriSpec;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public abstract class AbstractSecurityGatewayRoute implements GatewayRoute {

  private final String SECURITY_USER_ID_HEADER_NAME = "X-Security-User-Id";
  private final String USER_ID_HEADER_NAME = "X-User-Id";

  @Autowired
  @Lazy
  private AuthServiceReactiveClient authServiceClient;

  @Autowired
  private ServiceRouteConfig serviceRouteConfig;

  @Override
  public Function<GatewayFilterSpec, UriSpec> getFilter() {
    return f -> f.filters(
        (exchange, chain) -> authFilter(exchange, chain, getRequireAuthorities()),
        this::dynamicRouteFilter);
  }

  private Mono<Void> authFilter(
      ServerWebExchange exchange,
      GatewayFilterChain chain,
      Set<String> requiredAuthorities) {
    return Mono.defer(() -> {
      String rawBearerToken = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

      if (!StringUtils.hasText(rawBearerToken)) {
        if (CollectionUtils.isEmpty(requiredAuthorities)) {
          return chain.filter(exchange);
        }
        return responseAsUnauthorized(exchange, chain);
      }

      return Mono.just(rawBearerToken.replace("Bearer ", ""))
          .map(ValidateTokenRequestBody::new)
          .flatMap(authServiceClient::validate)
          .flatMap(tokenBody -> {

            Optional<TokenBody> tokenBodyOpt = Optional.ofNullable(tokenBody);
            Set<String> authorities = tokenBodyOpt
                .map(TokenBody::getAuthorities)
                .orElse(Collections.emptySet());


            boolean isAccessAllowed = CollectionUtils.isEmpty(requiredAuthorities) ||
                CollectionUtils.containsAny(
                    authorities, requiredAuthorities);

            if (isAccessAllowed) {
              String securityUserId = tokenBodyOpt
                  .map(TokenBody::getSecurityUserId)
                  .map(UUID::toString)
                  .orElse(null);

              String userId = tokenBodyOpt
                  .map(TokenBody::getUserId)
                  .map(UUID::toString)
                  .orElse(null);

              exchange.mutate()
                  .request(builder -> builder
                      .header(SECURITY_USER_ID_HEADER_NAME, securityUserId)
                      .header(USER_ID_HEADER_NAME, userId)
                      .build()).build();

              return chain.filter(exchange);
            }

            return responseAsUnauthorized(exchange, chain);
          });
    });
  }

  private Mono<Void> responseAsUnauthorized(ServerWebExchange exchange, GatewayFilterChain chain) {
    return Mono.defer(() -> {
      ServerHttpResponse response = exchange.getResponse();
      response.setStatusCode(HttpStatus.UNAUTHORIZED);
      ServerWebExchangeUtils.setAlreadyRouted(exchange);
      return chain.filter(exchange);
    });
  }

  private Mono<Void> dynamicRouteFilter(ServerWebExchange exchange, GatewayFilterChain chain) {
    return Mono.just(ServerWebExchangeUtils.getUriTemplateVariables(exchange))
        .flatMap(uriTemplateVariables -> {
          if (uriTemplateVariables.isEmpty()) {
            return chain.filter(exchange);
          }

          String targetRouting = getTargetRouting();
          for (Entry<String, String> templateVarToValue : uriTemplateVariables.entrySet()) {
            String template = "{" + templateVarToValue.getKey() + "}";
            targetRouting = targetRouting.replace(template, templateVarToValue.getValue());
          }

          String query = exchange.getRequest().getURI().getRawQuery();
          if (query != null) {
            targetRouting = targetRouting + "?" + query;
          }

          String finalTargetRouting = targetRouting;

          return chain.filter(exchange.mutate().request(builder ->
                  builder.uri(URI.create(finalTargetRouting))
                      .build())
              .build());
        });
  }

  public abstract Set<String> getRequireAuthorities();

  @Override
  public String getUri() {
    return serviceRouteConfig.getConfigs().get(getService()).getUrl();
  }

  public abstract String getTargetRouting();

  protected abstract TargetService getService();
}
