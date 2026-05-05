public class Main {
    public static void main(String[] args) {
        //Creamos la clase que los contiene
        InventarioDeVerduras miTienda = new InventarioDeVerduras();

        //Aqui creo 5 objetos con los constructores
        Verdura v1 = new Verdura("Zanahoria", 20.0, 100.0, "Raiz", true);
        Verdura v2 = new Verdura("Cebolla", 15.0, 50.0, "Bulbo", false);
        Verdura v3 = new Verdura("Tomate", 35.0); // Constructor 2
        Verdura v4 = new Verdura("Lechuga", 12.0, 30.0, "Hoja", false);
        Verdura v5 = new Verdura("Papa"); // Constructor 3

        //Los agrego a la clase que los contiene
        miTienda.AgregarVerdura(v1);
        miTienda.AgregarVerdura(v2);
        miTienda.AgregarVerdura(v3);
        miTienda.AgregarVerdura(v4);
        miTienda.AgregarVerdura(v5);

        //Demostramos el uso de los métodos
        System.out.println("\nAcciones de prueba:");
        v3.agregarStock(40); //Le ponemos stock al tomate que no tenía
        v5.aplicarDescuento(10); //Descuento a la papa
        
        //Mostramos todo
        miTienda.mostrarInventario();

        //Probamos la búsqueda
        System.out.println("Buscando la Cebolla...");
        Verdura encontrada = miTienda.buscarVerdura("Cebolla");
        if (encontrada != null) {
            System.out.println("¡Encontrada! Su stock es: " + encontrada.stock + "kg");
        }
    }
}