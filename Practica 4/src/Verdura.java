
//Clase Hija
class Verdura extends Alimento {
    private boolean premium;

    public Verdura(String ID, double precio, int dias, String nombre, boolean premium) {
        super(ID, precio, dias, nombre);
        this.premium = premium;
    }

    @Override
    public double CalcularPrecio(){
        double precio = PrecioBase;
        if (premium) precio *= 1.20; //Aumento por ser premium
        if (caducidad < 3) precio *= 0.50; //Descuento por vencimiento cercano
        return precio;
    }

    @Override
    public String MostrarInfo() {
        String Premium = (premium) ? "Si, aumenta un 20%" : "No";
        String descuento = (caducidad < 3) ? "50%" : "0%";
        return String.format("| %-5s | %-20s | %-15.2f | %-17d | %-20s | %-10s | %-15.2f |"
            ,ID, nombre, PrecioBase, caducidad, Premium, descuento, CalcularPrecio());
    }

    @Override
    public boolean AptoParaConsumo(){
        //Sobrescribe la logica: las verduras no se consumen si vencen en menos de 1 dia
        return super.AptoParaConsumo() && caducidad > 1;
    }
}
