class Lacteo extends Alimento {
    private double temperaturaRecomendada;

    public Lacteo(String ID, double precio, int dias, String nombre, double temp) {
        super(ID, precio, dias, nombre);
        this.temperaturaRecomendada = temp;
    }

    @Override
    public double CalcularPrecio() {
        //Los lacteos tienen un remate del 70% si vencen en 2 dias o menos
        if (caducidad <= 2) {
            return PrecioBase * 0.30;
        }
        return PrecioBase;
    }

    @Override
    public String MostrarInfo() {
        String infoTemp = temperaturaRecomendada + "°C";
        String descuento = (caducidad <= 2) ? "70%" : "-";
        
        return String.format("| %-5s | %-20s | %-15.2f | %-17d | %-20s | %-10s | %-15.2f |",
            ID, nombre, PrecioBase, caducidad, infoTemp, descuento, CalcularPrecio());
    }

    @Override
    public boolean AptoParaConsumo() {
        //Un lacteo no es apto si la temperatura ambiente sube de 7 grados
        return super.AptoParaConsumo() && temperaturaRecomendada <= 7.0;
    }
}
