package com.pits.pist_api.controller;

import com.pits.pist_api.dto.CrearServicioRequest;
import com.pits.pist_api.dto.ServicioDto;
import com.pits.pist_api.service.ServicioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/servicios")
public class ServicioController {

    private final ServicioService service;

    public ServicioController(ServicioService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ServicioDto> crear(Authentication auth,
                                             @RequestBody CrearServicioRequest req) {
        Long idCliente = (Long) auth.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED).body(service.crear(idCliente, req));
    }
}
