package com.marketplace.pedido.service;

import com.marketplace.pedido.model.Pedido;
import com.marketplace.pedido.repository.PedidoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public Pedido save(Pedido pedido){ return pedidoRepository.save(pedido);
    }
    public List<Pedido> findAll(){ return pedidoRepository.findAll();
    }
    public Pedido findById(Long id){ return pedidoRepository.findById(id).get();
    }
    public void deleteById(Long id){
        pedidoRepository.deleteById(id);
    }
}
