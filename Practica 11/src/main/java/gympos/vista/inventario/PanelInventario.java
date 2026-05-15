package gympos.vista.inventario;

import gympos.excepcion.GymPOSException;
import gympos.modelo.equipo.Equipo;
import gympos.modelo.equipo.Equipo.EstadoEquipo;
import gympos.servicio.ServicioEquipo;
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

public class PanelInventario extends VBox {

    private final ServicioEquipo servicio;
    private TableView<Equipo> tabla;
    private ObservableList<Equipo> datos;
    private FilteredList<Equipo> filtrados;
    private TextField campoBusqueda;
    private TextField campoNombre;
    private TextField campoCategoria;
    private TextField campoCantidad;
    private ComboBox<EstadoEquipo> comboEstado;
    private Label etiquetaEstado;

    public PanelInventario(ServicioEquipo servicio){
        this.servicio = servicio;
        setSpacing(10);
        setPadding(new Insets(15));
        getStyleClass().add("panel");
        etiquetaEstado = new Label("");
        etiquetaEstado.getStyleClass().add("etiqueta-estado");
        campoBusqueda = new TextField();
        campoBusqueda.setPromptText("Filtrar por nombre o categoria...");
        campoBusqueda.getStyleClass().add("campo-busqueda");
        tabla = CrearTabla();
        getChildren().addAll(CrearTitulo(), CrearBarraBusqueda(), tabla, CrearFormulario(), etiquetaEstado);
        Refrescar();
    }

    private Label CrearTitulo(){
        Label l = new Label("Inventario de Equipos");
        l.setStyle("-fx-font-size:18px; -fx-font-weight:bold; -fx-text-fill:#f0a500;");
        return l;
    }

    private HBox CrearBarraBusqueda(){
        HBox.setHgrow(campoBusqueda, Priority.ALWAYS);
        HBox caja = new HBox(8, new Label("Buscar:"), campoBusqueda);
        caja.setAlignment(Pos.CENTER_LEFT);
        return caja;
    }

    @SuppressWarnings("unchecked")
    private TableView<Equipo> CrearTabla() {
        TableView<Equipo> t = new TableView<>();
        t.getStyleClass().add("tabla-principal");
        t.setPrefHeight(260);

        TableColumn<Equipo, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().GetId()).asObject());
        colId.setPrefWidth(50);

        TableColumn<Equipo, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().GetNombre()));
        colNombre.setPrefWidth(180);

        TableColumn<Equipo, String> colCategoria = new TableColumn<>("Categoria");
        colCategoria.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().GetCategoria()));
        colCategoria.setPrefWidth(120);

        TableColumn<Equipo, Integer> colCantidad = new TableColumn<>("Cantidad");
        colCantidad.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().GetCantidad()).asObject());
        colCantidad.setPrefWidth(80);

        TableColumn<Equipo, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().GetEstado().name()));
        colEstado.setPrefWidth(140);

        t.getColumns().addAll(colId, colNombre, colCategoria, colCantidad, colEstado);

        t.setOnMouseClicked(e -> {
            if(e.getClickCount() == 2){
                Equipo sel = t.getSelectionModel().getSelectedItem();
                if(sel != null){
                    campoNombre.setText(sel.GetNombre());
                    campoCategoria.setText(sel.GetCategoria());
                    campoCantidad.setText(String.valueOf(sel.GetCantidad()));
                    comboEstado.setValue(sel.GetEstado());
                }
            }
        });

        t.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.DELETE){
                Equipo sel = t.getSelectionModel().getSelectedItem();
                if(sel != null){
                    try{servicio.Eliminar(sel.GetId()); Refrescar();}
                    catch(GymPOSException ex) {MostrarError(ex.getMessage());}
                }
            }
        });

        return t;
    }

    private GridPane CrearFormulario(){
        campoNombre = new TextField(); campoNombre.setPromptText("Nombre equipo"); campoNombre.setPrefWidth(160);
        campoCategoria = new TextField(); campoCategoria.setPromptText("Categoria"); campoCategoria.setPrefWidth(120);
        campoCantidad = new TextField(); campoCantidad.setPromptText("Cantidad"); campoCantidad.setPrefWidth(80);
        comboEstado = new ComboBox<>();
        comboEstado.getItems().addAll(EstadoEquipo.values());
        comboEstado.setValue(EstadoEquipo.DISPONIBLE);

        BotonIcono btnAgregar = new BotonIcono(BotonIcono.TipoBoton.AGREGAR);
        btnAgregar.setOnAction(e -> AgregarEquipo());

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(8);
        grid.setPadding(new Insets(10));
        grid.getStyleClass().add("formulario");
        grid.addRow(0, new Label("Nombre:"), campoNombre, new Label("Categoria:"), campoCategoria);
        grid.addRow(1, new Label("Cantidad:"), campoCantidad, new Label("Estado:"), comboEstado);
        grid.add(new HBox(8, btnAgregar), 0, 2, 4, 1);
        return grid;
    }

    private void AgregarEquipo(){
        try{
            int cantidad = Integer.parseInt(campoCantidad.getText().trim());
            servicio.Agregar(campoNombre.getText(), campoCategoria.getText(), cantidad);
            MostrarExito("Equipo agregado.");
            Refrescar();
            campoNombre.clear(); campoCategoria.clear(); campoCantidad.clear();
        }catch(NumberFormatException e) {MostrarError("Cantidad invalida.");
        }catch(GymPOSException e) {MostrarError(e.getMessage());}
    }

    private void Refrescar(){
        datos = FXCollections.observableArrayList(servicio.GetTodos());
        if(filtrados == null){
            filtrados = new FilteredList<>(datos, p -> true);
            campoBusqueda.textProperty().addListener((obs, v, nuevo) ->
                filtrados.setPredicate(eq -> nuevo == null || nuevo.isBlank()
                    || eq.GetNombre().toLowerCase().contains(nuevo.toLowerCase())
                    || eq.GetCategoria().toLowerCase().contains(nuevo.toLowerCase()))
            );
            SortedList<Equipo> ordenados = new SortedList<>(filtrados);
            ordenados.comparatorProperty().bind(tabla.comparatorProperty());
            tabla.setItems(ordenados);
        }else{
            filtrados.setAll(datos);
        }
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
