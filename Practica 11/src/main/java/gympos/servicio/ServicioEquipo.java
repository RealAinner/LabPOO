package gympos.servicio;

import gympos.excepcion.GymPOSException;
import gympos.modelo.equipo.Equipo;
import gympos.modelo.equipo.Equipo.EstadoEquipo;
import gympos.util.DatosIniciales;
import gympos.util.Serializador;

import java.util.ArrayList;
import java.util.List;

public class ServicioEquipo {
    private static final String ARCHIVO = "equipos.ser";
    private List<Equipo> equipos;
    private int SiguienteId;

    public ServicioEquipo(){
        equipos = Serializador.Cargar(ARCHIVO);
        if(equipos.isEmpty()){
            equipos = DatosIniciales.GenerarEquipos();
            Serializador.Guardar(equipos, ARCHIVO);
        }
        SiguienteId = equipos.stream().mapToInt(Equipo::GetId).max().orElse(0) + 1;
    }

    public List<Equipo> GetTodos() {return new ArrayList<>(equipos);}

    public void Agregar(String nombre, String categoria, int cantidad) throws GymPOSException {
        if(nombre == null || nombre.isBlank()) throw new GymPOSException("Nombre invalido.");
        if(cantidad <= 0) throw new GymPOSException("Cantidad debe ser mayor a cero.");
        equipos.add(new Equipo(SiguienteId++, nombre.trim(), categoria.trim(), cantidad));
        Serializador.Guardar(equipos, ARCHIVO);
    }

    public void ActualizarEstado(int id, EstadoEquipo estado) throws GymPOSException {
        Equipo e = equipos.stream().filter(eq -> eq.GetId() == id).findFirst().orElseThrow(() -> new GymPOSException("Equipo no encontrado."));
        e.SetEstado(estado);
        Serializador.Guardar(equipos, ARCHIVO);
    }

    public void Eliminar(int id) throws GymPOSException {
        Equipo e = equipos.stream().filter(eq -> eq.GetId() == id).findFirst().orElseThrow(() -> new GymPOSException("Equipo no encontrado."));
        equipos.remove(e);
        Serializador.Guardar(equipos, ARCHIVO);
    }
}
