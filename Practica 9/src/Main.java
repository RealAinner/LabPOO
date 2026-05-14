import java.util.*;
import java.io.*;

public class Main {
    private static List<Empleado> empleados = new ArrayList<>();
    private static final String ArchivoCSV = "empleados.csv";
    private static final String DirDatos = "datos";

    public static void main(String[] args) throws IOException {
        GestorArchivos.CrearDirectorio(DirDatos);
        CargarDatos();

        Scanner scanner = new Scanner(System.in);
        int opcion = -1;

        while(opcion != 0){
            MostrarMenu();
            try{
                opcion = Integer.parseInt(scanner.nextLine().trim());
            }catch(NumberFormatException e){
                opcion = -1;
            }

            switch(opcion){
                case 1 -> ListarEmpleados();
                case 2 -> AgregarEmpleado(scanner);
                case 3 -> GuardarDatos();
                case 4 -> HacerBackup();
                case 5 -> ExportarXML();
                case 6 -> CargarDesdeCSV(scanner);
                case 7 -> GuardarEmpleadoBinario(scanner);
                case 8 -> CargarEmpleadoBinario(scanner);
                case 9 -> ListarDirectorio();
                case 0 -> System.out.println("Saliendo...");
                default -> System.out.println("Opcion no valida.");
            }
        }
        scanner.close();
    }

    static void MostrarMenu(){
        System.out.println("\n----Sistema de Empleados----");
        System.out.println("1. Listar empleados");
        System.out.println("2. Agregar empleado");
        System.out.println("3. Guardar CSV");
        System.out.println("4. Hacer backup");
        System.out.println("5. Exportar XML");
        System.out.println("6. Cargar desde CSV externo");
        System.out.println("7. Guardar empleado binario");
        System.out.println("8. Cargar empleado binario");
        System.out.println("9. Listar directorio datos");
        System.out.println("0. Salir");
        System.out.print("Opcion: ");
    }

    static void ListarEmpleados(){
        if(empleados.isEmpty()){ System.out.println("Sin empleados registrados."); return; }
        System.out.printf("%-5s %-20s %-20s %-10s %-12s%n", "ID", "Nombre", "Puesto", "Salario", "Ingreso");
        System.out.println("-".repeat(70));
        for(Empleado e : empleados){
            System.out.printf("%-5d %-20s %-20s %-10.2f %-12s%n",
                e.GetId(), e.GetNombre(), e.GetPuesto(), e.GetSalario(), e.GetFechaIngreso());
        }
    }

    static void AgregarEmpleado(Scanner scanner){
        try{
            System.out.print("ID: ");
            int id = Integer.parseInt(scanner.nextLine().trim());
            System.out.print("Nombre: ");
            String nombre = scanner.nextLine().trim();
            System.out.print("Puesto: ");
            String puesto = scanner.nextLine().trim();
            System.out.print("Salario: ");
            double salario = Double.parseDouble(scanner.nextLine().trim());
            System.out.print("Fecha de ingreso (YYYY-MM-DD): ");
            String fecha = scanner.nextLine().trim();

            empleados.add(new Empleado(id, nombre, puesto, salario, fecha));
            System.out.println("Empleado agregado.");
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void GuardarDatos(){
        try{
            GestorArchivos.GuardarCSV(DirDatos + "/" + ArchivoCSV, empleados);
            System.out.println("Datos guardados en CSV.");
        }catch(IOException e){
            System.out.println("Error al guardar: " + e.getMessage());
        }
    }

    static void HacerBackup(){
        try{
            String ruta = DirDatos + "/" + ArchivoCSV;
            if(!new java.io.File(ruta).exists()){
                GuardarDatos();
            }
            String backup = GestorArchivos.HacerBackup(ruta);
            System.out.println("Backup creado: " + backup);
        }catch(IOException e){
            System.out.println("Error en backup: " + e.getMessage());
        }
    }

    static void ExportarXML(){
        try{
            String ruta = DirDatos + "/empleados.xml";
            GestorArchivos.ExportarXML(ruta, empleados);
            System.out.println("Exportado a XML: " + ruta);
        }catch(IOException e){
            System.out.println("Error al exportar: " + e.getMessage());
        }
    }

    static void CargarDesdeCSV(Scanner scanner){
        System.out.print("Ruta del CSV: ");
        String ruta = scanner.nextLine().trim();
        try{
            empleados = GestorArchivos.LeerCSV(ruta);
            System.out.println("Cargados " + empleados.size() + " empleados.");
        }catch(IOException e){
            System.out.println("Error al leer CSV: " + e.getMessage());
        }
    }

    static void GuardarEmpleadoBinario(Scanner scanner){
        System.out.print("ID del empleado a guardar: ");
        try{
            int id = Integer.parseInt(scanner.nextLine().trim());
            Empleado encontrado = BuscarPorId(id);
            if (encontrado == null) { System.out.println("Empleado con ese ID no existe en el registro."); return; }
            String ruta = DirDatos + "/empleado_" + id + ".bin";
            Empleado.Guardar(encontrado, ruta);
            System.out.println("Guardado en: " + ruta);
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void CargarEmpleadoBinario(Scanner scanner){
        System.out.print("ID del binario a cargar: ");
        try{
            int id = Integer.parseInt(scanner.nextLine().trim());
            String ruta = DirDatos + "/empleado_" + id + ".bin";
            Empleado e = Empleado.Cargar(ruta);
            System.out.println("Cargado: " + e.GetNombre() + " - " + e.GetPuesto());
        }catch(Exception e){
            System.out.println("Error al cargar: " + e.getMessage());
        }
    }

    static void ListarDirectorio(){
        String[] archivos = GestorArchivos.ListarDirectorio(DirDatos);
        System.out.println("Archivos en '" + DirDatos + "':");
        for(String a : archivos) System.out.println("  " + a);
    }

    static void CargarDatos(){
        try{
            String ruta = DirDatos + "/" + ArchivoCSV;
            if(new java.io.File(ruta).exists()){
                empleados = GestorArchivos.LeerCSV(ruta);
                System.out.println("Datos cargados: " + empleados.size() + " empleados.");
            }
        }catch(IOException e){
            System.out.println("No se pudo cargar el archivo previo.");
        }
    }

    static Empleado BuscarPorId(int id){
        for(Empleado e : empleados){
            if(e.GetId() == id) return e;
        }
        return null;
    }
}
