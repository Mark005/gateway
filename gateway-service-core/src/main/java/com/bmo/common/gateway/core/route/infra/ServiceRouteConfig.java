package com.bmo.common.gateway.core.route.infra;

import java.util.Map;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "services.routes")
public class ServiceRouteConfig {

  private Map<TargetService, Configs> configs;

  @Data
  public static class Configs {

    private String url;
  }
}

