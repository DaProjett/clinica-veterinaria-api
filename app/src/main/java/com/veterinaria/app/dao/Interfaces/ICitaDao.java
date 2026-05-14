package com.veterinaria.app.dao.interfaces;

import com.veterinaria.app.model.Cita;
import java.util.List;

public interface ICitaDao {

    boolean guardar(Cita cita);

    List<Cita> buscarTodos();

    Cita buscarPorId(int id);

    boolean actualizar(Long id, Cita cita);

    boolean eliminar(Long id);
}