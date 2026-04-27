package com.marketplace.valoracion.service;

import com.marketplace.valoracion.model.Valoracion;
import com.marketplace.valoracion.repository.ValoracionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ValoracionService {

    @Autowired
    private ValoracionRepository valoracionRepository;

    //Listar
    public List<Valoracion> getValoracion(){
        return valoracionRepository.findAll();
    }

    //Crear
    public ValoracionDTO createValoracion(ValoracionNewDTO newValoracionDTO){
        Valoracion valoracion = new Valoracion(0, newValoracionDTO.getNumEstrella(), newValoracionDTO.getRecomendacion());
        valoracion = valoracionRepository.save(valoracion);
        ValoracionDTO valoracionDTO = new ValoracionDTO(valoracion.getId(), valoracion.getNumEstrella(), valoracion.getRecomendacion());
        return valoracionDTO;
    }

    //Buscar
    public Valoracion getValoracion(long id){
        return valoracionRepository.findById(id).get();
    }

    //Eliminar
    public void deleteValoracion(long id){
        Valoracion valoracion = valoracionRepository.findById(id).get();
        valoracionRepository.delete(valoracion);
    }
}
