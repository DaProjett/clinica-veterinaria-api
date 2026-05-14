package com.veterinaria.app.service.interfaces;

import com.veterinaria.app.model.Dueno;
import java.util.List;

public interface IDuenoService {
    boolean guardar(Dueno dueno);
    List<Dueno> buscarTodos();
    Dueno buscarPorId(Long id);
    boolean actualizar(Long id, Dueno dueno);
    boolean eliminar(Long id);
}
``