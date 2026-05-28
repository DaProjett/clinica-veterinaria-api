package com.veterinaria.app.controller;

import com.veterinaria.app.model.Cita;
import com.veterinaria.app.service.CitaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class CitaController {

    private final CitaService citaService;

    public CitaController(CitaService citaService) {
        this.citaService = citaService;
    }

    @Operation(summary = "Obtener todas las citas")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de citas obtenida correctamente")
    })
    @GetMapping("/api/v1/citas")
    public ResponseEntity<List<Cita>> obtenerTodas() {
        return ResponseEntity.ok(citaService.buscarTodos());
    }

    @Operation(summary = "Obtener una cita por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cita encontrada"),
        @ApiResponse(responseCode = "404", description = "Cita no encontrada")
    })
    @GetMapping("/api/v1/citas/{id}")
    public ResponseEntity<Cita> obtenerPorId(@PathVariable Long id) {
        Optional<Cita> cita = citaService.buscarPorId(id);

        if (cita.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(cita.get());
    }

    @Operation(summary = "Obtener citas por mascota")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de citas de la mascota")
    })
    @GetMapping("/api/v1/citas/mascota/{mascotaId}")
    public ResponseEntity<List<Cita>> obtenerPorMascota(@PathVariable Long mascotaId) {
        return ResponseEntity.ok(citaService.buscarPorMascota(mascotaId));
    }

    @Operation(summary = "Crear una nueva cita")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Cita creada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos")
    })
    @PostMapping("/api/v1/citas")
    public ResponseEntity<?> crear(@RequestBody Map<String, Object> request) {
        try {
            Cita cita = convertirRequestACita(request);
            Cita guardada = citaService.guardar(cita);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(Map.of(
                        "mensaje", "Cita creada correctamente",
                        "cita", guardada,
                        "status", "SUCCESS"
                    ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                        "error", e.getMessage(),
                        "status", "ERROR"
                    ));
        } catch (ConstraintViolationException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                        "error", obtenerMensajeValidacion(e),
                        "status", "ERROR"
                    ));
        }
    }

    @Operation(summary = "Actualizar una cita existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cita actualizada"),
        @ApiResponse(responseCode = "404", description = "Cita no encontrada"),
        @ApiResponse(responseCode = "400", description = "Datos invalidos")
    })
    @PutMapping("/api/v1/citas/{id}")
    public ResponseEntity<?> actualizar(
            @PathVariable Long id,
            @RequestBody Map<String, Object> request) {

        try {
            Cita cita = convertirRequestACita(request);
            Optional<Cita> actualizada = citaService.actualizar(id, cita);

            if (actualizada.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(Map.of(
                            "error", "Cita no encontrada",
                            "status", "ERROR"
                        ));
            }

            return ResponseEntity.ok(Map.of(
                "mensaje", "Cita actualizada correctamente",
                "cita", actualizada.get(),
                "status", "SUCCESS"
            ));
        } catch (IllegalArgumentException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                        "error", e.getMessage(),
                        "status", "ERROR"
                    ));
        } catch (ConstraintViolationException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                        "error", obtenerMensajeValidacion(e),
                        "status", "ERROR"
                    ));
        }
    }

    @Operation(summary = "Eliminar una cita por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cita eliminada"),
        @ApiResponse(responseCode = "404", description = "Cita no encontrada")
    })
    @DeleteMapping("/api/v1/citas/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        boolean eliminado = citaService.eliminar(id);

        if (!eliminado) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                        "error", "Cita no encontrada",
                        "status", "ERROR"
                    ));
        }

        return ResponseEntity.ok(Map.of(
            "mensaje", "Cita eliminada correctamente",
            "status", "SUCCESS"
        ));
    }

    private Cita convertirRequestACita(Map<String, Object> request) {
        Cita cita = new Cita();

        String fechaHora = obtenerTexto(request, "fechaHora");
        if (fechaHora != null && !fechaHora.isBlank()) {
            try {
                cita.setFechaHora(LocalDateTime.parse(fechaHora));
            } catch (DateTimeParseException e) {
                throw new IllegalArgumentException("La fecha y hora deben tener formato valido");
            }
        }

        cita.setMotivo(obtenerTexto(request, "motivo"));
        cita.setNotas(obtenerTexto(request, "notas"));

        String estado = obtenerTexto(request, "estado");
        if (estado != null && !estado.isBlank()) {
            try {
                cita.setEstado(Cita.Estado.valueOf(estado));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("El estado de la cita no es valido");
            }
        }

        Long mascotaId = obtenerMascotaId(request);
        if (mascotaId != null) {
            com.veterinaria.app.model.Mascota mascota = new com.veterinaria.app.model.Mascota();
            mascota.setId(mascotaId);
            cita.setMascota(mascota);
        }

        return cita;
    }

    private String obtenerTexto(Map<String, Object> request, String campo) {
        Object valor = request.get(campo);
        if (valor == null) {
            return null;
        }
        String texto = valor.toString();
        return texto.isBlank() ? null : texto;
    }

    @SuppressWarnings("unchecked")
    private Long obtenerMascotaId(Map<String, Object> request) {
        Object mascotaId = request.get("mascotaId");
        if (mascotaId instanceof Number number) {
            return number.longValue();
        }
        if (mascotaId instanceof String texto && !texto.isBlank()) {
            return Long.parseLong(texto);
        }

        Object mascota = request.get("mascota");
        if (mascota instanceof Map<?, ?> mascotaMap) {
            Object id = mascotaMap.get("id");
            if (id instanceof Number number) {
                return number.longValue();
            }
            if (id instanceof String texto && !texto.isBlank()) {
                return Long.parseLong(texto);
            }
        }

        return null;
    }

    private String obtenerMensajeValidacion(ConstraintViolationException e) {
        return e.getConstraintViolations()
                .stream()
                .findFirst()
                .map(violation -> violation.getMessage())
                .orElse("Datos invalidos");
    }
}
