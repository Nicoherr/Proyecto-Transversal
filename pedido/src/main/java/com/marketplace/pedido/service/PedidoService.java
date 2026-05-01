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

@Service
@RequiredArgsConstructor
public class PedidoService {

    @Autowired
    private final PedidoRepository pedidoRepository;

    private PedidoResponseDTO makeToReporteResponseDTO(Pedido pedido{
        return new PedidoResponseDTO(pedido.getId(),pedido.getNomProducto(),pedido.getTipoProducto(),pedido.getPrecio());

    //Listar
    public List<Pedido> getPedido(){
        return pedidoRepository.findAll();
    }

    //Crear
    public PedidoRequestDTO createPedido(PedidoResponseDTO newPedidoDTO){
        Pedido pedido = new Pedido(0,newPedidoDTO.getNomProducto(), newPedidoDTO.getTipoProducto(), newPedidoDTO.getPrecio());
        pedido = pedidoRepository.save(pedido);
        PedidoRequestDTO pedidoDTO = new PedidoRequestDTO(pedido.getId(), pedido.getNomProducto(),pedido.getTipoProducto(), pedido.getPrecio());
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
