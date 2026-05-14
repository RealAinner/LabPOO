public class Main {
    public static void main(String[] args) throws InterruptedException {
        Log.Imprimir("\n\n\t\tSistema de Nomina Concurrente");

        Log.Imprimir("\n---Fase 1: Cajeros y Depositadores---");
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

        Log.Imprimir("\n---Fase 2: Patron Productor-Consumidor---");
        BufferPagos buffer = new BufferPagos(3);

        String[] lote1 = {"Pedro Vega", "Sofia Cruz", "Luis Mora"};
        String[] lote2 = {"Elena Rios", "Jorge Diaz", "Carmen Alba"};

        Thread p1 = new Thread(new ProductorPagos(buffer, lote1), "Productor-1");
        Thread p2 = new Thread(new ProductorPagos(buffer, lote2), "Productor-2");
        Thread c1 = new Thread(new ConsumidorPagos(buffer, 3), "Consumidor-1");
        Thread c2 = new Thread(new ConsumidorPagos(buffer, 3), "Consumidor-2");

        p1.start(); p2.start(); c1.start(); c2.start();
        p1.join(); p2.join(); c1.join(); c2.join();
    }
}
