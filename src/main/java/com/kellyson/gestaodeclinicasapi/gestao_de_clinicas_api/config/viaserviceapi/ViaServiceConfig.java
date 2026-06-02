package com.kellyson.gestaodeclinicasapi.gestao_de_clinicas_api.config.viaserviceapi;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class ViaServiceConfig {

    @Bean
    public RestClient restClient() {
        return RestClient.builder()
                .baseUrl("https://viacep.com.br")
                .build();
    }

}
