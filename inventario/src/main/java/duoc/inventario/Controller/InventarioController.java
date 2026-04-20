package duoc.inventario.Controller;
import duoc.inventario.model.Inventario;
import duoc.inventario.service.InventarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/inventario")
@RequiredArgsConstructor
public class InventarioController {

    private final InventarioService inventarioService;

    @GetMapping
    public ResponseEntity<List<Inventario>> listarTodo() {
        return ResponseEntity.ok(inventarioService.obtenerTodos());
    }

    @GetMapping("/producto/{productoId}")
    public ResponseEntity<Inventario> verStock(@PathVariable Long productoId) {
        Inventario inventario = inventarioService.consultarStockPorProducto(productoId);
        return (inventario != null) ? ResponseEntity.ok(inventario) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Inventario> inicializarStock(@RequestBody Inventario inventario) {
        return ResponseEntity.ok(inventarioService.crearInventario(inventario));
    }

    @PutMapping("/producto/{productoId}/sumar")
    public ResponseEntity<Inventario> sumarStock(@PathVariable Long productoId, @RequestParam Integer cantidad) {
        return ResponseEntity.ok(inventarioService.actualizarStock(productoId, cantidad));
    }

    @PutMapping("/producto/{productoId}/restar")
    public ResponseEntity<Inventario> restarStock(@PathVariable Long productoId, @RequestParam Integer cantidad) {
        try {
            return ResponseEntity.ok(inventarioService.actualizarStock(productoId, -cantidad));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
