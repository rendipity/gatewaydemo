package org.example.dynamicroute.service.dubboclient.impl;

import org.apache.dubbo.config.annotation.DubboReference;
import org.example.ApiFacade;
import org.example.dto.ApiResourceDTO;
import org.example.dynamicroute.service.dubboclient.ApiClient;
import org.springframework.stereotype.Service;

@Service
public class ApiClientImpl implements ApiClient {


    @DubboReference
    ApiFacade apiFacade;

    @Override
    public ApiResourceDTO getApiResource(String code) {
        return apiFacade.getApi(code);
    }
}
