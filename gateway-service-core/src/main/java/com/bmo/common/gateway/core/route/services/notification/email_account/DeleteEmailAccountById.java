package com.bmo.common.gateway.core.route.services.notification.email_account;

import com.bmo.common.auth_service.model.AuthorityEnum;
import com.bmo.common.gateway.core.route.infra.AbstractSecurityGatewayRoute;
import com.bmo.common.gateway.core.route.infra.TargetService;
import java.util.List;
import java.util.Set;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
public class DeleteEmailAccountById extends AbstractSecurityGatewayRoute {

  @Override
  protected TargetService getService() {
    return TargetService.MARKET_SERVICE;
  }

  @Override
  public List<HttpMethod> getMethods() {
    return List.of(HttpMethod.PUT);
  }

  @Override
  public List<String> getPathPatterns() {
    return List.of("/email-accounts/{id}");
  }

  @Override
  public String getTargetRouting() {
    return "/email-accounts/{id}";
  }

  @Override
  public Set<AuthorityEnum> getRequiredAuthorities() {
    return Set.of(AuthorityEnum.EMAIL_ACCOUNT_DELETE);
  }
}
