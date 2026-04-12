package com.veterinaria.app.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "citas")
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @Column(name = "motivo", nullable = false, length = 200)
    private String motivo;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    private Estado estado;

    @Column(name = "notas", length = 500)
    private String notas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mascota_id", nullable = false)
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
                ", mascota=" + (mascota != null ? mascota.getId() : "null") +
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }
}