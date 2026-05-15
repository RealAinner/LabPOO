package gympos.vista;

import gympos.config.Configuracion;
import gympos.controlador.ClienteController;
import gympos.controlador.MembresiaController;
import gympos.servicio.*;
import gympos.vista.acceso.PanelAcceso;
import gympos.vista.calendario.PanelCalendario;
import gympos.vista.cliente.PanelClientes;
import gympos.vista.inventario.PanelInventario;
import gympos.vista.membresia.PanelMembresias;
import gympos.vista.reporte.PanelReportes;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class VentanaPrincipal {

    private final Stage stage;
    private BorderPane raiz;
    private final ServicioPago servicioPago = new ServicioPago();
    private final ServicioReporte servicioReporte = new ServicioReporte();

    public VentanaPrincipal(Stage stage) {this.stage = stage;}

    public void Mostrar(){
        raiz = new BorderPane();
        raiz.setTop(CrearMenu());
        raiz.setCenter(CrearBienvenida());

        Scene escena = new Scene(raiz, 980, 680);
        escena.getStylesheets().add(getClass().getResource("/css/estilos.css").toExternalForm());
        stage.setTitle(Configuracion.GetInstancia().Get("gym.nombre") + " - GymPOS");
        stage.setScene(escena);
        stage.setMinWidth(780);
        stage.setMinHeight(500);
        stage.setOnCloseRequest(e -> { servicioPago.Cerrar(); servicioReporte.Cerrar(); });
        stage.show();
    }

    private MenuBar CrearMenu(){
        Menu menuClientes = new Menu("Clientes");
        MenuItem miGestion = new MenuItem("Gestion de Clientes");
        miGestion.setOnAction(e -> raiz.setCenter(new PanelClientes(new ClienteController(new ServicioCliente()))));
        menuClientes.getItems().add(miGestion);

        Menu menuMembresias = new Menu("Membresias");
        MenuItem miMembresias = new MenuItem("Sistema de Membresias");
        miMembresias.setOnAction(e -> raiz.setCenter(new PanelMembresias(new MembresiaController(new ServicioMembresia()), servicioPago, stage)));
        menuMembresias.getItems().add(miMembresias);

        Menu menuAcceso = new Menu("Acceso");
        MenuItem miAcceso = new MenuItem("Control de Acceso");
        miAcceso.setOnAction(e -> raiz.setCenter(new PanelAcceso(new ServicioAcceso())));
        menuAcceso.getItems().add(miAcceso);

        Menu menuInventario = new Menu("Inventario");
        MenuItem miEquipos = new MenuItem("Equipos");
        miEquipos.setOnAction(e -> raiz.setCenter(new PanelInventario(new ServicioEquipo())));
        MenuItem miClases = new MenuItem("Clases Grupales");
        miClases.setOnAction(e -> raiz.setCenter(new PanelCalendario(new ServicioClase())));
        menuInventario.getItems().addAll(miEquipos, miClases);

        Menu menuReportes = new Menu("Reportes");
        MenuItem miReportes = new MenuItem("Generar Reporte");
        miReportes.setOnAction(e -> raiz.setCenter(new PanelReportes(servicioReporte, new ServicioCliente(), new ServicioMembresia(), servicioPago)));
        menuReportes.getItems().add(miReportes);

        Menu menuAyuda = new Menu("Ayuda");
        MenuItem miAcerca = new MenuItem("Acerca de...");
        miAcerca.setOnAction(e -> MostrarAcercaDe());
        menuAyuda.getItems().add(miAcerca);

        MenuBar barra = new MenuBar(menuClientes, menuMembresias, menuAcceso, menuInventario, menuReportes, menuAyuda);
        barra.getStyleClass().add("barra-menu");
        return barra;
    }

    private VBox CrearBienvenida(){
        Label titulo = new Label(Configuracion.GetInstancia().Get("gym.nombre"));
        titulo.getStyleClass().add("titulo-bienvenida");
        Label sub = new Label("Sistema de Gestion - GymPOS");
        sub.getStyleClass().add("subtitulo-bienvenida");
        VBox caja = new VBox(15, titulo, sub);
        caja.setAlignment(Pos.CENTER);
        caja.setPadding(new Insets(40));
        return caja;
    }

    private void MostrarAcercaDe(){
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle("Acerca de GymPOS");
        a.setHeaderText(Configuracion.GetInstancia().Get("gym.nombre"));
        a.setContentText("GymPOS v" + Configuracion.GetInstancia().Get("gym.version") + "\nSistema de gestion de gimnasio.\nDesarrollado con JavaFX.");
        a.showAndWait();
    }
}
