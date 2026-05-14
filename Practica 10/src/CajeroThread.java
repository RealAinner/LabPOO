public class CajeroThread extends Thread {
    private final FondoNomina fondo;
    private final String NombreEmpleado;
    private final double pago;

    public CajeroThread(FondoNomina fondo, String NombreEmpleado, double pago){
        this.fondo = fondo;
        this.NombreEmpleado = NombreEmpleado;
        this.pago = pago;
        setName("Cajero-" + NombreEmpleado);
    }

    @Override
    public void run(){
        Log.Imprimir("[CAJERO] Procesando pago de " + NombreEmpleado + " por $" + pago);
        try{
            boolean ok = fondo.Retirar(pago, getName());
            if(ok) Log.Imprimir("[CAJERO] Pago completado a " + NombreEmpleado);
        }catch(InterruptedException e){
            Log.Imprimir("[CAJERO] Interrumpido: " + getName());
            Thread.currentThread().interrupt();
        }
    }
}
