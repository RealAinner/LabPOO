
//Clase Abuela
abstract class Productos {
    protected String ID;
    protected double PrecioBase;

    public Productos(String ID, double PrecioBase){
        this.ID = ID;
        this.PrecioBase = PrecioBase;
    }

    public abstract double CalcularPrecio();
    public abstract String MostrarInfo();
}

//Clase Padre
abstract class Alimento extends Productos{
    protected int caducidad; //Dias restantes para vencer su caducidad
    protected String nombre;

    public Alimento(String ID, double PrecioBase, int caducidad, String nombre){
        super(ID, PrecioBase); //Uso de super en constructor
        this.caducidad = caducidad;
        this.nombre = nombre;
    }

    public boolean AptoParaConsumo(){
        return caducidad > 1;
    }
}
