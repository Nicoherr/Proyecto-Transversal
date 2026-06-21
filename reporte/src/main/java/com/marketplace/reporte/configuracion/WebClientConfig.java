package com.marketplace.reporte.configuracion;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Value("${app.pago.url}")
    private String pagoUrl;

    @Bean
    public WebClient pagoWebClient() {
        return WebClient.builder()
                .baseUrl(pagoUrl)
                .build();
    }
}