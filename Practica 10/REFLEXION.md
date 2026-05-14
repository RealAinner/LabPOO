1. ¿Qué es una race condition? Describe un escenario concreto de tu código donde podría ocurrir.  
	Una race condition pasa cuando dos o mas hilos intentan leer o modificar el mismo dato al mismo  
	tiempo y el resultado depende del orden en que se ejecuten. En mi codigo podria pasar en FondoNomina con  
	saldo, por ejemplo si dos hilos intentaran retirar dinero al mismo tiempo. Imaginando que hay $5000 y entran  
	dos cajeros: uno quiere retirar $3000 y otro $2500. Si ambos revisan el saldo casi al mismo tiempo, los dos  
	podrian ver que "si alcanza" y despues restar su cantidad. Eso podria dejar el saldo mal calculado o incluso  
	negativo. Algo parecido tambien podria pasar en BufferPagos si no  
  
2. ¿Por qué =synchronized= resuelve el problema? ¿Qué desventaja de rendimiento tiene?  
	synchronized resuelve el problema porque hace que solo un hilo a la vez pueda entrar a un metodo protegido,  
	como Depositar() o Retirar(). Asi nadie puede modificar saldo mientras otro hilo lo esta usando. En BufferPagos  
	tambien sirve para que la cola no se corrompa cuando productores y consumidores trabajan juntos. La desventaja es  
	que baja el rendimiento cuando hay muchos hilos, porque se forman esperas y se pierde paralelismo. En otras palabras,  
	el programa se vuelve mas seguro, pero mas lento si hay mucha contencion.  
  
3. ¿Qué diferencia hay entre =Thread.sleep()= y =Object.wait()=? ¿Cuándo  usarías cada uno?  
	Thread.sleep() y Object.wait() no hacen lo mismo ya que sleep() solo pausa al hilo por un tiempo, pero no libera el candado  
	del objeto que este usando. En cambio wait() si libera el bloqueo y deja que otro hilo entre al bloque sincronizado  
	despues el hilo despierta cuando alguien llama notify() o notifyAll(), sleep() se usa bien en DepositadorRunnable y  
	ConsumidorPagos para simular tiempo de trabajo, wait() se usa bien en BufferPagos y FondoNomina porque ahi los hilos  
	de verdad necesitan esperar a que haya espacio en la cola o saldo suficiente para retirar.  
  