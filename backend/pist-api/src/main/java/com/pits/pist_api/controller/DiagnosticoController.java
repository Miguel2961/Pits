package com.pits.pist_api.controller;

import com.pits.pist_api.repository.EspecialidadRepository;
import com.pits.pist_api.repository.MecanicoRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Endpoint de diagnóstico para verificar que la DB está conectada y con datos.
 * Requiere estar logueado. Útil para comprobar que el seed se aplicó a la base correcta.
 */
@RestController
@RequestMapping("/api")
public class DiagnosticoController {

    private final EspecialidadRepository especialidadRepo;
    private final MecanicoRepository mecanicoRepo;

    public DiagnosticoController(EspecialidadRepository especialidadRepo,
                                 MecanicoRepository mecanicoRepo) {
        this.especialidadRepo = especialidadRepo;
        this.mecanicoRepo = mecanicoRepo;
    }

    @GetMapping("/diagnostico")
    public ResponseEntity<Map<String, Object>> diagnostico() {
        long numEspecialidades = especialidadRepo.count();
        long numMecanicos = mecanicoRepo.count();
        return ResponseEntity.ok(Map.of(
                "especialidades", numEspecialidades,
                "mecanicos", numMecanicos,
                "mensaje", numEspecialidades == 0 || numMecanicos == 0
                        ? "Ejecuta el seed sobre la base 'pits': scripts/seed.sql"
                        : "DB conectada y con datos."
        ));
    }
}
