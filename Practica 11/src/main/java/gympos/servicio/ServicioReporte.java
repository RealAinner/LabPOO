package gympos.servicio;

import gympos.modelo.cliente.Cliente;
import gympos.modelo.membresia.Membresia;
import gympos.modelo.pago.Pago;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class ServicioReporte {
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm");
    private final ExecutorService ejecutor = Executors.newSingleThreadExecutor();

    public void GenerarTxtAsync(List<Cliente> clientes, List<Membresia> membresias, List<Pago> pagos, Consumer<String> alTerminar, Consumer<String> alError){
        ejecutor.submit(() -> {
            try{
                new File("reportes").mkdirs();
                String nombre = "reportes/reporte_" + LocalDateTime.now().format(FMT) + ".txt";
                try(PrintWriter pw = new PrintWriter(new FileWriter(nombre))){
                    pw.println("========================================");
                    pw.println("  REPORTE GYMPOS - " + LocalDateTime.now());
                    pw.println("========================================");
                    pw.println();
                    pw.println("---CLIENTES (" + clientes.size() + ")---");
                    for(Cliente c : clientes)
                        pw.println("  [" + c.GetId() + "] " + c.GetNombreCompleto() + " | " + c.GetCorreo() + " | Puntos: " + c.GetPuntos());
                    pw.println();
                    pw.println("---MEMBRESIAS (" + membresias.size() + ")---");
                    for(Membresia m : membresias)
                        pw.println("  [" + m.GetId() + "] Cliente " + m.GetIdCliente() + " | " + m.GetTipo().GetNombre() + " | Vence: " + m.GetFechaFin() + " | " + (m.EstaVencida() ? "VENCIDA" : "Activa"));
                    pw.println();
                    pw.println("---PAGOS (" + pagos.size() + ")---");
                    double total = 0;
                    for(Pago p : pagos){
                        pw.println("  [" + p.GetId() + "] Cliente " + p.GetIdCliente() + " | $" + String.format("%.2f", p.GetMonto()) + " | " + p.GetMetodo() + " | " + p.GetEstado());
                        if(p.GetEstado() == Pago.EstadoPago.COMPLETADO) total += p.GetMonto();
                    }
                    pw.println();
                    pw.println("  Total recaudado: $" + String.format("%.2f", total));
                    pw.println("========================================");
                }
                Thread.sleep(800);
                final String ruta = nombre;
                javafx.application.Platform.runLater(() -> alTerminar.accept(ruta));
            }catch (Exception e){
                javafx.application.Platform.runLater(() -> alError.accept(e.getMessage()));
            }
        });
    }

    public void Cerrar() {ejecutor.shutdown();}
}
