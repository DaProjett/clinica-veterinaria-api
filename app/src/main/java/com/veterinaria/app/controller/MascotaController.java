package com.veterinaria.app.controller;

import com.veterinaria.app.model.Mascota;
import com.veterinaria.app.service.MascotaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class MascotaController {

    private final MascotaService mascotaService;

    public MascotaController(MascotaService mascotaService) {
        this.mascotaService = mascotaService;
    }

    @Operation(summary = "Obtener todas las mascotas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de mascotas obtenida correctamente")
    })
    @GetMapping("/mascotas")
    public ResponseEntity<List<Mascota>> obtenerTodas() {
        List<Mascota> mascotas = mascotaService.buscarTodos();
        return ResponseEntity.ok(mascotas);
    }

    @Operation(summary = "Obtener una mascota por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Mascota encontrada"),
            @ApiResponse(responseCode = "404", description = "Mascota no encontrada")
    })
    @GetMapping("/mascotas/{id}")
    public ResponseEntity<Mascota> obtenerPorId(@PathVariable int id) {
        Mascota mascota = mascotaService.buscarPorId(id);

        if (mascota == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(mascota);
    }

    @Operation(summary = "Crear una nueva mascota")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Mascota creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping("/mascotas")
    public ResponseEntity<String> crear(@RequestBody Mascota mascota) {
        boolean guardado = mascotaService.guardar(mascota);

        if (!guardado) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("No se pudo crear la mascota");
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Mascota creada correctamente");
    }

    @Operation(summary = "Actualizar una mascota existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Mascota actualizada"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PutMapping("/mascotas/{id}")
    public ResponseEntity<String> actualizar(
            @PathVariable int id,
            @RequestBody Mascota mascota) {

        boolean actualizado = mascotaService.actualizar(id, mascota);

        if (!actualizado) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("No se pudo actualizar la mascota");
        }

        return ResponseEntity.ok("Mascota actualizada correctamente");
    }

    @Operation(summary = "Eliminar una mascota por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Mascota eliminada"),
            @ApiResponse(responseCode = "404", description = "Mascota no encontrada")
    })
    @DeleteMapping("/mascotas/{id}")
    public ResponseEntity<String> eliminar(@PathVariable int id) {
        boolean eliminado = mascotaService.eliminar(id);

        if (!eliminado) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No se pudo eliminar la mascota");
        }

        return ResponseEntity.ok("Mascota eliminada correctamente");
    }

    @Operation(summary = "Obtener las mascotas de un dueño")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de mascotas del dueño"),
            @ApiResponse(responseCode = "404", description = "Dueño no encontrado")
    })
    @GetMapping("/duenos/{idDueno}/mascotas")
    public ResponseEntity<List<Mascota>> obtenerPorDueno(@PathVariable int idDueno) {
        List<Mascota> mascotas = mascotaService.buscarPorDueno(idDueno);
        return ResponseEntity.ok(mascotas);
    }
}
