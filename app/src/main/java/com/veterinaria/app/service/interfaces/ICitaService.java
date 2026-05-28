package com.veterinaria.app.service.interfaces;

import com.veterinaria.app.model.Cita;
import java.util.List;
import java.util.Optional;

public interface ICitaService {
    Cita guardar(Cita cita);
    List<Cita> buscarTodos();
    Optional<Cita> buscarPorId(Long id);
    List<Cita> buscarPorMascota(Long mascotaId);
    Optional<Cita> actualizar(Long id, Cita cita);
    boolean eliminar(Long id);
}
