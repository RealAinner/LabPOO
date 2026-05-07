## #+BEGIN_QUOTE  
  
OP: necesito organizar mi proyecto de Java usando herencia, interfaces y polimorfismo pero aun no tengo como estructurarlo.  
Quiero que el programa sea sobre un cine y peliculas.  
  
IA: Una buena forma de estructurarlo es separar las responsabilidades usando programacion orientada a objetos. Primero podrias crear  
una interfaz que contenga los metodos relacionados con calculos de precios. Despues, una clase abstracta serviria como base comun para  
todas las funciones del cine, ya que todas comparten atributos como titulo, duracion y precio base. Finalmente, las clases concretas  
heredarian de esa clase abstracta para especializar el comportamiento dependiendo del tipo de funcion. De esta manera el codigo queda  
mucho mas organizado y reutilizable.  
  
OP: entonces la interfaz solamente tendria las firmas de los metodos y no la logica?  
  
IA: Exactamente. La interfaz se utiliza para definir el comportamiento que las clases deben implementar, pero sin escribir la logica interna.  
En este caso, la interfaz puede declarar varios metodos usando sobrecarga, por ejemplo uno sin parametros, otro con cantidad de boletos y otro  
agregando combos o descuentos. Asi obligas a todas las clases concretas a implementar esos metodos y mantienes consistencia en el sistema.  
  
OP: y que ventajas tendria usar una clase abstracta en lugar de poner todo directamente en las clases hijas?  
  
IA: La principal ventaja es evitar duplicacion de codigo. La clase abstracta permite centralizar atributos y comportamientos comunes  
que todas las funciones comparten. Por ejemplo, los atributos titulo, duracion y precio existen en cualquier tipo de funcion del cine.  
Tambien puedes incluir getters, setters y validaciones una sola vez. Ademas, una clase abstracta puede tener tanto metodos concretos como  
abstractos, lo cual es muy util para implementar el patron Template Method.
  
OP: como funcionaria el template method dentro de este programa 
  
IA: Podrias crear un metodo concreto dentro de la clase abstracta. Ese metodo construiria automaticamente un resumen de la funcion  
usando informacion general como titulo y duracion, pero internamente llamaria a metodos abstractos. Cada clase hija implementaria esos  
metodos de manera distinta. Asi, la estructura general del algoritmo queda definida en la clase padre y las hijas solamente personalizan  
ciertas partes del comportamiento.  
    
OP: tambien me pidieron demostrar sobrecarga de metodos como podria hacerlo bien?  
  
IA: La sobrecarga ocurre cuando varios metodos tienen el mismo nombre pero diferente cantidad o tipo de parametros.  
En este proyecto se puede demostrar facilmente con distintas versiones de un metodo. Por ejemplo:  
  
* calcularTotal()  
* calcularTotal(int boletos)  
* calcularTotal(int boletos, boolean combo)  
* calcularTotal(int boletos, boolean combo, double descuento)  
  
Todos tienen el mismo nombre pero realizan calculos diferentes dependiendo de los datos enviados. Esto ayuda a que el codigo sea mas  
flexible y facil de usar.  
  
OP: tambien necesito usar casting e instanceof donde puedo usarlos?  
  
IA: En un arreglo polimorfico todos los objetos son tratados como FuncionCine, pero en algunos casos podrias necesitar acceder  
a caracteristicas especificas de una clase hija. Por ejemplo, antes de convertir un objeto a Funcion3D puedes usar instanceof para  
verificar que realmente pertenezca a ese tipo. Despues haces el casting y ya puedes acceder a metodos particulares de Funcion3D.  
  
OP: que problema evita usar instanceof antes del casting
  
IA Evita la excepcion ClassCastException. Esa excepcion ocurre cuando intentas convertir un objeto a un tipo incompatible.  
Al usar instanceof primero, verificas el tipo real del objeto antes de hacer la conversion, haciendo el programa mucho mas seguro.  
  
## #+END_QUOTE  
  


