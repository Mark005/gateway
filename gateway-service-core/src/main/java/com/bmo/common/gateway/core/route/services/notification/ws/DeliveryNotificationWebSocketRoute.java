package com.bmo.common.gateway.core.route.services.notification.ws;

import com.bmo.common.auth_service.model.Authority;
import com.bmo.common.gateway.core.route.infra.AbstractSecurityGatewayRoute;
import com.bmo.common.gateway.core.route.infra.TargetService;
import java.util.List;
import java.util.Set;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

@Service
public class DeliveryNotificationWebSocketRoute extends AbstractSecurityGatewayRoute {

  @Override
  protected TargetService getService() {
    return TargetService.NOTIFICATION_SERVICE;
  }

  @Override
  public List<HttpMethod> getMethods() {
    return List.of(HttpMethod.GET);
  }

  @Override
  public List<String> getPathPatterns() {
    return List.of("/websocket/delivery-notifications");
  }

  @Override
  public String getTargetRouting() {
    return "/websocket/delivery-notifications";
  }

  @Override
  public Set<Authority> getRequireAuthorities() {
    return Set.of();
  }
}
