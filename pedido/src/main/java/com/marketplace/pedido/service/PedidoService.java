package com.marketplace.pedido.service;

import com.marketplace.pedido.DTO.PedidoDTO;
import com.marketplace.pedido.DTO.PedidoNewDTO;
import com.marketplace.pedido.model.Pedido;
import com.marketplace.pedido.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public PedidoDTO createdPedido(PedidoNewDTO newPedidoDTO){
        Pedido pedido = new Pedido(0,newPedidoDTO.getNomProducto(), newPedidoDTO.getTipoProducto(),0);
        pedido = pedidoRepository.save(pedido);
        PedidoDTO pedidoDTO = new PedidoDTO(pedido.getId(), pedido.getNomProducto(), pedido.getTipoProducto());
        return pedidoDTO;
    }
    public List<Pedido> findAll(){ return pedidoRepository.findAll();
    }
    public Pedido findById(Long id){ return pedidoRepository.findById(id).get();
    }
    public void deleteById(Long id){
        pedidoRepository.deleteById(id);
    }
}
