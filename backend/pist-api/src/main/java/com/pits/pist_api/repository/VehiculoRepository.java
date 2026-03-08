package com.pits.pist_api.repository;

import com.pits.pist_api.entity.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
    List<Vehiculo> findByClienteIdCliente(Long idCliente);
}
