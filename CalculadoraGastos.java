import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class CalculadoraGastos {

    // Ahora uso ArrayList en vez de arrays
    private ArrayList<Movimiento> todosLosMovimientos;
    private HashMap<String, Gasto> gastosMap; // Para buscar rápido

    private double presupuesto;
    private Scanner scanner;

    public CalculadoraGastos() {
        todosLosMovimientos = new ArrayList<>();
        gastosMap = new HashMap<>();
        presupuesto = 0.0;
        scanner = new Scanner(System.in);
    }

    public void empezar() {
        System.out.println("===========================================");
        System.out.println("   CALCULADORA DE GASTOS PERSONALES");
        System.out.println("===========================================");

        int opcion;

        do {
            mostrarMenu();
            opcion = leerOpcion();

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
                    System.out.println("\nGracias por usar la calculadora!");
                    break;
                default:
                    System.out.println("Opción no válida");
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
        System.out.println("Disponible: $" + presupuesto);
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

    private void agregarGasto() {
        scanner.nextLine();

        System.out.println("--- NUEVO GASTO ---");
        System.out.print("Qué compraste/pagaste?: ");
        String descripcion = scanner.nextLine();

        while (descripcion.trim().isEmpty()) {
            System.out.print("Tenés que escribir algo: ");
            descripcion = scanner.nextLine();
        }

        System.out.print("Cuánto?: $");
        double monto = leerMonto();

        System.out.println("Categoría:");
        System.out.println("1. Comida");
        System.out.println("2. Transporte");
        System.out.println("3. Entretenimiento");
        System.out.println("4. Salud");
        System.out.println("5. Otros");
        System.out.print("Elegí: ");

        int cat = leerCategoria();
        String categoria = "";

        // Lo dejé con if-else porque me parece más claro
        if (cat == 1) {
            categoria = "Comida";
        } else if (cat == 2) {
            categoria = "Transporte";
        } else if (cat == 3) {
            categoria = "Entretenimiento";
        } else if (cat == 4) {
            categoria = "Salud";
        } else {
            categoria = "Otros";
        }

        // Creo el objeto Gasto
        Gasto nuevoGasto = new Gasto(descripcion, monto, categoria);

        // Lo agrego al ArrayList
        todosLosMovimientos.add(nuevoGasto);

        // Y tambn al HashMap para buscarlo despues
        gastosMap.put(descripcion.toLowerCase(), nuevoGasto);

        presupuesto = presupuesto - monto;

        System.out.println("Ok! Gasto registrado");
        System.out.println("Gastaste: $" + monto);
        System.out.println("Te quedan: $" + presupuesto);

        if (presupuesto < 0) {
            System.out.println("OJO! Estás en negativo!");
        }
    }

    private void agregarIngreso() {
        scanner.nextLine();

        System.out.println("--- NUEVO INGRESO ---");
        System.out.print("¿De dónde vino la plata?: ");
        String descripcion = scanner.nextLine();

        while (descripcion.trim().isEmpty()) {
            System.out.print("Tenés que escribir algo: ");
            descripcion = scanner.nextLine();
        }

        System.out.print("¿Cuánto?: $");
        double monto = leerMonto();

        // Creo el objeto Ingreso
        Ingreso nuevoIngreso = new Ingreso(descripcion, monto);
        todosLosMovimientos.add(nuevoIngreso);

        presupuesto = presupuesto + monto;

        System.out.println("\nOk! Ingreso registrado");
        System.out.println("Ingresaste: $" + monto);
        System.out.println("Ahora tenés: $" + presupuesto);
    }

    private void verTodo() {
        System.out.println("--- TODOS LOS MOVIMIENTOS ---");

        if (todosLosMovimientos.isEmpty()) {
            System.out.println("No hay nada registrado todavía");
            return;
        }

        // Aca el polimorfismo cada uno se muestra diferente
        for (Movimiento m : todosLosMovimientos) {
            m.mostrar();
        }

        System.out.println("Total: " + todosLosMovimientos.size() + " movimientos");
    }

    private void verSoloGastos() {
        System.out.println("--- MIS GASTOS ---");

        int contador = 0;

        // Filtro solo los gastos usando instanceof
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

        System.out.println("--- FILTRAR POR CATEGORIA ---");
        System.out.println("1. Comida");
        System.out.println("2. Transporte");
        System.out.println("3. Entretenimiento");
        System.out.println("4. Salud");
        System.out.println("5. Otros");
        System.out.print("¿Cuál querés ver?: ");

        int cat = leerCategoria();
        String categoriaElegida = "";

        if (cat == 1) categoriaElegida = "Comida";
        else if (cat == 2) categoriaElegida = "Transporte";
        else if (cat == 3) categoriaElegida = "Entretenimiento";
        else if (cat == 4) categoriaElegida = "Salud";
        else categoriaElegida = "Otros";

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
        System.out.print("¿Qué gasto querés buscar?: ");
        String buscar = scanner.nextLine().toLowerCase();

        // Acá uso el HashMap para buscar rápido
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

        // Separo gastos de ingresos
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

        // Muestro por categoría
        System.out.println("Por categoría:");
        mostrarPorCategorias();
    }

    private void mostrarPorCategorias() {
        String[] categorias = {"Comida", "Transporte", "Entretenimiento", "Salud", "Otros"};

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