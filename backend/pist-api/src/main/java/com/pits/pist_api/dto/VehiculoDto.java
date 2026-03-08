package com.pits.pist_api.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehiculoDto {
    private Long idVehiculo;
    private String carburacion;
    private String placa;
    private String modelo;
    private Integer año;
    private String tecnoMecanica;
    private String tipo;
    private String color;
}
