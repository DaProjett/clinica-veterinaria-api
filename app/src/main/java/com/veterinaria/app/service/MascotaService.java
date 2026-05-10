package com.veterinaria.app.service;

import com.veterinaria.app.dao.DuenoDao;
import com.veterinaria.app.dao.MascotaDao;
import com.veterinaria.app.model.Mascota;
import com.veterinaria.app.service.interfaces.IMascotaService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MascotaService implements IMascotaService {

    private final MascotaDao mascotaDao;
    private final DuenoDao duenoDao;

    public MascotaService(MascotaDao mascotaDao, DuenoDao duenoDao) {
        this.mascotaDao = mascotaDao;
        this.duenoDao = duenoDao;
    }

    @Override
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

        if (duenoDao.buscarPorId((long) mascota.getIdDueno()) == null) {
            return false;
        }

        return mascotaDao.guardar(mascota);
    }

    @Override
    public List<Mascota> buscarTodos() {
        return mascotaDao.buscarTodos();
    }

    @Override
    public Mascota buscarPorId(int id) {
        if (id <= 0) {
            return null;
        }
        return mascotaDao.buscarPorId(id);
    }

    @Override
    public List<Mascota> buscarPorDueno(int idDueno) {
        if (idDueno <= 0) {
            return List.of();
        }
        return mascotaDao.buscarPorDueno(idDueno);
    }

    @Override
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

