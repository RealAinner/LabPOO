package gympos.vista.componente;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;

public class BotonIcono extends Button {
    public enum TipoBoton {AGREGAR, EDITAR, ELIMINAR, GUARDAR, CANCELAR}

    public BotonIcono(TipoBoton tipo){
        super();
        String texto;
        String estilo;
        switch(tipo){
            case AGREGAR -> {texto = "+ Agregar";  estilo = "boton-agregar";}
            case EDITAR -> {texto = "Editar";     estilo = "boton-editar";}
            case ELIMINAR -> {texto = "Eliminar";   estilo = "boton-eliminar";}
            case GUARDAR -> {texto = "Guardar";    estilo = "boton-guardar";}
            default -> {texto = "Cancelar";   estilo = "boton-cancelar";}
        }
        setText(texto);
        getStyleClass().addAll("boton-icono", estilo);
        setTooltip(new Tooltip(texto));
    }
}
