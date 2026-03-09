package com.pits.pist_api.service;

import com.pits.pist_api.dto.CrearVehiculoRequest;
import com.pits.pist_api.dto.VehiculoDto;
import com.pits.pist_api.entity.Cliente;
import com.pits.pist_api.entity.Vehiculo;
import com.pits.pist_api.repository.ClienteRepository;
import com.pits.pist_api.repository.VehiculoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehiculoService {

    private final VehiculoRepository vehiculoRepo;
    private final ClienteRepository clienteRepo;

    public VehiculoService(VehiculoRepository vehiculoRepo, ClienteRepository clienteRepo) {
        this.vehiculoRepo = vehiculoRepo;
        this.clienteRepo = clienteRepo;
    }

    public List<VehiculoDto> listarPorCliente(Long idCliente) {
        return vehiculoRepo.findByClienteIdCliente(idCliente)
                .stream().map(this::toDto).toList();
    }

    public VehiculoDto crear(Long idCliente, CrearVehiculoRequest req) {
        Cliente cliente = clienteRepo.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Vehiculo v = Vehiculo.builder()
                .cliente(cliente)
                .carburacion(req.getCarburacion())
                .placa(req.getPlaca())
                .modelo(req.getModelo())
                .año(req.getAño())
                .tecnoMecanica(req.getTecnoMecanica())
                .tipo(req.getTipo())
                .color(req.getColor())
                .build();

        v = vehiculoRepo.save(v);
        return toDto(v);
    }

    private VehiculoDto toDto(Vehiculo v) {
        return VehiculoDto.builder()
                .idVehiculo(v.getIdVehiculo())
                .carburacion(v.getCarburacion())
                .placa(v.getPlaca())
                .modelo(v.getModelo())
                .año(v.getAño())
                .tecnoMecanica(v.getTecnoMecanica())
                .tipo(v.getTipo())
                .color(v.getColor())
                .build();
    }
}
