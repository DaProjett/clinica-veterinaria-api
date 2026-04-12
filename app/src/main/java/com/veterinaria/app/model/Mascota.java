package com.veterinaria.app.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "mascotas")
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "especie", nullable = false, length = 30)
    private String especie;

    @Column(name = "raza", length = 50)
    private String raza;

    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @Enumerated(EnumType.STRING)
    @Column(name = "sexo", nullable = false, length = 10)
    private Sexo sexo;

    @Column(name = "color", length = 30)
    private String color;

    @Column(name = "peso")
    private Double peso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dueño_id", nullable = false)
    private Dueno dueño;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro;

    // Enumeración para el sexo
    public enum Sexo {
        MACHO, HEMBRA
    }

    // Constructores
    public Mascota() {
        this.fechaRegistro = LocalDate.now();
    }

    public Mascota(String nombre, String especie, String raza, LocalDate fechaNacimiento,
                   Sexo sexo, String color, Double peso, Dueno dueño) {
        this();
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.color = color;
        this.peso = peso;
        this.dueño = dueño;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public Sexo getSexo() {
        return sexo;
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Dueno getDueño() {
        return dueño;
    }

    public void setDueño(Dueno dueño) {
        this.dueño = dueño;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    @Override
    public String toString() {
        return "Mascota{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", especie='" + especie + '\'' +
                ", raza='" + raza + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", sexo=" + sexo +
                ", color='" + color + '\'' +
                ", peso=" + peso +
                ", dueño=" + (dueño != null ? dueño.getId() : "null") +
                ", fechaRegistro=" + fechaRegistro +
                '}';
    }
}