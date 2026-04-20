package duoc.carrito.Service;
import duoc.carrito.model.*;
import duoc.carrito.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarritoService {

    private final CarritoRepository carritoRepository;
    private final CarritoProductoRepository productoRepository;

    public CarritoService(CarritoRepository carritoRepository,
                          CarritoProductoRepository productoRepository) {
        this.carritoRepository = carritoRepository;
        this.productoRepository = productoRepository;
    }

    public Carrito crearCarrito(Long usuarioId) {
        Carrito carrito = new Carrito(null, usuarioId);
        return carritoRepository.save(carrito);
    }

    public List<CarritoProducto> verCarrito(Long carritoId) {
        return productoRepository.findByCarritoId(carritoId);
    }

    public CarritoProducto agregarProducto(Long carritoId, Long productoId, int cantidad) {
        CarritoProducto cp = new CarritoProducto(null, carritoId, productoId, cantidad);
        return productoRepository.save(cp);
    }

    public void eliminarProducto(Long id) {
        productoRepository.deleteById(id);
    }

    public CarritoProducto actualizarCantidad(Long id, int cantidad) {
        CarritoProducto cp = productoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

        cp.setCantidad(cantidad);
        return productoRepository.save(cp);
    }
}
