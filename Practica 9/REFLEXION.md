1. ¿Qué es la serialización y cuándo es útil en comparación con guardar texto plano?  
La serializacion es el proceso de convertir un objeto java en una secuencia de bytes para guardarlo en disco y luego reconstruirlo  
exactamente como estaba. En mi practica la use en la clase Empleado implementando serializable, lo que me permite guardar y cargar  
un objeto completo con todos sus atributos sin tener que armarlo manualmente campo por campo. Comparado con texto plano, la ventaja  
es que no necesito parsear ni validar nada al cargarlo, el objeto llega listo para usarse. El texto plano es mas legible y portable  
entre lenguajes, pero si el sistema es puramente java y necesito persistir objetos completos con frecuencia, la serializacion es mas  
directa y menos propensa a errores de parseo.  
  
2. ¿Por qué usamos BufferedReader en lugar de leer byte a byte? ¿Qué mejora en rendimiento ofrece?  
Leer byte a byte implica una llamada al sistema operativo por cada byte leido, lo cual es costoso porque cada llamada tiene su propio  
overhead. bufferedreader resuelve esto cargando un bloque grande de datos en memoria de una sola vez y entregandolos desde ahi, reduciendo  
el numero de operaciones de entrada y salida. En mi practica lo use para leer el csv y los archivos de texto, y la diferencia se vuelve  
notable cuando el archivo tiene muchas filas. Ademas, bufferedreader ofrece el metodo readline() que facilita procesar el archivo linea  
por linea, algo que haciendo lectura byte a byte tendria que implementar manualmente.  
  
3. ¿Qué riesgos tiene no cerrar un archivo después de usarlo? ¿Cómo los mitigaste?  
Si no se cierra un archivo el sistema operativo mantiene ese recurso ocupado, lo que puede causar fugas de memoria, bloqueos al intentar  
acceder al mismo archivo desde otro proceso, y en escritura existe el riesgo de que los datos en el buffer no se vacien al disco y se pierdan.  
En mi practica lo mitigue usando try with resources en todos los metodos de GestorArchivos, lo que garantiza que el archivo se cierre  
automaticamente al terminar el bloque incluso si ocurre una excepcion. Esto es preferible a cerrar manualmente en un bloque finally porque  
elimina la posibilidad de olvidar el cierre o de que una excepcion lo salte.  