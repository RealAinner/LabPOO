1. ¿Cuál es la diferencia entre una excepción chequeada (checked) y una no chequeada (unchecked)?  
	Checked: El lenguaje te obliga a manejarlas con try-catch porque son errores externos  
	previsibles. Si no se gestiona, el codigo no compila.  
	Unchecked: Son errores de logica del programador. El compilador no nos obliga a  
	escribirlas, pero detendran el programa si ocurren.  
  
2. ¿Por qué creaste una jerarquía de excepciones en lugar de usar =Exception= directamente?  
	Usar una jerarquia permite que el sistema tome decisiones diferentes segun el fallo.  
	No es lo mismo un error de dedo del cajero (PrecioInvalido) que un problema logistico (InventarioAgotado).  
	Al tener clases separadas, puedo capturar solo el error que me interesa y darle una solucion especifica  
	(como pedir mas mercancia automaticamente) en lugar de solo mostrar un mensaje de error generico.
  
3. ¿Qué ventaja tiene =try-with-resources= sobre un bloque =finally= tradicional?  
	La automatizacion ya que el try-with-resources garantiza que el archivo de log se cierre solo, pase lo que pase  
	eliminando el riesgo de que el archivo quede "bloqueado" o se corrompa por un cierre olvidado.
	Y la simplicidad porque reduce drasticamente las lineas de codigo, evitando tener que escribir bloques finally  
	complejos que a menudo requierian sus propios try-catch internos.