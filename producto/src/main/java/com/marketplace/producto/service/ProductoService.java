package com.marketplace.producto.service;

import com.marketplace.producto.dto.ProductoRequestDTO;
import com.marketplace.producto.dto.ProductoResponseDTO;
import com.marketplace.producto.model.Producto;
import com.marketplace.producto.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {

    private final ProductoRepository repository;

    public ProductoService(ProductoRepository repository) {
        this.repository = repository;
    }

    public ProductoResponseDTO crear(ProductoRequestDTO dto) {
        Producto producto = new Producto();
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setPrecio(dto.getPrecio());
        producto.setStock(dto.getStock());
        producto.setVendedorId(dto.getVendedorId());
        // 'activo' ya es true por defecto en tu modelo

        return convertirAResponse(repository.save(producto));
    }

    public List<ProductoResponseDTO> listar() {
        return repository.findAll().stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    public ProductoResponseDTO obtener(Long id) {
        Producto p = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Producto no encontrado"));
        return convertirAResponse(p);
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
