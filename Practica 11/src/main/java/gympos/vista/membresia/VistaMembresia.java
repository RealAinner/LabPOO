package gympos.vista.membresia;

import gympos.modelo.membresia.Membresia;
import java.util.List;

public interface VistaMembresia {
    void Refrescar(List<Membresia> membresias);
    void MostrarError(String mensaje);
    void MostrarExito(String mensaje);
}
