package com.marketplace.pedido.configuracion;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${app.producto.url}")
    private String productoUrl;

    @Bean
    public WebClient productoWebClient() {
        return WebClient.builder()
                .baseUrl(productoUrl)
                .build();
    }
}