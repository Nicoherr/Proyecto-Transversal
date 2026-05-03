package com.marketplace.vendedor.service;
import com.marketplace.vendedor.dto.VendedorRequestDTO;
import com.marketplace.vendedor.dto.VendedorResponseDTO;
import com.marketplace.vendedor.model.Vendedor;
import com.marketplace.vendedor.repository.VendedorRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendedorService {

    private final VendedorRepository repository;

    public VendedorService(VendedorRepository repository) {
        this.repository = repository;
    }

    public VendedorResponseDTO crear(VendedorRequestDTO dto) {
        Vendedor vendedor = new Vendedor();
        vendedor.setNombreTienda(dto.getNombreTienda());
        vendedor.setDescripcion(dto.getDescripcion());
        vendedor.setUsuarioId(dto.getUsuarioId());
        // reputacion, cantidadValoraciones y activo ya tienen sus valores por defecto gracias a tu modelo

        return convertirAResponse(repository.save(vendedor));
    }

    public List<VendedorResponseDTO> listar() {
        return repository.findAll().stream()
                .map(this::convertirAResponse)
                .collect(Collectors.toList());
    }

    public VendedorResponseDTO obtener(Long id) {
        Vendedor v = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vendedor/Tienda no encontrado"));
        return convertirAResponse(v);
    }

    // Método para mapear tu modelo real al Response
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
