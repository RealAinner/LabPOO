package escuela;
import java.util.*;

public class Main {

    private static final Scanner entrada = new Scanner(System.in);
    private static final GestorEscolar gestor = new GestorEscolar();

    public static void main(String[] args) {
        CargarDatosPrueba();
        System.out.println("Sistema de Gestion Escolar - " + gestor.GetTotal() + " alumnos cargados.");

        boolean activo = true;
        while(activo){
            System.out.println("\n\n------MENU PRINCIPAL------");
            System.out.println("1. Listar todos los alumnos");
            System.out.println("2. Buscar alumno por matricula");
            System.out.println("3. Agregar alumno");
            System.out.println("4. Actualizar promedio");
            System.out.println("5. Eliminar alumno");
            System.out.println("6. Filtrar por carrera");
            System.out.println("7. Busqueda compuesta (carrera + semestre + promedio)");
            System.out.println("8. Ordenar alumnos");
            System.out.println("9. Ver historial de operaciones");
            System.out.println("10. Analisis de tiempo de ejecucion");
            System.out.println("0. Salir");
            System.out.print("Opcion: ");

            switch (LeerEntero()){
                case 1 -> ListarAlumnos();
                case 2 -> BuscarAlumno();
                case 3 -> AgregarAlumno();
                case 4 -> ActualizarPromedio();
                case 5 -> EliminarAlumno();
                case 6 -> FiltrarCarrera();
                case 7 -> BusquedaCompuesta();
                case 8 -> OrdenarAlumnos();
                case 9 -> gestor.GetHistorial().forEach(h -> System.out.println("  " + h));
                case 10 -> AnalisisTiempo();
                case 0 -> activo = false;
                default -> System.out.println("Opcion no valida.");
            }
        }
        System.out.println("Hasta luego.");
        entrada.close();
    }

    // Opciones del menu
    private static void ListarAlumnos(){
        System.out.println("\n-----Listado-----");
        gestor.MostrarConIterador();
    }

    private static void BuscarAlumno(){
        System.out.print("Matricula: ");
        String m = entrada.nextLine().trim();
        long inicio = System.nanoTime();
        Optional<Alumno> resultado = gestor.BuscarPorMatricula(m);
        long fin = System.nanoTime();
        System.out.printf("Tiempo: %.4f ms%n", (fin - inicio) / 1_000_000.0);
        resultado.ifPresentOrElse(a -> System.out.println("Encontrado: " + a),() -> System.out.println("No encontrado."));
    }

    private static void AgregarAlumno(){
        System.out.print("Matricula: "); String mat = entrada.nextLine().trim();
        System.out.print("Nombre: "); String nom = entrada.nextLine().trim();
        System.out.print("Carrera: "); String car = entrada.nextLine().trim();
        System.out.print("Semestre: "); int sem = LeerEntero();
        System.out.print("Promedio: "); double pro = LeerDouble();
        boolean ok = gestor.AgregarAlumno(new Alumno(mat, nom, car, sem, pro));
        System.out.println(ok ? "Alumno agregado." : "Matricula ya existente.");
    }

    private static void ActualizarPromedio(){
        System.out.print("Matricula: "); String m = entrada.nextLine().trim();
        System.out.print("Nuevo promedio: "); double p = LeerDouble();
        System.out.println(gestor.ActualizarPromedio(m, p) ? "Actualizado." : "No encontrado.");
    }

    private static void EliminarAlumno(){
        System.out.print("Matricula: "); String m = entrada.nextLine().trim();
        System.out.println(gestor.EliminarAlumno(m) ? "Eliminado." : "No encontrado.");
    }

    private static void FiltrarCarrera(){
        System.out.print("Carrera: "); String c = entrada.nextLine().trim();
        List<Alumno> lista = gestor.FiltrarPorCarrera(c);
        lista.forEach(a -> System.out.println("  " + a));
        System.out.println("Total: " + lista.size());
    }

    private static void BusquedaCompuesta(){
        System.out.print("Carrera: "); String c = entrada.nextLine().trim();
        System.out.print("Semestre: "); int s = LeerEntero();
        System.out.print("Promedio minimo: "); double p = LeerDouble();
        List<Alumno> lista = gestor.BusquedaCompuesta(c, s, p);
        lista.forEach(a -> System.out.println("  " + a));
        System.out.println("Total: " + lista.size());
    }

    private static void OrdenarAlumnos(){
        System.out.println("1. Por matricula (Comparable)");
        System.out.println("2. Por promedio descendente");
        System.out.println("3. Por nombre ascendente");
        System.out.print("Criterio: ");
        Comparator<Alumno> criterio = switch(LeerEntero()){
            case 2  -> ComparadorAlumno.PorPromedioDesc;
            case 3  -> ComparadorAlumno.PorNombre;
            default -> Comparator.naturalOrder();
        };
        long inicio = System.nanoTime();
        List<Alumno> ordenados = gestor.ObtenerOrdenados(criterio);
        long fin = System.nanoTime();
        System.out.printf("Tiempo ordenamiento (n=%d): %.4f ms%n",ordenados.size(), (fin - inicio) / 1_000_000.0);
        ordenados.forEach(a -> System.out.println("  " + a));
    }

