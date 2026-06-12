package com.marketplace.notificacion.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketplace.notificacion.DTO.NotificacionRequestDTO;
import com.marketplace.notificacion.DTO.NotificacionResponseDTO;
import com.marketplace.notificacion.controller.NotificacionController;
import com.marketplace.notificacion.service.NotificacionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NotificacionController.class)
public class NotificacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private NotificacionService notificacionService;

    @MockBean
    private WebClient pedidoWebClient;

    @Test
    public void testGetNotificaciones() throws Exception {
        NotificacionResponseDTO dto = crearResponseDTO();
        when(notificacionService.findAllNotificaciones()).thenReturn(List.of(dto));

        mockMvc.perform(get("/notificaciones")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].asunto").value("Oferta especial"));
    }

    @Test
    public void testGetNotificacionById() throws Exception {
        NotificacionResponseDTO dto = crearResponseDTO();
        when(notificacionService.findNotificacionesById(1L)).thenReturn(dto);

        mockMvc.perform(get("/notificaciones/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.asunto").value("Oferta especial"));
    }

    @Test
    public void testGetNotificacionById_NotFound() throws Exception {
        when(notificacionService.findNotificacionesById(99L))
                .thenThrow(new IllegalArgumentException("Notificación no encontrada con id: 99"));

        mockMvc.perform(get("/notificaciones/99")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testPostNotificacion() throws Exception {
        NotificacionRequestDTO request = new NotificacionRequestDTO();
        request.setPedidoId(1L);
        request.setAsunto("Oferta especial");
        request.setMensaje("El producto bajó de precio");

        NotificacionResponseDTO response = crearResponseDTO();
        when(notificacionService.makeNotificacion(any(NotificacionRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/notificaciones")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.asunto").value("Oferta especial"));
    }

    @Test
    public void testDeleteNotificacion() throws Exception {
        doNothing().when(notificacionService).deleteNotificacion(anyLong());

        mockMvc.perform(delete("/notificaciones/1"))
                .andExpect(status().isNoContent());

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
