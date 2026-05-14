package gympos.modelo.membresia;

import java.io.Serializable;
import java.time.LocalDate;

public class Membresia implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int IdCliente;
    private TipoMembresia tipo;
    private LocalDate FechaInicio;
    private LocalDate FechaFin;
    private boolean activa;
    private boolean RenovacionAutomatica;

    public Membresia(int id, int IdCliente, TipoMembresia tipo){
        this.id = id;
        this.IdCliente = IdCliente;
        this.tipo = tipo;
        this.FechaInicio = LocalDate.now();
        this.FechaFin = FechaInicio.plusMonths(1);
        this.activa = true;
        this.RenovacionAutomatica = false;
    }

    public int GetId() { return id; }
    public void SetId(int id) { this.id = id; }
    public int GetIdCliente() { return IdCliente; }
    public TipoMembresia GetTipo() { return tipo; }
    public void SetTipo(TipoMembresia tipo) { this.tipo = tipo; }
    public LocalDate GetFechaInicio() { return FechaInicio; }
    public LocalDate GetFechaFin() { return FechaFin; }
    public void SetFechaFin(LocalDate FechaFin) { this.FechaFin = FechaFin; }
    public boolean IsActiva() { return activa; }
    public void SetActiva(boolean activa) { this.activa = activa; }
    public boolean IsRenovacionAutomatica() { return RenovacionAutomatica; }
    public void SetRenovacionAutomatica(boolean val) { this.RenovacionAutomatica = val; }

    public boolean EstaVencida(){
        return LocalDate.now().isAfter(FechaFin);
    }

    public void Renovar(){
        this.FechaInicio = LocalDate.now();
        this.FechaFin = FechaInicio.plusMonths(1);
        this.activa = true;
    }

    public long DiasRestantes(){
        return java.time.temporal.ChronoUnit.DAYS.between(LocalDate.now(), FechaFin);
    }
}
