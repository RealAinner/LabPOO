import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Log {
    private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");

    public static synchronized void Imprimir(String mensaje) {
        System.out.println("[" + LocalTime.now().format(fmt) + "] " + mensaje);
    }
}
