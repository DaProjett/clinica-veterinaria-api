package com.veterinaria.app.service;


import com.veterinaria.app.dao.DuenoDao;
import com.veterinaria.app.MascotaRepository;
import com.veterinaria.app.dao.MascotaDao;
import com.veterinaria.app.model.Mascota;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MascotaService {

    private final MascotaRepository mascotaRepository;
    private final MascotaDao mascotaDao;
    private final DuenoDao duenoDao;

    
    public MascotaService(MascotaRepository mascotaRepository, DuenoDao duenoDao) {
        this.mascotaRepository = mascotaRepository;
        this.duenoDao = duenoDao;
    }

    public boolean guardar(Mascota mascota) {
        if (mascota == null) {
            return false;
        }

        if (mascota == null || mascota.getNombre() == null || mascota.getNombre().trim().isEmpty()) {
            return false;
        }

        if (mascota.getEdad() < 0) {
            return false;
        }

        if (mascota.getIdDueno() == null || mascota.getIdDueno() <= 0) {
            return false;
        }

        if (duenoDao.buscarPorId(mascota.getIdDueno()) == null) {
            return false;
        }

        mascotaRepository.save(mascota);
        return true;
    }

    public List<Mascota> buscarTodos() {
       return mascotaRepository.findAll();
    }

    public Mascota buscarPorId(int id) {
        if (id <= 0) {
            return null;
        }
        return mascotaRepository.findById((long) id).orElse(null);
    }

    public List<Mascota> buscarPorDueno(int idDueno) {
        if (idDueno <= 0) {
            return List.of();
        }
         return mascotaRepository.findByDuenoId((long) idDueno);
    }

    public boolean actualizar(int id, Mascota mascota) {
        if (id <= 0 || mascota == null || mascota.getNombre() == null || mascota.getNombre().trim().isEmpty()) {
            return false;
        }

        if (mascota.getEdad() < 0) {
            return false;
        }

        if (mascota.getIdDueno() == null || mascota.getIdDueno() <= 0) {
            return false;
        }

        if (!mascotaRepository.existsById((long) id)) {
            return false;
        }

        if (duenoDao.buscarPorId(mascota.getIdDueno()) == null) {
            return false;
        }

        mascota.setId(id);
        mascotaRepository.save(mascota);
        return true;
    }

    public boolean eliminar(int id) {
        if (id <= 0) {
            return false;
        }
        if (!mascotaRepository.existsById((long) id)) {
            return false;
        }

        mascotaRepository.deleteById((long) id);
        return true;
    }
}