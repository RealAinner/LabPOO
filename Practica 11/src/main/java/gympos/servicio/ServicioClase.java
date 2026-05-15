package gympos.servicio;

import gympos.excepcion.GymPOSException;
import gympos.modelo.clase.ClaseGrupal;
import gympos.util.DatosIniciales;
import gympos.util.Serializador;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ServicioClase {
    private static final String ARCHIVO = "clases.ser";
    private List<ClaseGrupal> clases;
    private int SiguienteId;

    public ServicioClase(){
        clases = Serializador.Cargar(ARCHIVO);
        if(clases.isEmpty()){
            clases = DatosIniciales.GenerarClases();
            Serializador.Guardar(clases, ARCHIVO);
        }
        SiguienteId = clases.stream().mapToInt(ClaseGrupal::GetId).max().orElse(0) + 1;
    }

    public List<ClaseGrupal> GetTodas() {return new ArrayList<>(clases);}

    public void Agregar(String nombre, String instructor, LocalDateTime FechaHora, int capacidad) throws GymPOSException {
        if(nombre == null || nombre.isBlank()) throw new GymPOSException("Nombre invalido.");
        if(capacidad <= 0) throw new GymPOSException("Capacidad debe ser mayor a cero.");
        clases.add(new ClaseGrupal(SiguienteId++, nombre.trim(), instructor.trim(), FechaHora, capacidad));
        Serializador.Guardar(clases, ARCHIVO);
    }

    public void Inscribir(int IdClase, int IdCliente) throws GymPOSException {
        ClaseGrupal c = clases.stream().filter(cl -> cl.GetId() == IdClase).findFirst().orElseThrow(() -> new GymPOSException("Clase no encontrada."));
        if(!c.Inscribir(IdCliente)) throw new GymPOSException("Sin lugares o ya inscrito.");
        Serializador.Guardar(clases, ARCHIVO);
    }

    public void Eliminar(int id) throws GymPOSException {
        ClaseGrupal c = clases.stream().filter(cl -> cl.GetId() == id).findFirst().orElseThrow(() -> new GymPOSException("Clase no encontrada."));
        clases.remove(c);
        Serializador.Guardar(clases, ARCHIVO);
    }
}
