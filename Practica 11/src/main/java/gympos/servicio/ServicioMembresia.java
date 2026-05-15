package gympos.servicio;

import gympos.excepcion.ClienteNoEncontradoException;
import gympos.excepcion.GymPOSException;
import gympos.modelo.membresia.Membresia;
import gympos.modelo.membresia.TipoMembresia;
import gympos.util.DatosIniciales;
import gympos.util.Serializador;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ServicioMembresia {
    private static final String ARCHIVO = "membresias.ser";
    private List<Membresia> membresias;
    private int SiguienteId;

    public ServicioMembresia() {
        membresias = Serializador.Cargar(ARCHIVO);
        if (membresias.isEmpty()) {
            membresias = DatosIniciales.GenerarMembresias();
            Serializador.Guardar(membresias, ARCHIVO);
        }
        SiguienteId = membresias.stream().mapToInt(Membresia::GetId).max().orElse(0) + 1;
    }

    public List<Membresia> GetTodas() { return new ArrayList<>(membresias); }

    public Membresia GetPorCliente(int IdCliente) throws ClienteNoEncontradoException {
        return membresias.stream()
                .filter(m -> m.GetIdCliente() == IdCliente && m.IsActiva())
                .findFirst()
                .orElseThrow(() -> new ClienteNoEncontradoException(IdCliente));
    }

    public void Registrar(int IdCliente, TipoMembresia tipo) throws GymPOSException {
        boolean tieneActiva = membresias.stream()
                .anyMatch(m -> m.GetIdCliente() == IdCliente && m.IsActiva());
        if (tieneActiva) throw new GymPOSException("El cliente ya tiene una membresia activa.");
        membresias.add(new Membresia(SiguienteId++, IdCliente, tipo));
        Serializador.Guardar(membresias, ARCHIVO);
    }

    public void Renovar(int IdCliente) throws ClienteNoEncontradoException {
        Membresia m = GetPorCliente(IdCliente);
        m.Renovar();
        Serializador.Guardar(membresias, ARCHIVO);
    }

    public void Cancelar(int IdCliente) throws ClienteNoEncontradoException {
        Membresia m = GetPorCliente(IdCliente);
        m.SetActiva(false);
        Serializador.Guardar(membresias, ARCHIVO);
    }

    public List<Membresia> GetProximasAVencer(int dias) {
        return membresias.stream()
                .filter(m -> m.IsActiva() && m.DiasRestantes() <= dias && m.DiasRestantes() >= 0)
                .collect(Collectors.toList());
    }

    public void RenovarAutomaticas() {
        membresias.stream()
                .filter(m -> m.IsRenovacionAutomatica() && m.EstaVencida())
                .forEach(Membresia::Renovar);
        Serializador.Guardar(membresias, ARCHIVO);
    }
}
