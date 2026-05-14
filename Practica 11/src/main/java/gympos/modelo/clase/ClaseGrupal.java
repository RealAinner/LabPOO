package gympos.modelo.clase;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ClaseGrupal implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String nombre;
    private String instructor;
    private LocalDateTime FechaHora;
    private int capacidad;
    private List<Integer> inscritos;

    public ClaseGrupal(int id, String nombre, String instructor, LocalDateTime FechaHora, int capacidad){
        this.id = id;
        this.nombre = nombre;
        this.instructor = instructor;
        this.FechaHora = FechaHora;
        this.capacidad = capacidad;
        this.inscritos = new ArrayList<>();
    }

    public int GetId() { return id; }
    public String GetNombre() { return nombre; }
    public void SetNombre(String nombre) { this.nombre = nombre; }
    public String GetInstructor() { return instructor; }
    public void SetInstructor(String instructor) { this.instructor = instructor; }
    public LocalDateTime GetFechaHora() { return FechaHora; }
    public void SetFechaHora(LocalDateTime FechaHora) { this.FechaHora = FechaHora; }
    public int GetCapacidad() { return capacidad; }
    public List<Integer> GetInscritos() { return inscritos; }

    public boolean HayLugar() { return inscritos.size() < capacidad; }

    public boolean Inscribir(int IdCliente){
        if(HayLugar() && !inscritos.contains(IdCliente)){
            inscritos.add(IdCliente);
            return true;
        }
        return false;
    }
}
