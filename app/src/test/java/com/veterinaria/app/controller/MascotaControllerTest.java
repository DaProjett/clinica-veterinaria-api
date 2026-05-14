package com.veterinaria.app.controller;

import com.veterinaria.app.model.Mascota;
import com.veterinaria.app.service.MascotaService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MascotaController.class)
class MascotaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MascotaService mascotaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void obtenerTodas_ok() throws Exception {

        Mascota mascota1 = new Mascota();
        Mascota mascota2 = new Mascota();

        Mockito.when(mascotaService.buscarTodos())
                .thenReturn(List.of(mascota1, mascota2));

        mockMvc.perform(get("/mascotas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    void obtenerPorId_ok() throws Exception {

        Mascota mascota = new Mascota();
        mascota.setId(1);

        Mockito.when(mascotaService.buscarPorId(1))
                .thenReturn(mascota);

        mockMvc.perform(get("/mascotas/1"))
                .andExpect(status().isOk());
    }

    @Test
    void obtenerPorId_noExiste() throws Exception {

        Mockito.when(mascotaService.buscarPorId(99))
                .thenReturn(null);

        mockMvc.perform(get("/mascotas/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void crearMascota_ok() throws Exception {

        Mascota mascota = new Mascota();

        Mockito.when(mascotaService.guardar(Mockito.any(Mascota.class)))
                .thenReturn(true);

        mockMvc.perform(post("/mascotas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mascota)))
                .andExpect(status().isCreated());
    }

    @Test
    void crearMascota_error() throws Exception {

        Mockito.when(mascotaService.guardar(Mockito.any(Mascota.class)))
                .thenReturn(false);

        mockMvc.perform(post("/mascotas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void actualizarMascota_ok() throws Exception {

        Mockito.when(mascotaService.actualizar(Mockito.eq(1), Mockito.any(Mascota.class)))
                .thenReturn(true);

        mockMvc.perform(put("/mascotas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void actualizarMascota_error() throws Exception {

        Mockito.when(mascotaService.actualizar(Mockito.eq(1), Mockito.any(Mascota.class)))
                .thenReturn(false);

        mockMvc.perform(put("/mascotas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void eliminarMascota_ok() throws Exception {

        Mockito.when(mascotaService.eliminar(1))
                .thenReturn(true);

        mockMvc.perform(delete("/mascotas/1"))
                .andExpect(status().isOk());
    }

    @Test
    void eliminarMascota_noExiste() throws Exception {

        Mockito.when(mascotaService.eliminar(99))
                .thenReturn(false);

        mockMvc.perform(delete("/mascotas/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void obtenerMascotasPorDueno_ok() throws Exception {

        Mascota mascota1 = new Mascota();
        Mascota mascota2 = new Mascota();

        Mockito.when(mascotaService.buscarPorDueno(1))
                .thenReturn(List.of(mascota1, mascota2));

        mockMvc.perform(get("/duenos/1/mascotas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }
}
