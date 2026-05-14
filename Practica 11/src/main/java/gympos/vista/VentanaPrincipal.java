package gympos.vista;

import gympos.config.Configuracion;
import gympos.controlador.ClienteController;
import gympos.servicio.ServicioCliente;
import gympos.vista.cliente.PanelClientes;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class VentanaPrincipal {

    private final Stage stage;
    private BorderPane raiz;
    private Label etiquetaCentro;

    public VentanaPrincipal(Stage stage) {
        this.stage = stage;
    }

    public void Mostrar() {
        raiz = new BorderPane();
        raiz.setTop(CrearMenu());
        raiz.setCenter(CrearBienvenida());

        Scene escena = new Scene(raiz, 900, 620);
        escena.getStylesheets().add(
            getClass().getResource("/css/estilos.css").toExternalForm()
        );

        stage.setTitle(Configuracion.GetInstancia().Get("gym.nombre") + " — GymPOS");
        stage.setScene(escena);
        stage.setMinWidth(750);
        stage.setMinHeight(500);
        stage.show();
    }

    private MenuBar CrearMenu() {
        Menu menuClientes = new Menu("Clientes");
        MenuItem miGestion = new MenuItem("Gestion de Clientes");
        miGestion.setOnAction(e -> MostrarPanel(new PanelClientes(
            new ClienteController(new ServicioCliente()))));
        menuClientes.getItems().add(miGestion);

        Menu menuMembresias = new Menu("Membresias");
        MenuItem miMembresias = new MenuItem("Sistema de Membresias");
        miMembresias.setOnAction(e -> MostrarProximamente("Membresias"));
        menuMembresias.getItems().add(miMembresias);

        Menu menuPagos = new Menu("Pagos");
        MenuItem miPagos = new MenuItem("Procesador de Pagos");
        miPagos.setOnAction(e -> MostrarProximamente("Pagos"));
        menuPagos.getItems().add(miPagos);

        Menu menuAcceso = new Menu("Acceso");
        MenuItem miAcceso = new MenuItem("Control de Acceso");
        miAcceso.setOnAction(e -> MostrarProximamente("Control de Acceso"));
        menuAcceso.getItems().add(miAcceso);

        Menu menuReportes = new Menu("Reportes");
        MenuItem miReportes = new MenuItem("Generar Reporte");
        miReportes.setOnAction(e -> MostrarProximamente("Reportes"));
        menuReportes.getItems().add(miReportes);

        Menu menuAyuda = new Menu("Ayuda");
        MenuItem miAcerca = new MenuItem("Acerca de...");
        miAcerca.setOnAction(e -> MostrarAcercaDe());
        menuAyuda.getItems().add(miAcerca);

        MenuBar barra = new MenuBar(menuClientes, menuMembresias, menuPagos, menuAcceso, menuReportes, menuAyuda);
        barra.getStyleClass().add("barra-menu");
        return barra;
    }

    private VBox CrearBienvenida() {
        String nombre = Configuracion.GetInstancia().Get("gym.nombre");
        Label titulo = new Label(nombre);
        titulo.getStyleClass().add("titulo-bienvenida");
        Label sub = new Label("Sistema de Gestion — GymPOS");
        sub.getStyleClass().add("subtitulo-bienvenida");
        VBox caja = new VBox(15, titulo, sub);
        caja.setAlignment(javafx.geometry.Pos.CENTER);
        caja.setPadding(new Insets(40));
        return caja;
    }

    private void MostrarPanel(javafx.scene.Node panel) {
        raiz.setCenter(panel);
    }

    private void MostrarProximamente(String modulo) {
        Label lbl = new Label(modulo + "\n(Disponible en siguiente ZIP)");
        lbl.getStyleClass().add("subtitulo-bienvenida");
        lbl.setAlignment(javafx.geometry.Pos.CENTER);
        VBox caja = new VBox(lbl);
        caja.setAlignment(javafx.geometry.Pos.CENTER);
        raiz.setCenter(caja);
    }

    private void MostrarAcercaDe() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle("Acerca de GymPOS");
        alerta.setHeaderText(Configuracion.GetInstancia().Get("gym.nombre"));
        alerta.setContentText("GymPOS v" + Configuracion.GetInstancia().Get("gym.version")
                + "\nSistema de gestion de gimnasio.\nDesarrollado con JavaFX.");
        alerta.showAndWait();
    }
}
