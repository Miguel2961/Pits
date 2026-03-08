package com.pits.pist_api.service;

import com.pits.pist_api.dto.ClienteDto;
import com.pits.pist_api.entity.Cliente;
import com.pits.pist_api.repository.ClienteRepository;
import org.springframework.stereotype.Service;

@Service
public class ClienteService {

    private final ClienteRepository repo;

    public ClienteService(ClienteRepository repo) {
        this.repo = repo;
    }

    public ClienteDto obtenerPorId(Long id) {
        Cliente c = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));
        return toDto(c);
    }

    public ClienteDto actualizar(Long id, ClienteDto dto) {
        Cliente c = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        if (dto.getNombre() != null) c.setNombre(dto.getNombre());
        if (dto.getFoto() != null) c.setFoto(dto.getFoto());

        c = repo.save(c);
        return toDto(c);
    }

    private ClienteDto toDto(Cliente c) {
        return ClienteDto.builder()
                .idCliente(c.getIdCliente())
                .email(c.getEmail())
                .nombre(c.getNombre())
                .foto(c.getFoto())
                .build();
    }
}
