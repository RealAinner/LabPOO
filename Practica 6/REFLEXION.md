1. ¿Cuándo preferirías una clase abstracta sobre una interfaz? ¿Y al revés?  
	Preferiria usar una clase abstracta cuando varias clases comparten atributos y parte de la logica,  
	como en FuncionCineBase, donde todas las funciones tienen titulo, duracion y precio base.  
	En cambio, usaria interfaces cuando solo quiero definir capacidades especificas, por ejemplo Cobro  
	o Reservaciones, ya que no todas las funciones necesitan lo mismo. 
  
2. ¿Una clase puede implementar varias interfaces? ¿Por qué Java permite eso pero no herencia múltiple de clases?  
	Si, una clase puede implementar varias interfaces. Por ejemplo FuncionVIP implementa Cobro, Promociones y Reservaciones.  
	Java permite eso porque las interfaces funcionan mas como contratos y no generan conflictos grandes de implementacion.  
	En cambio, la herencia multiple de clases podria causar problemas si dos clases tienen metodos iguales y Java no sabe cual usar.  
  
3. Si agregas un método nuevo a una de tus interfaces, ¿qué clases se ven afectadas? ¿Cómo lo resolverías con un método =default=?  
	Si agrego un metodo nuevo a una interfaz, todas las clases que implementan esa interfaz se verian afectadas porque tendrian  
	que implementar ese metodo.  
	Para evitar eso se puede usar un metodo default, que ya trae una implementacion base dentro de la interfaz y asi las clases  
	no se rompen aunque no lo sobrescriban.  
