package com.marketplace.inventario.service;

import com.marketplace.inventario.dto.InventarioRequestDTO;
import com.marketplace.inventario.dto.InventarioResponseDTO;
import com.marketplace.inventario.model.Inventario;
import com.marketplace.inventario.repository.InventarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InventarioService {

    private final InventarioRepository repository;

    public InventarioService(InventarioRepository repository) {
        this.repository = repository;
    }

    public InventarioResponseDTO crear(InventarioRequestDTO dto) {
        log.info("[POST] Creando registro de inventario para Producto ID: {} - Stock inicial: {}",
                dto.getProductoId(), dto.getStock());
        log.debug("[POST] DTO recibido: {}", dto);
        
        try {
            Inventario inventario = new Inventario();
            inventario.setProductoId(dto.getProductoId());
            inventario.setStock(dto.getStock());
            // Si el DTO trae stockMinimo lo asignamos, si no, tomará el 5 por defecto del modelo
            if (dto.getStockMinimo() > 0) {
                inventario.setStockMinimo(dto.getStockMinimo());
            }

            Inventario guardado = repository.save(inventario);

            log.info("[POST] Inventario creado exitosamente - ID: {}, Producto ID: {}, Stock: {}",
                    guardado.getId(), guardado.getProductoId(), guardado.getStock());
            return convertirAResponse(guardado);
        } catch (Exception e) {
            log.error("[POST] Error al crear inventario para Producto ID {}: {}", 
                    dto.getProductoId(), e.getMessage());
            throw e;
        }
    }

    public List<InventarioResponseDTO> listar() {
        log.info("[GET] Listando todos los registros de inventario");
        
        try {
            List<InventarioResponseDTO> inventarios = repository.findAll().stream()
                    .map(this::convertirAResponse)
                    .collect(Collectors.toList());
            
            log.info("[GET] Se encontraron {} registros de inventario", inventarios.size());
            return inventarios;
        } catch (Exception e) {
            log.error("[GET] Error al listar inventario: {}", e.getMessage());
            throw e;
        }
    }

    public InventarioResponseDTO obtener(Long id) {
        log.info("[GET] Buscando inventario con ID: {}", id);
        
        try {
            Inventario i = repository.findById(id)
                    .orElseThrow(() -> {
                        log.error("[GET] Inventario no encontrado - ID: {}", id);
                        return new RuntimeException("Registro de inventario no encontrado con id: " + id);
                    });

            log.info("[GET] Inventario encontrado - ID: {}, Producto ID: {}, Stock: {}",
                    i.getId(), i.getProductoId(), i.getStock());
            return convertirAResponse(i);
        } catch (Exception e) {
            log.error("[GET] Error al obtener inventario ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    // Método de mapeo
    private InventarioResponseDTO convertirAResponse(Inventario i) {
        InventarioResponseDTO res = new InventarioResponseDTO();
        res.setId(i.getId());
        res.setProductoId(i.getProductoId());
        res.setStock(i.getStock());
        res.setStockMinimo(i.getStockMinimo());
        return res;
    }
}
