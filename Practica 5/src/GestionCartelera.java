import java.util.List;

class GestionCartelera{
    public void MostrarCartelera(List<FuncionCine> funciones){
        for(FuncionCine funcion : funciones){
            System.out.println(funcion);
        }
    }

    public double CalcularTotal(List<FuncionCine> funciones){
        double total = 0;
        for(FuncionCine funcion : funciones){
            total = total + funcion.CalcularPrecio();
        }
        return total;
    }
}
