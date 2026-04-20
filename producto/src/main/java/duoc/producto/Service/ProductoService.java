package duoc.producto.Service;
import duoc.producto.model.Producto;
import duoc.producto.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository repository;

    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    }

    public List<Producto> listar() {
        return repository.findAll();
    }

    public Producto crear(Producto producto) {
        return repository.save(producto);
    }

    public Producto obtener(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
    }

    public Producto actualizar(Long id, Producto nuevo) {
        Producto p = obtener(id);

        p.setNombre(nuevo.getNombre());
        p.setDescripcion(nuevo.getDescripcion());
        p.setPrecio(nuevo.getPrecio());
        p.setStock(nuevo.getStock());

        return repository.save(p);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    public Producto actualizarStock(Long id, int cantidad) {
        Producto p = obtener(id);

        if (p.getStock() + cantidad < 0) {
            throw new RuntimeException("Stock insuficiente");
        }

        p.setStock(p.getStock() + cantidad);
        return repository.save(p);
    }
}
