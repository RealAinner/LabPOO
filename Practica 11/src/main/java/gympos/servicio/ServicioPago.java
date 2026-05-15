package gympos.servicio;

import gympos.config.Configuracion;
import gympos.excepcion.PagoException;
import gympos.modelo.pago.Pago;
import gympos.modelo.pago.Pago.EstadoPago;
import gympos.modelo.pago.Pago.MetodoPago;
import gympos.util.DatosIniciales;
import gympos.util.Serializador;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class ServicioPago {
    private static final String ARCHIVO = "pagos.ser";
    private List<Pago> pagos;
    private int SiguienteId;
    private final ExecutorService ejecutor = Executors.newSingleThreadExecutor();

    public ServicioPago() {
        pagos = Serializador.Cargar(ARCHIVO);
        if (pagos.isEmpty()) {
            pagos = DatosIniciales.GenerarPagos();
            Serializador.Guardar(pagos, ARCHIVO);
        }
        SiguienteId = pagos.stream().mapToInt(Pago::GetId).max().orElse(0) + 1;
    }

    public List<Pago> GetTodos() { return new ArrayList<>(pagos); }

    public void ProcesarAsync(int IdCliente, int IdMembresia, double monto,
                               MetodoPago metodo, Consumer<Pago> alTerminar, Consumer<String> alError) {
        Pago pago = new Pago(SiguienteId++, IdCliente, IdMembresia, monto, metodo);
        pago.SetEstado(EstadoPago.PROCESANDO);
        pagos.add(pago);

        ejecutor.submit(() -> {
            try {
                Thread.sleep(1500); // simulacion de procesamiento
                if (Math.random() < 0.95) {
                    pago.SetEstado(EstadoPago.COMPLETADO);
                    int puntosPorPago = Configuracion.GetInstancia().GetInt("puntos.porPago");
                    Serializador.Guardar(pagos, ARCHIVO);
                    javafx.application.Platform.runLater(() -> alTerminar.accept(pago));
                } else {
                    pago.SetEstado(EstadoPago.FALLIDO);
                    Serializador.Guardar(pagos, ARCHIVO);
                    javafx.application.Platform.runLater(() -> alError.accept("El pago fue rechazado."));
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }

    public double GetTotalRecaudado() {
        return pagos.stream()
                .filter(p -> p.GetEstado() == EstadoPago.COMPLETADO)
                .mapToDouble(Pago::GetMonto).sum();
    }

    public void Cerrar() { ejecutor.shutdown(); }
}
