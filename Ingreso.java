public class Ingreso extends Movimiento {

    public Ingreso(String descripcion, double monto) {
        super(descripcion, monto);
    }

    @Override
    public void mostrar() {
        System.out.println("  + " + descripcion + ": $" + monto);
    }
}