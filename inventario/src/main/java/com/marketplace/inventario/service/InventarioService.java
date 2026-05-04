package com.marketplace.inventario.service;

import com.marketplace.inventario.dto.InventarioRequestDTO;
import com.marketplace.inventario.dto.InventarioResponseDTO;
import com.marketplace.inventario.model.Inventario;
import com.marketplace.inventario.repository.InventarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventarioService {

    private final InventarioRepository repository;

    public InventarioService(InventarioRepository repository) {
        this.repository = repository;
    }

    public InventarioResponseDTO crear(InventarioRequestDTO dto) {
        Inventario inventario = new Inventario();
        inventario.setProductoId(dto.getProductoId());
        inventario.setStock(dto.getStock());

        // Si nos envían un stock mínimo específico, lo usamos.
        // Si no, se queda con el 5 que le agregamos en el modelo.
        if (dto.getStockMinimo() != null) {
            inventario.setStockMinimo(dto.getStockMinimo());
        }

        return convertirAResponse(repository.save(inventario));
    }

    public List<InventarioResponseDTO> listar() {
        return repository.findAll().stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    public InventarioResponseDTO obtener(Long id) {
        Inventario inv = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventario no encontrado"));
        return convertirAResponse(inv);
    }

    // Método para mapear el modelo al DTO
    private InventarioResponseDTO convertirAResponse(Inventario inv) {
        InventarioResponseDTO res = new InventarioResponseDTO();
        res.setId(inv.getId());
        res.setProductoId(inv.getProductoId());
        res.setStock(inv.getStock());
        res.setStockMinimo(inv.getStockMinimo());
        return res;
    }
}

