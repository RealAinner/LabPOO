## Estrategias de sincronizacion usadas en el proyecto

El sistema simula una nomina concurrente donde multiples cajeros y depositadores
acceden al mismo fondo de dinero al mismo tiempo. A continuacion se explica
cada estrategia aplicada y por que se eligio.

**synchronized en metodos de FondoNomina**
Los metodos Depositar y Retirar estan marcados como synchronized, lo que garantiza
que solo un hilo a la vez puede modificar el saldo. Sin esto, dos cajeros podrian
leer el mismo saldo, retirar simultaneamente y dejar el fondo en negativo sin que
ninguno lo detecte, lo que se conoce como condicion de carrera.

**wait() y notifyAll() para coordinacion**
Cuando un cajero intenta retirar mas de lo disponible, entra en espera con wait()
liberando el lock para que otros hilos puedan continuar. Cada vez que llega un
deposito se llama notifyAll() para despertar a todos los cajeros en espera y que
verifiquen si ya hay fondos suficientes. Se uso notifyAll() en lugar de notify()
porque pueden haber multiples cajeros esperando y solo uno de ellos puede continuar
segun su monto especifico.

**extends Thread en CajeroThread**
Se extendio Thread directamente para representar a cada cajero como una entidad
independiente con nombre propio, lo que facilita identificarlos en los logs.
Esta forma es util cuando el hilo tiene identidad propia y no necesita heredar
de otra clase.

**implements Runnable en DepositadorRunnable y demas clases**
El resto de clases implementan Runnable porque es la forma mas flexible: permite
que la misma tarea sea ejecutada por un Thread comun o por un ExecutorService
sin cambiar el codigo de la tarea.

**BufferPagos con capacidad limitada (Productor-Consumidor)**
El buffer usa synchronized con wait() y notifyAll() para bloquear al productor
cuando esta lleno y al consumidor cuando esta vacio. Esto evita que se acumulen
solicitudes sin limite y que los consumidores intenten procesar algo que no existe.

**ExecutorService con pool fijo**
Se uso Executors.newFixedThreadPool(3) para limitar a tres hilos concurrentes
al procesar bonos. Esto evita crear un hilo por cada tarea, lo cual seria
ineficiente si hay muchas tareas. El pool reutiliza los hilos disponibles
y los gestiona automaticamente.

**Log sincronizado**
El metodo Imprimir de la clase Log es synchronized para que los mensajes de
distintos hilos no se mezclen o corten entre si en la consola.
