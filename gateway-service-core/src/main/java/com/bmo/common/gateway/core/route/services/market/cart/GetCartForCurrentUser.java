package com.bmo.common.gateway.core.route.services.market.cart;

import com.bmo.common.auth_service.model.AuthorityEnum;
import com.bmo.common.gateway.core.route.infra.AbstractSecurityGatewayRoute;
import com.bmo.common.gateway.core.route.infra.TargetService;
import java.util.List;
import java.util.Set;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
public class GetCartForCurrentUser extends AbstractSecurityGatewayRoute {

  @Override
  protected TargetService getService() {
    return TargetService.MARKET_SERVICE;
  }

  @Override
  public List<HttpMethod> getMethods() {
    return List.of(HttpMethod.PATCH);
  }

  @Override
  public List<String> getPathPatterns() {
    return List.of("/users/current/cart");
  }

  @Override
  public String getTargetRouting() {
    return "/users/current/cart";
  }

  @Override
  public Set<AuthorityEnum> getRequiredAuthorities() {
    return Set.of();
  }

  @Override
  public boolean authenticatedOnly() {
    return true;
  }
}
