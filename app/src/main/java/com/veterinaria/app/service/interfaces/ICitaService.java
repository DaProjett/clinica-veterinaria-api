package com.veterinaria.app.service.interfaces;

import com.veterinaria.app.model.Cita;
import java.util.List;

public interface ICitaService {
    boolean guardar(Cita cita);
    List<Cita> buscarTodos();
    Cita buscarPorId(int id);
    boolean actualizar(int id, Cita cita);
    boolean eliminar(int id);
}
``