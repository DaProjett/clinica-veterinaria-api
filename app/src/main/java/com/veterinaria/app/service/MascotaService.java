package com.veterinaria.service;

import com.veterinaria.dao.DuenoDao;
import com.veterinaria.dao.MascotaDao;
import com.veterinaria.model.Mascota;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MascotaService {

    private final MascotaDao mascotaDao;
    private final DuenoDao duenoDao;

    public MascotaService(MascotaDao mascotaDao, DuenoDao duenoDao) {
        this.mascotaDao = mascotaDao;
        this.duenoDao = duenoDao;
    }

    public boolean guardar(Mascota mascota) {
        if (mascota == null) {
            return false;
        }

        if (mascota.getNombre() == null || mascota.getNombre().trim().isEmpty()) {
            return false;
        }

        if (mascota.getEdad() < 0) {
            return false;
        }

        if (mascota.getIdDueno() <= 0) {
            return false;
        }

        if (duenoDao.buscarPorId(mascota.getIdDueno()) == null) {
            return false;
        }

        return mascotaDao.guardar(mascota);
    }

    public List<Mascota> buscarTodos() {
        return mascotaDao.buscarTodos();
    }

    public Mascota buscarPorId(int id) {
        if (id <= 0) {
            return null;
        }
        return mascotaDao.buscarPorId(id);
    }

    public List<Mascota> buscarPorDueno(int idDueno) {
        if (idDueno <= 0) {
            return List.of();
        }
        return mascotaDao.buscarPorDueno(idDueno);
    }

    public boolean actualizar(int id, Mascota mascota) {
        if (id <= 0 || mascota == null) {
            return false;
        }

        if (mascota.getNombre() == null || mascota.getNombre().trim().isEmpty()) {
            return false;
        }

        if (mascota.getEdad() < 0) {
            return false;
        }

        if (mascota.getIdDueno() <= 0) {
            return false;
        }

        if (duenoDao.buscarPorId(mascota.getIdDueno()) == null) {
            return false;
        }

        mascota.setId(id);
        return mascotaDao.actualizar(mascota);
    }

    public boolean eliminar(int id) {
        if (id <= 0) {
            return false;
        }
        return mascotaDao.eliminar(id);
    }
}