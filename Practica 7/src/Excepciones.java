import java.time.LocalDateTime;

// Clase padre
class VerduleriaExcepcion extends Exception {
    public VerduleriaExcepcion(String mensaje){
        super(mensaje);
    }
}

// Clase hija 1
class PrecioInvalidoExcepcion extends VerduleriaExcepcion {
    public PrecioInvalidoExcepcion(String mensaje){
        super(mensaje);
    }
}

// Clase hija 2
class PesoInvalidoExcepcion extends VerduleriaExcepcion {
    public PesoInvalidoExcepcion(String mensaje){
        super(mensaje);
    }
}

// Clase hija 3 con contexto adicional (Elemento de Decision Propia)
class InventarioAgotadoExcepcion extends VerduleriaExcepcion {
    private String NombreProducto;
    private double CantidadSolicitada;
    private double StockDisponible;
    private LocalDateTime tiempoError;

    public InventarioAgotadoExcepcion(String mensaje, String NombreProducto, double CantidadSolicitada, double StockDisponible) {
        super(mensaje);
        this.NombreProducto = NombreProducto;
        this.CantidadSolicitada = CantidadSolicitada;
        this.StockDisponible = StockDisponible;
        this.tiempoError = LocalDateTime.now();
    }

    public String ObtenerReporteDetallado() {
        return String.format("| Producto: %s | Pedido: %.2f kg | Stock: %.2f kg | Fecha: %s |", 
                NombreProducto, CantidadSolicitada, StockDisponible, tiempoError.toString());
    }
}
