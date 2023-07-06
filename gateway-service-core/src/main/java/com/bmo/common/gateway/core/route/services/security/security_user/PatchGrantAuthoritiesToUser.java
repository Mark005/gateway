package com.bmo.common.gateway.core.route.services.security.security_user;

import com.bmo.common.auth_service.model.AuthorityEnum;
import com.bmo.common.gateway.core.route.infra.AbstractSecurityGatewayRoute;
import com.bmo.common.gateway.core.route.infra.TargetService;
import java.util.List;
import java.util.Set;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
public class PatchGrantAuthoritiesToUser extends AbstractSecurityGatewayRoute {

  @Override
  protected TargetService getService() {
    return TargetService.AUTH_SERVICE;
  }

  @Override
  public List<HttpMethod> getMethods() {
    return List.of(HttpMethod.PATCH);
  }

  @Override
  public List<String> getPathPatterns() {
    return List.of("/users/{userId}/authorities/add");
  }

  @Override
  public String getTargetRouting() {
    return "/users/{userId}/authorities/add";
  }

  @Override
  public Set<AuthorityEnum> getRequiredAuthorities() {
    return Set.of(AuthorityEnum.USER_AUTHORITY_UPDATE);
  }
}
