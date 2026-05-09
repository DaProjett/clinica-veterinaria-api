package com.veterinaria.app.model;

import javax.validation.constraints.*;
import java.time.LocalDate;

public class Mascota {

    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    private String nombre;

    @NotBlank(message = "La especie es obligatoria")
    @Size(max = 30, message = "La especie no puede exceder 30 caracteres")
    private String especie;

    @Size(max = 30, message = "La raza no puede exceder 30 caracteres")
    private String raza;

    @PastOrPresent(message = "La fecha de nacimiento no puede ser futura")
    private LocalDate fechaNacimiento;

    @NotNull(message = "El sexo es obligatorio")
    private Sexo sexo;

    @Size(max = 30, message = "El color no puede exceder 30 caracteres")
    private String color;

    @Positive(message = "El peso debe ser mayor a 0")
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