package com.pits.pist_api.service;

import com.pits.pist_api.dto.*;
import com.pits.pist_api.entity.Cliente;
import com.pits.pist_api.repository.ClienteRepository;
import com.pits.pist_api.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final ClienteRepository clienteRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(ClienteRepository clienteRepo,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.clienteRepo = clienteRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest request) {
        Cliente cliente = clienteRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Credenciales inválidas"));

        if (!passwordEncoder.matches(request.getPassword(), cliente.getContraseña())) {
            throw new RuntimeException("Credenciales inválidas");
        }

        return buildLoginResponse(cliente);
    }

    public LoginResponse registro(RegistroRequest request) {
        if (clienteRepo.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El email ya está registrado");
        }

        Cliente cliente = Cliente.builder()
                .nombre(request.getNombre())
                .email(request.getEmail())
                .contraseña(passwordEncoder.encode(request.getPassword()))
                .foto(request.getFoto())
                .build();

        cliente = clienteRepo.save(cliente);
        return buildLoginResponse(cliente);
    }

    public LoginResponse refresh(RefreshRequest request) {
        String refreshToken = request.getRefreshToken();

        if (!jwtService.isTokenValid(refreshToken)) {
            throw new RuntimeException("Refresh token inválido");
        }

        String type = jwtService.getTokenType(refreshToken);
        if (!"refresh".equals(type)) {
            throw new RuntimeException("Token no es de tipo refresh");
        }

        Long idCliente = jwtService.getClienteId(refreshToken);
        String email = jwtService.getEmail(refreshToken);

        Cliente cliente = clienteRepo.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        return buildLoginResponse(cliente);
    }

    public ClienteDto me(Long idCliente) {
        Cliente cliente = clienteRepo.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        return toDto(cliente);
    }

    private LoginResponse buildLoginResponse(Cliente cliente) {
        String accessToken = jwtService.generateAccessToken(cliente.getIdCliente(), cliente.getEmail());
        String refreshToken = jwtService.generateRefreshToken(cliente.getIdCliente(), cliente.getEmail());

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tipo("Bearer")
                .expiresIn(jwtService.getAccessExpirationMs())
                .cliente(toDto(cliente))
                .build();
    }

    private ClienteDto toDto(Cliente c) {
        return ClienteDto.builder()
                .idCliente(c.getIdCliente())
                .email(c.getEmail())
                .nombre(c.getNombre())
                .foto(c.getFoto())
                .build();
    }
}
