import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

class GestorInventario {
    //Metodo que implementa try-with-resources para el log
    public void RegistrarLog(String mensaje){
        try(FileWriter archivo = new FileWriter("log_verduleria.txt", true); PrintWriter escritor = new PrintWriter(archivo)) {
            escritor.println("[REGISTRO]: " + mensaje);
        }catch(IOException e){
            System.out.println("No se pudo escribir en el log: " + e.getMessage());
        }
    }

    public void ValidarEntrada(String nombre, double precio, double peso) throws PrecioInvalidoExcepcion, PesoInvalidoExcepcion {
        if(precio <= 0){
            String error = "Error en " + nombre + ": El precio debe ser mayor a cero.";
            RegistrarLog(error);
            throw new PrecioInvalidoExcepcion(error);
        }
        if(peso <= 0){
            String error = "Error en " + nombre + ": El peso debe ser positivo.";
            RegistrarLog(error);
            throw new PesoInvalidoExcepcion(error);
        }
    }

    public void DespacharPedido(String producto, double pedido, double existencia) throws InventarioAgotadoExcepcion {
        if(pedido > existencia){
            InventarioAgotadoExcepcion ex = new InventarioAgotadoExcepcion("No hay suficiente producto en bascula.", producto, pedido, existencia);
            RegistrarLog("Fallo de inventario: " + ex.ObtenerReporteDetallado());
            throw ex;
        }
        System.out.println("Pedido de " + producto + " procesado correctamente.");
    }
}