package com.veterinaria.app.service;


import com.veterinaria.app.dao.CitaDao;
import com.veterinaria.app.dao.MascotaDao;
import com.veterinaria.app.model.Cita;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CitaService {

    private final CitaDao citaDao;
    private final MascotaDao mascotaDao;

    public CitaService(CitaDao citaDao, MascotaDao mascotaDao) {
        this.citaDao = citaDao;
        this.mascotaDao = mascotaDao;
    }

    public boolean guardar(Cita cita) {
        if (cita == null) {
            return false;
        }

        if (cita.getFechaHora() == null) {
            return false;
        }

        if (cita.getFechaHora().isBefore(LocalDateTime.now())) {
            return false;
        }

        if (cita.getIdMascota() <= 0) {
            return false;
        }

        if (mascotaDao.buscarPorId(cita.getIdMascota()) == null) {
            return false;
        }

        return citaDao.guardar(cita);
    }

    public List<Cita> buscarTodos() {
        return citaDao.buscarTodos();
    }

    public Cita buscarPorId(int id) {
        if (id <= 0) {
            return null;
        }
        return citaDao.buscarPorId(id);
    }

    public boolean actualizar(int id, Cita cita) {
        if (id <= 0 || cita == null) {
            return false;
        }

        if (cita.getFechaHora() == null) {
            return false;
        }

        if (cita.getFechaHora().isBefore(LocalDateTime.now())) {
            return false;
        }

        if (cita.getIdMascota() <= 0) {
            return false;
        }

        if (mascotaDao.buscarPorId(cita.getIdMascota()) == null) {
            return false;
        }

        cita.setId(id);
        return citaDao.actualizar(cita);
    }

    public boolean eliminar(int id) {
        if (id <= 0) {
            return false;
        }
        return citaDao.eliminar(id);
    }
}