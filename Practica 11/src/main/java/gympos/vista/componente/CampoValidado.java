package gympos.vista.componente;

import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;

import java.util.function.Predicate;

public class CampoValidado extends TextField {
    private Predicate<String> regla;
    private final String mensajeError;

    public CampoValidado(String prompt, String mensajeError, Predicate<String> regla) {
        super();
        this.regla = regla;
        this.mensajeError = mensajeError;
        setPromptText(prompt);
        setTooltip(new Tooltip(mensajeError));
        textProperty().addListener((obs, viejo, nuevo) -> Validar(nuevo));
    }

    public boolean Validar(String valor) {
        boolean valido = regla.test(valor);
        if (valido) {
            getStyleClass().removeAll("campo-invalido");
            getStyleClass().add("campo-valido");
        } else {
            getStyleClass().removeAll("campo-valido");
            getStyleClass().add("campo-invalido");
        }
        return valido;
    }

    public boolean EsValido() { return Validar(getText()); }
    public String GetMensajeError() { return mensajeError; }
}
