package escuela;
import java.util.*;
import java.util.stream.Collectors;

/*
 * Estructuras utilizadas:
 *   ArrayList -> lista principal de alumnos (acceso por indice, compatible con Streams)
 *   LinkedList -> historial de operaciones (insercion O(1) en los extremos)
 *   HashMap -> alumnos indexados por matricula (busqueda O(1))
 *   HashSet -> carreras registradas (consulta O(1))
 */

public class GestorEscolar {

    private final ArrayList<Alumno> ListaAlumnos = new ArrayList<>();
    private final LinkedList<String> Historial = new LinkedList<>();
    private final HashMap<String,Alumno> IndiceMatricula = new HashMap<>();
    private final HashSet<String> Carreras = new HashSet<>();

    // CRUD
    public boolean AgregarAlumno(Alumno alumno){
        if (IndiceMatricula.containsKey(alumno.GetMatricula())) return false;
        ListaAlumnos.add(alumno);
        IndiceMatricula.put(alumno.GetMatricula(), alumno);
        Carreras.add(alumno.GetCarrera());
        Historial.addLast("ALTA: " + alumno.GetMatricula());
        return true;
    }

    //O(1) gracias al HashMap
    public Optional<Alumno> BuscarPorMatricula(String matricula){
        Historial.addLast("BUSQUEDA: " + matricula);
        return Optional.ofNullable(IndiceMatricula.get(matricula));
    }

    public boolean ActualizarPromedio(String matricula, double nuevoPromedio){
        Alumno alumno = IndiceMatricula.get(matricula);
        if (alumno == null) return false;
        alumno.SetPromedio(nuevoPromedio);
        Historial.addLast("UPDATE promedio: " + matricula + " -> " + nuevoPromedio);
        return true;
    }

    public boolean EliminarAlumno(String matricula){
        Alumno alumno = IndiceMatricula.remove(matricula);
        if (alumno == null) return false;
        ListaAlumnos.remove(alumno);
        Historial.addLast("BAJA: " + matricula);
        return true;
    }

    //Busqueda y filtrado con Streams
    public List<Alumno> FiltrarPorCarrera(String carrera){
        return ListaAlumnos.stream()
                .filter(a -> a.GetCarrera().equalsIgnoreCase(carrera))
                .collect(Collectors.toList());
    }

    //Busqueda compuesta
    public List<Alumno> BusquedaCompuesta(String carrera, int semestre, double promedioMin){
        return ListaAlumnos.stream()
                .filter(a -> a.GetCarrera().equalsIgnoreCase(carrera))
                .filter(a -> a.GetSemestre() == semestre)
                .filter(a -> a.GetPromedio() >= promedioMin)
                .sorted(ComparadorAlumno.PorPromedioDesc)
                .collect(Collectors.toList());
    }

    // Iterador explicito
    public void MostrarConIterador(){
        Iterator<Alumno> iter = ListaAlumnos.iterator();
        int i = 1;
        while(iter.hasNext()){
            System.out.println("  " + i++ + ". " + iter.next());
        }
    }

    // Ordenamiento
    public List<Alumno> ObtenerOrdenados(Comparator<Alumno> criterio){
        List<Alumno> copia = new ArrayList<>(ListaAlumnos);
        copia.sort(criterio);
        return copia;
    }

    //Accesores de apoyo
    public List<Alumno> GetListaAlumnos() {return Collections.unmodifiableList(ListaAlumnos);}
    public Set<String> GetCarreras() {return Collections.unmodifiableSet(Carreras);}
    public List<String> GetHistorial() {return Collections.unmodifiableList(Historial);}
    public int GetTotal() {return ListaAlumnos.size();}
}
