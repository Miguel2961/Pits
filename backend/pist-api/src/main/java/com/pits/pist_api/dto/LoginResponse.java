package com.pits.pist_api.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private String tipo;
    private long expiresIn;
    private ClienteDto cliente;
}
