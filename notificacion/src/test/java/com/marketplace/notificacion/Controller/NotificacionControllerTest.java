package com.marketplace.notificacion.Controller;

import com.marketplace.notificacion.DTO.NotificacionRequestDTO;
import com.marketplace.notificacion.DTO.NotificacionResponseDTO;
import com.marketplace.notificacion.service.NotificacionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class NotificacionControllerTest {

    @InjectMocks
    private com.marketplace.notificacion.controller.NotificacionController notificacionController;

    @Mock
    private NotificacionService notificacionService;

    @Mock
    private com.marketplace.notificacion.assemblers.NotificacionModelAssembler assembler;

    @Test
    public void testListar_RetornaLista() {
        NotificacionResponseDTO dto = crearResponseDTO();
        when(notificacionService.findAllNotificaciones()).thenReturn(List.of(dto));

        var resultado = notificacionService.findAllNotificaciones();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Oferta especial", resultado.get(0).getAsunto());
    }

    @Test
    public void testObtener_RetornaDTO() {
        NotificacionResponseDTO dto = crearResponseDTO();
        when(notificacionService.findNotificacionesById(1L)).thenReturn(dto);

        NotificacionResponseDTO resultado = notificacionService.findNotificacionesById(1L);

        assertNotNull(resultado);
        assertEquals("Oferta especial", resultado.getAsunto());
    }

    @Test
    public void testObtener_NotFound() {
        when(notificacionService.findNotificacionesById(99L))
                .thenThrow(new IllegalArgumentException("Notificación no encontrada"));

        assertThrows(IllegalArgumentException.class, () -> {
            notificacionService.findNotificacionesById(99L);
        });
    }

    @Test
    public void testEliminar_LlamaAlService() {
        doNothing().when(notificacionService).deleteNotificacion(1L);

        notificacionService.deleteNotificacion(1L);

        verify(notificacionService, times(1)).deleteNotificacion(1L);
    }

    private NotificacionResponseDTO crearResponseDTO() {
        NotificacionResponseDTO dto = new NotificacionResponseDTO();
        dto.setId(1L);
        dto.setAsunto("Oferta especial");
        dto.setMensaje("El producto bajó de precio");
        dto.setFecha(new Date());
        return dto;
    }
}
