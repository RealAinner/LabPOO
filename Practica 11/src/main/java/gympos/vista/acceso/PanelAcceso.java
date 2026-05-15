package gympos.vista.acceso;

import gympos.excepcion.GymPOSException;
import gympos.modelo.acceso.RegistroAcceso;
import gympos.servicio.ServicioAcceso;
import gympos.vista.componente.BotonIcono;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;

import java.time.format.DateTimeFormatter;

public class PanelAcceso extends VBox {

    private final ServicioAcceso servicio;
    private TableView<RegistroAcceso> tabla;
    private ObservableList<RegistroAcceso> datos;
    private FilteredList<RegistroAcceso> filtrados;
    private TextField campoId;
    private TextField campoBusqueda;
    private Label etiquetaAforo;
    private Label etiquetaEstado;
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM HH:mm");

    public PanelAcceso(ServicioAcceso servicio){
        this.servicio = servicio;
        setSpacing(10);
        setPadding(new Insets(15));
        getStyleClass().add("panel");
        etiquetaAforo = new Label();
        etiquetaAforo.setStyle("-fx-text-fill:#a0a0c0;");
        etiquetaEstado = new Label("");
        etiquetaEstado.getStyleClass().add("etiqueta-estado");
        campoBusqueda = new TextField();
        campoBusqueda.setPromptText("Filtrar por ID cliente...");
        campoBusqueda.getStyleClass().add("campo-busqueda");
        campoId = new TextField();
        campoId.setPromptText("ID Cliente");
        campoId.setPrefWidth(120);
        tabla = CrearTabla();
        getChildren().addAll(CrearEncabezado(), CrearBarraBusqueda(), tabla, CrearFormulario(), etiquetaEstado);
        Refrescar();
    }

    private HBox CrearEncabezado(){
        Label titulo = new Label("Control de Acceso");
        titulo.setStyle("-fx-font-size:18px; -fx-font-weight:bold; -fx-text-fill:#f0a500;");
        Region sep = new Region();
        HBox.setHgrow(sep, Priority.ALWAYS);
        HBox caja = new HBox(titulo, sep, etiquetaAforo);
        caja.setAlignment(Pos.CENTER_LEFT);
        return caja;
    }

    private HBox CrearBarraBusqueda(){
        HBox.setHgrow(campoBusqueda, Priority.ALWAYS);
        HBox caja = new HBox(8, new Label("Buscar:"), campoBusqueda);
        caja.setAlignment(Pos.CENTER_LEFT);
        return caja;
    }

    @SuppressWarnings("unchecked")
    private TableView<RegistroAcceso> CrearTabla(){
        TableView<RegistroAcceso> t = new TableView<>();
        t.getStyleClass().add("tabla-principal");
        t.setPrefHeight(280);

        TableColumn<RegistroAcceso, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().GetId()).asObject());
        colId.setPrefWidth(50);

        TableColumn<RegistroAcceso, Integer> colCliente = new TableColumn<>("ID Cliente");
        colCliente.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().GetIdCliente()).asObject());
        colCliente.setPrefWidth(90);

        TableColumn<RegistroAcceso, String> colEntrada = new TableColumn<>("Entrada");
        colEntrada.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().GetEntrada().format(FMT)));
        colEntrada.setPrefWidth(120);

        TableColumn<RegistroAcceso, String> colSalida = new TableColumn<>("Salida");
        colSalida.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().GetSalida() != null ? c.getValue().GetSalida().format(FMT) : "Adentro"));
        colSalida.setPrefWidth(120);

        TableColumn<RegistroAcceso, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().EstaAdentro() ? "En gimnasio" : "Salio"));
        colEstado.setPrefWidth(100);

        t.getColumns().addAll(colId, colCliente, colEntrada, colSalida, colEstado);
        t.setOnMouseClicked(e -> {
            if(e.getClickCount() == 2){
                RegistroAcceso sel = t.getSelectionModel().getSelectedItem();
                if(sel != null) campoId.setText(String.valueOf(sel.GetIdCliente()));
            }
        });
        return t;
    }

    private GridPane CrearFormulario(){
        campoId.setOnKeyPressed(e -> { if (e.getCode() == KeyCode.ENTER) RegistrarEntrada(); });
        BotonIcono btnEntrada = new BotonIcono(BotonIcono.TipoBoton.AGREGAR);
        btnEntrada.setText("Entrada");
        BotonIcono btnSalida = new BotonIcono(BotonIcono.TipoBoton.EDITAR);
        btnSalida.setText("Salida");
        btnEntrada.setOnAction(e -> RegistrarEntrada());
        btnSalida.setOnAction(e -> RegistrarSalida());
        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(8);
        grid.setPadding(new Insets(10));
        grid.getStyleClass().add("formulario");
        grid.addRow(0, new Label("ID Cliente:"), campoId, btnEntrada, btnSalida);
        return grid;
    }

    private void RegistrarEntrada(){
        try{
            servicio.RegistrarEntrada(Integer.parseInt(campoId.getText().trim()));
            MostrarExito("Entrada registrada.");
            Refrescar();
        }catch(NumberFormatException e) {MostrarError("ID invalido.");
        }catch(GymPOSException e) {MostrarError(e.getMessage());}
    }

    private void RegistrarSalida(){
        try{
            servicio.RegistrarSalida(Integer.parseInt(campoId.getText().trim()));
            MostrarExito("Salida registrada.");
            Refrescar();
        }catch(NumberFormatException e) {MostrarError("ID invalido.");
        }catch(GymPOSException e) {MostrarError(e.getMessage());}
    }

    private void Refrescar(){
        datos = FXCollections.observableArrayList(servicio.GetTodos());
        if(filtrados == null){
            filtrados = new FilteredList<>(datos, p -> true);
            campoBusqueda.textProperty().addListener((obs, v, nuevo) ->
                filtrados.setPredicate(r -> nuevo == null || nuevo.isBlank()
                    || String.valueOf(r.GetIdCliente()).contains(nuevo.trim()))
            );
            SortedList<RegistroAcceso> ordenados = new SortedList<>(filtrados);
            ordenados.comparatorProperty().bind(tabla.comparatorProperty());
            tabla.setItems(ordenados);
        }else{
            filtrados.setAll(datos);
        }
        etiquetaAforo.setText("Aforo: " + servicio.GetAforo() + " personas");
    }

    private void MostrarError(String msg){
        etiquetaEstado.setText(msg);
        etiquetaEstado.getStyleClass().removeAll("estado-exito");
        etiquetaEstado.getStyleClass().add("estado-error");
    }
    private void MostrarExito(String msg){
        etiquetaEstado.setText(msg);
        etiquetaEstado.getStyleClass().removeAll("estado-error");
        etiquetaEstado.getStyleClass().add("estado-exito");
    }
}
