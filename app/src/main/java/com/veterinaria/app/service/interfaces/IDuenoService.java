package com.veterinaria.app.service.interfaces;

import com.veterinaria.app.model.Dueno;
import java.util.List;
import java.util.Optional;

public interface IDuenoService {
    Dueno guardar(Dueno dueno);
    List<Dueno> buscarTodos();
    Optional<Dueno> buscarPorId(Long id);
    Optional<Dueno> actualizar(Long id, Dueno dueno);
    boolean eliminar(Long id);
}