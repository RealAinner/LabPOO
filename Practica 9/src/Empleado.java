import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Empleado implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String nombre;
    private String puesto;
    private double salario;
    private String FechaIngreso;

    public Empleado(int id, String nombre, String puesto, double salario, String FechaIngreso){
        SetId(id);
        SetNombre(nombre);
        SetPuesto(puesto);
        SetSalario(salario);
        SetFechaIngreso(FechaIngreso);
    }

    public static boolean ValidarId(int id){
        return id > 0;
    }

    public static boolean ValidarNombre(String nombre){
        return nombre != null && nombre.matches("[a-zA-Z ]{2,50}");
    }

    public static boolean ValidarPuesto(String puesto){
        return puesto != null && !puesto.trim().isEmpty() && puesto.length() <= 40;
    }

    public static boolean ValidarSalario(double salario){
        return salario >= 0;
    }

    public static boolean ValidarFecha(String fecha){
        try{
            LocalDate.parse(fecha);
            return true;
        }catch(DateTimeParseException e){
            return false;
        }
    }

    public void SetId(int id){
        if(!ValidarId(id)) throw new IllegalArgumentException("ID invalido: debe ser mayor a 0");
        this.id = id;
    }

    public void SetNombre(String nombre){
        if(!ValidarNombre(nombre)) throw new IllegalArgumentException("Nombre invalido");
        this.nombre = nombre;
    }

    public void SetPuesto(String puesto){
        if(!ValidarPuesto(puesto)) throw new IllegalArgumentException("Puesto invalido");
        this.puesto = puesto;
    }

    public void SetSalario(double salario){
        if(!ValidarSalario(salario)) throw new IllegalArgumentException("Salario invalido");
        this.salario = salario;
    }

    public void SetFechaIngreso(String FechaIngreso){
        if(!ValidarFecha(FechaIngreso)) throw new IllegalArgumentException("Fecha invalida (formato: YYYY-MM-DD)");
        this.FechaIngreso = FechaIngreso;
    }

    public int GetId() { return id; }
    public String GetNombre() { return nombre; }
    public String GetPuesto() { return puesto; }
    public double GetSalario() { return salario; }
    public String GetFechaIngreso() { return FechaIngreso; }

    public static void Guardar(Empleado empleado, String ruta) throws IOException {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ruta))){
            oos.writeObject(empleado);
        }
    }

    public static Empleado Cargar(String ruta) throws IOException, ClassNotFoundException {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ruta))){
            return (Empleado) ois.readObject();
        }
    }

    @Override
    public String toString(){
        return id + "," + nombre + "," + puesto + "," + salario + "," + FechaIngreso;
    }
}
