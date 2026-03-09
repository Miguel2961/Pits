package com.pits.pist_api.repository;

import com.pits.pist_api.entity.Mecanico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MecanicoRepository extends JpaRepository<Mecanico, Long> {

    @Query("SELECT DISTINCT m FROM Mecanico m JOIN m.especialidades e " +
           "WHERE (:especialidadId IS NULL OR e.idEspecialidad = :especialidadId) " +
           "AND (:ciudad IS NULL OR m.ciudad = :ciudad) " +
           "AND (:disponible IS NULL OR m.disponible = :disponible)")
    List<Mecanico> findWithFilters(@Param("especialidadId") Long especialidadId,
                                   @Param("ciudad") String ciudad,
                                   @Param("disponible") Boolean disponible);
}
