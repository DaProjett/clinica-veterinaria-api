package com.veterinaria.app.repository;

import com.veterinaria.app.model.Mascota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface MascotaRepository extends JpaRepository<Mascota, Long> {
    
    List<Mascota> findByDuenoId(Long duenoId);
    
    @Query("SELECT m FROM Mascota m WHERE m.nombre LIKE %:nombre%")
    List<Mascota> findByNombreContaining(@Param("nombre") String nombre);
    
    @Query("SELECT m FROM Mascota m WHERE m.especie = :especie")
    List<Mascota> findByEspecie(@Param("especie") String especie);
    
    boolean existsByDuenoId(Long duenoId);
}