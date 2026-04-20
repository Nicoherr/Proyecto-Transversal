package duoc.inventario.Service;
import duoc.inventario.model.Inventario;
import duoc.inventario.repository.InventarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventarioService {

    private final InventarioRepository inventarioRepository;

    public List<Inventario> obtenerTodos() {
        return inventarioRepository.findAll();
    }

    public Inventario consultarStockPorProducto(Long productoId) {
        return inventarioRepository.findByProductoId(productoId).orElse(null);
    }

    public Inventario crearInventario(Inventario inventario) {
        return inventarioRepository.save(inventario);
    }

    public Inventario actualizarStock(Long productoId, Integer cantidadAmodificar) {
        Optional<Inventario> inventarioOpt = inventarioRepository.findByProductoId(productoId);

        if (inventarioOpt.isPresent()) {
            Inventario inventario = inventarioOpt.get();
            int nuevoStock = inventario.getCantidadDisponible() + cantidadAmodificar;

            if (nuevoStock < 0) {
                throw new RuntimeException("Error: Stock insuficiente");
            }

            inventario.setCantidadDisponible(nuevoStock);
            return inventarioRepository.save(inventario);
        }
        throw new RuntimeException("Error: Inventario no encontrado");
    }
}

