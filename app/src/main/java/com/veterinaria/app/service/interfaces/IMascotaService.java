package com.veterinaria.app.service.interfaces;

import com.veterinaria.app.model.Mascota;
import java.util.List;

public interface IMascotaService {
    boolean guardar(Mascota mascota);
    List<Mascota> buscarTodos();
    Mascota buscarPorId(int id);
    List<Mascota> buscarPorDueno(int idDueno);
    boolean actualizar(int id, Mascota mascota);
    boolean eliminar(int id);
}
``