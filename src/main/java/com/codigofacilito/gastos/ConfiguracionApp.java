package src.main.java.com.codigofacilito.gastos;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;


public class ConfiguracionApp {
    private Properties config;
    private static final String ARCHIVO_CONFIG = "config.properties";

    public ConfiguracionApp() {
        config = new Properties();
        cargarConfiguracion();
    }

    private void cargarConfiguracion() {
        try (InputStream input = new FileInputStream(ARCHIVO_CONFIG);
             Reader reader = new InputStreamReader(input, StandardCharsets.UTF_8)) {

            config.load(reader);
            System.out.println("✅ Configuración cargada");
        } catch (IOException e) {
            System.out.println("⚠️ Usando configuración por defecto");
            cargarDefaults();
        }
    }

    private void cargarDefaults() {
        config.setProperty("app.nombre", "Calculadora de Gastos");
        config.setProperty("moneda.simbolo", "$");
        config.setProperty("archivo.datos", "gastos.dat");
        config.setProperty("categorias", "Comida,Transporte,Entretenimiento,Salud,Otros");
        config.setProperty("alerta.presupuesto.bajo", "500.0");
        config.setProperty("alerta.gasto.grande", "1000.0");
    }

    public String getNombreApp() {
        return config.getProperty("app.nombre");
    }

    public String getSimboloMoneda() {
        return config.getProperty("moneda.simbolo");
    }

    public String getArchivoDatos() {
        return config.getProperty("archivo.datos");
    }

    public String[] getCategorias() {
        String cats = config.getProperty("categorias");
        return cats.split(",");
    }

    public double getAlertaPresupuestoBajo() {
        return Double.parseDouble(config.getProperty("alerta.presupuesto.bajo"));
    }

    public double getAlertaGastoGrande() {
        return Double.parseDouble(config.getProperty("alerta.gasto.grande"));
    }
}