package org.example.dynamicroute;


import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.example.dynamicroute.apimodal.ApiResource;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.filter.FilterDefinition;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Service;
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
public class DynamicRouteService implements ApplicationEventPublisherAware {

    @Resource
    private RouteDefinitionWriter routeDefinitionWriter;


    @Resource
    private ApplicationEventPublisher publisher;
    public void init(){
        ApiResource api =new ApiResource();
        api.setCode("testApi");
        api.setHost("http://localhost:8001");
        api.setHttpMethod("GET");
        api.setPath("/serviceapi/hello");
        addRoute(api);
    }

    public void  addRoute(ApiResource apiResource){
        RouteDefinition definition = new RouteDefinition();
        // route id
        definition.setId(IdUtil.simpleUUID());
        // uri
        //URI uri = UriComponentsBuilder.fromHttpUrl("http://localhost:8001").build().toUri();
        URI uri = UriComponentsBuilder.fromHttpUrl(apiResource.getHost()).build().toUri();
        definition.setUri(uri);
        // predicate
        PredicateDefinition pathPredicate = new PredicateDefinition();
        pathPredicate.setName("Path");
        Map<String,String> pathPredicateParams = new HashMap<>();
        String path = "/"+apiResource.getCode()+apiResource.getPath();
        //String path = "/testApi/serviceapi/hello";
        pathPredicateParams.put("_genkey_0",path);
        pathPredicate.setArgs(pathPredicateParams);
        PredicateDefinition methodPredicate = new PredicateDefinition();
        methodPredicate.setName("Method");
        Map<String,String> methodPredicateParams = new HashMap<>();
        //String httpMethod = "GET";
        String httpMethod = apiResource.getHttpMethod();
        methodPredicateParams.put("_genkey_0", httpMethod);
        methodPredicate.setArgs(methodPredicateParams);
        definition.setPredicates(Stream.of(pathPredicate,methodPredicate).collect(Collectors.toList()));
        // filter
        FilterDefinition filter = new FilterDefinition();
        filter.setName("StripPrefix");
        Map<String,String> filterParam = new HashMap<>();
        filterParam.put("_genkey_0","1");
        filter.setArgs(filterParam);
        definition.setFilters(Stream.of(filter).collect(Collectors.toList()));
        routeDefinitionWriter.save(Mono.just(definition)).subscribe();
        // 刷新网关路由配置 生效---
        //log.info("刷新网关");
        this.publisher.publishEvent(new RefreshRoutesEvent(this));
        log.info("不刷新网关");
        log.info("addRouteSuccess");
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}
