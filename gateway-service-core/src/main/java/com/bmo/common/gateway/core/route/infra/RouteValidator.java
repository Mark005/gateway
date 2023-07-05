package com.bmo.common.gateway.core.route.infra;

import java.util.List;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

@Component
public class RouteValidator {

  public RouteValidator(List<AbstractSecurityGatewayRoute> securityRoutes) {
    securityRoutes.forEach(route -> {
      if (!CollectionUtils.isEmpty(route.getRequiredAuthorities()) && !route.authenticatedOnly()) {
        throw new IllegalStateException(
            "Route cant be NOT authenticatedOnly (for all) and have Required Authorities");
      }
    });
  }
}
