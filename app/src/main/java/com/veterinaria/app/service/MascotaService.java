package com.veterinaria.app.service;

import com.veterinaria.app.model.Mascota;
import com.veterinaria.app.repository.MascotaRepository;
import com.veterinaria.app.service.interfaces.IMascotaService;
import com.veterinaria.app.service.interfaces.IDuenoService;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class MascotaService implements IMascotaService {

    private final MascotaRepository mascotaRepository;
    private final IDuenoService duenoService;

    public MascotaService(MascotaRepository mascotaRepository, IDuenoService duenoService) {
        this.mascotaRepository = mascotaRepository;
        this.duenoService = duenoService;
    }

    @Override
    public Mascota guardar(@Valid Mascota mascota) {
        if (mascota == null) {
            throw new IllegalArgumentException("La mascota no puede ser nula");
        }

        if (mascota.getNombre() == null || mascota.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la mascota es obligatorio");
        }

        if (mascota.getSexo() == null) {
            throw new IllegalArgumentException("El sexo de la mascota es obligatorio");
        }

        if (mascota.getDueno() == null || mascota.getDueno().getId() == null) {
            throw new IllegalArgumentException("La mascota debe tener un dueño");
        }

        // Validar que el dueño exista
        Optional<com.veterinaria.app.model.Dueno> duenoExistente = duenoService.buscarPorId(mascota.getDueno().getId());
        if (duenoExistente.isEmpty()) {
            throw new IllegalArgumentException("El dueño especificado no existe");
        }

        // Asignar el dueño existente
        mascota.setDueno(duenoExistente.get());

        return mascotaRepository.save(mascota);
    }

    @Override
    public List<Mascota> buscarTodos() {
        return mascotaRepository.findAll();
    }

    @Override
    public Optional<Mascota> buscarPorId(Long id) {
        if (id == null || id <= 0) {
            return Optional.empty();
        }
        return mascotaRepository.findById(id);
    }

    @Override
    public List<Mascota> buscarPorDueno(Long idDueno) {
        if (idDueno == null || idDueno <= 0) {
            return List.of();
        }
        return mascotaRepository.findByDuenoId(idDueno);
    }

    @Override
    public Optional<Mascota> actualizar(Long id, @Valid Mascota mascota) {
        if (id == null || id <= 0) {
            return Optional.empty();
        }

        if (!mascotaRepository.existsById(id)) {
            return Optional.empty();
        }

        // Validar que el dueño exista
        if (mascota.getDueno() != null && mascota.getDueno().getId() != null) {
            Optional<com.veterinaria.app.model.Dueno> duenoExistente = duenoService.buscarPorId(mascota.getDueno().getId());
            if (duenoExistente.isEmpty()) {
                throw new IllegalArgumentException("El dueño especificado no existe");
            }
            mascota.setDueno(duenoExistente.get());
        }

        mascota.setId(id);
        return Optional.of(mascotaRepository.save(mascota));
    }

    @Override
    public boolean eliminar(Long id) {
        if (id == null || id <= 0) {
            return false;
        }

        if (!mascotaRepository.existsById(id)) {
            return false;
        }

        mascotaRepository.deleteById(id);
        return true;
    }

    public List<Mascota> buscarPorNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            return List.of();
        }
        return mascotaRepository.findByNombreContaining(nombre);
    }

    public List<Mascota> buscarPorEspecie(String especie) {
        if (especie == null || especie.trim().isEmpty()) {
            return List.of();
        }
        return mascotaRepository.findByEspecie(especie);
    }
}