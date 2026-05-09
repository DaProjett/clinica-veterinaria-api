package com.veterinaria.app.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "mascotas")
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String especie;
    private String raza;
    private LocalDate fechaNacimiento;
    
    @Enumerated(EnumType.STRING)
    private Sexo sexo;
    private String color;
    private Double peso;
    private Long dueñoId;  // ID del dueño en lugar de relación JPA
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
                   Sexo sexo, String color, Double peso, Long dueñoId) {
        this();
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.color = color;
        this.peso = peso;
        this.dueñoId = dueñoId;
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

    public Long getDueñoId() {
        return dueñoId;
    }

    public void setDueñoId(Long dueñoId) {
        this.dueñoId = dueñoId;
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
                ", dueñoId=" + dueñoId +
                ", fechaRegistro=" + fechaRegistro +
                '}';
    }
}