package com.pits.pist_api.controller;

import com.pits.pist_api.dto.EspecialidadDto;
import com.pits.pist_api.service.EspecialidadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/especialidades")
public class EspecialidadController {

    private final EspecialidadService service;

    public EspecialidadController(EspecialidadService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<EspecialidadDto>> listar() {
        return ResponseEntity.ok(service.listar());
    }
}
