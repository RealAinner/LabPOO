public class ConsumidorPagos implements Runnable {
    private final BufferPagos buffer;
    private final int cantidad;

    public ConsumidorPagos(BufferPagos buffer, int cantidad){
        this.buffer = buffer;
        this.cantidad = cantidad;
    }

    @Override
    public void run(){
        for(int i = 0; i < cantidad; i++){
            try{
                String solicitud = buffer.Consumir();
                Log.Imprimir("[CONSUMIDOR] " + Thread.currentThread().getName() + " proceso: " + solicitud);
                Thread.sleep(400);
            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}
