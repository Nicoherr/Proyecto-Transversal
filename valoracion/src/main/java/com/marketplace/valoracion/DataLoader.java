package com.marketplace.valoracion;

import com.marketplace.valoracion.model.Valoracion;
import com.marketplace.valoracion.repository.ValoracionRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

// Solo corre cuando spring.profiles.active=dev
// Así no contamina la base de datos de producción
@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ValoracionRepository valoracionRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();

        // Genera 10 valoraciones de prueba
        for (int i = 1; i <= 10; i++) {
            Valoracion valoracion = new Valoracion();
            valoracion.setNumEstrella(faker.number().numberBetween(1, 6)); // Entre 1 y 5
            valoracion.setRecomendacion(faker.lorem().sentence(10)); // Frase de 10 palabras
            valoracion.setSugerencia(faker.lorem().sentence(5));
            valoracionRepository.save(valoracion);
        }

        System.out.println("Valoraciones generadas con DataFaker");
    }
}
