## #+BEGIN_QUOTE  
  
OP: porque usamos excepciones checked en lugar de las normales?

IA: La razón fundamental es el control de robustez. Al heredar de Exception, obligamos al compilador a verificar que cada operación crítica  
(como validar el peso o el precio) tenga un plan de contingencia. En un negocio de productos perecederos, no podemos permitir que el programa  
ignore un dato erróneo; las excepciones chequeadas garantizan que el flujo de trabajo se detenga o se corrija antes de que el error afecte la  
base de datos o la báscula.  
  
OP: que se gana creando una jerarquia propia en vez de usar exception?

IA: Ganamos capacidad de diagnóstico y respuesta diferenciada. Si usamos solo Exception, el sistema sabe que "algo falló", pero no sabe qué.  
Con una jerarquía , podemos programar respuestas automáticas específicas: por ejemplo, ante un inventario agotado, el sistema podría disparar  
una orden de compra, mientras que ante un precio inválido,  
  
OP: porque es mejor try with resources que cerrar los archivos manualmente?

IA: Por seguridad y limpieza. El try-with-resources implementa la interfaz AutoCloseable, lo que garantiza que el flujo hacia el archivo  
.txt se cierre siempre, incluso si ocurre una excepción catastrófica. En el método tradicional con finally, el programador a menudo olvida cerrar  
el recurso o el propio cierre genera una nueva excepción que oculta el error original. Aquí, el código es más legible y eliminamos fugas de memoria  
o bloqueos de archivos en el sistema operativo.  
  
OP: me sale un warning de bloque inalcanzable al final de mis pruebas a que se debe?

IA: Ese aviso ocurre por la jerarquía de captura. Si el código dentro de un try ya fue filtrado por un catch de una excepción hija  
(como InventarioAgotadoExcepcion), y no hay más métodos que lancen otras excepciones, el bloque del padre (VerduleriaExcepcion) se queda "sin trabajo".  
El compilador detecta que ese código jamás se ejecutará. Aunque no rompe el programa, lo ideal para la bitácora es agrupar varias operaciones en un  
mismo try para que el bloque general sirva como red de seguridad para los errores que no capturamos específicamente.  
  
## #+END_QUOTE  