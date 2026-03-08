package com.pits.pist_api.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServicioDto {
    private Long idServicio;
    private Long idVehiculo;
    private String placaVehiculo;
    private Long idMecanico;
    private String nombreMecanico;
    private String servicioActual;
    private String reporte;
}
