package com.veterinaria.app.dao.interfaces;

import com.veterinaria.app.model.Dueno;
import java.util.List;

public interface IDuenoDao {

    Dueno buscarPorId(int id);

    List<Dueno> buscarTodos();

    boolean guardar(Dueno dueno);

    boolean actualizar(Long id, Dueno dueno);

    boolean eliminar(Long id);
}