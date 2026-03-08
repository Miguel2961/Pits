package com.pits.pist_api.service;

import com.pits.pist_api.dto.EspecialidadDto;
import com.pits.pist_api.dto.MecanicoDto;
import com.pits.pist_api.entity.Mecanico;
import com.pits.pist_api.repository.MecanicoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MecanicoService {

    private final MecanicoRepository repo;

    public MecanicoService(MecanicoRepository repo) {
        this.repo = repo;
    }

    public List<MecanicoDto> listar(Long especialidadId, String ciudad, Boolean disponible) {
        boolean sinFiltros = especialidadId == null
                && (ciudad == null || ciudad.isBlank())
                && disponible == null;
        if (sinFiltros) {
            return repo.findAll().stream().map(this::toDto).toList();
        }
        return repo.findWithFilters(especialidadId, ciudad, disponible)
                .stream().map(this::toDto).toList();
    }

    public MecanicoDto obtenerPorId(Long id) {
        Mecanico m = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Mecánico no encontrado"));
        return toDto(m);
    }

    private MecanicoDto toDto(Mecanico m) {
        List<EspecialidadDto> especialidades = m.getEspecialidades().stream()
                .map(e -> EspecialidadDto.builder()
                        .idEspecialidad(e.getIdEspecialidad())
                        .nombre(e.getNombre())
                        .build())
                .toList();

        return MecanicoDto.builder()
                .idMecanico(m.getIdMecanico())
                .nombre(m.getNombre())
                .contacto(m.getContacto())
                .certificacion(m.getCertificacion())
                .edad(m.getEdad())
                .experiencia(m.getExperiencia())
                .ciudad(m.getCiudad())
                .foto(m.getFoto())
                .disponible(m.getDisponible())
                .rating(m.getRating())
                .cantidadValoraciones(m.getCantidadValoraciones())
                .especialidades(especialidades)
                .build();
    }
}
