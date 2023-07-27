package org.example.dynamicroute;


import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class DynamicRouteService {

    @Resource
    private RouteDefinitionWriter routeDefinitionWriter;

    public void addRoute(){
        RouteDefinition definition = new RouteDefinition();
        // route id
        definition.setId(IdUtil.simpleUUID());
        // uri
        URI uri = UriComponentsBuilder.fromHttpUrl("http://localhost:8001").build().toUri();
        definition.setUri(uri);
        // predicate
        PredicateDefinition predicate = new PredicateDefinition();
        predicate.setName("Path");
        Map<String,String> predicateParams = new HashMap<>();
        predicateParams.put("_genkey_0","/publicApi/**");
        predicate.setArgs(predicateParams);
        definition.setPredicates(Stream.of(predicate).collect(Collectors.toList()));
        // filter
        FilterDefinition filter = new FilterDefinition();
        filter.setName("StripPrefix");
        Map<String,String> filterParam = new HashMap<>();
        filterParam.put("_genkey_0","1");
        filter.setArgs(filterParam);
        definition.setFilters(Stream.of(filter).collect(Collectors.toList()));
        routeDefinitionWriter.save(Mono.just(definition)).subscribe();
        log.info("addRouteSuccess");
    }
}
