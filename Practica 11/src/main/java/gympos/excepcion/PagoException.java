package gympos.excepcion;

public class PagoException extends GymPOSException {
    public PagoException(String mensaje) { super("Error de pago: " + mensaje); }
}
