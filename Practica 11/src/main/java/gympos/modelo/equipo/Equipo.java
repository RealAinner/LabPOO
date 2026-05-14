package gympos.modelo.equipo;

import java.io.Serializable;

public class Equipo implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum EstadoEquipo { DISPONIBLE, EN_USO, MANTENIMIENTO, DADO_DE_BAJA }

    private int id;
    private String nombre;
    private String categoria;
    private EstadoEquipo estado;
    private int cantidad;

    public Equipo(int id, String nombre, String categoria, int cantidad) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.cantidad = cantidad;
        this.estado = EstadoEquipo.DISPONIBLE;
    }

    public int GetId() { return id; }
    public String GetNombre() { return nombre; }
    public void SetNombre(String nombre) { this.nombre = nombre; }
    public String GetCategoria() { return categoria; }
    public void SetCategoria(String categoria) { this.categoria = categoria; }
    public EstadoEquipo GetEstado() { return estado; }
    public void SetEstado(EstadoEquipo estado) { this.estado = estado; }
    public int GetCantidad() { return cantidad; }
    public void SetCantidad(int cantidad) { this.cantidad = cantidad; }
}
