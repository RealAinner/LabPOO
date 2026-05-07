public class Cine {
    protected String nombre;
    protected String ubicacion;

    //Aqui encapsulamos los datos
    public Cine(String nombre, String ubicacion){
        setNombre(nombre);
        setUbicacion(ubicacion);
    }

    //Validacion: minimo 3 caracteres y solo letras y espacios
    public void setNombre(String nombre){
        if (nombre != null && nombre.length() >= 3 && nombre.matches("[a-zA-Z ]+")) {
            this.nombre = nombre;
        } else {
            throw new IllegalArgumentException(
                "Error: El nombre del cine debe tener al menos 3 caracteres y solo contener letras."
            );
        }
    }

    //Validacion: debe contener una coma
    public void setUbicacion(String ubicacion){
        if (ubicacion != null && ubicacion.contains(",") && ubicacion.length() >= 5) {
            this.ubicacion = ubicacion;
        } else {
            throw new IllegalArgumentException(
                "Error: La ubicacion debe incluir zona y ciudad separadas por coma. Ej: Centro, Monterrey."
            );
        }
    }

    public void mostrarCine(){
        System.out.printf("\n| %-30s | %-30s |", nombre, ubicacion);
    }
}
