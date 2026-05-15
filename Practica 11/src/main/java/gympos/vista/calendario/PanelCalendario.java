package gympos.vista.calendario;

import gympos.excepcion.GymPOSException;
import gympos.modelo.clase.ClaseGrupal;
import gympos.servicio.ServicioClase;
import gympos.vista.componente.BotonIcono;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PanelCalendario extends VBox {

    private final ServicioClase servicio;
    private TableView<ClaseGrupal> tabla;
    private ObservableList<ClaseGrupal> datos;
    private FilteredList<ClaseGrupal> filtrados;
    private TextField campoBusqueda;
    private TextField campoNombre;
    private TextField campoInstructor;
    private TextField campoFecha;
    private TextField campoCapacidad;
    private TextField campoIdClase;
    private TextField campoIdCliente;
    private Label etiquetaEstado;
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public PanelCalendario(ServicioClase servicio) {
        this.servicio = servicio;
        setSpacing(10);
        setPadding(new Insets(15));
        getStyleClass().add("panel");
        etiquetaEstado = new Label("");
        etiquetaEstado.getStyleClass().add("etiqueta-estado");
        campoBusqueda = new TextField();
        campoBusqueda.setPromptText("Filtrar por nombre o instructor...");
        campoBusqueda.getStyleClass().add("campo-busqueda");
        tabla = CrearTabla();
        getChildren().addAll(CrearTitulo(), CrearBarraBusqueda(), tabla,
                CrearFormularioClase(), CrearFormularioInscripcion(), etiquetaEstado);
        Refrescar();
    }

    private Label CrearTitulo() {
        Label l = new Label("Calendario de Clases Grupales");
        l.setStyle("-fx-font-size:18px; -fx-font-weight:bold; -fx-text-fill:#f0a500;");
        return l;
    }

    private HBox CrearBarraBusqueda() {
        HBox.setHgrow(campoBusqueda, Priority.ALWAYS);
        HBox caja = new HBox(8, new Label("Buscar:"), campoBusqueda);
        caja.setAlignment(Pos.CENTER_LEFT);
        return caja;
    }

    @SuppressWarnings("unchecked")
    private TableView<ClaseGrupal> CrearTabla() {
        TableView<ClaseGrupal> t = new TableView<>();
        t.getStyleClass().add("tabla-principal");
        t.setPrefHeight(220);

        TableColumn<ClaseGrupal, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().GetId()).asObject());
        colId.setPrefWidth(50);

        TableColumn<ClaseGrupal, String> colNombre = new TableColumn<>("Clase");
        colNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().GetNombre()));
        colNombre.setPrefWidth(130);

        TableColumn<ClaseGrupal, String> colInstructor = new TableColumn<>("Instructor");
        colInstructor.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().GetInstructor()));
        colInstructor.setPrefWidth(130);

        TableColumn<ClaseGrupal, String> colFecha = new TableColumn<>("Fecha y Hora");
        colFecha.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().GetFechaHora().format(FMT)));
        colFecha.setPrefWidth(140);

        TableColumn<ClaseGrupal, String> colLugares = new TableColumn<>("Inscritos/Cap.");
        colLugares.setCellValueFactory(c -> new SimpleStringProperty(
                c.getValue().GetInscritos().size() + "/" + c.getValue().GetCapacidad()));
        colLugares.setPrefWidth(110);

        t.getColumns().addAll(colId, colNombre, colInstructor, colFecha, colLugares);
        t.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                ClaseGrupal sel = t.getSelectionModel().getSelectedItem();
                if (sel != null && campoIdClase != null)
                    campoIdClase.setText(String.valueOf(sel.GetId()));
            }
        });
        return t;
    }

    private GridPane CrearFormularioClase() {
        campoNombre = new TextField(); campoNombre.setPromptText("Nombre clase"); campoNombre.setPrefWidth(140);
        campoInstructor = new TextField(); campoInstructor.setPromptText("Instructor"); campoInstructor.setPrefWidth(140);
        campoFecha = new TextField(); campoFecha.setPromptText("yyyy-MM-ddTHH:mm"); campoFecha.setPrefWidth(160);
        campoCapacidad = new TextField(); campoCapacidad.setPromptText("Capacidad"); campoCapacidad.setPrefWidth(80);

        BotonIcono btnAgregar = new BotonIcono(BotonIcono.TipoBoton.AGREGAR);
        btnAgregar.setText("Agregar Clase");
        btnAgregar.setOnAction(e -> AgregarClase());

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(8);
        grid.setPadding(new Insets(10));
        grid.getStyleClass().add("formulario");
        grid.addRow(0, new Label("Clase:"), campoNombre, new Label("Instructor:"), campoInstructor);
        grid.addRow(1, new Label("Fecha (ISO):"), campoFecha, new Label("Capacidad:"), campoCapacidad);
        grid.add(new HBox(btnAgregar), 0, 2, 4, 1);
        return grid;
    }

    private HBox CrearFormularioInscripcion() {
        campoIdClase = new TextField(); campoIdClase.setPromptText("ID Clase"); campoIdClase.setPrefWidth(80);
        campoIdCliente = new TextField(); campoIdCliente.setPromptText("ID Cliente"); campoIdCliente.setPrefWidth(80);
        BotonIcono btnInscribir = new BotonIcono(BotonIcono.TipoBoton.GUARDAR);
        btnInscribir.setText("Inscribir");
        btnInscribir.setOnAction(e -> Inscribir());
        HBox caja = new HBox(10, new Label("ID Clase:"), campoIdClase,
                new Label("ID Cliente:"), campoIdCliente, btnInscribir);
        caja.setAlignment(Pos.CENTER_LEFT);
        caja.setPadding(new Insets(5, 10, 5, 10));
        caja.getStyleClass().add("formulario");
        return caja;
    }

    private void AgregarClase() {
        try {
            int cap = Integer.parseInt(campoCapacidad.getText().trim());
            LocalDateTime fecha = LocalDateTime.parse(campoFecha.getText().trim());
            servicio.Agregar(campoNombre.getText(), campoInstructor.getText(), fecha, cap);
            MostrarExito("Clase agregada.");
            Refrescar();
        } catch (Exception e) { MostrarError("Datos invalidos. Fecha: 2026-06-01T09:00"); }
    }

    private void Inscribir() {
        try {
            servicio.Inscribir(Integer.parseInt(campoIdClase.getText().trim()),
                               Integer.parseInt(campoIdCliente.getText().trim()));
            MostrarExito("Inscripcion exitosa.");
            Refrescar();
        } catch (NumberFormatException e) { MostrarError("IDs invalidos.");
        } catch (GymPOSException e) { MostrarError(e.getMessage()); }
    }

    private void Refrescar() {
        if (datos == null) {
            datos = FXCollections.observableArrayList(servicio.GetTodas());
            filtrados = new FilteredList<>(datos, p -> true);
            campoBusqueda.textProperty().addListener((obs, v, nuevo) ->
                filtrados.setPredicate(cl -> nuevo == null || nuevo.isBlank()
                    || cl.GetNombre().toLowerCase().contains(nuevo.toLowerCase())
                    || cl.GetInstructor().toLowerCase().contains(nuevo.toLowerCase()))
            );
            SortedList<ClaseGrupal> ordenados = new SortedList<>(filtrados);
            ordenados.comparatorProperty().bind(tabla.comparatorProperty());
            tabla.setItems(ordenados);
        } else {
            datos.setAll(servicio.GetTodas());
        }
    }

    private void MostrarError(String msg) {
        etiquetaEstado.setText(msg);
        etiquetaEstado.getStyleClass().removeAll("estado-exito");
        etiquetaEstado.getStyleClass().add("estado-error");
    }
    private void MostrarExito(String msg) {
        etiquetaEstado.setText(msg);
        etiquetaEstado.getStyleClass().removeAll("estado-error");
        etiquetaEstado.getStyleClass().add("estado-exito");
    }
}
