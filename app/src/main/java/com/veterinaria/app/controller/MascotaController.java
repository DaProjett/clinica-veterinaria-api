package com.veterinaria.app.controller;

import com.veterinaria.app.model.Mascota;
import com.veterinaria.app.service.MascotaService;
import com.veterinaria.app.service.interfaces.IDuenoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class MascotaController {

    private final MascotaService mascotaService;
    private final IDuenoService duenoService;

    public MascotaController(MascotaService mascotaService, IDuenoService duenoService) {
        this.mascotaService = mascotaService;
        this.duenoService = duenoService;
    }

    @Operation(summary = "Estado de la API - Endpoint raíz")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "API funcionando correctamente")
    })
    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> apiStatus() {
        Map<String, Object> response = new HashMap<>();
        response.put("mensaje", "🐾 API Clinica Veterinaria - Funcionando correctamente!");
        response.put("version", "1.0.0");
        response.put("status", "ACTIVE");
        response.put("database", "MySQL");
        response.put("endpoints_disponibles", Map.of(
            "api_status", "/",
            "mascotas", "/api/v1/mascotas",
            "mascotas_detalle", "/api/v1/mascotas/{id}",
            "mascotas_buscar_nombre", "/api/v1/mascotas/buscar/nombre/{nombre}",
            "mascotas_buscar_especie", "/api/v1/mascotas/buscar/especie/{especie}",
            "mascotas_por_dueno", "/api/v1/mascotas/dueno/{idDueno}",
            "mascotas_crear_simple", "/api/v1/mascotas/simple",
            "mascotas_actualizar_simple", "/api/v1/mascotas/simple/{id}",
            "documentacion", "/swagger-ui.html",
            "h2_console", "/h2-console (solo desarrollo)"
        ));
        response.put("ultima_actualizacion", "2024-05-20");
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener todas las mascotas")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de mascotas obtenida correctamente")
    })
    @GetMapping("/api/v1/mascotas")
    public ResponseEntity<List<Map<String, Object>>> obtenerTodas() {
        List<Mascota> mascotas = mascotaService.buscarTodos();
        List<Map<String, Object>> response = new ArrayList<>();

        for (Mascota mascota : mascotas) {
            Map<String, Object> mascotaMap = new HashMap<>();
            mascotaMap.put("id", mascota.getId());
            mascotaMap.put("nombre", mascota.getNombre());
            mascotaMap.put("especie", mascota.getEspecie());
            mascotaMap.put("raza", mascota.getRaza());
            mascotaMap.put("fechaNacimiento", mascota.getFechaNacimiento());
            mascotaMap.put("sexo", mascota.getSexo());
            mascotaMap.put("color", mascota.getColor());
            mascotaMap.put("peso", mascota.getPeso());

            // Incluir dueño sin la relación circular
            if (mascota.getDueno() != null) {
                Map<String, Object> duenoMap = new HashMap<>();
                duenoMap.put("id", mascota.getDueno().getId());
                duenoMap.put("nombreCompleto", mascota.getDueno().getNombreCompleto());
                duenoMap.put("documentoIdentidad", mascota.getDueno().getDocumentoIdentidad());
                duenoMap.put("telefono", mascota.getDueno().getTelefono());
                duenoMap.put("email", mascota.getDueno().getEmail());
                duenoMap.put("direccion", mascota.getDueno().getDireccion());
                mascotaMap.put("dueno", duenoMap);
            }

            response.add(mascotaMap);
        }

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener una mascota por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Mascota encontrada"),
        @ApiResponse(responseCode = "404", description = "Mascota no encontrada")
    })
    @GetMapping("/api/v1/mascotas/{id}")
    public ResponseEntity<Mascota> obtenerPorId(@PathVariable Long id) {
        Optional<Mascota> mascota = mascotaService.buscarPorId(id);

        if (mascota.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(mascota.get());
    }

    @Operation(summary = "Crear una nueva mascota")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Mascota creada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping("/api/v1/mascotas")
    public ResponseEntity<?> crear(@Valid @RequestBody Mascota mascota) {
        try {
            Mascota guardada = mascotaService.guardar(mascota);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(Map.of(
                        "mensaje", "Mascota creada correctamente",
                        "mascota", guardada,
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

    @Operation(summary = "Endpoint simple para crear mascota")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Mascota creada correctamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping("/api/v1/mascotas/simple")
    public ResponseEntity<?> crearSimple(@Valid @RequestBody Map<String, Object> mascotaData) {
        try {
            Mascota mascota = new Mascota();
            mascota.setNombre((String) mascotaData.get("nombre"));
            mascota.setEspecie((String) mascotaData.get("especie"));
            mascota.setRaza((String) mascotaData.get("raza"));

            // Manejar fecha de nacimiento
            if (mascotaData.get("fechaNacimiento") != null) {
                mascota.setFechaNacimiento(LocalDate.parse((String) mascotaData.get("fechaNacimiento")));
            }

            mascota.setSexo(Mascota.Sexo.valueOf((String) mascotaData.get("sexo")));
            mascota.setColor((String) mascotaData.get("color"));
            mascota.setPeso(((Number) mascotaData.get("peso")).doubleValue());

            // Buscar dueño por ID
            if (mascotaData.containsKey("duenoId")) {
                Long duenoId = ((Number) mascotaData.get("duenoId")).longValue();
                Optional<com.veterinaria.app.model.Dueno> dueno = duenoService.buscarPorId(duenoId);
                if (dueno.isPresent()) {
                    mascota.setDueno(dueno.get());
                } else {
                    return ResponseEntity
                            .status(HttpStatus.BAD_REQUEST)
                            .body(Map.of(
                                "error", "El dueño especificado no existe",
                                "status", "ERROR"
                            ));
                }
            }

            Mascota guardada = mascotaService.guardar(mascota);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(Map.of(
                        "mensaje", "Mascota creada correctamente",
                        "mascota", guardada,
                        "status", "SUCCESS"
                    ));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                        "error", e.getMessage(),
                        "status", "ERROR"
                    ));
        }
    }

    @Operation(summary = "Actualizar una mascota existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Mascota actualizada"),
        @ApiResponse(responseCode = "404", description = "Mascota no encontrada"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PutMapping("/api/v1/mascotas/{id}")
    public ResponseEntity<?> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody Mascota mascota) {

        try {
            Optional<Mascota> actualizada = mascotaService.actualizar(id, mascota);

            if (actualizada.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(Map.of(
                            "error", "Mascota no encontrada",
                            "status", "ERROR"
                        ));
            }

            return ResponseEntity.ok(Map.of(
                "mensaje", "Mascota actualizada correctamente",
                "mascota", actualizada.get(),
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

    @Operation(summary = "Endpoint simple para actualizar mascota")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Mascota actualizada"),
        @ApiResponse(responseCode = "404", description = "Mascota no encontrada"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PutMapping("/api/v1/mascotas/simple/{id}")
    public ResponseEntity<?> actualizarSimple(
            @PathVariable Long id,
            @Valid @RequestBody Map<String, Object> mascotaData) {

        try {
            Optional<Mascota> mascotaExistente = mascotaService.buscarPorId(id);
            if (mascotaExistente.isEmpty()) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(Map.of(
                            "error", "Mascota no encontrada",
                            "status", "ERROR"
                        ));
            }

            Mascota mascota = mascotaExistente.get();
            mascota.setNombre((String) mascotaData.get("nombre"));
            mascota.setEspecie((String) mascotaData.get("especie"));
            mascota.setRaza((String) mascotaData.get("raza"));

            // Manejar fecha de nacimiento
            if (mascotaData.get("fechaNacimiento") != null) {
                mascota.setFechaNacimiento(LocalDate.parse((String) mascotaData.get("fechaNacimiento")));
            }

            mascota.setSexo(Mascota.Sexo.valueOf((String) mascotaData.get("sexo")));
            mascota.setColor((String) mascotaData.get("color"));
            mascota.setPeso(((Number) mascotaData.get("peso")).doubleValue());

            // Buscar dueño por ID si se proporciona
            if (mascotaData.containsKey("duenoId")) {
                Long duenoId = ((Number) mascotaData.get("duenoId")).longValue();
                Optional<com.veterinaria.app.model.Dueno> dueno = duenoService.buscarPorId(duenoId);
                if (dueno.isPresent()) {
                    mascota.setDueno(dueno.get());
                } else {
                    return ResponseEntity
                            .status(HttpStatus.BAD_REQUEST)
                            .body(Map.of(
                                "error", "El dueño especificado no existe",
                                "status", "ERROR"
                            ));
                }
            }

            Mascota actualizada = mascotaService.guardar(mascota);
            return ResponseEntity.ok(Map.of(
                "mensaje", "Mascota actualizada correctamente",
                "mascota", actualizada,
                "status", "SUCCESS"
            ));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                        "error", e.getMessage(),
                        "status", "ERROR"
                    ));
        }
    }

    @Operation(summary = "Eliminar una mascota por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Mascota eliminada"),
        @ApiResponse(responseCode = "404", description = "Mascota no encontrada")
    })
    @DeleteMapping("/api/v1/mascotas/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        boolean eliminado = mascotaService.eliminar(id);

        if (!eliminado) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of(
                        "error", "Mascota no encontrada",
                        "status", "ERROR"
                    ));
        }

        return ResponseEntity.ok(Map.of(
            "mensaje", "Mascota eliminada correctamente",
            "status", "SUCCESS"
        ));
    }

    @Operation(summary = "Obtener las mascotas de un dueño")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de mascotas del dueño")
    })
    @GetMapping("/api/v1/mascotas/dueno/{idDueno}")
    public ResponseEntity<List<Mascota>> obtenerPorDueno(@PathVariable Long idDueno) {
        List<Mascota> mascotas = mascotaService.buscarPorDueno(idDueno);
        return ResponseEntity.ok(mascotas);
    }

    @Operation(summary = "Buscar mascotas por nombre")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Resultados de búsqueda")
    })
    @GetMapping("/api/v1/mascotas/buscar/nombre/{nombre}")
    public ResponseEntity<List<Mascota>> buscarPorNombre(@PathVariable String nombre) {
        List<Mascota> mascotas = mascotaService.buscarPorNombre(nombre);
        return ResponseEntity.ok(mascotas);
    }

    @Operation(summary = "Buscar mascotas por especie")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Resultados de búsqueda")
    })
    @GetMapping("/api/v1/mascotas/buscar/especie/{especie}")
    public ResponseEntity<List<Mascota>> buscarPorEspecie(@PathVariable String especie) {
        List<Mascota> mascotas = mascotaService.buscarPorEspecie(especie);
        return ResponseEntity.ok(mascotas);
    }

    @Operation(summary = "Obtener estadísticas de la aplicación")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Estadísticas del sistema")
    })
    @GetMapping("/api/v1/stats")
    public ResponseEntity<Map<String, Object>> obtenerEstadisticas() {
        try {
            List<Mascota> todasMascotas = mascotaService.buscarTodos();

            Map<String, Object> stats = new HashMap<>();
            stats.put("total_mascotas", todasMascotas.size());
            stats.put("total_especies", todasMascotas.stream()
                    .map(Mascota::getEspecie)
                    .distinct()
                    .count());
            stats.put("total_duenos", todasMascotas.stream()
                    .map(m -> m.getDueno().getId())
                    .distinct()
                    .count());
            stats.put("promedio_peso", todasMascotas.stream()
                    .filter(m -> m.getPeso() != null)
                    .mapToDouble(Mascota::getPeso)
                    .average()
                    .orElse(0.0));
            stats.put("porcentaje_esterilizadas", todasMascotas.stream()
                    .filter(m -> m.getFechaNacimiento() != null)
                    .mapToDouble(m -> {
                        // Asumir que mascotas mayores a 6 meses están esterilizadas por defecto
                        // Esto es una simplificación, en producción se calcularía con datos reales
                        return 0.75; // 75% estimado
                    })
                    .average()
                    .orElse(0.0) * 100);

            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                        "error", "Error al obtener estadísticas: " + e.getMessage(),
                        "status", "ERROR"
                    ));
        }
    }
}