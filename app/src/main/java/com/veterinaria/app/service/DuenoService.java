package com.veterinaria.app.service;

import com.veterinaria.app.dao.DuenoDao;
import com.veterinaria.app.model.Dueno;
import com.veterinaria.app.service.interfaces.IDuenoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DuenoService implements IDuenoService {

    private final DuenoDao duenoDao;

    public DuenoService(DuenoDao duenoDao) {
        this.duenoDao = duenoDao;
    }

    @Override
    public boolean guardar(Dueno dueno) {
        if (dueno == null) {
            return false;
        }

        if (dueno.getNombreCompleto() == null || dueno.getNombreCompleto().trim().isEmpty()) {
            return false;
        }

        if (dueno.getDocumentoIdentidad() == null || dueno.getDocumentoIdentidad().trim().isEmpty()) {
            return false;
        }

        if (dueno.getTelefono() == null || dueno.getTelefono().trim().length() < 7) {
            return false;
        }

        if (dueno.getEmail() == null || !dueno.getEmail().contains("@")) {
            return false;
        }

        return duenoDao.guardar(dueno);
    }

    @Override
    public List<Dueno> buscarTodos() {
        return duenoDao.buscarTodos();
    }

    @Override
    public Dueno buscarPorId(Long id) {
        if (id == null || id <= 0) {
            return null;
        }
        return duenoDao.buscarPorId(id);
    }

    @Override
    public boolean actualizar(Long id, Dueno dueno) {
        if (id == null || id <= 0 || dueno == null) {
            return false;
        }

        if (dueno.getNombreCompleto() == null || dueno.getNombreCompleto().trim().isEmpty()) {
            return false;
        }

        if (dueno.getDocumentoIdentidad() == null || dueno.getDocumentoIdentidad().trim().isEmpty()) {
            return false;
        }

        if (dueno.getTelefono() == null || dueno.getTelefono().trim().length() < 7) {
            return false;
        }

        if (dueno.getEmail() == null || !dueno.getEmail().contains("@")) {
