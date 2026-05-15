package gympos.excepcion;

public class ClienteNoEncontradoException extends GymPOSException {
    public ClienteNoEncontradoException(int id) {
        super("Cliente con ID " + id + " no encontrado.");
    }
}
