import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class Movimiento {
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