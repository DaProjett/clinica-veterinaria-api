package com.veterinaria.app.service.interfaces;

import com.veterinaria.app.model.Mascota;
import java.util.List;
import java.util.Optional;

public interface IMascotaService {
    Mascota guardar(Mascota mascota);
    List<Mascota> buscarTodos();
    Optional<Mascota> buscarPorId(Long id);
    List<Mascota> buscarPorDueno(Long idDueno);
    Optional<Mascota> actualizar(Long id, Mascota mascota);
    boolean eliminar(Long id);
}