package gympos.modelo.acceso;

import java.io.Serializable;
import java.time.LocalDateTime;

public class RegistroAcceso implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private int IdCliente;
    private LocalDateTime entrada;
    private LocalDateTime salida;

    public RegistroAcceso(int id, int IdCliente){
        this.id = id;
        this.IdCliente = IdCliente;
        this.entrada = LocalDateTime.now();
    }

    public int GetId() {return id;}
    public int GetIdCliente() {return IdCliente;}
    public LocalDateTime GetEntrada() {return entrada;}
    public LocalDateTime GetSalida() {return salida;}
    public void SetSalida(LocalDateTime salida) {this.salida = salida;}

    public boolean EstaAdentro() {return salida == null;}
}
