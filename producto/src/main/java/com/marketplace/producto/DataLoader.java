package com.marketplace.producto;

import com.marketplace.producto.model.Producto;
import com.marketplace.producto.repository.ProductoRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ProductoRepository productoRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();

        for (int i = 1; i <= 15; i++) {
            Producto p = new Producto();
            p.setNombre(faker.commerce().productName());
            p.setDescripcion(faker.lorem().sentence());
            p.setPrecio(faker.number().randomDouble(2, 1000, 99999));
            p.setStock(faker.number().numberBetween(0, 100));
            p.setVendedorId((long) faker.number().numberBetween(1, 10));
            // activo se asigna true por defecto en el modelo
            productoRepository.save(p);
        }

        System.out.println("✅ 15 productos generados con DataFaker");
    }
}

