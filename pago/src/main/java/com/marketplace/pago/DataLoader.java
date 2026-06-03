package com.marketplace.pago;

import com.marketplace.pago.model.Pago;
import com.marketplace.pago.repository.PagoRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private PagoRepository pagoRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();

        List<String> metodos = List.of(
                "Tarjeta de crédito", "Tarjeta de débito", "Transferencia bancaria", "PayPal"
        );

        // Genera 10 pagos de prueba
        for (int i = 1; i <= 10; i++) {
            Pago pago = new Pago();
            pago.setMetodoPago(metodos.get(faker.number().numberBetween(0, metodos.size())));
            pago.setComprobante("COMP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
            pago.setFecha(new Date());
            pago.setPedidoId((long) i); // pedidoId del 1 al 10
            pagoRepository.save(pago);
        }

        System.out.println("Pagos generados con DataFaker");
    }
}
