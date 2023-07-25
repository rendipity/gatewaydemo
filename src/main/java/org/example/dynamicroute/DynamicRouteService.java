package org.example.dynamicroute;


import cn.hutool.core.util.IdUtil;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;

import javax.annotation.Resource;

public class DynamicRouteService {

    @Resource
    private RouteDefinitionWriter routeDefinitionWriter;

    public void addRoute(){
        RouteDefinition definition = new RouteDefinition();
        // route id
        definition.setId(IdUtil.simpleUUID());
    }
}
