package com.veterinaria.app.controller;

import com.veterinaria.app.model.Dueno;
import com.veterinaria.app.service.DuenoService;
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

@WebMvcTest(DuenoController.class)
class DuenoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DuenoService duenoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void crearDueno_ok() throws Exception {

        Dueno dueno = new Dueno();
        dueno.setNombre("Carlos");
        dueno.setTelefono("1234567");
        dueno.setEmail("carlos@mail.com");

        Mockito.when(duenoService.guardar(Mockito.any(Dueno.class)))
                .thenReturn(true);

        mockMvc.perform(post("/duenos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dueno)))
                .andExpect(status().isCreated());
    }

    @Test
    void crearDueno_error() throws Exception {

        Mockito.when(duenoService.guardar(Mockito.any(Dueno.class)))
                .thenReturn(false);

        mockMvc.perform(post("/duenos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void obtenerTodos_ok() throws Exception {

        Dueno dueno1 = new Dueno();
        dueno1.setNombre("Ana");

        Dueno dueno2 = new Dueno();
        dueno2.setNombre("Luis");

        Mockito.when(duenoService.buscarTodos())
                .thenReturn(List.of(dueno1, dueno2));

        mockMvc.perform(get("/duenos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    void obtenerPorId_ok() throws Exception {

        Dueno dueno = new Dueno();
        dueno.setId(1);
        dueno.setNombre("Carlos");

        Mockito.when(duenoService.buscarPorId(1))
                .thenReturn(dueno);

        mockMvc.perform(get("/duenos/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Carlos"));
    }

    @Test
    void obtenerPorId_noExiste() throws Exception {

        Mockito.when(duenoService.buscarPorId(99))
                .thenReturn(null);

        mockMvc.perform(get("/duenos/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void eliminar_ok() throws Exception {

        Mockito.when(duenoService.eliminar(1))
                .thenReturn(true);

        mockMvc.perform(delete("/duenos/1"))
                .andExpect(status().isOk());
    }

    @Test
    void eliminar_noExiste() throws Exception {

        Mockito.when(duenoService.eliminar(99))
                .thenReturn(false);

        mockMvc.perform(delete("/duenos/99"))
                .andExpect(status().isNotFound());
    }
}

