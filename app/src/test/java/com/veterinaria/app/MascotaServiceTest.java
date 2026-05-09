package com.veterinaria.app;

import com.veterinaria.app.model.Mascota;
import com.veterinaria.app.service.MascotaService;
import com.veterinaria.app.dao.DuenoDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Optional;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class MascotaServiceTest {
    @Mock
    private MascotaRepository mascotaRepository;
    @Mock
    private DuenoDao duenoDao;
    @InjectMocks
    private MascotaService mascotaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testEliminarMascota_IdValidoExiste() {
        int id = 1;
        when(mascotaRepository.existsById((long) id)).thenReturn(true);
        boolean result = mascotaService.eliminar(id);
        verify(mascotaRepository).deleteById((long) id);
        assertTrue(result);
    }

    @Test
    void testEliminarMascota_IdInvalido() {
        int id = 0;
        boolean result = mascotaService.eliminar(id);
        verify(mascotaRepository, never()).deleteById(anyLong());
        assertFalse(result);
    }

    @Test
    void testEliminarMascota_IdNoExiste() {
        int id = 2;
        when(mascotaRepository.existsById((long) id)).thenReturn(false);
        boolean result = mascotaService.eliminar(id);
        verify(mascotaRepository, never()).deleteById(anyLong());
        assertFalse(result);
    }
}
