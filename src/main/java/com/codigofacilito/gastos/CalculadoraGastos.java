package src.main.java.com.codigofacilito.gastos;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class CalculadoraGastos {

    // configuracion
    private ConfiguracionApp configuracion;
    private String ARCHIVO_DATOS;  // Ya no es final, se lee de config

    private ArrayList<Movimiento> todosLosMovimientos;
    private HashMap<String, Gasto> gastosMap;
    private double presupuesto;
    private Scanner scanner;

    public CalculadoraGastos() {
        // NUEVO: Cargar configuración primero
        configuracion = new ConfiguracionApp();
        ARCHIVO_DATOS = configuracion.getArchivoDatos();

        todosLosMovimientos = new ArrayList<>();
        gastosMap = new HashMap<>();
        presupuesto = 0.0;
        scanner = new Scanner(System.in);

        cargarDatos();
    }

    private void guardarDatos() {
        try (FileOutputStream fos = new FileOutputStream(ARCHIVO_DATOS);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(todosLosMovimientos);
            oos.writeObject(gastosMap);
            oos.writeDouble(presupuesto);

            System.out.println("✅ Datos guardados correctamente");

        } catch (IOException e) {
            System.out.println("Error al guardar datos: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void cargarDatos() {
        File archivo = new File(ARCHIVO_DATOS);

        if (!archivo.exists()) {
            System.out.println("No hay datos previos. Iniciando desde cero.");
            return;
        }

        try (FileInputStream fis = new FileInputStream(ARCHIVO_DATOS);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            todosLosMovimientos = (ArrayList<Movimiento>) ois.readObject();
            gastosMap = (HashMap<String, Gasto>) ois.readObject();
            presupuesto = ois.readDouble();

            System.out.println("✅ Datos cargados correctamente");
            System.out.println("   Movimientos recuperados: " + todosLosMovimientos.size());

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error al cargar datos: " + e.getMessage());
            System.out.println("Iniciando desde cero.");
        }
    }

    public void empezar() {
        System.out.println("===========================================");
        // NUEVO: Usar nombre desde configuración
        System.out.println("   " + configuracion.getNombreApp());
        System.out.println("===========================================");

        int opcion;

        do {
            mostrarMenu();
            opcion = leerOpcion();

            try {
                switch (opcion) {
                    case 1:
                        agregarGasto();
                        break;
                    case 2:
                        agregarIngreso();
                        break;
                    case 3:
                        verTodo();
                        break;
                    case 4:
                        verSoloGastos();
                        break;
                    case 5:
                        verPorCategoria();
                        break;
                    case 6:
                        buscarGasto();
                        break;
                    case 7:
                        verResumen();
                        break;
                    case 8:
                        System.out.println("Guardando datos...");
                        guardarDatos();
                        System.out.println("Gracias por usar la calculadora!");
                        break;
                    default:
                        System.out.println("Opción no válida");
                }
            } catch (PresupuestoInsuficienteException e) {
                System.out.println("ERROR: " + e.getMessage());
            }

        } while (opcion != 8);

        scanner.close();
    }

    private void mostrarMenu() {
        System.out.println("--- MENU ---");
        System.out.println("1. Registrar un gasto");
        System.out.println("2. Registrar un ingreso");
        System.out.println("3. Ver todo");
        System.out.println("4. Ver solo gastos");
        System.out.println("5. Ver por categoría");
        System.out.println("6. Buscar un gasto");
        System.out.println("7. Ver resumen");
        System.out.println("8. Salir");

        // NUEVO: Usar símbolo de moneda desde config
        String simbolo = configuracion.getSimboloMoneda();
        System.out.println("Disponible: " + simbolo + presupuesto);

        // NUEVO: Alerta si presupuesto bajo
        if (presupuesto > 0 && presupuesto < configuracion.getAlertaPresupuestoBajo()) {
            System.out.println("⚠️ ALERTA: Presupuesto bajo!");
        }

        System.out.print("Opción: ");
    }

    private int leerOpcion() {
        int opcion = 0;

        while (opcion < 1 || opcion > 8) {
            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                if (opcion < 1 || opcion > 8) {
                    System.out.print("Tiene que ser entre 1 y 8: ");
                }
            } else {
                System.out.print("Eso no es un número: ");
                scanner.next();
            }
        }

        return opcion;
    }

    private void agregarGasto() throws PresupuestoInsuficienteException {
        scanner.nextLine();

        System.out.println("\n--- NUEVO GASTO ---");
        System.out.print("¿Qué compraste/pagaste?: ");
        String descripcion = scanner.nextLine();

        while (descripcion.trim().isEmpty()) {
            System.out.print("Tenés que escribir algo: ");
            descripcion = scanner.nextLine();
        }

        // NUEVO: Usar símbolo desde config
        String simbolo = configuracion.getSimboloMoneda();
        System.out.print("¿Cuánto?: " + simbolo);
        double monto = leerMonto();

        if (monto > presupuesto) {
            throw new PresupuestoInsuficienteException(monto - presupuesto);
        }

        // NUEVO: Alerta para gasto grande
        if (monto > configuracion.getAlertaGastoGrande()) {
            System.out.println("⚠️ Este es un gasto grande!");
        }

        // NUEVO: Usar categorías desde configuración
        System.out.println("Categoría:");
        String[] categorias = configuracion.getCategorias();
        for (int i = 0; i < categorias.length; i++) {
            System.out.println((i + 1) + ". " + categorias[i]);
        }
        System.out.print("Elegí: ");

        int cat = leerCategoria();
        String categoria = categorias[cat - 1];

        Gasto nuevoGasto = new Gasto(descripcion, monto, categoria);
        todosLosMovimientos.add(nuevoGasto);
        gastosMap.put(descripcion.toLowerCase(), nuevoGasto);

        presupuesto = presupuesto - monto;

        System.out.println("✅ Gasto registrado");
        System.out.println("Gastaste: " + simbolo + monto);
        System.out.println("Te quedan: " + simbolo + presupuesto);

        if (presupuesto < 0) {
            System.out.println("⚠️ OJO! Estás en negativo!");
        }
    }

    private void agregarIngreso() {
        scanner.nextLine();

        System.out.println("--- NUEVO INGRESO ---");
        System.out.print("De dónde vino la plata?: ");
        String descripcion = scanner.nextLine();

        while (descripcion.trim().isEmpty()) {
            System.out.print("Tenés que escribir algo: ");
            descripcion = scanner.nextLine();
        }

        // NUEVO: Usar símbolo desde config
        String simbolo = configuracion.getSimboloMoneda();
        System.out.print("¿Cuánto?: " + simbolo);
        double monto = leerMonto();

        Ingreso nuevoIngreso = new Ingreso(descripcion, monto);
        todosLosMovimientos.add(nuevoIngreso);

        presupuesto = presupuesto + monto;

        System.out.println("✅ Ingreso registrado");
        System.out.println("Ingresaste: " + simbolo + monto);
        System.out.println("Ahora tenés: " + simbolo + presupuesto);
    }

    private void verTodo() {
        System.out.println("--- TODOS LOS MOVIMIENTOS ---");

        if (todosLosMovimientos.isEmpty()) {
            System.out.println("No hay nada registrado todavía");
            return;
        }

        for (Movimiento m : todosLosMovimientos) {
            m.mostrar();
        }

        System.out.println("Total: " + todosLosMovimientos.size() + " movimientos");
    }

    private void verSoloGastos() {
        System.out.println("--- MIS GASTOS ---");

        int contador = 0;

        for (Movimiento m : todosLosMovimientos) {
            if (m instanceof Gasto) {
                m.mostrar();
                contador++;
            }
        }

        if (contador == 0) {
            System.out.println("No hay gastos todavía");
        } else {
            System.out.println("Total: " + contador + " gastos");
        }
    }

    private void verPorCategoria() {
        if (todosLosMovimientos.isEmpty()) {
            System.out.println("No hay gastos registrados");
            return;
        }

        // NUEVO: Usar categorías desde configuración
        System.out.println("--- FILTRAR POR CATEGORIA ---");
        String[] categorias = configuracion.getCategorias();
        for (int i = 0; i < categorias.length; i++) {
            System.out.println((i + 1) + ". " + categorias[i]);
        }
        System.out.print("Cuál querés ver?: ");

        int cat = leerCategoria();
        String categoriaElegida = categorias[cat - 1];

        System.out.println("Gastos en: " + categoriaElegida);

        double total = 0;
        int contador = 0;

        for (Movimiento m : todosLosMovimientos) {
            if (m instanceof Gasto) {
                Gasto g = (Gasto) m;
                if (g.getCategoria().equals(categoriaElegida)) {
                    contador++;
                    System.out.println(contador + ". " + g.getDescripcion() + " - $" + g.getMonto());
                    total = total + g.getMonto();
                }
            }
        }

        if (contador == 0) {
            System.out.println("No hay gastos en esta categoría");
        } else {
            System.out.println("Total en " + categoriaElegida + ": $" + total);
        }
    }

    private void buscarGasto() {
        scanner.nextLine();

        System.out.println("--- BUSCAR GASTO ---");
        System.out.print("Qué gasto querés buscar?: ");
        String buscar = scanner.nextLine().toLowerCase();

        Gasto encontrado = gastosMap.get(buscar);

        if (encontrado != null) {
            System.out.println("Encontrado:");
            encontrado.mostrar();
        } else {
            System.out.println("No encontré ese gasto");
        }
    }

    private void verResumen() {
        System.out.println("===========================================");
        System.out.println("              RESUMEN");
        System.out.println("===========================================");

        if (todosLosMovimientos.isEmpty()) {
            System.out.println("No hay nada que mostrar");
            return;
        }

        double totalGastos = 0;
        double totalIngresos = 0;
        int cantGastos = 0;
        int cantIngresos = 0;

        for (Movimiento m : todosLosMovimientos) {
            if (m instanceof Gasto) {
                totalGastos = totalGastos + m.getMonto();
                cantGastos++;
            } else if (m instanceof Ingreso) {
                totalIngresos = totalIngresos + m.getMonto();
                cantIngresos++;
            }
        }

        System.out.println("Ingresos: $" + totalIngresos + " (" + cantIngresos + ")");
        System.out.println("Gastos: $" + totalGastos + " (" + cantGastos + ")");
        System.out.println("Balance: $" + (totalIngresos - totalGastos));
        System.out.println("Disponible ahora: $" + presupuesto);

        if (cantGastos > 0) {
            double promedio = totalGastos / cantGastos;
            System.out.println("Promedio por gasto: $" + promedio);
        }

        System.out.println("Por categoría:");
        mostrarPorCategorias();
    }

    private void mostrarPorCategorias() {
        // NUEVO: Usar categorías desde configuración
        String[] categorias = configuracion.getCategorias();

        for (String cat : categorias) {
            double total = 0;
            int cant = 0;

            for (Movimiento m : todosLosMovimientos) {
                if (m instanceof Gasto) {
                    Gasto g = (Gasto) m;
                    if (g.getCategoria().equals(cat)) {
                        total = total + g.getMonto();
                        cant++;
                    }
                }
            }

            if (cant > 0) {
                System.out.println("  " + cat + ": $" + total + " (" + cant + ")");
            }
        }
    }

    private double leerMonto() {
        double monto = -1;

        while (monto <= 0) {
            if (scanner.hasNextDouble()) {
                monto = scanner.nextDouble();
                if (monto <= 0) {
                    System.out.print("Tiene que ser más que cero: $");
                }
            } else {
                System.out.print("Eso no es un número: $");
                scanner.next();
            }
        }

        return monto;
    }

    private int leerCategoria() {
        int opcion = 0;

        while (opcion < 1 || opcion > 5) {
            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                if (opcion < 1 || opcion > 5) {
                    System.out.print("Del 1 al 5: ");
                }
            } else {
                System.out.print("Tiene que ser un número: ");
                scanner.next();
            }
        }

        return opcion;
    }

    public static void main(String[] args) {
        CalculadoraGastos calc = new CalculadoraGastos();
        calc.empezar();
    }
}