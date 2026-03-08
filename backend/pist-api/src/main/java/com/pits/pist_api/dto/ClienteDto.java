package com.pits.pist_api.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteDto {
    private Long idCliente;
    private String email;
    private String nombre;
    private String foto;
}
