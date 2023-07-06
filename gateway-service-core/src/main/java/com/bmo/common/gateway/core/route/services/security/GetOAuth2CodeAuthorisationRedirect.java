package com.bmo.common.gateway.core.route.services.security;

import com.bmo.common.auth_service.model.AuthorityEnum;
import com.bmo.common.gateway.core.route.infra.AbstractSecurityGatewayRoute;
import com.bmo.common.gateway.core.route.infra.TargetService;
import java.util.List;
import java.util.Set;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
public class GetOAuth2CodeAuthorisationRedirect extends AbstractSecurityGatewayRoute {

  @Override
  protected TargetService getService() {
    return TargetService.AUTH_SERVICE;
  }

  @Override
  public List<HttpMethod> getMethods() {
    return List.of(HttpMethod.GET);
  }

  @Override
  public List<String> getPathPatterns() {
    return List.of("/login/oauth2/code/{provider}");
  }

  @Override
  public String getTargetRouting() {
    return "/login/oauth2/code/{provider}";
  }

  @Override
  public Set<AuthorityEnum> getRequiredAuthorities() {
    return Set.of();
  }
}
