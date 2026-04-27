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

    //Listar
    public List<Pedido> getPedido(){
        return pedidoRepository.findAll();
    }

    //Crear
    public PedidoDTO createPedido(PedidoNewDTO newPedidoDTO){
        Pedido pedido = new Pedido(0,newPedidoDTO.getNomProducto(), newPedidoDTO.getTipoProducto(), newPedidoDTO.getPrecio());
        pedido = pedidoRepository.save(pedido);
        PedidoDTO pedidoDTO = new PedidoDTO(pedido.getId(), pedido.getNomProducto, pedido.getTipoProducto(), pedido.getPrecio());
        return pedidoDTO;
    }

    //Buscar
    public Pedido getPedido(long id){
        return pedidoRepository.findById(id).get();
    }

    //Eliminar
    public void deletePedido(long id){
        Pedido pedido = pedidoRepository.findById(id).get();
        pedidoRepository.delete(pedido);

    }
}
