package com.marketplace.notificacion;

import com.marketplace.notificacion.model.Notificacion;
import com.marketplace.notificacion.repository.NotificacionRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Date;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private NotificacionRepository notificacionRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();

        for (int i = 1; i <= 10; i++) {
            Notificacion notificacion = new Notificacion();
            notificacion.setPedidoId((long) i);
            notificacion.setAsunto(faker.lorem().sentence(3));
            notificacion.setMensaje(faker.lorem().sentence(10));
            notificacion.setFecha(new Date());
            notificacionRepository.save(notificacion);
        }

        System.out.println("Notificaciones generadas con DataFaker");
    }
}