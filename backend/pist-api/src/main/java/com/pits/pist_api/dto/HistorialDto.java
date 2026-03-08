package com.pits.pist_api.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistorialDto {
    private Long idReporte;
    private ServicioDto servicio;
    private String informacionServicio;
}
