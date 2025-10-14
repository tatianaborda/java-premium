public class Gasto extends Movimiento {
    private String categoria;

    public Gasto(String descripcion, double monto, String categoria) {
        super(descripcion, monto);
        this.categoria = categoria;
    }

    @Override
    public void mostrar() {
        System.out.println("  - " + descripcion + ": $" + monto + " (" + categoria + ")");
    }

    public String getCategoria() {
        return categoria;
    }
}