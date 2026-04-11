package com.veterinaria.app.controller;

import com.veterinaria.app.model.Cita;
import com.veterinaria.app.service.CitaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/citas")
public class CitaController {

    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    @Operation(summary = "Obtener todas las citas")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de citas obtenida correctamente")
    })
    @GetMapping
    public ResponseEntity<List<Cita>> obtenerTodas() {
        List<Cita> citas = citaService.buscarTodos();
        return ResponseEntity.ok(citas);
    }

    @Operation(summary = "Obtener una cita por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cita encontrada"),
            @ApiResponse(responseCode = "404", description = "Cita no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Cita> obtenerPorId(@PathVariable int id) {
        Cita cita = citaService.buscarPorId(id);

        if (cita == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(cita);
    }

    @Operation(summary = "Crear una nueva cita")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cita creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<String> crear(@RequestBody Cita cita) {
        boolean guardado = citaService.guardar(cita);

        if (!guardado) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("No se pudo crear la cita");
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Cita creada correctamente");
    }

    @Operation(summary = "Actualizar una cita existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cita actualizada"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<String> actualizar(
            @PathVariable int id,
            @RequestBody Cita cita) {

        boolean actualizado = citaService.actualizar(id, cita);

        if (!actualizado) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("No se pudo actualizar la cita");
        }

        return ResponseEntity.ok("Cita actualizada correctamente");
    }

    @Operation(summary = "Eliminar una cita por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cita eliminada"),
            @ApiResponse(responseCode = "404", description = "Cita no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable int id) {
        boolean eliminado = citaService.eliminar(id);

        if (!eliminado) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No se pudo eliminar la cita");
        }

        return ResponseEntity.ok("Cita eliminada correctamente");
    }
}
