package com.veterinaria.app.config;

import com.veterinaria.app.model.Dueno;
import com.veterinaria.app.model.Mascota;
import com.veterinaria.app.repository.DuenoRepository;
import com.veterinaria.app.repository.MascotaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(DuenoRepository duenoRepo, MascotaRepository mascotaRepo) {
        return args -> {
            // Solo insertar datos si la base de datos está vacía
            if (duenoRepo.count() > 0) {
                System.out.println("========================================");
                System.out.println("Base de datos ya tiene datos, omitiendo inicialización");
                System.out.println("========================================");
                return;
            }

            // Crear 10 dueños
            Dueno d1 = new Dueno("Juan Pérez García", "CC-1012345678", "555-1234", "juan.perez@email.com", "Calle 123 #45-67, Bogotá");
            Dueno d2 = new Dueno("María García López", "CC-1087654321", "555-5678", "maria.garcia@email.com", "Carrera 45 #12-34, Medellín");
            Dueno d3 = new Dueno("Pedro Martínez Ruiz", "CC-1023456789", "555-9012", "pedro.martinez@email.com", "Avenida Caracas #56-78, Cali");
            Dueno d4 = new Dueno("Ana López Hernández", "CC-1098765432", "555-3456", "ana.lopez@email.com", "Calle 78 #90-12, Bogotá");
            Dueno d5 = new Dueno("Carlos Rodríguez Díaz", "CC-1034567890", "555-7890", "carlos.rodriguez@email.com", "Carrera 15 #32-54, Barranquilla");
            Dueno d6 = new Dueno("Laura Sánchez Vargas", "CC-1045678901", "555-2345", "laura.sanchez@email.com", "Avenida Eldorado #66-88, Bogotá");
            Dueno d7 = new Dueno("Miguel Fernández Castro", "CC-1056789012", "555-6789", "miguel.fernandez@email.com", "Calle 90 #11-22, Bucaramanga");
            Dueno d8 = new Dueno("Carmen González Morales", "CC-1067890123", "555-0123", "carmen.gonzalez@email.com", "Carrera 30 #45-60, Cartagena");
            Dueno d9 = new Dueno("Javier Díaz Peña", "CC-1078901234", "555-4567", "javier.diaz@email.com", "Avenida Boyacá #70-80, Bogotá");
            Dueno d10 = new Dueno("Sofía Muñoz Ramírez", "CC-1089012345", "555-8901", "sofia.munoz@email.com", "Calle 55 #77-99, Pereira");

            // Guardar dueños primero
            duenoRepo.save(d1);
            duenoRepo.save(d2);
            duenoRepo.save(d3);
            duenoRepo.save(d4);
            duenoRepo.save(d5);
            duenoRepo.save(d6);
            duenoRepo.save(d7);
            duenoRepo.save(d8);
            duenoRepo.save(d9);
            duenoRepo.save(d10);

            // Crear 15 mascotas
            Mascota m1 = new Mascota("Max", "Perro", "Golden Retriever", LocalDate.of(2021, 5, 15), Mascota.Sexo.MACHO, "Dorado", 28.5, d1);
            Mascota m2 = new Mascota("Luna", "Gato", "Siames", LocalDate.of(2022, 3, 20), Mascota.Sexo.HEMBRA, "Crema", 4.2, d1);
            Mascota m3 = new Mascota("Rocky", "Perro", "Bulldog Francés", LocalDate.of(2019, 8, 10), Mascota.Sexo.MACHO, "Blanco", 12.0, d2);
            Mascota m4 = new Mascota("Bella", "Gato", "Persa", LocalDate.of(2023, 1, 5), Mascota.Sexo.HEMBRA, "Gris", 3.5, d2);
            Mascota m5 = new Mascota("Charlie", "Perro", "Labrador", LocalDate.of(2020, 7, 22), Mascota.Sexo.MACHO, "Negro", 30.0, d3);
            Mascota m6 = new Mascota("Milo", "Gato", "Maine Coon", LocalDate.of(2021, 9, 30), Mascota.Sexo.MACHO, "Gris atigrado", 6.8, d3);
            Mascota m7 = new Mascota("Lucas", "Perro", "Husky Siberiano", LocalDate.of(2022, 11, 15), Mascota.Sexo.MACHO, "Blanco y gris", 22.5, d4);
            Mascota m8 = new Mascota("Nala", "Gato", "Bengala", LocalDate.of(2023, 2, 28), Mascota.Sexo.HEMBRA, "Marrón manchado", 4.0, d4);
            Mascota m9 = new Mascota("Duke", "Perro", "Doberman", LocalDate.of(2018, 4, 12), Mascota.Sexo.MACHO, "Negro", 35.2, d5);
            Mascota m10 = new Mascota("Coco", "Gato", "Sphynx", LocalDate.of(2022, 6, 18), Mascota.Sexo.HEMBRA, "Rosa", 3.8, d5);
            Mascota m11 = new Mascota("Oreo", "Perro", "Dálmata", LocalDate.of(2021, 8, 25), Mascota.Sexo.MACHO, "Blanco con negro", 28.0, d6);
            Mascota m12 = new Mascota("Simba", "Gato", "Ragdoll", LocalDate.of(2020, 12, 10), Mascota.Sexo.MACHO, "Blanco y gris", 5.5, d6);
            Mascota m13 = new Mascota("Toby", "Perro", "Beagle", LocalDate.of(2019, 2, 28), Mascota.Sexo.MACHO, "Tricolor", 12.5, d7);
            Mascota m14 = new Mascota("Lola", "Gato", "Scottish Fold", LocalDate.of(2022, 4, 15), Mascota.Sexo.HEMBRA, "Gris", 4.5, d7);
            Mascota m15 = new Mascota("Buddy", "Perro", "Boxer", LocalDate.of(2022, 9, 12), Mascota.Sexo.MACHO, "Marrón", 25.5, d8);

            // Guardar mascotas
            mascotaRepo.save(m1);
            mascotaRepo.save(m2);
            mascotaRepo.save(m3);
            mascotaRepo.save(m4);
            mascotaRepo.save(m5);
            mascotaRepo.save(m6);
            mascotaRepo.save(m7);
            mascotaRepo.save(m8);
            mascotaRepo.save(m9);
            mascotaRepo.save(m10);
            mascotaRepo.save(m11);
            mascotaRepo.save(m12);
            mascotaRepo.save(m13);
            mascotaRepo.save(m14);
            mascotaRepo.save(m15);

            System.out.println("========================================");
            System.out.println("Base de datos inicializada con datos de prueba");
            System.out.println("Dueños: 10 | Mascotas: 15");
            System.out.println("========================================");
        };
    }
}