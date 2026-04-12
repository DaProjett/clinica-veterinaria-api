package com.veterinaria.app.controller;

import com.veterinaria.app.model.Dueno;
import com.veterinaria.app.service.DuenoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/duenos")
public class DuenoController {

    private final DuenoService duenoService;

    public DuenoController(DuenoService duenoService) {
        this.duenoService = duenoService;
    }

    @Operation(summary = "Obtener todos los dueños")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de dueños obtenida correctamente")
    })

    @GetMapping
    public ResponseEntity<List<Dueno>> obtenerTodos() {
        List<Dueno> duenos = duenoService.buscarTodos();
        return ResponseEntity.ok(duenos);
    }


    @Operation(summary = "Obtener un dueño por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Dueño encontrado"),
            @ApiResponse(responseCode = "404", description = "Dueño no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Dueno> obtenerPorId(@PathVariable int id) {
        Dueno dueno = duenoService.buscarPorId(id);

        if (dueno == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        return ResponseEntity.ok(dueno);
    }

    @Operation(summary = "Crear un nuevo dueño")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Dueño creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PostMapping
    public ResponseEntity<String> crear(@RequestBody Dueno dueno) {
        boolean guardado = duenoService.guardar(dueno);

        if (!guardado) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("No se pudo crear el dueño");
        }

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("Dueño creado correctamente");
    }

    @Operation(summary = "Actualizar un dueño existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Dueño actualizado"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    @PutMapping("/{id}")
    public ResponseEntity<String> actualizar(
            @PathVariable int id,
            @RequestBody Dueno dueno) {

        boolean actualizado = duenoService.actualizar(id, dueno);

        if (!actualizado) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("No se pudo actualizar el dueño");
        }

        return ResponseEntity.ok("Dueño actualizado correctamente");
    }

    @Operation(summary = "Eliminar un dueño por ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Dueño eliminado"),
            @ApiResponse(responseCode = "404", description = "Dueño no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable int id) {
        boolean eliminado = duenoService.eliminar(id);

        if (!eliminado) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body("No se pudo eliminar el dueño");
        }

        return ResponseEntity.ok("Dueño eliminado correctamente");
    }
}
