package com.veterinaria.app.controller;

import com.veterinaria.app.model.Dueno;
import com.veterinaria.app.service.DuenoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class DuenoController {

    private final DuenoService duenoService;

    public DuenoController(DuenoService duenoService) {
        this.duenoService = duenoService;
    }

    @Operation(summary = "Obtener todos los dueños")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de dueños obtenida correctamente")
    })
    @GetMapping("/api/v1/duenos")
    public ResponseEntity<List<Dueno>> obtenerTodos() {
        List<Dueno> duenos = duenoService.buscarTodos();
        return ResponseEntity.ok(duenos);
    }

    @Operation(summary = "Obtener un dueño por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Dueño encontrado"),
        @ApiResponse(responseCode = "404", description = "Dueño no encontrado")
    })
    @GetMapping("/api/v1/duenos/{id}")
    public ResponseEntity<Dueno> obtenerPorId(@PathVariable Long id) {
        Optional<Dueno> dueno = duenoService.buscarPorId(id);

        if (dueno.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(dueno.get());
    }

    @Operation(summary = "Crear un nuevo dueño")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Dueño creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping("/api/v1/duenos")
    public ResponseEntity<?> crear(@Valid @RequestBody Dueno dueno) {
        try {
            Dueno guardado = duenoService.guardar(dueno);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(Map.of(
                        "mensaje", "Dueño creado correctamente",
                        "dueno", guardado,
                        "status", "SUCCESS"
                    ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                        "error", e.getMessage(),
                        "status", "ERROR"
                    ));
        }
    }

    @Operation(summary = "Actualizar un dueño existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Dueño actualizado"),
        @ApiResponse(responseCode = "404", description = "Dueño no encontrado"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PutMapping("/api/v1/duenos/{id}")
    public ResponseEntity<?> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody Dueno dueno) {

        Optional<Dueno> actualizado = duenoService.actualizar(id, dueno);

        if (actualizado.isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                        "error", "Dueño no encontrado",
                        "status", "ERROR"
                    ));
        }

        return ResponseEntity.ok(Map.of(
            "mensaje", "Dueño actualizado correctamente",
            "dueno", actualizado.get(),
            "status", "SUCCESS"
        ));
    }

    @Operation(summary = "Eliminar un dueño por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Dueño eliminado"),
        @ApiResponse(responseCode = "404", description = "Dueño no encontrado")
    })
    @DeleteMapping("/api/v1/duenos/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        boolean eliminado = duenoService.eliminar(id);

        if (!eliminado) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                        "error", "Dueño no encontrado",
                        "status", "ERROR"
                    ));
        }

        return ResponseEntity.ok(Map.of(
            "mensaje", "Dueño eliminado correctamente",
            "status", "SUCCESS"
        ));
    }
}