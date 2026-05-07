import java.util.ArrayList;
import java.util.List;

class GestorInventario {
    private List<Productos> productos = new ArrayList<>();

    public void agregar(Productos p){
        productos.add(p);
    }

    private void Encabezado(String titulo, String columna) {
        System.out.println("\n\n" + titulo + "\n----------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-5s | %-20s | %-15s | %-17s | %-20s | %-10s | %-15s |\n",
                "ID", "Nombre", "Precio(Kg/L)", "Dias Para Vencer", columna, "Descuento", "Subtotal");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------");
    }

    public void MostrarInventario(){
        //Aqui se muestran las verduras
        Encabezado("Verduras", "Premium");
        for (Productos p : productos) {
            if (p instanceof Verdura) {
                System.out.println(p.MostrarInfo());
            }
        }

        //Aqui se muestran los lacteos y su temperatura recomendada
        Encabezado("Lacteos", "Temperatura");
        for (Productos p : productos) {
            if (p instanceof Lacteo) {
                System.out.println(p.MostrarInfo());
            }
        }

        //Aqui se muestran las carnes
        Encabezado("Carnes", "Carne de calidad");
        for (Productos p : productos) {
            if (p instanceof Carnes) {
                System.out.println(p.MostrarInfo());
            }
        }
    }
}


public class Main {
    public static void main(String[] args) throws Exception {
        GestorInventario inventario = new GestorInventario();

        //Agregamos verduras
        inventario.agregar(new Verdura("V01", 20.0, 5, "Lechuga", true));
        inventario.agregar(new Verdura("V02", 15.0, 1, "Zanahoria", false)); // En remate
        inventario.agregar(new Verdura("V03", 30.0, 10, "Aguacate", true));
        //Agregamos lacteos
        inventario.agregar(new Lacteo("L01", 45.0, 2, "Leche Entera", 4.0)); // Con descuento 70%
        inventario.agregar(new Lacteo("L02", 40.0, 5, "Leche Deslactosada", 3.0));
        inventario.agregar(new Lacteo("L03", 55.0, 1, "Leche Proteina", 4.5));
        //Agregamos carnes
        inventario.agregar(new Carnes("C01", 150.0, 10, "T-Bone", true)); // Con aumento 30%
        inventario.agregar(new Carnes("C02", 200.0, 1, "Ribeye", true));
        inventario.agregar(new Carnes("C03", 150.0, 3, "Tomahawk", false));
        
        System.out.println("\n\n--- Reporte de Inventario ---");
        inventario.MostrarInventario();
    }
}
