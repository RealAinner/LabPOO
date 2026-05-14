package gympos.servicio;

import gympos.excepcion.ClienteNoEncontradoException;
import gympos.excepcion.ValidacionException;
import gympos.modelo.cliente.Cliente;
import gympos.util.DatosIniciales;
import gympos.util.Serializador;
import gympos.util.Validador;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ServicioCliente {
    private static final String ARCHIVO = "clientes.ser";
    private List<Cliente> clientes;
    private int SiguienteId;

    public ServicioCliente() {
        clientes = Serializador.Cargar(ARCHIVO);
        if (clientes.isEmpty()) {
            clientes = DatosIniciales.GenerarClientes();
            Serializador.Guardar(clientes, ARCHIVO);
        }
        SiguienteId = clientes.stream().mapToInt(Cliente::GetId).max().orElse(0) + 1;
    }

    public List<Cliente> GetTodos() { return new ArrayList<>(clientes); }

    public Cliente GetPorId(int id) throws ClienteNoEncontradoException {
        return clientes.stream().filter(c -> c.GetId() == id)
                .findFirst().orElseThrow(() -> new ClienteNoEncontradoException(id));
    }

    public void Agregar(String nombre, String apellido, String correo, String telefono)
            throws ValidacionException {
        Validar(nombre, apellido, correo, telefono);
        clientes.add(new Cliente(SiguienteId++, nombre.trim(), apellido.trim(), correo.trim(), telefono.trim()));
        Serializador.Guardar(clientes, ARCHIVO);
    }

    public void Actualizar(int id, String nombre, String apellido, String correo, String telefono)
            throws ClienteNoEncontradoException, ValidacionException {
        Validar(nombre, apellido, correo, telefono);
        Cliente c = GetPorId(id);
        c.SetNombre(nombre.trim());
        c.SetApellido(apellido.trim());
        c.SetCorreo(correo.trim());
        c.SetTelefono(telefono.trim());
        Serializador.Guardar(clientes, ARCHIVO);
    }

    public void Eliminar(int id) throws ClienteNoEncontradoException {
        Cliente c = GetPorId(id);
        clientes.remove(c);
        Serializador.Guardar(clientes, ARCHIVO);
    }

    private void Validar(String nombre, String apellido, String correo, String telefono)
            throws ValidacionException {
        if (!Validador.EsTextoValido(nombre, 2, 50))
            throw new ValidacionException("nombre", "Debe tener entre 2 y 50 caracteres.");
        if (!Validador.EsTextoValido(apellido, 2, 50))
            throw new ValidacionException("apellido", "Debe tener entre 2 y 50 caracteres.");
        if (!Validador.EsCorreoValido(correo))
            throw new ValidacionException("correo", "Formato de correo invalido.");
        if (!Validador.EsTelefonoValido(telefono))
            throw new ValidacionException("telefono", "Debe tener exactamente 10 digitos.");
    }
}
