package src.main.java.com.codigofacilito.gastos;

import java.io.Serializable;

public class Ingreso extends Movimiento implements Serializable {
    private static final long serialVersionUID = 1L;
    public Ingreso(String descripcion, double monto) {
        super(descripcion, monto);
    }

    @Override
    public void mostrar() {
        System.out.println("  + " + descripcion + ": $" + monto +
                " [" + getFechaFormateada() + "]");
    }
}