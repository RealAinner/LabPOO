abstract class FuncionCine implements CalculosCine{
    private String titulo;
    private int duracion;
    private double precio;

    public FuncionCine(String titulo, int duracion, double precio){
        SetTitulo(titulo);
        SetDuracion(duracion);
        SetPrecio(precio);
    }

    public String GetTitulo(){
        return titulo;
    }

    public void SetTitulo(String titulo){
        if(titulo != null && titulo.trim().length() >= 2){
            this.titulo = titulo.trim();
        }else{
            throw new IllegalArgumentException("Error: El titulo debe tener al menos 2 caracteres.");
        }
    }

    public int GetDuracion(){
        return duracion;
    }

    public void SetDuracion(int duracion){
        if(duracion >= 60 && duracion <= 300){
            this.duracion = duracion;
        }else{
            throw new IllegalArgumentException("Error: La duracion debe estar entre 60 y 300 minutos.");
        }
    }

    public double GetPrecio(){
        return precio;
    }

    public void SetPrecio(double precio){
        if(precio > 0) {
            this.precio = precio;
        }else{
            throw new IllegalArgumentException("Error: El precio debe ser mayor a 0.");
        }
    }

    public abstract String ObtenerTipo();
    public abstract String ObtenerFormato();
    public abstract double CalcularPrecio();
    public abstract double CalcularPrecio(int boletos);
    public abstract double CalcularPrecio(int boletos, boolean combo);

    public final String GenerarResumen(){
        return String.format("| %-13s | %-20s | %-11d min | %-25s | %-15.2f | %-15.2f |",
            ObtenerTipo(), GetTitulo(), GetDuracion(), ObtenerFormato(), GetPrecio(), CalcularPrecio());
    }

    public void MostrarResumen(){
        System.out.println(GenerarResumen());
    }

    @Override
    public String toString(){
        return GenerarResumen();
    }
}