public class Main {
    public static void main(String[] args) throws InterruptedException {
        Log.Imprimir("\n\n\t\tSistema de Nomina Concurrente");

        Log.Imprimir("\n-- Fase 1: Cajeros y Depositadores --");
        FondoNomina fondo = new FondoNomina(5000);

        String[][] empleados = {
            {"Ana Lopez", "3000"},
            {"Carlos Ruiz", "2500"},
            {"Maria Gomez", "4000"}
        };

        CajeroThread[] cajeros = new CajeroThread[empleados.length];
        for(int i = 0; i < empleados.length; i++){
            cajeros[i] = new CajeroThread(fondo, empleados[i][0], Double.parseDouble(empleados[i][1]));
        }

        Thread[] depositadores = new Thread[2];
        depositadores[0] = new Thread(new DepositadorRunnable(fondo, 6000, "Finanzas-Norte"));
        depositadores[1] = new Thread(new DepositadorRunnable(fondo, 4000, "Finanzas-Sur"));

        for(CajeroThread c : cajeros) c.start();
        for(Thread d : depositadores) d.start();
        for(CajeroThread c : cajeros) c.join();
        for(Thread d : depositadores) d.join();

        Log.Imprimir("Saldo final del fondo: $" + fondo.GetSaldo());
    }
}
