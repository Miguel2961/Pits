package com.pits.pist_api.controller;

import com.pits.pist_api.dto.MecanicoDto;
import com.pits.pist_api.service.MecanicoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mecanicos")
public class MecanicoController {

    private final MecanicoService service;

    public MecanicoController(MecanicoService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<MecanicoDto>> listar(
            @RequestParam(required = false) Long especialidadId,
            @RequestParam(required = false) String ciudad,
            @RequestParam(required = false) Boolean disponible) {
        return ResponseEntity.ok(service.listar(especialidadId, ciudad, disponible));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MecanicoDto> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(service.obtenerPorId(id));
    }
}
