package com.marketplace.pago.service;

import com.marketplace.pago.model.Pago;
import com.marketplace.pago.repository.PagoRepository;
import jakarta.transaction.Transactional;
import org.apache.catalina.startup.ContextRuleSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PagoService {

    @Autowired
    private PagoRepository pagoRepository;

    public Pago save(Pago pago){
        return pagoRepository.save(pago);
    }

    public List<Pago> findAll(){
        return pagoRepository.findAll();
    }

    public Pago findById(Long id){
        return pagoRepository.findById(id).get();

    }

}
