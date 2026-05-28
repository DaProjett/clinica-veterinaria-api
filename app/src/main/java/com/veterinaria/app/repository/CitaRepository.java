package com.veterinaria.app.repository;

import com.veterinaria.app.model.Cita;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CitaRepository extends JpaRepository<Cita, Long> {

    List<Cita> findByMascotaIdOrderByFechaHoraAsc(Long mascotaId);
}
