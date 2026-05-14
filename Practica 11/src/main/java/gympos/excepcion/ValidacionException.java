package gympos.excepcion;

public class ValidacionException extends GymPOSException {
    public ValidacionException(String campo, String mensaje){
        super("Validacion en [" + campo + "]: " + mensaje);
    }
}
