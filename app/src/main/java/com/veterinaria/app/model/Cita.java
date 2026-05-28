package com.veterinaria.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;

@Entity
@Table(name = "citas")
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La fecha y hora son obligatorias")
    @FutureOrPresent(message = "La fecha de la cita debe ser presente o futura")
    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @NotBlank(message = "El motivo es obligatorio")
    @Size(min = 2, max = 200, message = "El motivo debe tener entre 2 y 200 caracteres")
    @Column(nullable = false, length = 200)
    private String motivo;

    @NotNull(message = "El estado es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Estado estado;

    @Size(max = 500, message = "Las notas no pueden exceder 500 caracteres")
    @Column(length = 500)
    private String notas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mascota_id", nullable = false)
    @JsonIgnoreProperties({"citas", "hibernateLazyInitializer", "handler"})
    private Mascota mascota;

    @Column(name = "fecha_creacion", nullable = false)
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

    public Cita(LocalDateTime fechaHora, String motivo, Mascota mascota) {
        this();
        this.fechaHora = fechaHora;
        this.motivo = motivo;
        this.mascota = mascota;
        mascota.addCita(this);
    }

    public Cita(LocalDateTime fechaHora, String motivo, String notas, Mascota mascota) {
        this(fechaHora, motivo, mascota);
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

    public Mascota getMascota() {
        return mascota;
    }

    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
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
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }
}
