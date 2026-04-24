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

    public Valoracion save(Valoracion valoracion){ return valoracionRepository.save(valoracion);
    }

    public Valoracion findById(Long id){ return valoracionRepository.findById(id).get();
    }

    public List<Valoracion> findAll(){
        return valoracionRepository.findAll();
    }

    public void deleteValoracion(long id){
        Valoracion valoracion = valoracionRepository.findById(id).get();
        valoracionRepository.delete(valoracion);
    }

}
