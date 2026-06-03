package com.marketplace.reporte;

import com.marketplace.reporte.model.DetalleReporte;
import com.marketplace.reporte.model.Reporte;
import com.marketplace.reporte.repository.DetalleReporteRepository;
import com.marketplace.reporte.repository.ReporteRepository;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ReporteRepository reporteRepository;

    @Autowired
    private DetalleReporteRepository detalleReporteRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();

        List<String> tipos = List.of(
                "Ventas mensuales", "Ventas anuales", "Pagos pendientes",
                "Productos más vendidos", "Clientes frecuentes"
        );

        // Genera 5 reportes con 2 detalles cada uno
        for (int i = 0; i < 5; i++) {
            Reporte reporte = new Reporte();
            reporte.setTipo(tipos.get(i));
            reporte.setDescripcion(faker.lorem().sentence(10));
            reporte.setFecha(new Date());
            reporte.setEstado(true);
            reporte = reporteRepository.save(reporte);

            // Genera 2 detalles por reporte
            for (int j = 0; j < 2; j++) {
                DetalleReporte detalle = new DetalleReporte();
                detalle.setObservacion(faker.lorem().sentence(8));
                detalle.setValor(faker.number().numberBetween(1, 10000));
                detalle.setReporte(reporte);
                detalleReporteRepository.save(detalle);
            }
        }

        System.out.println("Reportes y detalles generados con DataFaker");
    }
}
