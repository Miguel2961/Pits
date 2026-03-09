package com.pits.pist_api.controller;

import com.pits.pist_api.dto.*;
import com.pits.pist_api.service.ClienteService;
import com.pits.pist_api.service.HistorialService;
import com.pits.pist_api.service.VehiculoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes/me")
public class ClienteController {

    private final ClienteService clienteService;
    private final VehiculoService vehiculoService;
    private final HistorialService historialService;

    public ClienteController(ClienteService clienteService,
                             VehiculoService vehiculoService,
                             HistorialService historialService) {
        this.clienteService = clienteService;
        this.vehiculoService = vehiculoService;
        this.historialService = historialService;
    }

    @GetMapping
    public ResponseEntity<ClienteDto> me(Authentication auth) {
        Long idCliente = (Long) auth.getPrincipal();
        return ResponseEntity.ok(clienteService.obtenerPorId(idCliente));
    }

    @PutMapping
    public ResponseEntity<ClienteDto> actualizar(Authentication auth, @RequestBody ClienteDto dto) {
        Long idCliente = (Long) auth.getPrincipal();
        return ResponseEntity.ok(clienteService.actualizar(idCliente, dto));
    }

    @GetMapping("/vehiculos")
    public ResponseEntity<List<VehiculoDto>> vehiculos(Authentication auth) {
        Long idCliente = (Long) auth.getPrincipal();
        return ResponseEntity.ok(vehiculoService.listarPorCliente(idCliente));
    }

    @PostMapping("/vehiculos")
    public ResponseEntity<VehiculoDto> crearVehiculo(Authentication auth,
                                                     @RequestBody CrearVehiculoRequest req) {
        Long idCliente = (Long) auth.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED).body(vehiculoService.crear(idCliente, req));
    }

    @GetMapping("/historial")
    public ResponseEntity<List<HistorialDto>> historial(Authentication auth) {
        Long idCliente = (Long) auth.getPrincipal();
        return ResponseEntity.ok(historialService.listarPorCliente(idCliente));
    }
}
