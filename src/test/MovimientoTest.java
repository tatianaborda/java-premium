package src.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;
import src.main.java.com.codigofacilito.gastos.Movimiento;
import src.main.java.com.codigofacilito.gastos.Gasto;
import src.main.java.com.codigofacilito.gastos.Ingreso;
import java.time.LocalDateTime;

public class MovimientoTest {

    private Movimiento gasto;
    private Movimiento ingreso;

    @BeforeEach
    public void setUp() {
        gasto = new Gasto("Pizza", 500.0, "Comida");
        ingreso = new Ingreso("Sueldo", 5000.0);
    }

    @Test
    public void testGetMonto() {
        assertEquals(500.0, gasto.getMonto(), 0.01);
        assertEquals(5000.0, ingreso.getMonto(), 0.01);
    }

    @Test
    public void testGetDescripcion() {
        assertEquals("Pizza", gasto.getDescripcion());
        assertEquals("Sueldo", ingreso.getDescripcion());
    }

    @Test
    public void testGetFecha() {
        assertNotNull(gasto.getFecha());
        assertTrue(gasto.getFecha() instanceof LocalDateTime);
    }

    @Test
    public void testGetFechaFormateada() {
        String fechaFormateada = gasto.getFechaFormateada();
        assertNotNull(fechaFormateada);
        assertTrue(fechaFormateada.matches("\\d{2}/\\d{2}/\\d{4} \\d{2}:\\d{2}"));
    }

    @Test
    public void testFechaEsReciente() {
        LocalDateTime ahora = LocalDateTime.now();
        assertTrue(gasto.getFecha().isBefore(ahora.plusSeconds(1)));
        assertTrue(gasto.getFecha().isAfter(ahora.minusSeconds(5)));
    }
}
