package com.marketplace.notificacion.Service;

import com.marketplace.notificacion.DTO.NotificacionResponseDTO;
import com.marketplace.notificacion.model.Notificacion;
import com.marketplace.notificacion.repository.NotificacionRepository;
import com.marketplace.notificacion.service.NotificacionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class NotificacionServiceTest {

    @InjectMocks
    private NotificacionService notificacionService;

    @Mock
    private NotificacionRepository notificacionRepository;

    @Mock
    private WebClient pedidoWebClient;

    @Test
    public void testFindAll() {
        Notificacion notificacion = crearNotificacion();
        when(notificacionRepository.findAll()).thenReturn(List.of(notificacion));

        List<NotificacionResponseDTO> resultado = notificacionService.findAllNotificaciones();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Oferta especial", resultado.get(0).getAsunto());
    }

    @Test
    public void testFindById() {
        long id = 1L;
        Notificacion notificacion = crearNotificacion();
        when(notificacionRepository.findById(id)).thenReturn(Optional.of(notificacion));

        NotificacionResponseDTO resultado = notificacionService.findNotificacionesById(id);

        assertNotNull(resultado);
        assertEquals("Oferta especial", resultado.getAsunto());
    }

    @Test
    public void testFindById_NotFound() {
        long id = 99L;
        when(notificacionRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            notificacionService.findNotificacionesById(id);
        });
    }

    @Test
    public void testDeleteNotificacion() {
        long id = 1L;
        Notificacion notificacion = crearNotificacion();
        when(notificacionRepository.findById(id)).thenReturn(Optional.of(notificacion));
        doNothing().when(notificacionRepository).delete(notificacion);

        notificacionService.deleteNotificacion(id);

        verify(notificacionRepository, times(1)).delete(notificacion);
    }

    @Test
    public void testMakeNotificacion_AsuntoCorto() {
        com.marketplace.notificacion.DTO.NotificacionRequestDTO dto = new com.marketplace.notificacion.DTO.NotificacionRequestDTO();
        dto.setPedidoId(1L);
        dto.setAsunto("Hi");
        dto.setMensaje("Mensaje válido para la prueba");

        assertThrows(IllegalArgumentException.class, () -> {
            notificacionService.makeNotificacion(dto);
        });
    }

    @Test
    public void testMakeNotificacion_MensajeVacio() {
        com.marketplace.notificacion.DTO.NotificacionRequestDTO dto = new com.marketplace.notificacion.DTO.NotificacionRequestDTO();
        dto.setPedidoId(1L);
        dto.setAsunto("Asunto válido");
        dto.setMensaje("");

        assertThrows(IllegalArgumentException.class, () -> {
            notificacionService.makeNotificacion(dto);
        });
    }

    private Notificacion crearNotificacion() {
        Notificacion notificacion = new Notificacion();
        notificacion.setId(1L);
        notificacion.setAsunto("Oferta especial");
        notificacion.setMensaje("El producto que seguías bajó de precio");
        notificacion.setFecha(new Date());
        return notificacion;
    }
}