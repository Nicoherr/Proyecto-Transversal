package com.marketplace.pedido.service;

import com.marketplace.pedido.DTO.PedidoRequestDTO;
import com.marketplace.pedido.DTO.PedidoResponseDTO;
import com.marketplace.pedido.model.Pedido;
import com.marketplace.pedido.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoService {

    @Autowired
    private final PedidoRepository pedidoRepository;

    private PedidoResponseDTO makeToPedidoResponseDTO(Pedido pedido) {
        return new PedidoResponseDTO(pedido.getId(), pedido.getNomProducto(), pedido.getTipoProducto(), pedido.getPrecio());
    }
    //Listar
    public List<PedidoResponseDTO> findAllPedidos(){
        return pedidoRepository.findAll().stream().map(this::makeToPedidoResponseDTO).collect(Collectors.toList());
    }

    //Crear
    public PedidoResponseDTO makePedido(PedidoRequestDTO newPedido){
        Pedido pedido = new Pedido(0, newPedido.getNomProducto(), newPedido.getTipoProducto(), newPedido.getPrecio());
        pedido = pedidoRepository.save(pedido);
        return new PedidoResponseDTO(pedido.getId(), pedido.getNomProducto(), pedido.getTipoProducto(), pedido.getPrecio());
    }


    //Buscar
    public PedidoRequestDTO findPedidoById(long id){
        Pedido pedido = pedidoRepository.findById(id).get();
        return new PedidoRequestDTO(pedido.getNomProducto(), pedido.getTipoProducto(), pedido.getPrecio());
    }


    //Eliminar
    public void deletePedido(long id){
        Pedido pedido = pedidoRepository.findById(id).get();
        pedidoRepository.delete(pedido);

    }
}
