package org.example.dynamicroute.service.dubboclient;

import org.example.dto.ApiResourceDTO;

public interface ApiClient {

    ApiResourceDTO getApiResource(String code);
}
