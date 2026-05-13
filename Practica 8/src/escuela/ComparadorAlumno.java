package escuela;
import java.util.Comparator;

public class ComparadorAlumno {

    //Criterio 1: por promedio descendente
    public static final Comparator<Alumno> PorPromedioDesc = (a, b) -> Double.compare(b.GetPromedio(), a.GetPromedio());

    //Criterio 2: por nombre ascendente
    public static final Comparator<Alumno> PorNombre = Comparator.comparing(Alumno::GetNombre);
}
