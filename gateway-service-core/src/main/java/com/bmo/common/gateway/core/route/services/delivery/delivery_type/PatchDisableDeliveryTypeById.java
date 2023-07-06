package com.bmo.common.gateway.core.route.services.delivery.delivery_type;

import com.bmo.common.auth_service.model.AuthorityEnum;
import com.bmo.common.gateway.core.route.infra.AbstractSecurityGatewayRoute;
import com.bmo.common.gateway.core.route.infra.TargetService;
import java.util.List;
import java.util.Set;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
public class PatchDisableDeliveryTypeById extends AbstractSecurityGatewayRoute {

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
    return List.of("/delivery-types/{id}/disable");
  }

  @Override
  public String getTargetRouting() {
    return "/delivery-types/{id}/disable";
  }

  @Override
  public Set<AuthorityEnum> getRequiredAuthorities() {
    return Set.of(AuthorityEnum.DELIVERY_TYPE_UPDATE);
  }

}
