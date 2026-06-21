package com.marketplace.notificacion.configuracion;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${app.pedido.url}")
    private String pedidoUrl;

    @Bean
    public WebClient pedidoWebClient() {
        return WebClient.builder()
                .baseUrl(pedidoUrl)
                .build();
    }
}