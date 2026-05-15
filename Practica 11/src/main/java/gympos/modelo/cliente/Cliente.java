package gympos.modelo.cliente;

import java.io.Serializable;
import java.time.LocalDate;

public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String nombre;
    private String apellido;
    private String correo;
    private String telefono;
    private LocalDate FechaRegistro;
    private int puntos;
    private boolean activo;

    public Cliente(int id, String nombre, String apellido, String correo, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.telefono = telefono;
        this.FechaRegistro = LocalDate.now();
        this.puntos = 0;
        this.activo = true;
    }

    public int GetId() { return id; }
    public void SetId(int id) { this.id = id; }
    public String GetNombre() { return nombre; }
    public void SetNombre(String nombre) { this.nombre = nombre; }
    public String GetApellido() { return apellido; }
    public void SetApellido(String apellido) { this.apellido = apellido; }
    public String GetCorreo() { return correo; }
    public void SetCorreo(String correo) { this.correo = correo; }
    public String GetTelefono() { return telefono; }
    public void SetTelefono(String telefono) { this.telefono = telefono; }
    public LocalDate GetFechaRegistro() { return FechaRegistro; }
    public void SetFechaRegistro(LocalDate FechaRegistro) { this.FechaRegistro = FechaRegistro; }
    public int GetPuntos() { return puntos; }
    public void SetPuntos(int puntos) { this.puntos = puntos; }
    public boolean IsActivo() { return activo; }
    public void SetActivo(boolean activo) { this.activo = activo; }

    public void AgregarPuntos(int cantidad) { this.puntos += cantidad; }

    public String GetNombreCompleto() { return nombre + " " + apellido; }

    @Override
    public String toString() {
        return id + " | " + GetNombreCompleto() + " | " + correo;
    }
}
