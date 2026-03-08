package com.pits.pist_api.dto;

import lombok.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MecanicoDto {
    private Long idMecanico;
    private String nombre;
    private String contacto;
    private String certificacion;
    private Integer edad;
    private Integer experiencia;
    private String ciudad;
    private String foto;
    private Boolean disponible;
    private Double rating;
    private Integer cantidadValoraciones;
    private List<EspecialidadDto> especialidades;
}
