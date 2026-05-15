package gympos.vista.cliente;

import gympos.controlador.ClienteController;
import gympos.modelo.cliente.Cliente;
import gympos.util.Validador;
import gympos.vista.componente.BotonIcono;
import gympos.vista.componente.CampoValidado;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;

import java.util.List;

public class PanelClientes extends VBox implements VistaCliente {

    private final ClienteController controlador;

    private TableView<Cliente> tabla;
    private ObservableList<Cliente> datos;
    private FilteredList<Cliente> filtrados;

    private CampoValidado campoNombre;
    private CampoValidado campoApellido;
    private CampoValidado campoCorreo;
    private CampoValidado campoTelefono;
    private TextField campoBusqueda;

    private Label etiquetaEstado;
    private int IdEditando = -1;

    public PanelClientes(ClienteController controlador) {
        this.controlador = controlador;
        controlador.SetVista(this);
        setSpacing(10);
        setPadding(new Insets(15));
        getStyleClass().add("panel");

        getChildren().addAll(
            CrearBarraBusqueda(),
            CrearTabla(),
            CrearFormulario(),
            CrearEtiquetaEstado()
        );

        Refrescar(controlador.CargarTodos());
    }

    private HBox CrearBarraBusqueda() {
        campoBusqueda = new TextField();
        campoBusqueda.setPromptText("Buscar por nombre, apellido o correo...");
        campoBusqueda.getStyleClass().add("campo-busqueda");
        HBox.setHgrow(campoBusqueda, Priority.ALWAYS);
        HBox caja = new HBox(new Label("Buscar:"), campoBusqueda);
        caja.setSpacing(8);
        caja.setAlignment(Pos.CENTER_LEFT);
        return caja;
    }

