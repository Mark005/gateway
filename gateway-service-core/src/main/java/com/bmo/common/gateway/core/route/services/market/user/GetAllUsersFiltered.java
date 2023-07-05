package com.bmo.common.gateway.core.route.services.market.user;

import com.bmo.common.auth_service.model.Authority;
import com.bmo.common.gateway.core.route.infra.AbstractSecurityGatewayRoute;
import com.bmo.common.gateway.core.route.infra.TargetService;
import java.util.List;
import java.util.Set;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
public class GetAllUsersFiltered extends AbstractSecurityGatewayRoute {

  @Override
  protected TargetService getService() {
    return TargetService.MARKET_SERVICE;
  }

  @Override
  public List<HttpMethod> getMethods() {
    return List.of(HttpMethod.GET);
  }

  @Override
  public List<String> getPathPatterns() {
    return List.of("/users");
  }

  @Override
  public String getTargetRouting() {
    return "/users";
  }

  @Override
  public Set<Authority> getRequiredAuthorities() {
    return Set.of(Authority.USER_READ);
  }
}
