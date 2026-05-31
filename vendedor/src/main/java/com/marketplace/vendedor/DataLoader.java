package com.marketplace.vendedor;
import com.marketplace.vendedor.model.Vendedor;
import com.marketplace.vendedor.repository.VendedorRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private VendedorRepository vendedorRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();

        for (int i = 1; i <= 10; i++) {
            Vendedor v = new Vendedor();
            v.setNombreTienda(faker.company().name());
            v.setDescripcion(faker.lorem().sentence());
            v.setUsuarioId((long) i);
            // reputacion, cantidadValoraciones y activo se asignan solos por el modelo
            vendedorRepository.save(v);
        }

        System.out.println("✅ 10 vendedores generados con DataFaker");
    }
}