    @SuppressWarnings("unchecked")
    private TableView<Cliente> CrearTabla() {
        tabla = new TableView<>();
        tabla.getStyleClass().add("tabla-principal");
        tabla.setPrefHeight(280);

        TableColumn<Cliente, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().GetId()).asObject());
        colId.setPrefWidth(50);

        TableColumn<Cliente, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().GetNombreCompleto()));
        colNombre.setPrefWidth(180);

        TableColumn<Cliente, String> colCorreo = new TableColumn<>("Correo");
        colCorreo.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().GetCorreo()));
        colCorreo.setPrefWidth(200);

        TableColumn<Cliente, String> colTelefono = new TableColumn<>("Telefono");
        colTelefono.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().GetTelefono()));
        colTelefono.setPrefWidth(120);

        TableColumn<Cliente, Integer> colPuntos = new TableColumn<>("Puntos");
        colPuntos.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().GetPuntos()).asObject());
        colPuntos.setPrefWidth(80);

        tabla.getColumns().addAll(colId, colNombre, colCorreo, colTelefono, colPuntos);

        tabla.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) CargarEnFormulario();
        });

        tabla.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.DELETE) ConfirmarEliminar();
            if (e.getCode() == KeyCode.ENTER) CargarEnFormulario();
        });

        return tabla;
    }

    private GridPane CrearFormulario() {
        campoNombre = new CampoValidado("Nombre", "Minimo 2 caracteres",
                s -> Validador.EsTextoValido(s, 2, 50));
        campoApellido = new CampoValidado("Apellido", "Minimo 2 caracteres",
                s -> Validador.EsTextoValido(s, 2, 50));
        campoCorreo = new CampoValidado("correo@ejemplo.com", "Formato invalido",
                Validador::EsCorreoValido);
        campoTelefono = new CampoValidado("10 digitos", "Exactamente 10 digitos numericos",
                Validador::EsTelefonoValido);

        BotonIcono btnGuardar = new BotonIcono(BotonIcono.TipoBoton.GUARDAR);
        BotonIcono btnCancelar = new BotonIcono(BotonIcono.TipoBoton.CANCELAR);

        btnGuardar.setOnAction(e -> Guardar());
        btnCancelar.setOnAction(e -> LimpiarFormulario());
        btnGuardar.setOnKeyPressed(e -> { if (e.getCode() == KeyCode.ENTER) Guardar(); });

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(8);
        grid.setPadding(new Insets(10));
        grid.getStyleClass().add("formulario");

        grid.addRow(0, new Label("Nombre:"), campoNombre, new Label("Apellido:"), campoApellido);
        grid.addRow(1, new Label("Correo:"), campoCorreo, new Label("Telefono:"), campoTelefono);
        grid.add(new HBox(10, btnGuardar, btnCancelar), 0, 2, 4, 1);

        return grid;
    }

    private Label CrearEtiquetaEstado() {
        etiquetaEstado = new Label("");
        etiquetaEstado.getStyleClass().add("etiqueta-estado");
        return etiquetaEstado;
    }

    private void Guardar() {
        if (!campoNombre.EsValido() || !campoApellido.EsValido()
                || !campoCorreo.EsValido() || !campoTelefono.EsValido()) {
            MostrarError("Corrige los campos marcados antes de guardar.");
            return;
        }
        if (IdEditando == -1) {
            controlador.Agregar(campoNombre.getText(), campoApellido.getText(),
                    campoCorreo.getText(), campoTelefono.getText());
        } else {
            controlador.Actualizar(IdEditando, campoNombre.getText(), campoApellido.getText(),
                    campoCorreo.getText(), campoTelefono.getText());
        }
        LimpiarFormulario();
    }

    private void CargarEnFormulario() {
        Cliente sel = tabla.getSelectionModel().getSelectedItem();
        if (sel == null) return;
        IdEditando = sel.GetId();
        campoNombre.setText(sel.GetNombre());
        campoApellido.setText(sel.GetApellido());
        campoCorreo.setText(sel.GetCorreo());
        campoTelefono.setText(sel.GetTelefono());
    }

    private void ConfirmarEliminar() {
        Cliente sel = tabla.getSelectionModel().getSelectedItem();
        if (sel == null) return;
        Alert alerta = new Alert(Alert.AlertType.CONFIRMATION);
        alerta.setTitle("Confirmar eliminacion");
        alerta.setHeaderText("Eliminar cliente");
        alerta.setContentText("Deseas eliminar a " + sel.GetNombreCompleto() + "?");
        alerta.showAndWait().ifPresent(r -> {
            if (r == ButtonType.OK) controlador.Eliminar(sel.GetId());
        });
    }

    private void LimpiarFormulario() {
        IdEditando = -1;
        campoNombre.clear(); campoApellido.clear();
        campoCorreo.clear(); campoTelefono.clear();
        campoNombre.getStyleClass().removeAll("campo-valido", "campo-invalido");
        campoApellido.getStyleClass().removeAll("campo-valido", "campo-invalido");
        campoCorreo.getStyleClass().removeAll("campo-valido", "campo-invalido");
        campoTelefono.getStyleClass().removeAll("campo-valido", "campo-invalido");
        etiquetaEstado.setText("");
    }

    @Override
    public void Refrescar(List<Cliente> clientes) {
        if (datos == null) {
            datos = FXCollections.observableArrayList(clientes);
            filtrados = new FilteredList<>(datos, p -> true);
            campoBusqueda.textProperty().addListener((obs, viejo, nuevo) ->
                filtrados.setPredicate(c -> {
                    if (nuevo == null || nuevo.isBlank()) return true;
                    String f = nuevo.toLowerCase();
                    return c.GetNombre().toLowerCase().contains(f)
                        || c.GetApellido().toLowerCase().contains(f)
                        || c.GetCorreo().toLowerCase().contains(f);
                })
            );
            SortedList<Cliente> ordenados = new SortedList<>(filtrados);
            ordenados.comparatorProperty().bind(tabla.comparatorProperty());
            tabla.setItems(ordenados);
        } else {
            datos.setAll(clientes);
        }
    }

    @Override
    public void MostrarError(String mensaje) {
        etiquetaEstado.setText(mensaje);
        etiquetaEstado.getStyleClass().removeAll("estado-exito");
        etiquetaEstado.getStyleClass().add("estado-error");
    }

    @Override
    public void MostrarExito(String mensaje) {
        etiquetaEstado.setText(mensaje);
        etiquetaEstado.getStyleClass().removeAll("estado-error");
        etiquetaEstado.getStyleClass().add("estado-exito");
    }
}
