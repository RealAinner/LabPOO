package gympos.vista.membresia;

import gympos.controlador.MembresiaController;
import gympos.modelo.membresia.Membresia;
import gympos.modelo.membresia.TipoMembresia;
import gympos.servicio.ServicioPago;
import gympos.vista.componente.BotonIcono;
import gympos.vista.dialogo.DialogoPago;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class PanelMembresias extends VBox implements VistaMembresia {

    private final MembresiaController controlador;
    private final ServicioPago servicioPago;
    private final Stage stage;

    private TableView<Membresia> tabla;
    private ObservableList<Membresia> datos;
    private FilteredList<Membresia> filtrados;

    private TextField campoBusqueda;
    private TextField campoIdCliente;
    private ComboBox<TipoMembresia> comboTipo;
    private Label etiquetaEstado;

    public PanelMembresias(MembresiaController controlador, ServicioPago servicioPago, Stage stage){
        this.controlador = controlador;
        this.servicioPago = servicioPago;
        this.stage = stage;
        controlador.SetVista(this);
        setSpacing(10);
        setPadding(new Insets(15));
        getStyleClass().add("panel");

        getChildren().addAll(CrearBarraBusqueda(), CrearTabla(), CrearFormulario(), CrearEtiquetaEstado());
        Refrescar(controlador.CargarTodas());
        VerificarNotificaciones();
    }

    private HBox CrearBarraBusqueda(){
        campoBusqueda = new TextField();
        campoBusqueda.setPromptText("Buscar por ID de cliente o tipo...");
        campoBusqueda.getStyleClass().add("campo-busqueda");
        HBox.setHgrow(campoBusqueda, Priority.ALWAYS);
        HBox caja = new HBox(8, new Label("Buscar:"), campoBusqueda);
        caja.setAlignment(Pos.CENTER_LEFT);
        return caja;
    }

    @SuppressWarnings("unchecked")
    private TableView<Membresia> CrearTabla(){
        tabla = new TableView<>();
        tabla.getStyleClass().add("tabla-principal");
        tabla.setPrefHeight(260);

        TableColumn<Membresia, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().GetId()).asObject());
        colId.setPrefWidth(50);

        TableColumn<Membresia, Integer> colCliente = new TableColumn<>("ID Cliente");
        colCliente.setCellValueFactory(c -> new javafx.beans.property.SimpleIntegerProperty(c.getValue().GetIdCliente()).asObject());
        colCliente.setPrefWidth(90);

        TableColumn<Membresia, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().GetTipo().GetNombre()));
        colTipo.setPrefWidth(100);

        TableColumn<Membresia, String> colPrecio = new TableColumn<>("Precio");
        colPrecio.setCellValueFactory(c -> new SimpleStringProperty(String.format("$%.2f", c.getValue().GetTipo().GetPrecioFinal())));
        colPrecio.setPrefWidth(90);

        TableColumn<Membresia, String> colFin = new TableColumn<>("Vence");
        colFin.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().GetFechaFin().toString()));
        colFin.setPrefWidth(110);

        TableColumn<Membresia, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().EstaVencida() ? "Vencida" : (c.getValue().IsActiva() ? "Activa" : "Cancelada")));
        colEstado.setPrefWidth(90);

        TableColumn<Membresia, String> colDias = new TableColumn<>("Dias rest.");
        colDias.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().DiasRestantes())));
        colDias.setPrefWidth(80);

        tabla.getColumns().addAll(colId, colCliente, colTipo, colPrecio, colFin, colEstado, colDias);
        return tabla;
    }

    private GridPane CrearFormulario(){
        campoIdCliente = new TextField();
        campoIdCliente.setPromptText("ID Cliente");
        campoIdCliente.setPrefWidth(120);

        comboTipo = new ComboBox<>();
        comboTipo.getItems().addAll(TipoMembresia.values());
        comboTipo.setValue(TipoMembresia.BASICO);

        BotonIcono btnRegistrar = new BotonIcono(BotonIcono.TipoBoton.AGREGAR);
        btnRegistrar.setText("Registrar");
        BotonIcono btnRenovar = new BotonIcono(BotonIcono.TipoBoton.EDITAR);
        btnRenovar.setText("Renovar");
        BotonIcono btnPagar = new BotonIcono(BotonIcono.TipoBoton.GUARDAR);
        btnPagar.setText("Pagar");
        BotonIcono btnCancelar = new BotonIcono(BotonIcono.TipoBoton.CANCELAR);
        btnCancelar.setText("Cancelar Mem.");

        btnRegistrar.setOnAction(e -> RegistrarMembresia());
        btnRenovar.setOnAction(e -> RenovarMembresia());
        btnCancelar.setOnAction(e -> CancelarMembresia());
        btnPagar.setOnAction(e -> AbrirDialogoPago());

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(8);
        grid.setPadding(new Insets(10));
        grid.getStyleClass().add("formulario");
        grid.addRow(0, new Label("ID Cliente:"), campoIdCliente, new Label("Plan:"), comboTipo);
        grid.add(new HBox(8, btnRegistrar, btnRenovar, btnPagar, btnCancelar), 0, 1, 4, 1);
        return grid;
    }

    private Label CrearEtiquetaEstado(){
        etiquetaEstado = new Label("");
        etiquetaEstado.getStyleClass().add("etiqueta-estado");
        return etiquetaEstado;
    }

    private void RegistrarMembresia(){
        try{
            controlador.Registrar(Integer.parseInt(campoIdCliente.getText().trim()), comboTipo.getValue());
        }catch(NumberFormatException e) {MostrarError("ID de cliente invalido.");}
    }

    private void RenovarMembresia(){
        try{
            controlador.Renovar(Integer.parseInt(campoIdCliente.getText().trim()));
        }catch(NumberFormatException e) {MostrarError("ID de cliente invalido.");}
    }

    private void CancelarMembresia(){
        try{
            controlador.Cancelar(Integer.parseInt(campoIdCliente.getText().trim()));
        }catch(NumberFormatException e) {MostrarError("ID de cliente invalido.");}
    }

    private void AbrirDialogoPago(){
        try{
            int id = Integer.parseInt(campoIdCliente.getText().trim());
            new DialogoPago(stage, servicioPago, id, id, comboTipo.getValue().GetPrecioFinal()).Mostrar();
        } catch (NumberFormatException e) { MostrarError("ID de cliente invalido."); }
    }

    private void VerificarNotificaciones(){
        List<Membresia> proximas = controlador.GetProximasVencer();
        if(!proximas.isEmpty()){
            Alert alerta = new Alert(Alert.AlertType.WARNING);
            alerta.setTitle("Membresias por vencer");
            alerta.setHeaderText(proximas.size() + " membresia(s) vencen pronto");
            StringBuilder sb = new StringBuilder();
            proximas.forEach(m -> sb.append("Cliente ").append(m.GetIdCliente()).append(" - ").append(m.DiasRestantes()).append(" dias\n"));
            alerta.setContentText(sb.toString());
            alerta.show();
        }
    }

    @Override
    public void Refrescar(List<Membresia> membresias){
        datos = FXCollections.observableArrayList(membresias);
        if(filtrados == null){
            filtrados = new FilteredList<>(datos, p -> true);
            campoBusqueda.textProperty().addListener((obs, viejo, nuevo) ->
                filtrados.setPredicate(m -> {
                    if(nuevo == null || nuevo.isBlank()) return true;
                    String f = nuevo.toLowerCase();
                    return String.valueOf(m.GetIdCliente()).contains(f)
                        || m.GetTipo().GetNombre().toLowerCase().contains(f);
                })
            );
            SortedList<Membresia> ordenados = new SortedList<>(filtrados);
            ordenados.comparatorProperty().bind(tabla.comparatorProperty());
            tabla.setItems(ordenados);
        }else{
            filtrados.setAll(datos);
        }
    }

    @Override
    public void MostrarError(String mensaje){
        etiquetaEstado.setText(mensaje);
        etiquetaEstado.getStyleClass().removeAll("estado-exito");
        etiquetaEstado.getStyleClass().add("estado-error");
    }

    @Override
    public void MostrarExito(String mensaje){
        etiquetaEstado.setText(mensaje);
        etiquetaEstado.getStyleClass().removeAll("estado-error");
        etiquetaEstado.getStyleClass().add("estado-exito");
    }
}
