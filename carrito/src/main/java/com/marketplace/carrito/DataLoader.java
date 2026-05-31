package com.marketplace.carrito;
import com.marketplace.carrito.model.Carrito;
import com.marketplace.carrito.model.CarritoProducto;
import com.marketplace.carrito.repository.CarritoProductoRepository;
import com.marketplace.carrito.repository.CarritoRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

// @Profile("dev") — solo corre cuando spring.profiles.active=dev
// así no contamina la base de datos de test
@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private CarritoProductoRepository carritoProductoRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();

        // Genera 10 carritos para distintos usuarios
        for (int i = 1; i <= 10; i++) {
            Carrito carrito = new Carrito();
            carrito.setUsuarioId((long) i); // usuarioId del 1 al 10
            carritoRepository.save(carrito);
        }

        // Para cada carrito agrega entre 1 y 3 productos
        carritoRepository.findAll().forEach(carrito -> {
            int cantidad = faker.number().numberBetween(1, 4);
            for (int j = 0; j < cantidad; j++) {
                CarritoProducto cp = new CarritoProducto();
                cp.setCarrito(carrito);
                cp.setProductoId((long) faker.number().numberBetween(1, 20));
                cp.setCantidad(faker.number().numberBetween(1, 5));
                carritoProductoRepository.save(cp);
            }
        });

        System.out.println("Carritos y productos generados con DataFaker");
    }
}
