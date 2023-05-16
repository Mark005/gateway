package com.bmo.common.gateway.core.route.infra;

import java.util.List;
import java.util.function.Function;
import org.springframework.cloud.gateway.route.builder.GatewayFilterSpec;
import org.springframework.cloud.gateway.route.builder.UriSpec;
import org.springframework.http.HttpMethod;

public interface GatewayRoute {
  List<HttpMethod> getMethods();

  List<String> getPathPatterns();

  Function<GatewayFilterSpec, UriSpec> getFilter();

  String getUri();
}
