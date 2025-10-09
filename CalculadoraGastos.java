import java.util.Scanner;

public class CalculadoraGastos {

    // Constantes
    private static final int MAX_GASTOS = 20;
    private static final double PRESUPUESTO_INICIAL = 0.0;

    // Variables globales para guardar los gastos
    private static String[] descripciones = new String[MAX_GASTOS];
    private static double[] montos = new double[MAX_GASTOS];
    private static String[] categorias = new String[MAX_GASTOS];
    private static int totalGastos = 0;
    private static double presupuestoActual = PRESUPUESTO_INICIAL;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        System.out.println("===========================================");
        System.out.println("   CALCULADORA DE GASTOS PERSONALES");
        System.out.println("===========================================");
        System.out.println("Presupuesto inicial: $" + PRESUPUESTO_INICIAL);

        do {
            mostrarMenu();
            opcion = leerOpcion(scanner);

            switch (opcion) {
                case 1:
                    agregarGasto(scanner);
                    break;
                case 2:
                    verGastos();
                    break;
                case 3:
                    verPorCategoria(scanner);
                    break;
                case 4:
                    verResumen();
                    break;
                case 5:
                    cambiarPresupuesto(scanner);
                    break;
                case 6:
                    System.out.println("Gracias por usar la calculadora!");
                    break;
                default:
                    System.out.println("Opción no válida");
            }

        } while (opcion != 6);

