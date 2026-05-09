package com.veterinaria.app.model;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

public class Cita {

    private Long id;

    @NotNull(message = "La fecha y hora son obligatorias")
    @FutureOrPresent(message = "La fecha de la cita debe ser presente o futura")
    private LocalDateTime fechaHora;

    @NotBlank(message = "El motivo es obligatorio")
    @Size(min = 5, max = 200, message = "El motivo debe tener entre 5 y 200 caracteres")
    private String motivo;

    @NotNull(message = "El estado es obligatorio")
    private Estado estado;

    @Size(max = 500, message = "Las notas no pueden exceder 500 caracteres")
    private String notas;

    @NotNull(message = "El ID de la mascota es obligatorio")
    private Long mascotaId;  // ID de la mascota en lugar de relación JPA

    private LocalDateTime fechaCreacion;

    // Enumeración para el estado de la cita
    public enum Estado {
        PROGRAMADA, EN_PROCESO, COMPLETADA, CANCELADA
    }

    // Constructores
    public Cita() {
        this.fechaCreacion = LocalDateTime.now();
        this.estado = Estado.PROGRAMADA;
    }

    public Cita(LocalDateTime fechaHora, String motivo, Long mascotaId) {
        this();
        this.fechaHora = fechaHora;
        this.motivo = motivo;
        this.mascotaId = mascotaId;
    }

    public Cita(LocalDateTime fechaHora, String motivo, String notas, Long mascotaId) {
        this(fechaHora, motivo, mascotaId);
        this.notas = notas;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public Long getMascotaId() {
        return mascotaId;
    }

    public void setMascotaId(Long mascotaId) {
        this.mascotaId = mascotaId;
    }


    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public String toString() {
        return "Cita{" +
                "id=" + id +
                ", fechaHora=" + fechaHora +
                ", motivo='" + motivo + '\'' +
                ", estado=" + estado +
                ", notas='" + notas + '\'' +
                ", mascotaId=" + mascotaId +
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }
}