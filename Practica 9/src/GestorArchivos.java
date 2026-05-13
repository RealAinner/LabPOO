import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class GestorArchivos {

    public static void EscribirTexto(String ruta, String contenido, boolean agregar) throws IOException {
        try(FileWriter fw = new FileWriter(ruta, agregar);
             BufferedWriter bw = new BufferedWriter(fw)){
            bw.write(contenido);
        }
    }

    public static String LeerTexto(String ruta) throws IOException {
        StringBuilder sb = new StringBuilder();
        try(BufferedReader br = new BufferedReader(new FileReader(ruta))){
            String linea;
            while((linea = br.readLine()) != null){
                sb.append(linea).append("\n");
            }
        }
        return sb.toString().trim();
    }

    public static void EscribirBinario(String ruta, byte[] datos) throws IOException {
        try(FileOutputStream fos = new FileOutputStream(ruta)){
            fos.write(datos);
        }
    }

    public static byte[] LeerBinario(String ruta) throws IOException {
        return Files.readAllBytes(Paths.get(ruta));
    }

    public static boolean CrearDirectorio(String ruta){
        return new File(ruta).mkdirs();
    }

    public static String[] ListarDirectorio(String ruta){
        String[] lista = new File(ruta).list();
        return lista != null ? lista : new String[0];
    }

    public static boolean EliminarArchivo(String ruta){
        return new File(ruta).delete();
    }

    public static List<Empleado> LeerCSV(String ruta) throws IOException {
        List<Empleado> lista = new ArrayList<>();
        try(BufferedReader br = new BufferedReader(new FileReader(ruta))){
            String linea;
            br.readLine(); //saltar encabezado
            while ((linea = br.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length < 5) continue;
                try{
                    int id = Integer.parseInt(partes[0].trim());
                    String nombre = partes[1].trim();
                    String puesto = partes[2].trim();
                    double salario = Double.parseDouble(partes[3].trim());
                    String fecha = partes[4].trim();
                    lista.add(new Empleado(id, nombre, puesto, salario, fecha));
                }catch(Exception e){
                    System.out.println("Fila omitida por datos invalidos: " + linea);
                }
            }
        }
        return lista;
    }

    public static void GuardarCSV(String ruta, List<Empleado> lista) throws IOException {
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(ruta))){
            bw.write("id,nombre,puesto,salario,fechaIngreso\n");
            for(Empleado e : lista){
                bw.write(e.toString() + "\n");
            }
        }
    }

    public static String HacerBackup(String rutaOrigen) throws IOException {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        String rutaBackup = rutaOrigen.replace(".csv", "_backup_" + timestamp + ".csv");
        Files.copy(Paths.get(rutaOrigen), Paths.get(rutaBackup), StandardCopyOption.REPLACE_EXISTING);
        return rutaBackup;
    }

    public static void ExportarXML(String ruta, List<Empleado> lista) throws IOException{
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<empleados>\n");
        for(Empleado e : lista){
            sb.append("  <empleado>\n")
              .append("    <id>").append(e.GetId()).append("</id>\n")
              .append("    <nombre>").append(e.GetNombre()).append("</nombre>\n")
              .append("    <puesto>").append(e.GetPuesto()).append("</puesto>\n")
              .append("    <salario>").append(e.GetSalario()).append("</salario>\n")
              .append("    <fechaIngreso>").append(e.GetFechaIngreso()).append("</fechaIngreso>\n")
              .append("  </empleado>\n");
        }
        sb.append("</empleados>");
        EscribirTexto(ruta, sb.toString(), false);
    }
}