        scanner.close();
    }

    private static void mostrarMenu() {
        System.out.println("--- MENU PRINCIPAL ---");
        System.out.println("1. Agregar un gasto");
        System.out.println("2. Ver todos mis gastos");
        System.out.println("3. Ver gastos por categoría");
        System.out.println("4. Ver resumen");
        System.out.println("5. Cambiar mi presupuesto");
        System.out.println("6. Salir");
        System.out.println("Presupuesto disponible: $" + presupuestoActual);
        System.out.print("Elegi una opción: ");
    }

    private static int leerOpcion(Scanner scanner) {
        int opcion = 0;

        while (opcion < 1 || opcion > 6) {
            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                if (opcion < 1 || opcion > 6) {
                    System.out.print("Debe ser entre 1 y 6, intenta de nuevo: ");
                }
            } else {
                System.out.print("Eso no es un número, intenta de nuevo: ");
                scanner.next();
            }
        }

        return opcion;
    }

    private static void agregarGasto(Scanner scanner) {
        if (totalGastos >= MAX_GASTOS) {
            System.out.println("Ya llegaste al límite de gastos que puedes registrar");
            return;
        }

        scanner.nextLine(); // limpiar

        System.out.println("--- AGREGAR GASTO ---");
        System.out.print("En qué gastaste?: ");
        String descripcion = scanner.nextLine();

        // Validar que escribió algo
        while (descripcion.trim().isEmpty()) {
            System.out.print("Necesitas escribir algo, en qué gastaste?: ");
            descripcion = scanner.nextLine();
        }

        // Leer cuánto gastó
        System.out.print("¿Cuánto gastaste? $");
        double monto = leerMonto(scanner);

        // Elegir categoría
        System.out.println("Categorías:");
        System.out.println("1. Comida");
        System.out.println("2. Transporte");
        System.out.println("3. Entretenimiento");
        System.out.println("4. Salud");
        System.out.println("5. Otros");
        System.out.print("¿En qué categoría va? ");

        int cat = leerCategoriaValida(scanner);
        String categoria = "";

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

        // Guardar el gasto
        descripciones[totalGastos] = descripcion;
        montos[totalGastos] = monto;
        categorias[totalGastos] = categoria;
        totalGastos++;

        presupuestoActual = presupuestoActual - monto;

        System.out.println("Listo! Gasto registrado");
        System.out.println("Gastaste: $" + monto);
        System.out.println("Te quedan: $" + presupuestoActual);

        // Avisar si ya se pasó
        if (presupuestoActual < 0) {
            System.out.println("CUIDADO! Ya te pasaste de tu presupuesto!");
        } else if (presupuestoActual < PRESUPUESTO_INICIAL * 0.2) {
            System.out.println("OJO! Ya solo te queda menos del 20% del presupuesto");
        }
    }

    private static double leerMonto(Scanner scanner) {
        double monto = -1;

        while (monto <= 0) {
            if (scanner.hasNextDouble()) {
                monto = scanner.nextDouble();
                if (monto <= 0) {
                    System.out.print("Debe ser mayor que cero. Intenta de nuevo: $");
                }
            } else {
                System.out.print("Eso no es un número válido. Intenta de nuevo: $");
                scanner.next();
            }
        }

        return monto;
    }

    private static int leerCategoriaValida(Scanner scanner) {
        int opcion = 0;

        while (opcion < 1 || opcion > 5) {
            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();
                if (opcion < 1 || opcion > 5) {
                    System.out.print("Debe ser entre 1 y 5: ");
                }
            } else {
                System.out.print("Eso no es un número: ");
                scanner.next();
            }
        }

        return opcion;
    }

    private static void verGastos() {
        System.out.println("--- MIS GASTOS ---");

        if (totalGastos == 0) {
            System.out.println("Todavía no has registrado ningún gasto");
            return;
        }

        for (int i = 0; i < totalGastos; i++) {
            System.out.println((i + 1) + ". " + descripciones[i] +
                    " - $" + montos[i] +
                    " (" + categorias[i] + ")");
        }
    }

    private static void verPorCategoria(Scanner scanner) {
        if (totalGastos == 0) {
            System.out.println("Todavía no has registrado gastos");
            return;
        }

        System.out.println("--- VER POR CATEGORIA ---");
        System.out.println("1. Comida");
        System.out.println("2. Transporte");
        System.out.println("3. Entretenimiento");
        System.out.println("4. Salud");
        System.out.println("5. Otros");
        System.out.print("¿Qué categoría quieres ver? ");

        int cat = leerCategoriaValida(scanner);
        String categoriaElegida = "";

        if (cat == 1) categoriaElegida = "Comida";
        else if (cat == 2) categoriaElegida = "Transporte";
        else if (cat == 3) categoriaElegida = "Entretenimiento";
        else if (cat == 4) categoriaElegida = "Salud";
        else categoriaElegida = "Otros";

        System.out.println("Gastos en: " + categoriaElegida);

        double totalCat = 0;
        int contador = 0;

        for (int i = 0; i < totalGastos; i++) {
            if (categorias[i].equals(categoriaElegida)) {
                contador++;
                System.out.println(contador + ". " + descripciones[i] + " - $" + montos[i]);
                totalCat = totalCat + montos[i];
            }
        }

        if (contador == 0) {
            System.out.println("No tienes gastos en esta categoría");
        } else {
            System.out.println("\nTotal en " + categoriaElegida + ": $" + totalCat);
            String texto = (contador == 1) ? " gasto" : " gastos";
            System.out.println("Tienes " + contador + texto + " en esta categoría");
        }
    }

    private static void verResumen() {
        System.out.println("===========================================");
        System.out.println("            RESUMEN FINANCIERO");
        System.out.println("===========================================");

        if (totalGastos == 0) {
            System.out.println("No hay gastos para mostrar");
            return;
        }

        // Calcular todo
        double totalGastado = 0;
        double mayor = montos[0];
        double menor = montos[0];

        for (int i = 0; i < totalGastos; i++) {
            totalGastado = totalGastado + montos[i];

            if (montos[i] > mayor) {
                mayor = montos[i];
            }

            if (montos[i] < menor) {
                menor = montos[i];
            }
        }

        double promedio = totalGastado / totalGastos;
        double porcentaje = (totalGastado / PRESUPUESTO_INICIAL) * 100;

        System.out.println("Total gastado: $" + totalGastado);
        System.out.println("Presupuesto inicial: $" + PRESUPUESTO_INICIAL);
        System.out.println("Te queda: $" + presupuestoActual);
        System.out.println("Porcentaje gastado: " + porcentaje + "%");
        System.out.println("Promedio por gasto: $" + promedio);
        System.out.println("Tu gasto más alto: $" + mayor);
        System.out.println("Tu gasto más bajo: $" + menor);
        System.out.println("Número de gastos: " + totalGastos);

        // Mostrar por categoria
        System.out.println("Por categoría:");
        mostrarTotalesPorCategoria();
    }

    private static void mostrarTotalesPorCategoria() {
        String[] cats = {"Comida", "Transporte", "Entretenimiento", "Salud", "Otros"};

        for (int c = 0; c < cats.length; c++) {
            double totalCat = 0;
            int cantidad = 0;

            for (int i = 0; i < totalGastos; i++) {
                if (categorias[i].equals(cats[c])) {
                    totalCat = totalCat + montos[i];
                    cantidad++;
                }
            }

            if (cantidad > 0) {
                String texto = (cantidad == 1) ? " gasto" : " gastos";
                System.out.println("  " + cats[c] + ": $" + totalCat + " (" + cantidad + texto + ")");
            }
        }
    }

    private static void cambiarPresupuesto(Scanner scanner) {
        System.out.println("--- AGREGAR PRESUPUESTO ---");
        System.out.println("Presupuesto actual: $" + presupuestoActual);
        System.out.print("¿Cuál será tu nuevo presupuesto? $");

        double nuevo = leerMonto(scanner);
        double diferencia = nuevo - presupuestoActual;

        presupuestoActual = nuevo;

        System.out.println("Listo! Tu nuevo presupuesto es: $" + presupuestoActual);

        if (diferencia > 0) {
            System.out.println("Aumentaste tu presupuesto en $" + diferencia);
        } else if (diferencia < 0) {
            System.out.println("Redujiste tu presupuesto en $" + (diferencia * -1));
        } else {
            System.out.println("El presupuesto quedó igual");
        }
    }
}