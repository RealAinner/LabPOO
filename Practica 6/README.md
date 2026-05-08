## Practica 6  
En esta practica seguimos con la tematica del cine, a continuacion explicare de manera mas  
detallada lo que se muestra en esta practica. Olvide comentar que todo se ejecuta desde la  
clase principal SistemaCine.   
  
--Funcion2D implementa solo Cobro, porque es la funcion mas simple y solo necesita calcular precios.  
--Funcion3D implementa Cobro y Promociones, porque ademas de calcular el costo tambien puede manejar  
    descuentos o promociones.  
--FuncionVIP implementa Cobro, Promociones y Reservaciones, porque una funcion VIP normalmente tiene  
    precio especial, promociones y control de asientos.  

***Documentacion de las combinaciones  
Funcion2D implementa solo Cobro porque una funcion 2D normalmente solo requiere calcular el precio base  
del boleto. Es la clase mas simple del sistema y por eso no necesita responsabilidades adicionales.  
  
Funcion3D implementa Cobro y Promociones porque, ademas de calcular el costo de la funcion, puede manejar  
descuentos o promociones. Esto tiene sentido en una funcion especial como 3D, donde puede haber ofertas  
por dia o por tipo de cliente.  
  
FuncionVIP implementa Cobro, Promociones y Reservaciones porque una sala VIP suele manejar precio especial,  
promociones y control de asientos reservados. Esa combinacion es la mas completa y coincide con la complejidad  
real de ese tipo de funcion.
