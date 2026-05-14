public class DepositadorRunnable implements Runnable {
    private final FondoNomina fondo;
    private final double monto;
    private final String origen;

    public DepositadorRunnable(FondoNomina fondo, double monto, String origen){
        this.fondo = fondo;
        this.monto = monto;
        this.origen = origen;
    }

    @Override
    public void run(){
        Log.Imprimir("[DEPOSITO] " + origen + " iniciando deposito de $" + monto);
        try{
            Thread.sleep(300);
            fondo.Depositar(monto, origen);
        }catch(InterruptedException e){
            Thread.currentThread().interrupt();
        }
    }
}
