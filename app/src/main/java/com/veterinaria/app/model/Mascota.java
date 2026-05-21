package com.veterinaria.app.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "mascota")
public class Mascota {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(min = 2, max = 50, message = "El nombre debe tener entre 2 y 50 caracteres")
    @Column(nullable = false, length = 50)
    private String nombre;

    @NotBlank(message = "La especie es obligatoria")
    @Size(max = 30, message = "La especie no puede exceder 30 caracteres")
    @Column(length = 30)
    private String especie;

    @Size(max = 30, message = "La raza no puede exceder 30 caracteres")
    @Column(length = 30)
    private String raza;

    @PastOrPresent(message = "La fecha de nacimiento no puede ser futura")
    @Column(name = "fecha_nacimiento")
    private LocalDate fechaNacimiento;

    @NotNull(message = "El sexo es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Sexo sexo;

    @Size(max = 30, message = "El color no puede exceder 30 caracteres")
    @Column(length = 30)
    private String color;

    @Positive(message = "El peso debe ser mayor a 0")
    private Double peso;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dueno_id", nullable = false)
    @JsonIgnoreProperties({"mascotas", "dueno"})
    private Dueno dueno;

    @Column(name = "fecha_registro")
    private LocalDate fechaRegistro;

    @OneToMany(mappedBy = "mascota", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("mascota")
    private List<Cita> citas = new ArrayList<>();

    // Enumeración para el sexo
    public enum Sexo {
        MACHO, HEMBRA
    }

    // Constructores
    public Mascota() {
        this.fechaRegistro = LocalDate.now();
    }

    public Mascota(String nombre, String especie, String raza, LocalDate fechaNacimiento,
                   Sexo sexo, String color, Double peso, Dueno dueno) {
        this();
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.color = color;
        this.peso = peso;
        this.dueno = dueno;
        dueno.addMascota(this);
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

    public Dueno getDueno() {
        return dueno;
    }

    public void setDueno(Dueno dueno) {
        this.dueno = dueno;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    // Métodos helper para manejar la relación con Cita
    public List<Cita> getCitas() {
        return citas;
    }

    public void setCitas(List<Cita> citas) {
        this.citas = citas;
    }

    public void addCita(Cita cita) {
        citas.add(cita);
        cita.setMascota(this);
    }

    public void removeCita(Cita cita) {
        citas.remove(cita);
        cita.setMascota(null);
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
                ", fechaRegistro=" + fechaRegistro +
                '}';
    }
}