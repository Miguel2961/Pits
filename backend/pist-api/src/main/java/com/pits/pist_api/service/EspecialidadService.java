package com.pits.pist_api.service;

import com.pits.pist_api.dto.EspecialidadDto;
import com.pits.pist_api.entity.Especialidad;
import com.pits.pist_api.repository.EspecialidadRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EspecialidadService {

    private final EspecialidadRepository repo;

    public EspecialidadService(EspecialidadRepository repo) {
        this.repo = repo;
    }

    public List<EspecialidadDto> listar() {
        return repo.findAll().stream().map(this::toDto).toList();
    }

    private EspecialidadDto toDto(Especialidad e) {
        return EspecialidadDto.builder()
                .idEspecialidad(e.getIdEspecialidad())
                .nombre(e.getNombre())
                .build();
    }
}
