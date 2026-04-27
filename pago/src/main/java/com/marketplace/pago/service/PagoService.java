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

    //Listar
    public List<Pago> getPago(){
        return pagoRepository.findAll();
    }

    //Crear
    public PagoDTO createPago(PagoNewDTO newPagoDTO){
        Pago pago = new Pago(0, newPagodto.getMetodoPago(), newPagoDTO.getComprobante(), new Date());
        pago = pagoRepository.save(pago);
        PagoDTO pagoDTO = new PagoDTO(pago.getId(), pago.getMetodopago());
        return pagoDTO;
    }

    //Buscar
    public Pago getPago(long id){
        return pagoRepository.findById(id).get();
    }

}
