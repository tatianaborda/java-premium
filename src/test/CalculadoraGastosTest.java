package src.test;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import src.main.java.com.codigofacilito.gastos.CalculadoraGastos;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class CalculadoraGastosTest {

    private CalculadoraGastos calculadora;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final InputStream originalIn = System.in;

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outContent));

        // Eliminar archivo de datos si existe para tests limpios
        File archivo = new File("gastos.dat");
        if (archivo.exists()) {
            archivo.delete();
        }
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setIn(originalIn);

        // Limpiar archivo de datos después de tests
        File archivo = new File("gastos.dat");
        if (archivo.exists()) {
            archivo.delete();
        }
    }

    @Test
    public void testConstructor() {
        calculadora = new CalculadoraGastos();
        assertNotNull(calculadora);
    }

    @Test
    public void testConstructorCargaDatos() {
        calculadora = new CalculadoraGastos();
        String output = outContent.toString();

        assertTrue(output.contains("datos previos") || output.contains("cargados correctamente"));
    }

    @Test
    public void testPresupuestoInicialCero() throws Exception {
        calculadora = new CalculadoraGastos();

        // Usar reflection para acceder al presupuesto privado
        Field presupuestoField = CalculadoraGastos.class.getDeclaredField("presupuesto");
        presupuestoField.setAccessible(true);
        double presupuesto = (double) presupuestoField.get(calculadora);

        assertEquals(0.0, presupuesto, 0.01);
    }

    @Test
    public void testLeerOpcionValida() throws Exception {
        String input = "3";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        calculadora = new CalculadoraGastos();

        Method leerOpcionMethod = CalculadoraGastos.class.getDeclaredMethod("leerOpcion");
        leerOpcionMethod.setAccessible(true);

        int opcion = (int) leerOpcionMethod.invoke(calculadora);

        assertTrue(opcion >= 1 && opcion <= 8);
    }

    @Test
    public void testLeerMontoValido() throws Exception {
        String input = "500.50";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        calculadora = new CalculadoraGastos();

        Method leerMontoMethod = CalculadoraGastos.class.getDeclaredMethod("leerMonto");
        leerMontoMethod.setAccessible(true);

        double monto = (double) leerMontoMethod.invoke(calculadora);

        assertEquals(500.50, monto, 0.01);
    }

    @Test
    public void testLeerCategoriaValida() throws Exception {
        String input = "1";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        calculadora = new CalculadoraGastos();

        Method leerCategoriaMethod = CalculadoraGastos.class.getDeclaredMethod("leerCategoria");
        leerCategoriaMethod.setAccessible(true);

        int categoria = (int) leerCategoriaMethod.invoke(calculadora);

        assertTrue(categoria >= 1 && categoria <= 5);
    }

    @Test
    public void testGuardarYCargarDatos() throws Exception {
        // Crear calculadora y agregar ingreso
        String input = "Sueldo 5000";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        calculadora = new CalculadoraGastos();

        Method agregarIngresoMethod = CalculadoraGastos.class.getDeclaredMethod("agregarIngreso");
        agregarIngresoMethod.setAccessible(true);
        agregarIngresoMethod.invoke(calculadora);

        // Guardar datos
        Method guardarDatosMethod = CalculadoraGastos.class.getDeclaredMethod("guardarDatos");
        guardarDatosMethod.setAccessible(true);
        guardarDatosMethod.invoke(calculadora);

        // Crear nueva instancia y verificar que cargo los datos
        outContent.reset();
        CalculadoraGastos nuevaCalculadora = new CalculadoraGastos();
        String output = outContent.toString();

        assertTrue(output.contains("cargados correctamente"));
    }

    @Test
    public void testAgregarIngresoAumentaPresupuesto() throws Exception {
        String input = "Freelance 1000";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        calculadora = new CalculadoraGastos();

        Method agregarIngresoMethod = CalculadoraGastos.class.getDeclaredMethod("agregarIngreso");
        agregarIngresoMethod.setAccessible(true);
        agregarIngresoMethod.invoke(calculadora);

        Field presupuestoField = CalculadoraGastos.class.getDeclaredField("presupuesto");
        presupuestoField.setAccessible(true);
        double presupuesto = (double) presupuestoField.get(calculadora);

        assertEquals(1000.0, presupuesto, 0.01);
    }

    @Test
    public void testVerTodoSinMovimientos() throws Exception {
        calculadora = new CalculadoraGastos();

        Method verTodoMethod = CalculadoraGastos.class.getDeclaredMethod("verTodo");
        verTodoMethod.setAccessible(true);

        outContent.reset();
        verTodoMethod.invoke(calculadora);
        String output = outContent.toString();

        assertTrue(output.contains("nada registrado") || output.contains("No hay"));
    }

    @Test
    public void testVerSoloGastosSinGastos() throws Exception {
        calculadora = new CalculadoraGastos();

        Method verSoloGastosMethod = CalculadoraGastos.class.getDeclaredMethod("verSoloGastos");
        verSoloGastosMethod.setAccessible(true);

        outContent.reset();
        verSoloGastosMethod.invoke(calculadora);
        String output = outContent.toString();

        assertTrue(output.contains("No hay gastos"));
    }

    @Test
    public void testMostrarMenu() throws Exception {
        calculadora = new CalculadoraGastos();

        // Simular entrada para que no se quede esperando
        String input = "8";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Method mostrarMenuMethod = CalculadoraGastos.class.getDeclaredMethod("mostrarMenu");
        mostrarMenuMethod.setAccessible(true);

        outContent.reset();
        mostrarMenuMethod.invoke(calculadora);
        String output = outContent.toString();

        assertTrue(output.contains("MENU"));
        assertTrue(output.contains("Registrar un gasto"));
        assertTrue(output.contains("Registrar un ingreso"));
        assertTrue(output.contains("Salir"));
        assertTrue(output.contains("Disponible"));
    }
}
