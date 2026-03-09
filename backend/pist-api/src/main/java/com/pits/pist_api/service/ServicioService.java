package com.pits.pist_api.service;

import com.pits.pist_api.dto.CrearServicioRequest;
import com.pits.pist_api.dto.ServicioDto;
import com.pits.pist_api.entity.*;
import com.pits.pist_api.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioService {

    private final ServicioRepository servicioRepo;
    private final HistorialRepository historialRepo;
    private final VehiculoRepository vehiculoRepo;
    private final MecanicoRepository mecanicoRepo;
    private final ClienteRepository clienteRepo;

    public ServicioService(ServicioRepository servicioRepo,
                           HistorialRepository historialRepo,
                           VehiculoRepository vehiculoRepo,
                           MecanicoRepository mecanicoRepo,
                           ClienteRepository clienteRepo) {
        this.servicioRepo = servicioRepo;
        this.historialRepo = historialRepo;
        this.vehiculoRepo = vehiculoRepo;
        this.mecanicoRepo = mecanicoRepo;
        this.clienteRepo = clienteRepo;
    }

    @Transactional
    public ServicioDto crear(Long idCliente, CrearServicioRequest req) {
        Vehiculo vehiculo = vehiculoRepo.findById(req.getIdVehiculo())
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));

        if (!vehiculo.getCliente().getIdCliente().equals(idCliente)) {
            throw new RuntimeException("El vehículo no pertenece al cliente");
        }

        Mecanico mecanico = mecanicoRepo.findById(req.getIdMecanico())
                .orElseThrow(() -> new RuntimeException("Mecánico no encontrado"));

        Cliente cliente = clienteRepo.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Servicio servicio = Servicio.builder()
                .vehiculo(vehiculo)
                .mecanico(mecanico)
                .servicioActual(req.getServicioActual())
                .reporte(req.getReporte())
                .build();

        servicio = servicioRepo.save(servicio);

        Historial historial = Historial.builder()
                .servicio(servicio)
                .cliente(cliente)
                .informacionServicio("Servicio solicitado: " + req.getServicioActual()
                        + " | Mecánico: " + mecanico.getNombre()
                        + " | Vehículo: " + vehiculo.getPlaca())
                .build();

        historialRepo.save(historial);

        return toDto(servicio);
    }

    private ServicioDto toDto(Servicio s) {
        return ServicioDto.builder()
                .idServicio(s.getIdServicio())
                .idVehiculo(s.getVehiculo().getIdVehiculo())
                .placaVehiculo(s.getVehiculo().getPlaca())
                .idMecanico(s.getMecanico().getIdMecanico())
                .nombreMecanico(s.getMecanico().getNombre())
                .servicioActual(s.getServicioActual())
                .reporte(s.getReporte())
                .build();
    }
}
