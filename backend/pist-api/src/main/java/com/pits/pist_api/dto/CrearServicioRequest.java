package com.pits.pist_api.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CrearServicioRequest {
    private Long idVehiculo;
    private Long idMecanico;
    private String servicioActual;
    private String reporte;
}
