package gympos.controlador;

import gympos.excepcion.ClienteNoEncontradoException;
import gympos.excepcion.GymPOSException;
import gympos.modelo.membresia.Membresia;
import gympos.modelo.membresia.TipoMembresia;
import gympos.servicio.ServicioMembresia;
import gympos.vista.membresia.VistaMembresia;

import java.util.List;

public class MembresiaController {
    private final ServicioMembresia servicio;
    private VistaMembresia vista;

    public MembresiaController(ServicioMembresia servicio){
        this.servicio = servicio;
    }

    public void SetVista(VistaMembresia vista) {this.vista = vista;}

    public List<Membresia> CargarTodas() {return servicio.GetTodas();}

    public void Registrar(int IdCliente, TipoMembresia tipo){
        try{
            servicio.Registrar(IdCliente, tipo);
            vista.MostrarExito("Membresia registrada.");
            vista.Refrescar(servicio.GetTodas());
        }catch(GymPOSException e){
            vista.MostrarError(e.getMessage());
        }
    }

    public void Renovar(int IdCliente){
        try{
            servicio.Renovar(IdCliente);
            vista.MostrarExito("Membresia renovada.");
            vista.Refrescar(servicio.GetTodas());
        }catch(ClienteNoEncontradoException e){
            vista.MostrarError(e.getMessage());
        }
    }

    public void Cancelar(int IdCliente){
        try{
            servicio.Cancelar(IdCliente);
            vista.MostrarExito("Membresia cancelada.");
            vista.Refrescar(servicio.GetTodas());
        }catch(ClienteNoEncontradoException e){
            vista.MostrarError(e.getMessage());
        }
    }

    public List<Membresia> GetProximasVencer(){
        int dias = gympos.config.Configuracion.GetInstancia().GetInt("notificacion.diasAntes");
        return servicio.GetProximasAVencer(dias);
    }
}
