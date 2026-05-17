1. ¿Qué es el hilo de la UI (JavaFX Application Thread) y por qué no debes hacer operaciones pesadas en él?  
	El hilo de la UI o JavaFX Application Thread es el hilo principal donde javafx dibuja y actualiza toda la interfaz grafica, botones,  
	tablas, labels, etc. Si yo hago una operacion pesada ahi como generar un reporte o procesar un pago, ese hilo se bloquea y la ventana se  
	congela completamente, el usuario no puede hacer nada hasta que termine. Por eso en GymPOS use ExecutorService para mover esas tareas a  
	un hilo secundario y cuando terminan uso Platform.runLater() para regresar al hilo de la UI y actualizar la interfaz de forma segura.
  
2. ¿Qué es un =EventHandler=? ¿Cómo conecta la acción del usuario con la lógica de tu programa?  
	Un EventHandler es basicamente una interfaz funcional que define que debe pasar cuando ocurre un evento, como un click o una tecla presionada.  
	En javafx lo conectas a un control con metodos como setOnAction() o setOnKeyPressed(). Por ejemplo en GymPOS cuando el usuario hace click en el  
	boton Guardar, el EventHandler llama al metodo Guardar() del panel, que a su vez llama al controlador, que llama al servicio. Es el puente entre  
	lo que el usuario hace en pantalla y la logica del programa.  
  
3. ¿Qué diferencia hay entre un =Stage=, una =Scene= y un =Node= en JavaFX?  
	Son tres niveles distintos:  
	Stage es la ventana del sistema operativo, la que tiene la barra de titulo, los botones de minimizar y cerrar. En GymPOS el stage es la ventana  
	principal de toda la aplicacion.  
	Scene es el contenido que vive dentro del stage. Un stage solo puede mostrar una scene a la vez pero puedes cambiarla. En GymPOS la scene  
	contiene el menu y el panel central.  
	Node es cualquier elemento visual dentro de la scene, un boton, una tabla, un label, un campo de texto. Todo lo que se ve en pantalla es un node.  
	En GymPOS por ejemplo PanelClientes es un node de tipo VBox que contiene otros nodes adentro.  
	La jerarquia seria: stage contiene una scene, y la scene contiene un arbol de nodes.  
