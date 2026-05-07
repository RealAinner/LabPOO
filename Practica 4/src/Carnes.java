class Carnes extends Alimento{
    private boolean premium;

    public Carnes(String ID, double precio, int dias, String nombre, boolean premium) {
        super(ID, precio, dias, nombre);
        this.premium = premium;
    }

    @Override
    public double CalcularPrecio() {
        double precio = PrecioBase;
        if (premium) precio *= 1.30; //Si es un corte premium, aumentamos 30%
        if (caducidad == 1) precio *= 0.60; //Si vence en un dia, entonces le damos un 40% de descuento
        return precio;
    }

    @Override
    public String MostrarInfo() {
        String descuento = (caducidad == 1) ? "40%" : "-";
        String Premium = (premium) ? "Si, aumenta un 30%" : "No";
        
        return String.format("| %-5s | %-20s | %-15.2f | %-17d | %-20s | %-10s | %-15.2f |",
            ID, nombre, PrecioBase, caducidad, Premium, descuento, CalcularPrecio());
    }

    @Override
    public boolean AptoParaConsumo() {
        //Logica simple de caducidad heredada
        return super.AptoParaConsumo();
    }
}
