package com.pits.pist_api.service;

import com.pits.pist_api.dto.HistorialDto;
import com.pits.pist_api.dto.ServicioDto;
import com.pits.pist_api.entity.Historial;
import com.pits.pist_api.entity.Servicio;
import com.pits.pist_api.repository.HistorialRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistorialService {

    private final HistorialRepository repo;

    public HistorialService(HistorialRepository repo) {
        this.repo = repo;
    }

    public List<HistorialDto> listarPorCliente(Long idCliente) {
        return repo.findByClienteIdCliente(idCliente)
                .stream().map(this::toDto).toList();
    }

    private HistorialDto toDto(Historial h) {
        Servicio s = h.getServicio();
        ServicioDto servicioDto = ServicioDto.builder()
                .idServicio(s.getIdServicio())
                .idVehiculo(s.getVehiculo().getIdVehiculo())
                .placaVehiculo(s.getVehiculo().getPlaca())
                .idMecanico(s.getMecanico().getIdMecanico())
                .nombreMecanico(s.getMecanico().getNombre())
                .servicioActual(s.getServicioActual())
                .reporte(s.getReporte())
                .build();

        return HistorialDto.builder()
                .idReporte(h.getIdReporte())
                .servicio(servicioDto)
                .informacionServicio(h.getInformacionServicio())
                .build();
    }
}
