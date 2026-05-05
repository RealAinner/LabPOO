import java.util.ArrayList;

public class InventarioDeVerduras {
    //Aqui guardaremos los objetos (en este caso verduras)
    public ArrayList<Verdura> listaDeVerduras;

    public InventarioDeVerduras() {
        this.listaDeVerduras = new ArrayList<>();
    }

    //Metodo para agregar
    public void AgregarVerdura(Verdura v) {
        listaDeVerduras.add(v);
        System.out.println(v.nombre + " añadida al inventario.");
    }

    //Metodo para busqueda por su nombre
    public Verdura buscarVerdura(String nombreBuscar) {
        for (Verdura v : listaDeVerduras) {
            if (v.nombre.equalsIgnoreCase(nombreBuscar)) {
                return v;
            }
        }
        return null;
    }

    //Metodo para imprimir el array
    public void mostrarInventario() {
        System.out.println("\n--- LISTA ACTUAL DE LA TIENDA ---");
        System.out.printf("%-15s | %-12s | %-15s | %-10s%n", "Nombre", "Stock", "Precio x Kg", "Premium");
        System.out.println("----------------------------------------------------------");
        for (Verdura v : listaDeVerduras) {
            v.mostrarInfo();
        }
        System.out.println("----------------------------------------------------------\n");
    }
}
