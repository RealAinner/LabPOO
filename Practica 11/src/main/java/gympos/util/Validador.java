package gympos.util;

public class Validador {

    public static boolean EsCorreoValido(String correo) {
        return correo != null && correo.matches("^[\\w.-]+@[\\w.-]+\\.[a-z]{2,}$");
    }

    public static boolean EsTelefonoValido(String telefono) {
        return telefono != null && telefono.matches("^[0-9]{10}$");
    }

    public static boolean EsTextoValido(String texto, int minLen, int maxLen) {
        return texto != null && texto.trim().length() >= minLen && texto.trim().length() <= maxLen;
    }

    public static boolean EsNumeroPositivo(double numero) {
        return numero > 0;
    }
}
