public class FondoNomina {
    private double saldo;
    private final String nombre = "Fondo Central";

    public FondoNomina(double SaldoInicial){
        this.saldo = SaldoInicial;
    }

    public synchronized boolean Depositar(double monto, String origen){
        if (monto <= 0) return false;
        saldo += monto;
        Log.Imprimir("[DEPOSITO] " + origen + " deposito $" + monto + " | Saldo: $" + saldo);
        notifyAll();
        return true;
    }

    public synchronized boolean Retirar(double monto, String origen) throws InterruptedException {
        while(saldo < monto){
            Log.Imprimir("[ESPERA] " + origen + " espera fondos (necesita $" + monto + " | disponible $" + saldo + ")");
            wait();
        }
        saldo -= monto;
        Log.Imprimir("[RETIRO] " + origen + " retiro $" + monto + " | Saldo: $" + saldo);
        return true;
    }

    public synchronized double GetSaldo() { return saldo; }
    public String GetNombre() { return nombre; }
}