    private static void AnalisisTiempo(){
        System.out.println("\n-- Analisis de tiempo de ejecucion --");
        long t;

        //AgregarAlumno O(1) promedio por HashMap
        Alumno tmp = new Alumno("TMP-00", "Temporal", "Comun", 1, 7.0);
        t = System.nanoTime();
        gestor.AgregarAlumno(tmp);
        System.out.printf("AgregarAlumno O(1): %.4f ms%n", (System.nanoTime()-t)/1_000_000.0);

        //BuscarPorMatricula O(1) por HashMap
        t = System.nanoTime();
        gestor.BuscarPorMatricula("TMP-00");
        System.out.printf("BuscarMatricula O(1): %.4f ms%n", (System.nanoTime()-t)/1_000_000.0);

        //ActualizarPromedio O(1)
        t = System.nanoTime();
        gestor.ActualizarPromedio("TMP-00", 8.0);
        System.out.printf("ActualizarProm. O(1): %.4f ms%n", (System.nanoTime()-t)/1_000_000.0);

        //EliminarAlumno O(n) por ArrayList.remove
        t = System.nanoTime();
        gestor.EliminarAlumno("TMP-00");
        System.out.printf("EliminarAlumno O(n): %.4f ms%n", (System.nanoTime()-t)/1_000_000.0);

        //FiltrarPorCarrera O(n)
        t = System.nanoTime();
        gestor.FiltrarPorCarrera("Sistemas");
        System.out.printf("FiltrarCarrera O(n): %.4f ms%n", (System.nanoTime()-t)/1_000_000.0);

        //BusquedaCompuesta O(n log n) por sort interno
        t = System.nanoTime();
        gestor.BusquedaCompuesta("Sistemas", 4, 8.0);
        System.out.printf("BusqCompuesta O(n logn): %.4f ms%n", (System.nanoTime()-t)/1_000_000.0);

        //Ordenamiento O(n log n)
        t = System.nanoTime();
        gestor.ObtenerOrdenados(ComparadorAlumno.PorPromedioDesc);
        System.out.printf("Ordenamiento  O(n logn): %.4f ms | n=%d%n", (System.nanoTime()-t)/1_000_000.0, gestor.GetTotal());
    }

    //15 registros
    private static void CargarDatosPrueba(){
        gestor.AgregarAlumno(new Alumno("SIS-001", "Carlos Ramirez", "Sistemas", 4, 9.4));
        gestor.AgregarAlumno(new Alumno("SIS-002", "Daniela Lopez", "Sistemas", 4, 8.7));
        gestor.AgregarAlumno(new Alumno("SIS-003", "Fernando Torres", "Sistemas", 6, 7.5));
        gestor.AgregarAlumno(new Alumno("SIS-004", "Mariana Gutierrez","Sistemas", 4, 9.1));
        gestor.AgregarAlumno(new Alumno("SIS-005", "Jorge Mendez", "Sistemas", 6, 6.8));
        gestor.AgregarAlumno(new Alumno("CON-001", "Sofia Hernandez", "Contaduria", 2, 9.8));
        gestor.AgregarAlumno(new Alumno("CON-002", "Miguel Castillo", "Contaduria", 2, 8.2));
        gestor.AgregarAlumno(new Alumno("CON-003", "Valeria Reyes", "Contaduria", 4, 7.9));
        gestor.AgregarAlumno(new Alumno("CON-004", "Diego Morales", "Contaduria", 4, 6.4));
        gestor.AgregarAlumno(new Alumno("ADM-001", "Andrea Sanchez", "Administracion", 2, 9.5));
        gestor.AgregarAlumno(new Alumno("ADM-002", "Ricardo Jimenez", "Administracion", 4, 8.0));
        gestor.AgregarAlumno(new Alumno("ADM-003", "Natalia Vargas", "Administracion", 6, 7.3));
        gestor.AgregarAlumno(new Alumno("ADM-004", "Pablo Rojas", "Administracion", 6, 9.0));
        gestor.AgregarAlumno(new Alumno("ADM-005", "Claudia Fuentes", "Administracion", 2, 6.1));
        gestor.AgregarAlumno(new Alumno("SIS-006", "Eduardo Perez", "Sistemas", 8, 8.5));
    }

    private static int LeerEntero() {
        while (true) {
            try { return Integer.parseInt(entrada.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.print("Ingrese un entero: "); }
        }
    }

    private static double LeerDouble() {
        while (true) {
            try { return Double.parseDouble(entrada.nextLine().trim()); }
            catch (NumberFormatException e) { System.out.print("Ingrese un decimal: "); }
        }
    }
}
