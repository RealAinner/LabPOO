import java.util.ArrayList;

public class SistemaCine{
    public static void main(String[] args){

        FuncionCine[] programacion = new FuncionCine[3];

        programacion[0] = new Funcion2D("Avengers", 150, 120, true);
        programacion[1] = new Funcion3D("Avatar", 165, 140, true);
        programacion[2] = new FuncionVIP("Batman", 130, 200, true);

        System.out.println("\n\nArray Polimorfico\n--------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-13s | %-20s | %-15s | %-25s | %-15s | %-15s |",
                        "Tipo", "Titulo", "Duracion", "Formato", "Precio base", "Precio final");
        System.out.println("\n--------------------------------------------------------------------------------------------------------------------------");
        for(FuncionCine funcion : programacion){
            System.out.println(funcion.GenerarResumen());
        }

        System.out.println();
        System.out.println("\n------Casting e Instanceof------");
        FuncionCine actual = programacion[1];

        if(actual instanceof Funcion3D){
            Funcion3D funcion3D = (Funcion3D) actual;
            System.out.println("La funcion seleccionada es 3D.");
            System.out.println("Incluye gafas: " + funcion3D.GetGafas());
            System.out.println("Precio para 3 boletos con combo: " + funcion3D.CalcularPrecio(3, true));
        }

        System.out.println();
        System.out.println("\n------Sobrecarga de metodos------");
        System.out.println("Precio simple 2D: " + programacion[0].CalcularPrecio());
        System.out.println("Precio 2 boletos 2D: " + programacion[0].CalcularPrecio(2));
        System.out.println("Precio 2 boletos 2D con combo: " + programacion[0].CalcularPrecio(2, true));
        System.out.println("Precio 2 boletos 2D con combo y descuento: " + ((Funcion2D) programacion[0]).CalcularPrecio(2, true, 15));

        System.out.println();
        System.out.println("\nColeccion\n--------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-13s | %-20s | %-15s | %-25s | %-15s | %-15s |",
                        "Tipo", "Titulo", "Duracion", "Formato", "Precio base", "Precio final");
        System.out.println("\n--------------------------------------------------------------------------------------------------------------------------");
        ArrayList<FuncionCine> cartelera = new ArrayList<>();
        cartelera.add(programacion[0]);
        cartelera.add(programacion[1]);
        cartelera.add(programacion[2]);

        GestionCartelera gestion = new GestionCartelera();
        gestion.MostrarCartelera(cartelera);
        System.out.println("Total cartelera: " + gestion.CalcularTotal(cartelera));

        System.out.println();
        System.out.println("\n------Prueba de validacion------");
        try{
            FuncionCine invalida = new Funcion2D("A", 20, -5, false);
            System.out.println(invalida);
        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }
}
