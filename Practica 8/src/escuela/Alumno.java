package escuela;
import java.util.Objects;

//Orden natural por matricula
public class Alumno implements Comparable<Alumno> {

    private String matricula;
    private String nombre;
    private String carrera;
    private int    semestre;
    private double promedio;

    public Alumno(String matricula, String nombre, String carrera, int semestre, double promedio) {
        this.matricula = matricula;
        this.nombre = nombre;
        this.carrera = carrera;
        this.semestre = semestre;
        this.promedio = promedio;
    }

    @Override
    public int compareTo(Alumno otro){
        return this.matricula.compareTo(otro.matricula);
    }

    @Override
    public boolean equals(Object o){
        if (!(o instanceof Alumno)) return false;
        return Objects.equals(matricula, ((Alumno) o).matricula);
    }

    @Override
    public int hashCode() { return Objects.hash(matricula); }

    @Override
    public String toString(){
        return String.format("| [%s] %-20s | %-18s | Sem %d | Prom %.1f |", matricula, nombre, carrera, semestre, promedio);
    }

    //Getters y setters
    public String GetMatricula() {return matricula;}
    public String GetNombre() {return nombre;}
    public String GetCarrera() {return carrera;}
    public int GetSemestre() {return semestre;}
    public double GetPromedio() {return promedio;}
    public void SetPromedio(double p) {this.promedio = p;}
    public void SetCarrera(String c) {this.carrera  = c;}
}
