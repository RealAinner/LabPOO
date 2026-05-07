import java.util.ArrayList;
import java.util.List;

class GestorInventario {
    private List<Productos> productos = new ArrayList<>();

    public void agregar(Productos p) {
        productos.add(p);
    }

    public void MostrarInventario() {
        System.out.printf("| %-5s | %-20s | %-15s | %-17s | %-20s | %-10s | %-15s |"
            ,"ID", "Nombre", "Precio(kg)", "Dias Para Vencer", "Premium", "Descuento", "Subtotal");
        System.out.println("\n----------------------------------------------------------------------------------------------------------------------------");
        for (Productos p : productos){
            System.out.println(p.MostrarInfo()); // Demostración de polimorfismo
        }
    }
}


public class Main {
    public static void main(String[] args) throws Exception {
        GestorInventario inventario = new GestorInventario();

        //Polimorfismo en accion
        inventario.agregar(new Verdura("V01", 20.0, 5, "Lechuga", true));
        inventario.agregar(new Verdura("V02", 15.0, 1, "Zanahoria", false)); // En remate
        inventario.agregar(new Verdura("V03", 30.0, 10, "Aguacate", true));
        
        System.out.println("--- Reporte de Inventario ---");
        inventario.MostrarInventario();
    }
}
