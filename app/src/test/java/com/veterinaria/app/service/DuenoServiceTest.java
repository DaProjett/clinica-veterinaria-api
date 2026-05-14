package com.veterinaria.app.service;

import com.veterinaria.app.dao.DuenoDao;
import com.veterinaria.app.model.Dueno;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DuenoServiceTest {

    @Mock
    private DuenoDao duenoDao;

    @InjectMocks
    private DuenoService duenoService;

    private Dueno duenoValido;

    @BeforeEach
    void setUp() {
        duenoValido = new Dueno();
        duenoValido.setNombreCompleto("Juan Perez");
        duenoValido.setDocumentoIdentidad("123456789");
        duenoValido.setTelefono("3001234567");
        duenoValido.setEmail("juan@correo.com");
        duenoValido.setDireccion("Calle 10");
    }

    @Test
    void noDebeGuardarSiNombreEsVacio() {
        duenoValido.setNombreCompleto(" ");

        boolean resultado = duenoService.guardar(duenoValido);

        assertFalse(resultado);
        verify(duenoDao, never()).guardar(any());
    }

    @Test
    void noDebeGuardarSiDocumentoEsVacio() {
        duenoValido.setDocumentoIdentidad("");

        boolean resultado = duenoService.guardar(duenoValido);

        assertFalse(resultado);
        verify(duenoDao, never()).guardar(any());
    }

    @Test
    void noDebeGuardarSiTelefonoEsInvalido() {
        duenoValido.setTelefono("123");

        boolean resultado = duenoService.guardar(duenoValido);

        assertFalse(resultado);
        verify(duenoDao, never()).guardar(any());
    }

    @Test
    void noDebeGuardarSiEmailEsInvalido() {
        duenoValido.setEmail("correo-invalido");

        boolean resultado = duenoService.guardar(duenoValido);

        assertFalse(resultado);
        verify(duenoDao, never()).guardar(any());
    }

    @Test
    void debeGuardarSiDatosSonValidos() {
        when(duenoDao.guardar(duenoValido)).thenReturn(true);

        boolean resultado = duenoService.guardar(duenoValido);

        assertTrue(resultado);
        verify(duenoDao, times(1)).guardar(duenoValido);
    }
}