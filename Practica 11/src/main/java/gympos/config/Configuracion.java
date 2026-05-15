package gympos.config;

import java.io.*;
import java.util.Properties;

public class Configuracion {
    private static Configuracion instancia;
    private static final String ARCHIVO = "gympos.properties";
    private Properties props;

    private Configuracion() {
        props = new Properties();
        props.setProperty("gym.nombre", "IronPeak Gym");
        props.setProperty("gym.version", "1.0");
        props.setProperty("datos.ruta", "datos/");
        props.setProperty("puntos.porPago", "10");
        props.setProperty("notificacion.diasAntes", "5");
        Cargar();
    }

    public static Configuracion GetInstancia() {
        if (instancia == null) instancia = new Configuracion();
        return instancia;
    }

    private void Cargar() {
        try (InputStream in = new FileInputStream(ARCHIVO)) {
            props.load(in);
        } catch (IOException e) {
            // usa valores por defecto
        }
    }

    public void Guardar() {
        try (OutputStream out = new FileOutputStream(ARCHIVO)) {
            props.store(out, "GymPOS Config");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String Get(String clave) { return props.getProperty(clave, ""); }
    public void Set(String clave, String valor) { props.setProperty(clave, valor); }
    public int GetInt(String clave) {
        try { return Integer.parseInt(Get(clave)); }
        catch (NumberFormatException e) { return 0; }
    }
}
