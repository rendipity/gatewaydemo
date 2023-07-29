package org.example.dynamicroute.controller;

import org.example.dynamicroute.DynamicRouteService;
import org.example.dynamicroute.apimodal.ApiResource;
import org.example.dynamicroute.apimodal.Result;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
    @RequestMapping("/route")
public class ApiRouteController {

    @Resource
    DynamicRouteService dynamicRouteService;

    @PostMapping("/add")
    public Result<Boolean> addRoute(@RequestBody ApiResource resource) {
        dynamicRouteService.addRoute(resource);
        return Result.success(true);
    }
}
