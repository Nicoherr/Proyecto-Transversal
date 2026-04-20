package duoc.vendedor.Model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class vendedor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreTienda;
    private String descripcion;

    private double reputacion = 0.0;
    private int cantidadValoraciones = 0;

    private Long usuarioId;

    private boolean activo = true;


}

