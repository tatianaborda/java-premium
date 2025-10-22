public class PresupuestoInsuficienteException extends Exception {
    private double faltante;

    public PresupuestoInsuficienteException(double faltante) {
        super("No hay suficiente presupuesto. Te faltan $" + faltante);
        this.faltante = faltante;
    }

    public double getFaltante() {
        return faltante;
    }
}