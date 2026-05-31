package com.marketplace.inventario;

import com.marketplace.inventario.model.Inventario;
import com.marketplace.inventario.repository.InventarioRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private InventarioRepository inventarioRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();

        // Genera inventario para 15 productos distintos
        for (int i = 1; i <= 15; i++) {
            Inventario inv = new Inventario();
            inv.setProductoId((long) i);
            inv.setStock(faker.number().numberBetween(0, 200));
            inv.setStockMinimo(faker.number().numberBetween(1, 10));
            inventarioRepository.save(inv);
        }

        System.out.println("15 inventarios generados con DataFaker");
    }
}

