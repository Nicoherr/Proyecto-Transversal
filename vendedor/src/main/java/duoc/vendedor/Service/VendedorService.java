package duoc.vendedor.Service;
import duoc.vendedor.model.Vendedor;
import duoc.vendedor.repository.VendedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendedorService {

    private final VendedorRepository repository;

    public VendedorService(VendedorRepository repository) {
        this.repository = repository;
    }

    public List<Vendedor> listar() {
        return repository.findAll();
    }

    public Vendedor crear(Vendedor vendedor) {
        return repository.save(vendedor);
    }

    public Vendedor obtener(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendedor no encontrado"));
    }

    public Vendedor actualizar(Long id, Vendedor nuevo) {
        Vendedor v = obtener(id);

        v.setNombreTienda(nuevo.getNombreTienda());
        v.setDescripcion(nuevo.getDescripcion());

        return repository.save(v);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }

    public Vendedor agregarValoracion(Long id, int nuevaValoracion) {
        Vendedor v = obtener(id);

        double total = v.getReputacion() * v.getCantidadValoraciones();
        total += nuevaValoracion;

        v.setCantidadValoraciones(v.getCantidadValoraciones() + 1);
        v.setReputacion(total / v.getCantidadValoraciones());

        return repository.save(v);
    }
}
