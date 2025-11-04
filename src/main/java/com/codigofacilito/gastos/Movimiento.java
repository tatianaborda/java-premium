package src.main.java.com.codigofacilito.gastos;
import java.io.Serializable;  // ← AGREGAR
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// CAMBIO: implementamos Serializable
public abstract class Movimiento implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String descripcion;
    protected double monto;
    protected LocalDateTime fecha;

    public Movimiento(String descripcion, double monto) {
        this.descripcion = descripcion;
        this.monto = monto;
        this.fecha = LocalDateTime.now();
    }

    public abstract void mostrar();

    public double getMonto() {
        return monto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public String getFechaFormateada() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return fecha.format(formato);
    }
}