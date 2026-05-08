import java.util.ArrayList;

public class SistemaCine {
    public static void main(String[] args) {

        Funcion2D funcion2D = new Funcion2D("Avengers", 150, 120, "B", true);
        Funcion3D funcion3D = new Funcion3D("Avatar", 165, 160, "B", true);
        FuncionVIP funcionVIP = new FuncionVIP("Batman", 130, 240, "C", 50);

        ArrayList<FuncionCineBase> cartelera = new ArrayList<>();
        cartelera.add(funcion2D);
        cartelera.add(funcion3D);
        cartelera.add(funcionVIP);

        GestionCartelera gestion = new GestionCartelera(cartelera);

        System.out.println("\n\nCartelera\n--------------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-20s | %-20s | %-10s | %-15s | %-20s | %12s |", 
                    "Tipo", "Titulo", "Duracion", "Clasificacion", "Formato", "Precio base");
        System.out.print("\n--------------------------------------------------------------------------------------------------------------------\n");
        gestion.MostrarCartelera();

        System.out.println();
        System.out.println("------Uso de conjunto de interfaces------");

        Cobro cobrable = funcion2D;
        System.out.println("Total 2D para 3 boletos: " + cobrable.CalcularTotal(3));

        Promociones promocion = funcion3D;
        double monto3D = funcion3D.CalcularTotal(2);
        System.out.println(promocion.ObtenerPromocion());
        System.out.println("Monto 3D con descuento: " + promocion.AplicarDescuento(10, monto3D));

        Reservaciones reservable = funcionVIP;
        String ReservaExitosa = (reservable.Reservar(10)) ? "Si" : "No";
        System.out.println("Reserva VIP exitosa: " + ReservaExitosa);
        System.out.println("Boletos VIP reservados: " + reservable.GetBoletosReservados());

        System.out.println("\nPoliformismo con clase abstracta\n--------------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-20s | %-20s | %-10s | %-15s | %-20s | %12s |", 
                    "Tipo", "Titulo", "Duracion", "Clasificacion", "Formato", "Precio base");
        System.out.print("\n--------------------------------------------------------------------------------------------------------------------\n");
        FuncionCineBase base = funcionVIP;
        System.out.println(base.GenerarFicha());

        System.out.println("\n-----Gesto de coleccion------");
        System.out.println("Ingresos totales de cartelera: " + gestion.CalcularIngresosTotales());

        System.out.println("\n------Promociones disponibles------");
        gestion.MostrarPromociones();

        System.out.println("\n------Reservaciones------");
        gestion.MostrarReservas();
    }
}