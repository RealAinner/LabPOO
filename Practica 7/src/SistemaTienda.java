public class SistemaTienda {
    public static void main(String[] args){
        GestorInventario miGestor = new GestorInventario();
        System.out.println("\n\n--- Inicio de Pruebas de Inventario de Verduleria ---");

        // Prueba 1: Precio Invalido
        try{
            miGestor.ValidarEntrada("Tomate", -5.0, 10.0);
        }catch(VerduleriaExcepcion e){
            System.out.println("Capturada: " + e.getMessage());
        }

        // Prueba 2: Peso Invalido
        try{
            miGestor.ValidarEntrada("Cebolla", 20.0, 0.0);
        }catch(VerduleriaExcepcion e){
            System.out.println("Capturada: " + e.getMessage());
        }

        // Prueba 3: Inventario Agotado (Con contexto)
        try{
            // Intentamos vender 50kg de Aguacate pero solo hay 5.5kg
            miGestor.DespacharPedido("Aguacate", 50.0, 5.5);
        }catch(InventarioAgotadoExcepcion e){
            System.out.println("Capturada: " + e.getMessage());
            System.out.println("Contexto Adicional: " + e.ObtenerReporteDetallado());
        }catch(VerduleriaExcepcion e){
            System.out.println("Error general: " + e.getMessage());
        }

        System.out.println("--- Pruebas finalizadas. Revise log_verduleria.txt ---\n\n");
    }
}
