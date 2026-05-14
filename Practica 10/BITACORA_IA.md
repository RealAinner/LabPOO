## #+BEGIN_QUOTE  
  
OP: podrias revisar mi codigo y darme algunas sugerencias para poder mejorarlo?  
  
IA: Estuve revisando tu codigo y en general esta bastante bien organizado para una practica de concurrencia. Me gusto que separaras  
las responsabilidades en varias clases, porque eso hace mas facil identificar que hilo hace cada tarea. Por ejemplo, los productores  
solo se encargan de generar pagos, los consumidores de procesarlos y FondoNomina de controlar el dinero disponible. Eso ayuda mucho  
cuando luego quieres depurar errores de sincronizacion.  
  
OP: oye porque decidiste usar synchronized en fondonomina?  
  
IA: Principalmente por el manejo del saldo. Piensa que varios hilos pueden intentar acceder al mismo valor al mismo tiempo. Si no hubiera  
sincronizacion, podria pasar que dos cajeros revisen el saldo simultaneamente y ambos crean que hay dinero suficiente para retirar.  
Entonces los dos harian el descuento y el resultado final quedaria mal. Con synchronized te aseguras de que solo un hilo a la vez pueda  
entrar al metodo y modificar el saldo. Asi evitas inconsistencias.  
  
OP: entonces synchronized es como poner una fila de espera?  
  
IA: Exactamente. Cuando un hilo entra a un metodo sincronizado, los demas tienen que esperar su turno hasta que el primero salga.  
Es basicamente una proteccion para la seccion critica del codigo, o sea la parte donde compartes recursos entre hilos.  
  
OP: y eso no vuelve mas lento el programa?  
  
IA: Si, esa es una de las desventajas. Entre mas sincronizacion pongas, menos paralelismo real tienes porque los hilos pasan tiempo  
esperando. Por eso normalmente se intenta sincronizar solo lo necesario. En tu caso esta bien porque el recurso compartido es pequeño y  
el objetivo es mantener la consistencia del saldo y de la cola de pagos.  
  
OP: vi que tambien usaste wait y notifyall pero porque no solo usar sleep  
  
IA: Esa diferencia es importante. sleep() simplemente pausa un hilo cierto tiempo, pero el hilo sigue conservando el bloqueo del objeto.  
O sea, aunque este dormido, nadie mas puede entrar a la parte sincronizada. En cambio wait() si libera el bloqueo y deja que otro hilo  
trabaje mientras el actual espera una condicion.  
  
OP: osea wait esta pensado para comunicacion entre hilos?  
  
IA: Exactamente. Por ejemplo, en BufferPagos, cuando la cola esta vacia, el consumidor no tiene nada que hacer. Entonces entra en wait()  
hasta que un productor agregue algo. Y cuando el productor mete un nuevo pago, usa notifyAll() para despertar a los hilos que estaban esperando.  
Eso hace que todo el flujo sea mas eficiente y coordinado.  
  
OP: y porque notifyall en vez de notify?  
  
IA: Porque tienes varios tipos de hilos esperando. Algunos esperan porque la cola esta llena y otros porque esta vacia. Si usaras solo notify(),  
podrias despertar al hilo equivocado y seguirias bloqueado. Con notifyAll() despiertas a todos y el que pueda continuar realmente lo hace.  
No es la opcion mas eficiente en rendimiento, pero si es mas segura para este escenario.  
  
OP: ademas porque usaste linkedlist para la cola?  
  
IA: Porque estas simulando una cola dinamica donde constantemente agregas y eliminas elementos. Con un arreglo tendrias que mover elementos manualmente  
o controlar indices. LinkedList ya trae operaciones comodas para insertar al final y sacar del inicio, que es justo como funciona una cola FIFO.  
  
OP: y donde dirias que existe el mayor riesgo de race condition en mi codigo?  
  
IA: Yo diria que en saldo dentro de FondoNomina. Porque ahi varios hilos pueden leer y modificar el mismo dato compartido. Si quitas synchronized,  
rapidamente empiezan los problemas. Tambien la cola en BufferPagos seria un punto critico, porque productores y consumidores la usan al mismo tiempo.  
Sin sincronizacion podrias terminar con elementos perdidos, duplicados o incluso errores de ejecucion.  
  
OP: y porque usar runnable en vez de heredar de thread  
  
IA: Porque Runnable te da mas flexibilidad. La clase ya no queda atada directamente al hilo, sino solo a la tarea que quieres ejecutar. Ademas en Java  
solo puedes heredar de una clase, entonces implementar Runnable suele ser mejor practica cuando quieres mantener el diseño mas limpio.  
  
## #+END_QUOTE  