1. ¿Qué diferencia hay entre sobrescritura (override) y sobrecarga (overload)? Da un ejemplo de cada una desde tu código.  
	La sobrescritura ocurre cuando una clase hija redefine un metodo heredado de la clase padre para darle  
	un comportamiento diferente. Para que exista sobrescritura, el metodo debe tener el mismo nombre, tipo de retorno   
	y parametros.  
	Esto ocurre por ejemplo con el metodo CalcularPrecio() definido en la clase abstracta FuncionCine y sobrescrito  
	en las clases Funcion2D, Funcion3D y FuncionVIP. Cada clase implementa el calculo del precio de manera distinta  
	dependiendo del tipo de funcion.  
	Por otra parte, la sobrecarga ocurre cuando existen varios metodos con el mismo nombre pero con distinta  
	cantidad o tipo de parametros dentro de una misma clase. En el programa esto se observa en los distintos metodos CalcularPrecio()  
  
2. ¿Por qué usaste =instanceof= antes de hacer un cast? ¿Qué excepción previene?  
	Se utilizo instanceof antes de realizar el casting para verificar que el objeto realmente perteneciera al tipo de  
	clase esperado. Porque en el arreglo polimorfico todos los objetos son tratados como FuncionCine, aunque puedan ser Funcion2D, Funcion3D o FuncionVIP.  
	En el codigo se utilizo: if (actual instanceof Funcion3D) y luego: Funcion3D funcion3D = (Funcion3D) actual  
	El uso de instanceof previene la excepcion ClassCastException, la cual pasa cuando se intenta convertir un objeto a un tipo que no es compatible.  
	Por ejemplo: seria invalido intentar convertir un objeto FuncionVIP en Funcion3D.  
  
3. ¿Podrías instanciar tu clase abstracta directamente? ¿Por qué sí o por qué no?  
	No es posible instanciar directamente la clase abstracta FuncionCine. Esto ocurre ya que las clases abstractas estan diseñadas para servir como  
	base de herencia y pueden contener metodos abstractos sin implementacion completa.
