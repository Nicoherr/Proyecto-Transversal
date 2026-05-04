package com.marketplace.pago.service;

import com.marketplace.pago.DTO.PagoRequestDTO;
import com.marketplace.pago.DTO.PagoResponseDTO;
import com.marketplace.pago.model.Pago;
import com.marketplace.pago.repository.PagoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.startup.ContextRuleSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PagoService {

    @Autowired
    private final PagoRepository pagoRepository;

    private PagoResponseDTO makeToPagoResponseDTO(Pago pago){
        return new PagoResponseDTO(pago.getId(), pago.getMetodoPago(), pago.getComprobante(), pago.getFecha());
    }

    //Listar
    public List<PagoResponseDTO> findAllPagos(){
        return pagoRepository.findAll().stream().map(this::makeToPagoResponseDTO).collect(Collectors.toList());
    }

    //Buscar
    public PagoRequestDTO findPagosById(long id) {
        Pago pago = pagoRepository.findById(id).get();
        return new PagoRequestDTO(pago.getMetodoPago());
    }

    //Crear
    public PagoResponseDTO makePago(PagoRequestDTO newPago){
        Pago pago = new Pago(0, newPago.getMetodoPago(), newPago.getMetodoPago(), new Date ());
        pago = pagoRepository.save(pago);
        return new PagoResponseDTO(pago.getId(), pago.getMetodoPago(), pago.getComprobante(), pago.getFecha());
    }


}
