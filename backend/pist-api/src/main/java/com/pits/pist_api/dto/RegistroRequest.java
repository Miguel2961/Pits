package com.pits.pist_api.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistroRequest {
    private String nombre;
    private String email;
    private String password;
    private String foto;
}
