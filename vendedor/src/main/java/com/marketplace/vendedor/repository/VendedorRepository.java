package com.marketplace.vendedor.repository;

import com.marketplace.vendedor.model.Vendedor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VendedorRepository extends JpaRepository<Vendedor, Long> {

    List<Vendedor> findByActivoTrue();
}

