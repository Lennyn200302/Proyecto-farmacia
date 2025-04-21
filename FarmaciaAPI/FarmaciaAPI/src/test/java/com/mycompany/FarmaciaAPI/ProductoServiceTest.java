package com.mycompany.FarmaciaAPI;

import com.mycompany.modelo.Producto;
import com.mycompany.repository.ProductoRepository;
import com.mycompany.service.ProductoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

// Pruebas unitarias
public class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository; // Simulamos el repositorio

    @InjectMocks
    private ProductoService productoService; // El servicio que vamos a probar

    private Producto producto;

    @BeforeEach
    void setUp() {
        // Inicializa los mocks
        MockitoAnnotations.openMocks(this);

        // Prepara un objeto Producto para las pruebas
        producto = new Producto();
        producto.setId(1L);
        producto.setNombre("Producto Test");
        producto.setPrecio(100.0);
        producto.setStock(50);
        producto.setFechaVencimiento(LocalDate.of(2025, 12, 31));
    }

    @Test
    void testGuardarProducto() {
        when(productoRepository.save(any(Producto.class))).thenReturn(producto);

        Producto result = productoService.guardar(producto);

        assertNotNull(result);
        assertEquals("Producto Test", result.getNombre());
        assertEquals(100.0, result.getPrecio());
        assertEquals(50, result.getStock());
        assertEquals(LocalDate.of(2025, 12, 31), result.getFechaVencimiento());
    }

    @Test
    void testObtenerPorId() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        Optional<Producto> result = productoService.obtenerPorId(1L);

        assertTrue(result.isPresent());
        assertEquals("Producto Test", result.get().getNombre());
        assertEquals(100.0, result.get().getPrecio());
        assertEquals(50, result.get().getStock());
        assertEquals(LocalDate.of(2025, 12, 31), result.get().getFechaVencimiento());
    }

}
