package com.pits.pist_api.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "mecanico")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mecanico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_mecanico")
    private Long idMecanico;

    @Column(nullable = false, length = 100)
    private String nombre;

    @Column(length = 100)
    private String contacto;

    @Column(length = 255)
    private String certificacion;

    @Column(name = "edad")
    private Integer edad;

    @Column(name = "experiencia")
    private Integer experiencia;

    @Column(length = 100)
    private String ciudad;

    @Column(name = "disponible", nullable = false)
    @Builder.Default
    private Boolean disponible = true;

    @ManyToMany
    @JoinTable(
            name = "mecanico_especialidad",
            joinColumns = @JoinColumn(name = "id_mecanico"),
            inverseJoinColumns = @JoinColumn(name = "id_especialidad")
    )
    @Builder.Default
    private List<Especialidad> especialidades = new ArrayList<>();

    @OneToMany(mappedBy = "mecanico", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<Servicio> servicios = new ArrayList<>();
}
