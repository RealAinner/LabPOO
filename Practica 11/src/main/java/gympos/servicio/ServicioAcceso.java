package gympos.servicio;

import gympos.excepcion.GymPOSException;
import gympos.modelo.acceso.RegistroAcceso;
import gympos.util.DatosIniciales;
import gympos.util.Serializador;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ServicioAcceso {
    private static final String ARCHIVO = "accesos.ser";
    private List<RegistroAcceso> registros;
    private int SiguienteId;

    public ServicioAcceso() {
        registros = Serializador.Cargar(ARCHIVO);
        if (registros.isEmpty()) {
            registros = DatosIniciales.GenerarAccesos();
            Serializador.Guardar(registros, ARCHIVO);
        }
        SiguienteId = registros.stream().mapToInt(RegistroAcceso::GetId).max().orElse(0) + 1;
    }

    public List<RegistroAcceso> GetTodos() { return new ArrayList<>(registros); }

    public List<RegistroAcceso> GetAdentro() {
        return registros.stream().filter(RegistroAcceso::EstaAdentro).collect(Collectors.toList());
    }

    public void RegistrarEntrada(int IdCliente) throws GymPOSException {
        boolean yaAdentro = registros.stream()
                .anyMatch(r -> r.GetIdCliente() == IdCliente && r.EstaAdentro());
        if (yaAdentro) throw new GymPOSException("El cliente ya se encuentra en el gimnasio.");
        registros.add(new RegistroAcceso(SiguienteId++, IdCliente));
        Serializador.Guardar(registros, ARCHIVO);
    }

    public void RegistrarSalida(int IdCliente) throws GymPOSException {
        RegistroAcceso r = registros.stream()
                .filter(a -> a.GetIdCliente() == IdCliente && a.EstaAdentro())
                .findFirst()
                .orElseThrow(() -> new GymPOSException("El cliente no tiene entrada activa."));
        r.SetSalida(LocalDateTime.now());
        Serializador.Guardar(registros, ARCHIVO);
    }

    public int GetAforo() {
        return (int) registros.stream().filter(RegistroAcceso::EstaAdentro).count();
    }
}
