package gympos.modelo.membresia;

public enum TipoMembresia {
    BASICO("Basico", 299.0, 0),
    ESTANDAR("Estandar", 499.0, 10),
    PREMIUM("Premium", 799.0, 20),
    VIP("VIP", 1299.0, 30);

    private final String nombre;
    private final double precio;
    private final int descuento;

    TipoMembresia(String nombre, double precio, int descuento){
        this.nombre = nombre;
        this.precio = precio;
        this.descuento = descuento;
    }

    public String GetNombre() { return nombre; }
    public double GetPrecio() { return precio; }
    public int GetDescuento() { return descuento; }

    public double GetPrecioFinal() {
        return precio - (precio * descuento / 100.0);
    }
}
