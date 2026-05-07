public class Peliculas {
    private String titulo;
    private int duracion; //En minutos
    private double precio;
    private String clasificacion;

    //Aqui encapsulamos los datos
    public Peliculas(String titulo, int duracion, double precio, String clasificacion){
        setTitulo(titulo);
        setDuracion(duracion);
        setPrecio(precio);
        setClasificacion(clasificacion);
    }

    public String getTitulo(){
        return titulo;
    }

    public void setTitulo(String titulo){
        if (titulo != null && titulo.length() >= 2){
            this.titulo = titulo;
        }else{
        throw new IllegalArgumentException(
            "Error: El titulo debe tener al menos 2 caracteres."
            );
        }
    }

    public int getDuracion(){
        return duracion;
    }

    public void setDuracion(int duracion){
        if (duracion >= 60 && duracion <= 240){
            this.duracion = duracion;
        }else{
        throw new IllegalArgumentException(
            "Error: La duracion debe estar entre 60 y 240 minutos."
            );
    }
    }

    public double getPrecio(){
        return precio;
    }

    public void setPrecio(double precio){
        if (precio > 0){
            this.precio = precio;
        }else{
            throw new IllegalArgumentException(
                "Error: El precio debe ser mayor a 0."
            );
        }
    }

    public String getClasificacion(){
        return clasificacion;
    }

    public void setClasificacion(String clasificacion){
        if (clasificacion.equals("A") || clasificacion.equals("B") || clasificacion.equals("C")){
            this.clasificacion = clasificacion;
        }else{
            throw new IllegalArgumentException(
                "Error: La clasificacion solo puede ser A, B o C."
            );
        }
    }

    public void mostrarPelicula(){
        System.out.printf("\n| %-25s | %-12dmin | %-7.2fmxn | %-15s |", titulo, duracion, precio, clasificacion);
    }
}
