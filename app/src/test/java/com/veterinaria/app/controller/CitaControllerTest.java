package com.veterinaria.app.controller;

import com.veterinaria.app.model.Cita;
import com.veterinaria.app.service.CitaService;
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

@WebMvcTest(CitaController.class)
class CitaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CitaService citaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void obtenerTodas_ok() throws Exception {

        Cita cita1 = new Cita();
        Cita cita2 = new Cita();

        Mockito.when(citaService.buscarTodos())
                .thenReturn(List.of(cita1, cita2));

        mockMvc.perform(get("/citas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    void obtenerPorId_ok() throws Exception {

        Cita cita = new Cita();
        cita.setId(1);

        Mockito.when(citaService.buscarPorId(1))
                .thenReturn(cita);

        mockMvc.perform(get("/citas/1"))
                .andExpect(status().isOk());
    }

    @Test
    void obtenerPorId_noExiste() throws Exception {

        Mockito.when(citaService.buscarPorId(99))
                .thenReturn(null);

        mockMvc.perform(get("/citas/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void crearCita_ok() throws Exception {

        Cita cita = new Cita();

        Mockito.when(citaService.guardar(Mockito.any(Cita.class)))
                .thenReturn(true);

        mockMvc.perform(post("/citas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(cita)))
                .andExpect(status().isCreated());
    }

    @Test
    void crearCita_error() throws Exception {

        Mockito.when(citaService.guardar(Mockito.any(Cita.class)))
                .thenReturn(false);

        mockMvc.perform(post("/citas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void actualizarCita_ok() throws Exception {

        Mockito.when(citaService.actualizar(Mockito.eq(1), Mockito.any(Cita.class)))
                .thenReturn(true);

        mockMvc.perform(put("/citas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    void actualizarCita_error() throws Exception {

        Mockito.when(citaService.actualizar(Mockito.eq(1), Mockito.any(Cita.class)))
                .thenReturn(false);

        mockMvc.perform(put("/citas/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void eliminarCita_ok() throws Exception {

        Mockito.when(citaService.eliminar(1))
                .thenReturn(true);

        mockMvc.perform(delete("/citas/1"))
                .andExpect(status().isOk());
    }

    @Test
    void eliminarCita_noExiste() throws Exception {

        Mockito.when(citaService.eliminar(99))
                .thenReturn(false);

        mockMvc.perform(delete("/citas/99"))
                .andExpect(status().isNotFound());
    }
}