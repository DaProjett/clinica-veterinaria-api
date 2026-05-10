package com.veterinaria.app.service;

import com.veterinaria.app.dao.DuenoDao;
import com.veterinaria.app.dao.MascotaDao;
import com.veterinaria.app.model.Dueno;
import com.veterinaria.app.model.Mascota;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MascotaServiceTest {

    @Mock
    private MascotaDao mascotaDao;

    @Mock
    private DuenoDao duenoDao;

    @InjectMocks
    private MascotaService mascotaService;

    private Mascota mascotaValida;

    @BeforeEach
    void setUp() {
        mascotaValida = new Mascota();
        mascotaValida.setNombre("Firulais");
        mascotaValida.setEdad(3);
        mascotaValida.setIdDueno(1);
    }

    @Test
    void noDebeGuardarSiNombreEsVacio() {
        mascotaValida.setNombre(" ");

        boolean resultado = mascotaService.guardar(mascotaValida);

        assertFalse(resultado);
        verify(mascotaDao, never()).guardar(any());
    }

    @Test
    void noDebeGuardarSiEdadEsNegativa() {
        mascotaValida.setEdad(-1);

        boolean resultado = mascotaService.guardar(mascotaValida);

        assertFalse(resultado);
        verify(mascotaDao, never()).guardar(any());
    }

    @Test
    void noDebeGuardarSiDuenoNoExiste() {
        when(duenoDao.buscarPorId(1L)).thenReturn(null);

        boolean resultado = mascotaService.guardar(mascotaValida);

        assertFalse(resultado);
        verify(mascotaDao, never()).guardar(any());
    }

    @Test
    void debeGuardarSiDatosSonValidosYDuenoExiste() {
        Dueno dueno = new Dueno();
        dueno.setId(1L);

        when(duenoDao.buscarPorId(1L)).thenReturn(dueno);
        when(mascotaDao.guardar(mascotaValida)).thenReturn(true);

        boolean resultado = mascotaService.guardar(mascotaValida);

        assertTrue(resultado);
        verify(mascotaDao, times(1)).guardar(mascotaValida);
    }
}