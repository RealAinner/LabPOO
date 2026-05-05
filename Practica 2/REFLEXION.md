1. ¿Cuál es la diferencia entre una clase y un objeto? Da un ejemplo con tupropio código.
	Una clase es como una plantilla donde se define que atributos o metodos tendra un objeto.
	Y el objeto es la instancia de la clase por asi decirlo, donde se definen sus atributos.
	Ejemplo la clase de Verdura es donde se establecen los tipos de datos de cada verdura ademas de sus metodos
	y el objeto son las verduras las cuales se les definen los datos a travez de dichos metodos y constructores.
2. ¿Por qué usaste 3 constructores distintos? ¿Qué problema resuelve cada uno?
	Use 3 ya que es dependiendo del gusto del usuario, en el primero dejamos que el usuario defina todos los
	datos de las verduras, en el segundo es un parcial es decir solamente el nombre y precio (lo demas se agrega 
	en automatico) si el usuario no quiere complicarse y el tercero es simplemente agregar el nombre para ser
	mas sencillo de llenar.
3. ¿Qué pasaría si no tuvieras constructores definidos? ¿Java sigue funcionando? ¿Por qué?
	Sigue funcionando pues java detecta que no tiene constructores y crea uno invisible por asi decirlo con
	valores predeterminados, ej: los numeros en 0, booleanos en false, los string en null etc.