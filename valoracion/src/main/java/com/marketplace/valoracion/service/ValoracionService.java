package com.marketplace.valoracion.service;

import com.marketplace.valoracion.DTO.ValoracionRequestDTO;
import com.marketplace.valoracion.DTO.ValoracionResponseDTO;
import com.marketplace.valoracion.model.Valoracion;
import com.marketplace.valoracion.repository.ValoracionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ValoracionService {

    @Autowired
    private final ValoracionRepository valoracionRepository;

    private ValoracionResponseDTO makeToValoracionResponseDTO(Valoracion valoracion){
        return new ValoracionResponseDTO(valoracion.getId(), valoracion.getNumEstrella(), valoracion.getRecomendacion());
    }

    //Listar
    public List<ValoracionResponseDTO> findAllValoraciones(){
        return valoracionRepository.findAll().stream().map(this::makeToValoracionResponseDTO).collect(Collectors.toList());
    }

    //Buscar
    public ValoracionRequestDTO findValoracionesById(long id) {
        Valoracion valoracion = valoracionRepository.findById(id).get();
        return new ValoracionRequestDTO(valoracion.getNumEstrella(), valoracion.getRecomendacion());
    }

    //Crear
    public ValoracionResponseDTO createValoracion(ValoracionRequestDTO newValoracionDTO){
        Valoracion valoracion = new Valoracion(0, newValoracionDTO.getNumEstrella(), newValoracionDTO.getRecomendacion());
        valoracion = valoracionRepository.save(valoracion);
        ValoracionResponseDTO valoracionDTO = new ValoracionResponseDTO(valoracion.getId(), valoracion.getNumEstrella(), valoracion.getRecomendacion());
        return valoracionDTO;
    }

    //Eliminar
    public void deleteValoracion(long id) {
        Valoracion valoracion = valoracionRepository.findById(id).get();
        valoracionRepository.delete(valoracion);
    }
}
