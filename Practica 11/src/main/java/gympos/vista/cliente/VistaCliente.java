package gympos.vista.cliente;

import gympos.modelo.cliente.Cliente;
import java.util.List;

public interface VistaCliente{
    void Refrescar(List<Cliente> clientes);
    void MostrarError(String mensaje);
    void MostrarExito(String mensaje);
}
