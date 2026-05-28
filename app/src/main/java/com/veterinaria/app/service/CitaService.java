package com.veterinaria.app.service;

import com.veterinaria.app.model.Cita;
import com.veterinaria.app.model.Mascota;
import com.veterinaria.app.repository.CitaRepository;
import com.veterinaria.app.service.interfaces.ICitaService;
import com.veterinaria.app.service.interfaces.IMascotaService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CitaService implements ICitaService {

    private final CitaRepository citaRepository;
    private final IMascotaService mascotaService;

    public CitaService(CitaRepository citaRepository, IMascotaService mascotaService) {
        this.citaRepository = citaRepository;
        this.mascotaService = mascotaService;
    }

    @Override
    public Cita guardar(@Valid Cita cita) {
        validarCita(cita);
        prepararCita(cita, null);
        return citaRepository.save(cita);
    }

    @Override
    public List<Cita> buscarTodos() {
        return citaRepository.findAll();
    }

    @Override
    public Optional<Cita> buscarPorId(Long id) {
        if (id == null || id <= 0) {
            return Optional.empty();
        }
        return citaRepository.findById(id);
    }

    @Override
    public List<Cita> buscarPorMascota(Long mascotaId) {
        if (mascotaId == null || mascotaId <= 0) {
            return List.of();
        }
        return citaRepository.findByMascotaIdOrderByFechaHoraAsc(mascotaId);
    }

    @Override
    public Optional<Cita> actualizar(Long id, @Valid Cita cita) {
        if (id == null || id <= 0 || !citaRepository.existsById(id)) {
            return Optional.empty();
        }

        validarCita(cita);
        Cita citaExistente = citaRepository.findById(id).orElseThrow();
        prepararCita(cita, citaExistente);
        cita.setId(id);

        return Optional.of(citaRepository.save(cita));
    }

    @Override
    public boolean eliminar(Long id) {
        if (id == null || id <= 0 || !citaRepository.existsById(id)) {
            return false;
        }

        citaRepository.deleteById(id);
        return true;
    }

    private void validarCita(Cita cita) {
        if (cita == null) {
            throw new IllegalArgumentException("La cita no puede ser nula");
        }

        if (cita.getFechaHora() == null) {
            throw new IllegalArgumentException("La fecha y hora de la cita son obligatorias");
        }

        if (cita.getFechaHora().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("No se puede agendar una cita en el pasado");
        }

        if (cita.getMotivo() == null || cita.getMotivo().trim().isEmpty()) {
            throw new IllegalArgumentException("El motivo de la cita es obligatorio");
        }

        if (cita.getMascota() == null || cita.getMascota().getId() == null) {
            throw new IllegalArgumentException("La cita debe estar asociada a una mascota");
        }
    }

    private void prepararCita(Cita cita, Cita citaExistente) {
        Optional<Mascota> mascotaExistente = mascotaService.buscarPorId(cita.getMascota().getId());
        if (mascotaExistente.isEmpty()) {
            throw new IllegalArgumentException("La mascota especificada no existe");
        }

        cita.setMascota(mascotaExistente.get());

        if (cita.getEstado() == null) {
            cita.setEstado(Cita.Estado.PROGRAMADA);
        }

        if (citaExistente != null) {
            cita.setFechaCreacion(citaExistente.getFechaCreacion());
        } else if (cita.getFechaCreacion() == null) {
            cita.setFechaCreacion(LocalDateTime.now());
        }
    }
}
