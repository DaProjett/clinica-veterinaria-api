package com.veterinaria.app.service;


import com.veterinaria.app.dao.DuenoDao;
import com.veterinaria.app.model.Dueno;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DuenoService {

    private final DuenoDao duenoDao;

    public DuenoService(DuenoDao duenoDao) {
        this.duenoDao = duenoDao;
    }

    public boolean guardar(Dueno dueno) {

        // Validación
        if (dueno.getNombre() == null || dueno.getNombre().isEmpty()) {
            return false;
        }

        if (dueno.getTelefono() == null || dueno.getTelefono().length() < 7) {
            return false;
        }

        if (dueno.getEmail() == null || !dueno.getEmail().contains("@")) {
            return false;
        }

        return duenoDao.guardar(dueno);
    }

    public List<Dueno> buscarTodos() {
        return duenoDao.buscarTodos();
    }

    public Dueno buscarPorId(int id) {
        return duenoDao.buscarPorId(id);
    }

    public boolean actualizar(int id, Dueno dueno) {
        if (id <= 0) {
            return false;
        }
        dueno.setId(id);
        return duenoDao.actualizar(dueno);
    }

    public boolean eliminar(int id) {
        return duenoDao.eliminar(id);
    }
}