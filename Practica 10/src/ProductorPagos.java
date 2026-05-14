public class ProductorPagos implements Runnable {
    private final BufferPagos buffer;
    private final String[] empleados;

    public ProductorPagos(BufferPagos buffer, String[] empleados){
        this.buffer = buffer;
        this.empleados = empleados;
    }

    @Override
    public void run(){
        for(String emp : empleados){
            try{
                String solicitud = "Pago:" + emp + ":" + Thread.currentThread().getName();
                buffer.Producir(solicitud);
                Thread.sleep(200);
            }catch(InterruptedException e){
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}
