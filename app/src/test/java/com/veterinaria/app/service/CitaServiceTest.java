package com.veterinaria.app.service;

import com.veterinaria.app.dao.CitaDao;
import com.veterinaria.app.dao.MascotaDao;
import com.veterinaria.app.model.Cita;
import com.veterinaria.app.model.Mascota;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CitaServiceTest {

    @Mock
    private CitaDao citaDao;

    @Mock
    private MascotaDao mascotaDao;

    @InjectMocks
    private CitaService citaService;

    private Cita citaValida;

    @BeforeEach
    void setUp() {
        citaValida = new Cita();
        citaValida.setFechaHora(LocalDateTime.now().plusDays(1));
        citaValida.setIdMascota(1);
    }

    @Test
    void noDebeGuardarSiFechaEsPasada() {
        citaValida.setFechaHora(LocalDateTime.now().minusDays(1));

        boolean resultado = citaService.guardar(citaValida);

        assertFalse(resultado);
        verify(citaDao, never()).guardar(any());
    }

    @Test
    void noDebeGuardarSiMascotaNoExiste() {
        when(mascotaDao.buscarPorId(1)).thenReturn(null);

        boolean resultado = citaService.guardar(citaValida);

        assertFalse(resultado);
        verify(citaDao, never()).guardar(any());
    }

    @Test
    void debeGuardarSiDatosSonValidos() {
        Mascota mascota = new Mascota();
        mascota.setId(1);

        when(mascotaDao.buscarPorId(1)).thenReturn(mascota);
        when(citaDao.guardar(citaValida)).thenReturn(true);

        boolean resultado = citaService.guardar(citaValida);

        assertTrue(resultado);
        verify(citaDao, times(1)).guardar(citaValida);
    }
}