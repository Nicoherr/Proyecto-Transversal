package com.marketplace.valoracion.service;

import com.marketplace.valoracion.DTO.ValoracionRequestDTO;
import com.marketplace.valoracion.DTO.ValoracionResponseDTO;
import com.marketplace.valoracion.model.Valoracion;
import com.marketplace.valoracion.repository.ValoracionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
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
    //Actualizar
    public ValoracionResponseDTO valoracionUser(long id, ValoracionRequestDTO updateValoracion){
        Valoracion valoracion = valoracionRepository.findById(id).get();
        valoracion.setRecomendacion(updateValoracion.getRecomendacion());
        valoracion = valoracionRepository.save(valoracion);
        return new ValoracionResponseDTO(valoracion.getId(), valoracion.getRecomendacion());
    }

    //Crear
    public ValoracionResponseDTO makeValoracion(ValoracionRequestDTO newValoracion){
        Valoracion valoracion = new Valoracion(0, newValoracion.getNumEstrella(), newValoracion.getRecomendacion());
        valoracion = valoracionRepository.save(valoracion);
        return new ValoracionResponseDTO(valoracion.getId(), valoracion.getNumEstrella(), valoracion.getRecomendacion());
    }

    //Eliminar
    public void deleteValoracion(long id) {
        Valoracion valoracion = valoracionRepository.findById(id).get();
        valoracionRepository.delete(valoracion);
    }
}
