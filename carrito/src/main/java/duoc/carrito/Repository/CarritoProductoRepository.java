package duoc.carrito.Repository;
import duoc.carrito.model.CarritoProducto;
import duoc.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarritoProductoRepository extends JpaRepository<CarritoProducto, Long> {

    List<CarritoProducto> findByCarritoId(Long carritoId);
}
