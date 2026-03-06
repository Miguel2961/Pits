package com.pits.pist_api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vehiculo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_vehiculo")
    private Long idVehiculo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @Column(length = 100)
    private String carburacion;

    @Column(nullable = false, length = 20)
    private String placa;

    @Column(length = 100)
    private String modelo;

    @Column(name = "anio")
    private Integer año;

    @Column(name = "tecno_mecanica", length = 100)
    private String tecnoMecanica;

    @Column(length = 50)
    private String tipo;

    @Column(length = 50)
    private String color;

    @OneToMany(mappedBy = "vehiculo", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Servicio> servicios = new ArrayList<>();
}
