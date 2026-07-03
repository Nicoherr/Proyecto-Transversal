package com.marketplace.vendedor.service;
import com.marketplace.vendedor.dto.VendedorRequestDTO;
import com.marketplace.vendedor.dto.VendedorResponseDTO;
import com.marketplace.vendedor.model.Vendedor;
import com.marketplace.vendedor.repository.VendedorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j // logs
@Service
public class VendedorService {

    private final VendedorRepository repository;

    public VendedorService(VendedorRepository repository) {
        this.repository = repository;
    }

    public VendedorResponseDTO crear(VendedorRequestDTO dto) {
        log.info("[POST] Creando nuevo vendedor - Tienda: '{}', Usuario ID: {}",
                dto.getNombreTienda(), dto.getUsuarioId());
        log.debug("[POST] DTO recibido: {}", dto);

        try {
            Vendedor vendedor = new Vendedor();
            vendedor.setNombreTienda(dto.getNombreTienda());
            vendedor.setDescripcion(dto.getDescripcion());
            vendedor.setUsuarioId(dto.getUsuarioId());
            // reputacion (0.0), cantidadValoraciones (0) y activo (true) ya se asignan solos por el modelo

            Vendedor guardado = repository.save(vendedor);

            log.info("[POST] Vendedor creado exitosamente - ID: {}, Tienda: '{}', Usuario ID: {}",
                    guardado.getId(), guardado.getNombreTienda(), guardado.getUsuarioId());
            return convertirAResponse(guardado);
        } catch (Exception e) {
            log.error("[POST] Error al crear vendedor '{}' para Usuario ID {}: {}",
                    dto.getNombreTienda(), dto.getUsuarioId(), e.getMessage());
            throw e;
        }
    }

    public List<VendedorResponseDTO> listar() {
        log.info("[GET] Listando todos los vendedores registrados");
        
        try {
            List<VendedorResponseDTO> vendedores = repository.findAll().stream()
                    .map(this::convertirAResponse)
                    .collect(Collectors.toList());
            
            log.info("[GET] Se encontraron {} vendedores registrados", vendedores.size());
            return vendedores;
        } catch (Exception e) {
            log.error("[GET] Error al listar vendedores: {}", e.getMessage());
            throw e;
        }
    }

    public VendedorResponseDTO obtener(Long id) {
        log.info("[GET] Buscando vendedor con ID: {}", id);

        try {
            Vendedor v = repository.findById(id)
                    .orElseThrow(() -> {
                        log.error("[GET] Vendedor no encontrado - ID: {}", id);
                        return new RuntimeException("Vendedor no encontrado con id: " + id);
                    });

            log.info("[GET] Vendedor encontrado - ID: {}, Tienda: '{}', Reputación: {:.2f}/5.0, Activo: {}",
                    v.getId(), v.getNombreTienda(), v.getReputacion(), v.isActivo());
            return convertirAResponse(v);
        } catch (Exception e) {
            log.error("[GET] Error al obtener vendedor ID {}: {}", id, e.getMessage());
            throw e;
        }
    }

    // Método de mapeo
    private VendedorResponseDTO convertirAResponse(Vendedor v) {
        VendedorResponseDTO res = new VendedorResponseDTO();
        res.setId(v.getId());
        res.setNombreTienda(v.getNombreTienda());
        res.setDescripcion(v.getDescripcion());
        res.setReputacion(v.getReputacion());
        res.setCantidadValoraciones(v.getCantidadValoraciones());
        res.setUsuarioId(v.getUsuarioId());
        res.setActivo(v.isActivo());
        return res;
    }
}