package gympos.controlador;

import gympos.excepcion.ClienteNoEncontradoException;
import gympos.excepcion.ValidacionException;
import gympos.modelo.cliente.Cliente;
import gympos.servicio.ServicioCliente;
import gympos.vista.cliente.VistaCliente;

import java.util.List;

public class ClienteController {
    private final ServicioCliente servicio;
    private VistaCliente vista;

    public ClienteController(ServicioCliente servicio) {
        this.servicio = servicio;
    }

    public void SetVista(VistaCliente vista) { this.vista = vista; }

    public List<Cliente> CargarTodos() { return servicio.GetTodos(); }

    public void Agregar(String nombre, String apellido, String correo, String telefono) {
        try {
            servicio.Agregar(nombre, apellido, correo, telefono);
            vista.MostrarExito("Cliente registrado correctamente.");
            vista.Refrescar(servicio.GetTodos());
        } catch (ValidacionException e) {
            vista.MostrarError(e.getMessage());
        }
    }

    public void Actualizar(int id, String nombre, String apellido, String correo, String telefono) {
        try {
            servicio.Actualizar(id, nombre, apellido, correo, telefono);
            vista.MostrarExito("Cliente actualizado correctamente.");
            vista.Refrescar(servicio.GetTodos());
        } catch (ClienteNoEncontradoException | ValidacionException e) {
            vista.MostrarError(e.getMessage());
        }
    }

    public void Eliminar(int id) {
        try {
            servicio.Eliminar(id);
            vista.MostrarExito("Cliente eliminado.");
            vista.Refrescar(servicio.GetTodos());
        } catch (ClienteNoEncontradoException e) {
            vista.MostrarError(e.getMessage());
        }
    }
}
