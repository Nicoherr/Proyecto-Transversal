package com.marketplace.pedido;

import com.marketplace.pedido.model.Pedido;
import com.marketplace.pedido.repository.PedidoRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();

        List<String> tipos = List.of(
                "Electrónica", "Ropa", "Alimentos", "Hogar", "Deportes"
        );

        for (int i = 1; i <= 10; i++) {
            Pedido pedido = new Pedido();
            pedido.setProductoId((long) i);
            pedido.setNomProducto(faker.commerce().productName());
            pedido.setTipoProducto(tipos.get(faker.number().numberBetween(0, tipos.size())));
            pedido.setPrecio(faker.number().numberBetween(1000, 100000));
            pedido.setDireccionEntrega(faker.address().streetAddress());
            pedidoRepository.save(pedido);
        }

        System.out.println("Pedidos generados con DataFaker");
    }
}
