public class Verdura {
    //Atributos
    public String nombre;
    public double precioPorKilo;
    public double stock;
    public String categoria;
    public boolean premium;

    //Constructores
    
    //Constructor 1
    public Verdura(String nombre, double precio, double stock, String categoria, boolean premium) {
        this.nombre = nombre;
        this.precioPorKilo = precio;
        this.stock = stock;
        this.categoria = categoria;
        this.premium = premium;
    }

    //Constructor 2
    public Verdura(String nombre, double precio) {
        this.nombre = nombre;
        this.precioPorKilo = precio;
        this.stock = 0.0;
        this.categoria = "General";
        this.premium = false;
    }

    //Constructor 3
    public Verdura(String nombre) {
        this.nombre = nombre;
        this.precioPorKilo = 0.0;
        this.stock = 0.0;
        this.categoria = "Sin clasificar";
        this.premium = false;
    }

    //Métodos de instancia
    public void agregarStock(double cantidad) {
        this.stock += cantidad;
        System.out.println("Se agregaron " + cantidad + "kg de " + nombre);
    }

    public void aplicarDescuento(double porcentaje) {
        this.precioPorKilo -= (this.precioPorKilo * (porcentaje / 100));
        System.out.println("Nuevo precio de " + nombre + ": $" + precioPorKilo);
    }

    public double vender(double kilos) {
        if (kilos <= this.stock) {
            this.stock -= kilos;
            return kilos * this.precioPorKilo;
        } else {
            System.out.println("No hay suficiente stock de " + nombre);
            return 0;
        }
    }

    public void marcarPremium() {
        this.premium = true;
        this.precioPorKilo += 5.0; //Subimos el precio por ser premium
    }

    public void mostrarInfo() {
        String premiumStr = (premium) ? "Sí" : "No";
        System.out.printf("%-15s | %-9.2f kg | $%-14.2f | %-10s%n", nombre, stock, precioPorKilo, premiumStr);
    }
}
