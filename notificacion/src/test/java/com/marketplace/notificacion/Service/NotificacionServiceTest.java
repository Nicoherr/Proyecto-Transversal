package com.marketplace.notificacion.Service;

import com.marketplace.notificacion.DTO.NotificacionRequestDTO;
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
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificacionServiceTest {

    @InjectMocks
    private NotificacionService notificacionService;

    @Mock
    private NotificacionRepository notificacionRepository;

    @Mock
    private WebClient pedidoWebClient;

    // ─── Tests CRUD ───────────────────────────────────────────────────────────

    @Test
    public void testFindAll() {
        when(notificacionRepository.findAll()).thenReturn(List.of(crearNotificacion()));

        List<NotificacionResponseDTO> resultado = notificacionService.findAllNotificaciones();

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Pedido confirmado", resultado.get(0).getAsunto());
    }

    @Test
    public void testFindById() {
        when(notificacionRepository.findById(1L)).thenReturn(Optional.of(crearNotificacion()));

        NotificacionResponseDTO resultado = notificacionService.findNotificacionesById(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getPedidoId());
    }

    @Test
    public void testFindById_NotFound() {
        when(notificacionRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () ->
                notificacionService.findNotificacionesById(99L));
    }

    @Test
    public void testDeleteNotificacion() {
        Notificacion notificacion = crearNotificacion();
        when(notificacionRepository.findById(1L)).thenReturn(Optional.of(notificacion));
        doNothing().when(notificacionRepository).delete(notificacion);

        notificacionService.deleteNotificacion(1L);

        verify(notificacionRepository, times(1)).delete(notificacion);
    }

    // ─── Tests reglas de negocio ──────────────────────────────────────────────

    @Test
    public void testMakeNotificacion_AsuntoCorto() {
        NotificacionRequestDTO dto = crearRequest();
        dto.setAsunto("Hi");

        assertThrows(IllegalArgumentException.class, () ->
                notificacionService.makeNotificacion(dto));
    }

    @Test
    public void testMakeNotificacion_MensajeCorto() {
        NotificacionRequestDTO dto = crearRequest();
        dto.setMensaje("Corto");

        assertThrows(IllegalArgumentException.class, () ->
                notificacionService.makeNotificacion(dto));
    }

    @Test
    public void testMakeNotificacion_MensajeLargo() {
        NotificacionRequestDTO dto = crearRequest();
        dto.setMensaje("a".repeat(501));

        assertThrows(IllegalArgumentException.class, () ->
                notificacionService.makeNotificacion(dto));
    }

    @Test
    public void testMakeNotificacion_Duplicado() {
        NotificacionRequestDTO dto = crearRequest();
        when(notificacionRepository.existsByPedidoIdAndAsunto(
                dto.getPedidoId(), dto.getAsunto())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () ->
                notificacionService.makeNotificacion(dto));
    }

    // ─── Helpers ──────────────────────────────────────────────────────────────

    private Notificacion crearNotificacion() {
        Notificacion n = new Notificacion();
        n.setId(1L);
        n.setPedidoId(1L);
        n.setAsunto("Pedido confirmado");
        n.setMensaje("Tu pedido ha sido confirmado exitosamente");
        n.setFecha(new Date());
        return n;
    }

    private NotificacionRequestDTO crearRequest() {
        NotificacionRequestDTO dto = new NotificacionRequestDTO();
        dto.setPedidoId(1L);
        dto.setAsunto("Pedido confirmado");
        dto.setMensaje("Tu pedido ha sido confirmado exitosamente en el sistema");
        return dto;
    }
}