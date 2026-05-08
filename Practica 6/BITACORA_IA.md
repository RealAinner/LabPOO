## #+BEGIN_QUOTE  

OP: ya e trabajado antes con interfaces y clases abstractas pero ahora no se como decidir cuales  
interfaces deberia poner para cada tipo de funcion del cine  
  
IA: Lo mejor es pensar en las responsabilidades reales de cada tipo de funcion. No todas necesitan hacer  
lo mismo. Por ejemplo, una funcion normal puede limitarse al cobro de boletos, mientras que una funcion  
VIP tiene sentido que tambien maneje reservaciones y promociones especiales.  
  
OP: entonces no estaria mal que algunas clases tengan mas interfaces que otras   
  
IA: No, de hecho esa es la idea. Asi cada clase solo implementa funcionalidades que realmente le  
corresponden y el diseño queda mas coherente con el dominio del cine.  
  
OP: tambien tenia duda sobre los atributos protegidos ya que antes usaba privados casi siempre  
  
IA: Los privados siguen siendo lo mas seguro normalmente, pero en una clase abstracta los protegidos pueden  
ser utiles cuando quieres que las clases hijas tengan acceso directo a informacion compartida sin tener que  
repetir codigo constantemente.  

OP: siento que hago demasiadas cosas en el main  

Asistente: Eso pasa mucho al inicio. Separar responsabilidades ayuda bastante porque el main queda solamente  
para pruebas y ejecucion general, mientras que la logica importante queda distribuida en otras clases.  
  
OP: tambien me pidieron justificar por que algunas clases usan ciertas interfaces y otras no  
  
IA: Esa parte es importante porque demuestra que no solo usaste interfaces por cumplir el requisito.  
La idea es que las combinaciones tengan sentido. Por ejemplo, una funcion VIP necesita mas operaciones relacionadas  
con reservas y promociones, mientras que una funcion sencilla no requiere tantacomplejidad.  
    
## #+END_QUOTE  
