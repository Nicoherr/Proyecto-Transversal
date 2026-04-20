package duoc.vendedor.Controller;
import duoc.vendedor.model.Vendedor;
import duoc.vendedor.service.VendedorService;
import duoc.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vendedor")
public class VendedorController {

    private final VendedorService service;

    public VendedorController(VendedorService service) {
        this.service = service;
    }

    @GetMapping
    public List<Vendedor> listar() {
        return service.listar();
    }

    @PostMapping
    public Vendedor crear(@RequestBody Vendedor vendedor) {
        return service.crear(vendedor);
    }

    @GetMapping("/{id}")
    public Vendedor obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @PutMapping("/{id}")
    public Vendedor actualizar(@PathVariable Long id, @RequestBody Vendedor vendedor) {
        return service.actualizar(id, vendedor);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }

    @PostMapping("/{id}/valorar")
    public Vendedor valorar(@PathVariable Long id, @RequestParam int puntuacion) {
        return service.agregarValoracion(id, puntuacion);
    }
}

