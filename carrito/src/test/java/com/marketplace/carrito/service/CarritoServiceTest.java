package com.marketplace.carrito.service;
import com.marketplace.carrito.dto.CarritoRequestDTO;
import com.marketplace.carrito.dto.CarritoResponseDTO;
import com.marketplace.carrito.dto.CarritoProductoRequestDTO;
import com.marketplace.carrito.dto.CarritoProductoResponseDTO;
import com.marketplace.carrito.model.Carrito;
import com.marketplace.carrito.model.CarritoProducto;
import com.marketplace.carrito.repository.CarritoProductoRepository;
import com.marketplace.carrito.repository.CarritoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CarritoServiceTest {

    // Inyecta el service real que vamos a probar
    @InjectMocks
    private CarritoService carritoService;

    // Mock del repositorio — simula la BD sin tocarla
    @Mock
    private CarritoRepository carritoRepository;

    @Mock
    private CarritoProductoRepository carritoProductoRepository;

    @Test
    public void testCrear_RetornaCarrito() {
        // ARRANGE — preparamos los datos falsos
        Carrito carrito = new Carrito();
        carrito.setId(1L);
        carrito.setUsuarioId(5L);

        CarritoRequestDTO dto = new CarritoRequestDTO();
        dto.setUsuarioId(5L);

        // cuando el repo guarde cualquier carrito, devuelve el nuestro
        when(carritoRepository.save(any(Carrito.class))).thenReturn(carrito);

        // ACT — llamamos al método real
        CarritoResponseDTO resultado = carritoService.crear(dto);

        // ASSERT — verificamos que el resultado es correcto
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(5L, resultado.getUsuarioId());
    }

    @Test
    public void testObtener_CuandoExiste() {
        // ARRANGE
        Carrito carrito = new Carrito();
        carrito.setId(1L);
        carrito.setUsuarioId(3L);

        when(carritoRepository.findById(1L)).thenReturn(Optional.of(carrito));

        // ACT
        CarritoResponseDTO resultado = carritoService.obtener(1L);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals(3L, resultado.getUsuarioId());
    }

    @Test
    public void testObtener_CuandoNoExiste() {
        // ARRANGE — simulamos que no existe
        when(carritoRepository.findById(99L)).thenReturn(Optional.empty());

        // ASSERT — verifica que lanza excepción
        assertThrows(RuntimeException.class, () -> carritoService.obtener(99L));
    }

    @Test
    public void testAgregarProducto_NuevoProducto() {
        // ARRANGE
        Carrito carrito = new Carrito();
        carrito.setId(1L);
        carrito.setUsuarioId(2L);

        CarritoProducto cp = new CarritoProducto();
        cp.setId(1L);
        cp.setCarrito(carrito);
        cp.setProductoId(10L);
        cp.setCantidad(2);

        CarritoProductoRequestDTO dto = new CarritoProductoRequestDTO();
        dto.setCarritoId(1L);
        dto.setProductoId(10L);
        dto.setCantidad(2);

        when(carritoRepository.findById(1L)).thenReturn(Optional.of(carrito));
        // producto no existe todavía en el carrito
        when(carritoProductoRepository.findByCarrito_IdAndProductoId(1L, 10L))
                .thenReturn(Optional.empty());
        when(carritoProductoRepository.save(any(CarritoProducto.class))).thenReturn(cp);

        // ACT
        CarritoProductoResponseDTO resultado = carritoService.agregarProducto(dto);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(10L, resultado.getProductoId());
        assertEquals(2, resultado.getCantidad());
    }

    @Test
    public void testAgregarProducto_ProductoYaExiste() {
        // ARRANGE — el producto ya estaba en el carrito con cantidad 3
        Carrito carrito = new Carrito();
        carrito.setId(1L);
        carrito.setUsuarioId(2L);

        CarritoProducto cpExistente = new CarritoProducto();
        cpExistente.setId(1L);
        cpExistente.setCarrito(carrito);
        cpExistente.setProductoId(10L);
        cpExistente.setCantidad(3); // ya tenía 3

        CarritoProductoRequestDTO dto = new CarritoProductoRequestDTO();
        dto.setCarritoId(1L);
        dto.setProductoId(10L);
        dto.setCantidad(2); // agrega 2 más

        CarritoProducto cpActualizado = new CarritoProducto();
        cpActualizado.setId(1L);
        cpActualizado.setCarrito(carrito);
        cpActualizado.setProductoId(10L);
        cpActualizado.setCantidad(5); // 3 + 2 = 5

        when(carritoRepository.findById(1L)).thenReturn(Optional.of(carrito));
        when(carritoProductoRepository.findByCarrito_IdAndProductoId(1L, 10L))
                .thenReturn(Optional.of(cpExistente));
        when(carritoProductoRepository.save(any(CarritoProducto.class))).thenReturn(cpActualizado);

        // ACT
        CarritoProductoResponseDTO resultado = carritoService.agregarProducto(dto);

        // ASSERT — verifica que la cantidad se sumó correctamente
        assertNotNull(resultado);
        assertEquals(5, resultado.getCantidad());
    }

    @Test
    public void testListarProductos() {
        // ARRANGE
        Carrito carrito = new Carrito();
        carrito.setId(1L);

        CarritoProducto cp = new CarritoProducto();
        cp.setId(1L);
        cp.setCarrito(carrito);
        cp.setProductoId(10L);
        cp.setCantidad(2);

        when(carritoProductoRepository.findByCarrito_Id(1L)).thenReturn(List.of(cp));

        // ACT
        List<CarritoProductoResponseDTO> resultado = carritoService.listarProductos(1L);

        // ASSERT
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(10L, resultado.get(0).getProductoId());
    }
}
