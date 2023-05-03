package com.bmo.common.gateway.route.services.security;

import com.bmo.common.gateway.route.infra.AbstractSecurityGatewayRoute;
import com.bmo.common.gateway.route.infra.TargetService;
import java.util.List;
import java.util.Set;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
public class RegisterRoute extends AbstractSecurityGatewayRoute {

  @Override
  protected TargetService getService() {
    return TargetService.AUTH;
  }

  @Override
  public List<HttpMethod> getMethods() {
    return List.of(HttpMethod.GET);
  }

  @Override
  public List<String> getPathPatterns() {
    return List.of("/register");
  }

  @Override
  public String getTargetRouting() {
    return "/register";
  }

  @Override
  public Set<String> getRequireAuthorities() {
    return Set.of();
  }
}
