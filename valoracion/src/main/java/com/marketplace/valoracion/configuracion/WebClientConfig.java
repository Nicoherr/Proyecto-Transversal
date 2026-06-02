package com.marketplace.valoracion.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient productoWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8088")
                .build();
    }
}
