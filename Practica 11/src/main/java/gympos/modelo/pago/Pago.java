package gympos.modelo.pago;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Pago implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum EstadoPago { PENDIENTE, PROCESANDO, COMPLETADO, FALLIDO }
    public enum MetodoPago { EFECTIVO, TARJETA, TRANSFERENCIA }

    private int id;
    private int IdCliente;
    private int IdMembresia;
    private double monto;
    private MetodoPago metodo;
    private EstadoPago estado;
    private LocalDateTime FechaPago;

    public Pago(int id, int IdCliente, int IdMembresia, double monto, MetodoPago metodo) {
        this.id = id;
        this.IdCliente = IdCliente;
        this.IdMembresia = IdMembresia;
        this.monto = monto;
        this.metodo = metodo;
        this.estado = EstadoPago.PENDIENTE;
        this.FechaPago = LocalDateTime.now();
    }

    public int GetId() { return id; }
    public int GetIdCliente() { return IdCliente; }
    public int GetIdMembresia() { return IdMembresia; }
    public double GetMonto() { return monto; }
    public MetodoPago GetMetodo() { return metodo; }
    public EstadoPago GetEstado() { return estado; }
    public void SetEstado(EstadoPago estado) { this.estado = estado; }
    public LocalDateTime GetFechaPago() { return FechaPago; }
}
