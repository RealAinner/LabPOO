import java.util.ArrayList;
import java.util.List;

public class GestionCartelera {

    private List<FuncionCineBase> cartelera;

    public GestionCartelera() {
        cartelera = new ArrayList<>();
    }

    public GestionCartelera(List<FuncionCineBase> cartelera) {
        if (cartelera != null) {
            this.cartelera = cartelera;
        } else {
            this.cartelera = new ArrayList<>();
        }
    }

    public void AgregarFuncion(FuncionCineBase Funcion) {
        if (Funcion != null) {
            cartelera.add(Funcion);
        } else {
            throw new IllegalArgumentException("Error: No se puede agregar una funcion nula.");
        }
    }

    public void MostrarCartelera() {
        for (FuncionCineBase funcion : cartelera) {
            System.out.println(funcion.GenerarFicha());
        }
    }

    public double CalcularIngresosTotales() {
        double total = 0;

        for (FuncionCineBase funcion : cartelera) {
            if (funcion instanceof Cobro) {
                total = total + ((Cobro) funcion).CalcularTotal();
            }
        }

        return total;
    }

    public void MostrarPromociones() {
        for (FuncionCineBase funcion : cartelera) {
            if (funcion instanceof Promociones) {
                Promociones promocion = (Promociones) funcion;
                System.out.println(funcion.GetTitulo() + " -> " + promocion.ObtenerPromocion());
            }
        }
    }

    public void MostrarReservas() {
        for (FuncionCineBase funcion : cartelera) {
            if (funcion instanceof Reservaciones) {
                Reservaciones reservable = (Reservaciones) funcion;
                System.out.println(funcion.GetTitulo() + " -> Boletos reservados: " + reservable.GetBoletosReservados());
            }
        }
    }
}