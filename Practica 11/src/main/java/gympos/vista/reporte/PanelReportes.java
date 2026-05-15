package gympos.vista.reporte;

import gympos.servicio.*;
import gympos.vista.componente.BotonIcono;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class PanelReportes extends VBox {

    private final ServicioReporte servicioReporte;
    private final ServicioCliente servicioCliente;
    private final ServicioMembresia servicioMembresia;
    private final ServicioPago servicioPago;

    private Label etiquetaEstado;
    private ProgressBar barra;
    private TextArea areaLog;

    public PanelReportes(ServicioReporte servicioReporte, ServicioCliente servicioCliente, ServicioMembresia servicioMembresia, ServicioPago servicioPago){
        this.servicioReporte = servicioReporte;
        this.servicioCliente = servicioCliente;
        this.servicioMembresia = servicioMembresia;
        this.servicioPago = servicioPago;
        setSpacing(12);
        setPadding(new Insets(15));
        getStyleClass().add("panel");
        getChildren().addAll(CrearTitulo(), CrearEstadisticas(), CrearAcciones(), CrearLog());
    }

    private Label CrearTitulo(){
        Label l = new Label("Generador de Reportes");
        l.setStyle("-fx-font-size:18px; -fx-font-weight:bold; -fx-text-fill:#f0a500;");
        return l;
    }

    private GridPane CrearEstadisticas(){
        int totalClientes = servicioCliente.GetTodos().size();
        long activas = servicioMembresia.GetTodas().stream().filter(m -> m.IsActiva()).count();
        double recaudado = servicioPago.GetTotalRecaudado();

        GridPane grid = new GridPane();
        grid.setHgap(30);
        grid.setVgap(8);
        grid.setPadding(new Insets(12));
        grid.getStyleClass().add("formulario");
        grid.addRow(0, Stat("Clientes registrados", String.valueOf(totalClientes)),
                       Stat("Membresias activas", String.valueOf(activas)),
                       Stat("Total recaudado", String.format("$%.2f", recaudado)));
        return grid;
    }

    private VBox Stat(String etiqueta, String valor){
        Label lbl = new Label(etiqueta);
        lbl.setStyle("-fx-text-fill:#a0a0c0; -fx-font-size:11px;");
        Label val = new Label(valor);
        val.setStyle("-fx-text-fill:#f0a500; -fx-font-size:20px; -fx-font-weight:bold;");
        VBox caja = new VBox(2, lbl, val);
        caja.setAlignment(Pos.CENTER_LEFT);
        return caja;
    }

    private VBox CrearAcciones(){
        barra = new ProgressBar(0);
        barra.setPrefWidth(400);
        barra.setVisible(false);

        etiquetaEstado = new Label("");
        etiquetaEstado.getStyleClass().add("etiqueta-estado");

        BotonIcono btnTxt = new BotonIcono(BotonIcono.TipoBoton.GUARDAR);
        btnTxt.setText("Generar Reporte TXT");
        btnTxt.setOnAction(e -> GenerarTxt(btnTxt));
        VBox caja = new VBox(10, new HBox(10, btnTxt), barra, etiquetaEstado);
        caja.setPadding(new Insets(5));
        return caja;
    }

    private TextArea CrearLog(){
        areaLog = new TextArea();
        areaLog.setEditable(false);
        areaLog.setPrefHeight(160);
        areaLog.setStyle("-fx-control-inner-background:#0f1a30; -fx-text-fill:#a0c0a0; -fx-font-family:monospace;");
        areaLog.setText("Sistema listo. Presiona 'Generar Reporte TXT' para comenzar.\n");
        return areaLog;
    }

    private void GenerarTxt(BotonIcono btn){
        btn.setDisable(true);
        barra.setVisible(true);
        barra.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        etiquetaEstado.setText("Generando reporte...");
        etiquetaEstado.getStyleClass().removeAll("estado-error");
        etiquetaEstado.getStyleClass().add("estado-exito");

        servicioReporte.GenerarTxtAsync(
            servicioCliente.GetTodos(),
            servicioMembresia.GetTodas(),
            servicioPago.GetTodos(),
            ruta -> {
                barra.setProgress(1.0);
                etiquetaEstado.setText("Reporte guardado: " + ruta);
                areaLog.appendText("Reporte generado: " + ruta + "\n");
                btn.setDisable(false);
            },
            error -> {
                barra.setVisible(false);
                etiquetaEstado.setText("Error: " + error);
                etiquetaEstado.getStyleClass().removeAll("estado-exito");
                etiquetaEstado.getStyleClass().add("estado-error");
                areaLog.appendText("ERROR: " + error + "\n");
                btn.setDisable(false);
            }
        );
    }
}
