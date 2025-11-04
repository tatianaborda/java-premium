package src.main.java.com.codigofacilito.gastos;
import java.io.Serializable;

public class Gasto extends Movimiento implements Serializable {
    private static final long serialVersionUID = 1L;
    private String categoria;

    public Gasto(String descripcion, double monto, String categoria) {
        super(descripcion, monto);
        this.categoria = categoria;
    }

    @Override
    public void mostrar() {
        System.out.println("  - " + descripcion + ": $" + monto +
                " (" + categoria + ") [" + getFechaFormateada() + "]");
    }

    public String getCategoria() {
        return categoria;
    }
}