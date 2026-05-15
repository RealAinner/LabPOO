package gympos.vista.dialogo;

import gympos.modelo.pago.Pago.MetodoPago;
import gympos.servicio.ServicioPago;
import gympos.vista.componente.BotonIcono;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DialogoPago {
    private final Stage dialogo;
    private final ServicioPago servicioPago;
    private final int IdCliente;
    private final int IdMembresia;
    private final double monto;

    private Label etiquetaEstado;
    private ProgressIndicator indicador;

    public DialogoPago(Stage padre, ServicioPago servicioPago,
                       int IdCliente, int IdMembresia, double monto) {
        this.servicioPago = servicioPago;
        this.IdCliente = IdCliente;
        this.IdMembresia = IdMembresia;
        this.monto = monto;

        dialogo = new Stage(StageStyle.DECORATED);
        dialogo.initModality(Modality.APPLICATION_MODAL);
        dialogo.initOwner(padre);
        dialogo.setTitle("Procesar Pago");
        dialogo.setResizable(false);
        dialogo.setScene(CrearEscena());
    }

    private Scene CrearEscena() {
        Label lblMonto = new Label(String.format("Monto a cobrar: $%.2f", monto));
        lblMonto.getStyleClass().add("titulo-bienvenida");
        lblMonto.setStyle("-fx-font-size:20px;");

        ComboBox<MetodoPago> comboMetodo = new ComboBox<>();
        comboMetodo.getItems().addAll(MetodoPago.values());
        comboMetodo.setValue(MetodoPago.EFECTIVO);
        comboMetodo.setPrefWidth(200);

        indicador = new ProgressIndicator();
        indicador.setVisible(false);
        indicador.setPrefSize(40, 40);

        etiquetaEstado = new Label("");
        etiquetaEstado.setWrapText(true);

        BotonIcono btnPagar = new BotonIcono(BotonIcono.TipoBoton.GUARDAR);
        btnPagar.setText("Pagar Ahora");
        BotonIcono btnCerrar = new BotonIcono(BotonIcono.TipoBoton.CANCELAR);

        btnPagar.setOnAction(e -> {
            btnPagar.setDisable(true);
            indicador.setVisible(true);
            etiquetaEstado.setText("Procesando...");
            etiquetaEstado.setStyle("-fx-text-fill:#f0a500;");
            servicioPago.ProcesarAsync(IdCliente, IdMembresia, monto,
                comboMetodo.getValue(),
                pago -> {
                    indicador.setVisible(false);
                    etiquetaEstado.setText("Pago completado con exito.");
                    etiquetaEstado.setStyle("-fx-text-fill:#4caf50;");
                    btnCerrar.setText("Cerrar");
                },
                error -> {
                    indicador.setVisible(false);
                    etiquetaEstado.setText(error);
                    etiquetaEstado.setStyle("-fx-text-fill:#f44336;");
                    btnPagar.setDisable(false);
                }
            );
        });

        btnCerrar.setOnAction(e -> dialogo.close());

        VBox contenido = new VBox(15,
            lblMonto,
            new HBox(10, new Label("Metodo:"), comboMetodo),
            new HBox(10, indicador, etiquetaEstado),
            new HBox(10, btnPagar, btnCerrar)
        );
        contenido.setPadding(new Insets(25));
        contenido.setAlignment(Pos.CENTER_LEFT);
        contenido.getStyleClass().add("panel");

        Scene escena = new Scene(contenido, 380, 260);
        escena.getStylesheets().add(
            getClass().getResource("/css/estilos.css").toExternalForm()
        );
        return escena;
    }

    public void Mostrar() { dialogo.showAndWait(); }
}
