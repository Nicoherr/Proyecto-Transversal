package com.marketplace.reporte.controller;

import com.marketplace.reporte.service.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;


    //CREATE
    @PostMapping
    public Reporte

    //READ
    //USAR TRY/CATCH PARA CUANDO NO HAY UN ID
    @GetMapping("/{id}")
    @GetMapping


    //UPDATE
    @PutMapping("/{id}")


    //DELETE
    @DeleteMapping("/{id}")
    @DeleteMapping
}
