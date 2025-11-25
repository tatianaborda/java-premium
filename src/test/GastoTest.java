package src.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.main.java.com.codigofacilito.gastos.Gasto;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GastoTest {

    private Gasto gasto;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        gasto = new Gasto("Supermercado", 1500.50, "Comida");
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void testConstructor() {
        assertEquals("Supermercado", gasto.getDescripcion());
        assertEquals(1500.50, gasto.getMonto(), 0.01);
        assertEquals("Comida", gasto.getCategoria());
    }

    @Test
    public void testGetCategoria() {
        assertEquals("Comida", gasto.getCategoria());

        Gasto gastoTransporte = new Gasto("Uber", 200.0, "Transporte");
        assertEquals("Transporte", gastoTransporte.getCategoria());
    }

    @Test
    public void testMostrar() {
        gasto.mostrar();
        String output = outContent.toString();

        assertTrue(output.contains("Supermercado"));
        assertTrue(output.contains("1500.5"));
        assertTrue(output.contains("Comida"));
    }

    @Test
    public void testGastoConMontoDecimal() {
        Gasto gastoDecimal = new Gasto("Café", 45.75, "Entretenimiento");
        assertEquals(45.75, gastoDecimal.getMonto(), 0.01);
    }

    @Test
    public void testMultiplesGastosDistintasCategories() {
        Gasto g1 = new Gasto("Hospital", 2000.0, "Salud");
        Gasto g2 = new Gasto("Bus", 50.0, "Transporte");
        Gasto g3 = new Gasto("Netflix", 300.0, "Entretenimiento");

        assertEquals("Salud", g1.getCategoria());
        assertEquals("Transporte", g2.getCategoria());
        assertEquals("Entretenimiento", g3.getCategoria());
    }
}
