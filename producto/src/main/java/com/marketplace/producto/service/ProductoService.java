package com.marketplace.producto.service;
import com.marketplace.producto.dto.ProductoRequestDTO;
import com.marketplace.producto.dto.ProductoResponseDTO;
import com.marketplace.producto.model.Producto;
import com.marketplace.producto.repository.ProductoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductoService {

    private final ProductoRepository repository;

    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    }

    public ProductoResponseDTO crear(ProductoRequestDTO dto) {
        log.info("[POST] Creando nuevo producto '{}' - Precio: ${}  - Vendedor ID: {}",
                dto.getNombre(), dto.getPrecio(), dto.getVendedorId());
        log.debug("[POST] DTO recibido: {}", dto);

        try {
            Producto producto = new Producto();
            producto.setNombre(dto.getNombre());
            producto.setDescripcion(dto.getDescripcion());
            producto.setPrecio(dto.getPrecio());
            producto.setStock(dto.getStock());
            producto.setVendedorId(dto.getVendedorId());
            // El campo 'activo' ya se asigna a true por defecto en el modelo

            Producto guardado = repository.save(producto);

            log.info("[POST] Producto creado exitosamente - ID: {}, Nombre: '{}', Stock: {}",
                    guardado.getId(), guardado.getNombre(), guardado.getStock());
            return convertirAResponse(guardado);
        } catch (Exception e) {
            log.error("[POST] Error al crear producto '{}': {}", dto.getNombre(), e.getMessage());
            throw e;
        }
    }

    public List<ProductoResponseDTO> listar() {
        log.info("[GET] Listando todos los productos del catálogo");
        
        try {
            List<ProductoResponseDTO> productos = repository.findAll().stream()
                    .map(this::convertirAResponse)
                    .collect(Collectors.toList());
            
            log.info("[GET] Se encontraron {} productos en el catálogo", productos.size());
            return productos;
        } catch (Exception e) {
            log.error("[GET] Error al listar productos: {}", e.getMessage());
            throw e;
        }
    }

    public ProductoResponseDTO obtener(Long id) {
        log.info("[GET] Buscando producto con ID: {}", id);

        try {
            Producto p = repository.findById(id)
                    .orElseThrow(() -> {
                        log.error("[GET] Producto no encontrado - ID: {}", id);
                        return new RuntimeException("Producto no encontrado con id: " + id);
                    });

            log.info("[GET] Producto encontrado - ID: {}, Nombre: '{}', Stock: {}, Activo: {}",
                    p.getId(), p.getNombre(), p.getStock(), p.isActivo());
            return convertirAResponse(p);
        } catch (Exception e) {
            log.error("[GET] Error al obtener producto ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    // Método de mapeo
    private ProductoResponseDTO convertirAResponse(Producto p) {
        ProductoResponseDTO res = new ProductoResponseDTO();
        res.setId(p.getId());
        res.setNombre(p.getNombre());
        res.setDescripcion(p.getDescripcion());
        res.setPrecio(p.getPrecio());
        res.setStock(p.getStock());
        res.setVendedorId(p.getVendedorId());
        res.setActivo(p.isActivo());
        return res;
    }
}