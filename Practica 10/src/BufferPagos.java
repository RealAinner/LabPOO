import java.util.LinkedList;
import java.util.Queue;

public class BufferPagos {
    private final Queue<String> cola = new LinkedList<>();
    private final int capacidad;

    public BufferPagos(int capacidad){
        this.capacidad = capacidad;
    }

    public synchronized void Producir(String solicitud) throws InterruptedException {
        while(cola.size() >= capacidad){
            Log.Imprimir("[BUFFER] Lleno, productor esperando...");
            wait();
        }
        cola.add(solicitud);
        Log.Imprimir("[BUFFER] Solicitud agregada: " + solicitud + " | En cola: " + cola.size());
        notifyAll();
    }

    public synchronized String Consumir() throws InterruptedException {
        while(cola.isEmpty()){
            Log.Imprimir("[BUFFER] Vacio, consumidor esperando...");
            wait();
        }
        String solicitud = cola.poll();
        Log.Imprimir("[BUFFER] Solicitud procesada: " + solicitud + " | En cola: " + cola.size());
        notifyAll();
        return solicitud;
    }
}
