1. ¿Por qué marcamos atributos como =private=? ¿Qué riesgo evitamos?  
	Se manejan como private para poder mantener la integridad del objeto y que cualquier acceso  
	sea por metodos controlados como los getters/setters. Asi evitamos el riesgo de que se "quiebre"  
	la logica de la clase.
2. ¿Cuál es la diferencia entre =private=, =protected= y =public=? Ilustra con un ejemplo de tu código.  
	Private solo hace que el atributo se pueda manejar dentro de la misma clase por ejemplo la clase  
	Peliculas la duracion solo puede modificarse dentro de esta misma clase. Protected hace que solo  
	pueda manejarse en su misma clase, clases del mismo paquete y clases hijas (por herencia aunque  
	esten en otro paquete). Y por ulitmo Public hace que sea de libre acceso desde cualquier parte.  
3. ¿Qué validación incluiste en un setter? ¿Qué pasa si el valor recibido es inválido?  
	En la clase Cine al poner el nombre del cine use un regex matches para que el nombre tuviera  
	3 caracteres y que fueran solo letras. Si el valor recibido es invalido el programa ignora este  
	valor y sigue con los siguientes.
