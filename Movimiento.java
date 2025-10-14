public abstract class Movimiento {
    protected String descripcion;
    protected double monto;

    public Movimiento(String descripcion, double monto) {
        this.descripcion = descripcion;
        this.monto = monto;
    }

    public abstract void mostrar();

    public double getMonto() {
        return monto;
    }

    public String getDescripcion() {
        return descripcion;
    }
}