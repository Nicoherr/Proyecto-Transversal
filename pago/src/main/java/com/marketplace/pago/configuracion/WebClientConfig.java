package com.marketplace.pago.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration // Indica que esta clase provee configuraciones para Spring
public class WebClientConfig {

    // Bean de WebClient apuntando al microservicio de Pedido (puerto 8086)
    @Bean
    public WebClient pedidoWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8087") // URL base del microservicio pedido
                .build();
    }
}