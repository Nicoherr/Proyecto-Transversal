package com.marketplace.pedido.configuracion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration // Indica que esta clase provee configuraciones para Spring
public class WebClientConfig {

    // Bean de WebClient apuntando al microservicio de Producto (puerto 8084)
    @Bean
    public WebClient productoWebClient() {
        return WebClient.builder()
                .baseUrl("http://localhost:8086") // URL base del microservicio producto
                .build();
    }
}