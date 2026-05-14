package com.veterinaria.app.dao.interfaces;

import com.veterinaria.app.model.Mascota;
import java.util.List;

public interface IMascotaDao {

    boolean guardar(Mascota mascota);

    List<Mascota> buscarTodos();

    Mascota buscarPorId(int id);

    boolean actualizar(Long id, Mascota mascota);

    boolean eliminar(Long id);

    List<Mascota> buscarPorDuenoId(Long duenoId);
}