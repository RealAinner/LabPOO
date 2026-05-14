## Practica 10
En esta practica use la tematica de un sistema de nomina concurrente para mostrar
lo que se pide, a continuacion explico un poco lo hecho:

La clase FondoNomina representa el recurso compartido central del sistema. Sus metodos
Depositar y Retirar estan sincronizados para evitar condiciones de carrera cuando varios
cajeros o depositadores acceden al saldo al mismo tiempo. El metodo Retirar usa wait()
para que un cajero espere si no hay fondos suficientes, y Depositar llama a notifyAll()
para avisar a todos los hilos en espera cuando llega dinero nuevo.

La clase CajeroThread extiende Thread y representa a cada cajero como un hilo con nombre
propio que procesa el pago de un empleado especifico. La clase DepositadorRunnable implementa
Runnable y simula una fuente de fondos que deposita al fondo central con un pequeño retardo
para generar situaciones reales de espera. La clase Log tiene su metodo sincronizado para
que los mensajes de distintos hilos no se mezclen en consola.

El patron Productor-Consumidor se implemento con BufferPagos, que tiene capacidad limitada
y bloquea al productor cuando esta lleno y al consumidor cuando esta vacio, usando wait()
y notifyAll() para la coordinacion. Dos productores y dos consumidores trabajan en paralelo
sobre el mismo buffer. Ademas se uso ExecutorService con un pool fijo de tres hilos para
procesar un lote de bonos, reutilizando hilos en lugar de crear uno nuevo por cada tarea.

El elemento de decision propia es el dominio de nomina concurrente. Este escenario es
un buen ejemplo de concurrencia porque en una empresa real multiples cajeros, sistemas
bancarios y departamentos de finanzas pueden intentar acceder al mismo fondo de nomina
al mismo tiempo. Sin sincronizacion, dos cajeros podrian leer el mismo saldo disponible,
procesar sus pagos simultaneamente y dejar el fondo en negativo sin que ninguno lo detecte.
La sincronizacion resuelve exactamente ese problema garantizando que cada operacion sobre
el saldo sea atomica y consistente.
