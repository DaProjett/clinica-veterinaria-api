package com.veterinaria.app.service;

import com.veterinaria.app.model.Dueno;
import com.veterinaria.app.repository.DuenoRepository;
import com.veterinaria.app.service.interfaces.IDuenoService;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class DuenoService implements IDuenoService {

    private final DuenoRepository duenoRepository;

    public DuenoService(DuenoRepository duenoRepository) {
        this.duenoRepository = duenoRepository;
    }

    @Override
    public Dueno guardar(@Valid Dueno dueno) {
        if (dueno == null) {
            throw new IllegalArgumentException("El dueño no puede ser nulo");
        }

        if (dueno.getNombreCompleto() == null || dueno.getNombreCompleto().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre completo del dueño es obligatorio");
        }

        if (dueno.getDocumentoIdentidad() == null || dueno.getDocumentoIdentidad().trim().isEmpty()) {
            throw new IllegalArgumentException("El documento de identidad del dueño es obligatorio");
        }

        return duenoRepository.save(dueno);
    }

    @Override
    public List<Dueno> buscarTodos() {
        return duenoRepository.findAll();
    }

    @Override
    public Optional<Dueno> buscarPorId(Long id) {
        if (id == null || id <= 0) {
            return Optional.empty();
        }
        return duenoRepository.findById(id);
    }

    @Override
    public Optional<Dueno> actualizar(Long id, @Valid Dueno dueno) {
        if (id == null || id <= 0) {
            return Optional.empty();
        }

        if (!duenoRepository.existsById(id)) {
            return Optional.empty();
        }

        dueno.setId(id);
        return Optional.of(duenoRepository.save(dueno));
    }

    @Override
    public boolean eliminar(Long id) {
        if (id == null || id <= 0) {
            return false;
        }

        if (!duenoRepository.existsById(id)) {
            return false;
        }

        duenoRepository.deleteById(id);
        return true;
    }
}