## #+BEGIN_QUOTE

---

OP: quiero hacer una aplicacion de gimnasio en java con javafx, como deberia organizar el proyecto?

IA: Buena pregunta para empezar. Lo primero que hay que definir es la arquitectura. Para este tipo de aplicacion lo mas adecuado es el patron MVC (Modelo-Vista-Controlador) porque nos permite separar claramente las responsabilidades de cada clase. El Modelo se encarga de los datos y la logica de negocio, la Vista maneja todo lo que el usuario ve en pantalla, y el Controlador es el intermediario que conecta ambos.

Para la estructura de packages te recomiendo organizarlos asi:

```
gympos/
  modelo/       — clases de dominio (Cliente, Membresia, Pago, etc.)
  vista/        — paneles y ventanas JavaFX
  controlador/  — controladores MVC
  servicio/     — logica de negocio
  excepcion/    — excepciones personalizadas
  util/         — utilidades (Serializador, Validador, DatosIniciales)
  config/       — configuracion del sistema
```

Esto nos da un total minimo de 15 clases bien organizadas. Cada modulo del sistema (clientes, membresias, pagos, acceso, inventario, clases) tendra su propio servicio, controlador y vista. Ademas necesitamos excepciones personalizadas para manejar errores de dominio de forma elegante, un `Serializador` para persistencia con `ObjectOutputStream`, y una clase `DatosIniciales` que genere los 20 registros de prueba automaticamente la primera vez que corre el sistema.

---

OP: como hago los modelos? tengo cliente, membresia, pago, registroacceso, equipo y clasegrupal

IA: Perfecto, esos son exactamente los modelos que necesitamos. Todos deben implementar `Serializable` para que el `Serializador` pueda guardarlos en disco. La estructura basica de cada uno seria la siguiente:

`Cliente` necesita id, nombre, apellido, correo, telefono, fechaRegistro, puntos y activo. Los puntos son para el sistema de recompensas.

`Membresia` necesita id, idCliente, tipo (que sera un enum `TipoMembresia`), fechaInicio, fechaFin, activa y renovacionAutomatica. El enum `TipoMembresia` debe tener BASICO, ESTANDAR, PREMIUM y VIP con sus precios y descuentos correspondientes. Ademas la clase debe tener metodos utiles como `EstaVencida()`, `Renovar()` y `DiasRestantes()`.

`Pago` necesita id, idCliente, idMembresia, monto, metodo (enum `MetodoPago`) y estado (enum `EstadoPago`). Los enums internos nos dan claridad sobre los estados posibles sin usar Strings sueltos.

`RegistroAcceso` es el mas sencillo: id, idCliente, entrada y salida como `LocalDateTime`. El metodo `EstaAdentro()` retorna true cuando salida es null.

`Equipo` necesita id, nombre, categoria, estado (enum `EstadoEquipo`) y cantidad.

`ClaseGrupal` necesita id, nombre, instructor, fechaHora, capacidad y una lista de ids de clientes inscritos. El metodo `Inscribir()` verifica que haya lugar antes de agregar.

Un punto importante: todos los getters y setters deben empezar con mayuscula siguiendo la convencion del proyecto, es decir `GetId()`, `SetNombre()`, etc.

---

OP: ahora necesito el serializador y el validador

IA: El `Serializador` es nuestra capa de persistencia. Usamos `ObjectOutputStream` y `ObjectInputStream` para guardar y cargar listas de objetos en archivos `.ser`. La implementacion debe ser generica para que funcione con cualquier tipo:

```java
public static <T> void Guardar(List<T> lista, String archivo) {
    new File("datos").mkdirs();
    try (ObjectOutputStream oos = new ObjectOutputStream(
            new FileOutputStream("datos/" + archivo))) {
        oos.writeObject(lista);
    } catch (IOException e) {
        e.printStackTrace();
    }
}
```

El `try-with-resources` garantiza que el stream se cierre aunque ocurra una excepcion. La carpeta `datos/` se crea automaticamente con `mkdirs()` si no existe.

El `Validador` contiene metodos estaticos con las reglas de validacion que usaremos en los formularios. Los mas importantes son:

`EsCorreoValido()` usa una expresion regular para verificar el formato del correo.

`EsTelefonoValido()` verifica que sean exactamente 10 digitos numericos.

`EsTextoValido()` verifica longitud minima y maxima de un texto.

Estos metodos los reutilizamos tanto en los servicios (para validar antes de guardar) como en los componentes `CampoValidado` de la vista (para validacion en tiempo real mientras el usuario escribe).

---

OP: como hago el filtrado en tiempo real de la tabla?

IA: Esta es una de las funcionalidades mas elegantes de JavaFX. Usamos tres clases en cadena: `ObservableList`, `FilteredList` y `SortedList`.

El flujo es el siguiente:

Primero creamos un `ObservableList` con todos los datos. Luego envolvemos ese observable en un `FilteredList` con un predicado inicial que acepta todo. Despues envolvemos el `FilteredList` en un `SortedList` y lo vinculamos al `comparatorProperty` de la tabla para que el ordenamiento por columnas siga funcionando. Finalmente asignamos el `SortedList` como items de la tabla.

```java
datos = FXCollections.observableArrayList(clientes);
filtrados = new FilteredList<>(datos, p -> true);

campoBusqueda.textProperty().addListener((obs, viejo, nuevo) ->
    filtrados.setPredicate(c -> {
        if (nuevo == null || nuevo.isBlank()) return true;
        String f = nuevo.toLowerCase();
        return c.GetNombre().toLowerCase().contains(f)
            || c.GetApellido().toLowerCase().contains(f)
            || c.GetCorreo().toLowerCase().contains(f);
    })
);

SortedList<Cliente> ordenados = new SortedList<>(filtrados);
ordenados.comparatorProperty().bind(tabla.comparatorProperty());
tabla.setItems(ordenados);
```

El listener en `textProperty()` se ejecuta en cada keystroke del usuario. Cada vez que cambia el texto actualiza el predicado del `FilteredList` y la tabla se re-renderiza automaticamente mostrando solo los registros que coinciden.

Un error comun aqui es llamar este bloque completo cada vez que se refresca la tabla, lo que duplica los listeners. La solucion es declarar `filtrados` como campo de la clase y solo inicializar el listener la primera vez cuando `filtrados == null`. En refrescos posteriores solo llamamos `datos.setAll(nuevaLista)` y el `FilteredList` detecta el cambio automaticamente.

---

OP: las tablas no se actualizan cuando registro una entrada o hago un cambio

IA: Este es un bug clasico con `FilteredList`. El problema estaba en el metodo `Refrescar()` de todos los paneles. Habia dos errores combinados:

El primero es que en cada llamada a `Refrescar()` se creaba un nuevo `ObservableList`, un nuevo `FilteredList` y se registraba un nuevo listener en `campoBusqueda`. Despues de diez refrescos habia diez listeners activos al mismo tiempo, todos intentando actualizar la tabla.

El segundo es que se intentaba llamar `filtrados.setAll()` para actualizar los datos, pero `FilteredList` no tiene ese metodo. Eso lanzaba un error en runtime que impedia la actualizacion.

La solucion correcta es este patron:

```java
private void Refrescar() {
    if (datos == null) {
        // Solo la primera vez: inicializar todo y registrar el listener
        datos = FXCollections.observableArrayList(servicio.GetTodos());
        filtrados = new FilteredList<>(datos, p -> true);
        campoBusqueda.textProperty().addListener(...);
        SortedList<...> ordenados = new SortedList<>(filtrados);
        ordenados.comparatorProperty().bind(tabla.comparatorProperty());
        tabla.setItems(ordenados);
    } else {
        // Refrescos siguientes: solo reemplazar el contenido
        datos.setAll(servicio.GetTodos());
    }
}
```

`datos.setAll()` reemplaza el contenido del `ObservableList` existente. Como `filtrados` esta vinculado a ese mismo objeto, detecta el cambio y notifica a la tabla para que se re-renderice. Un solo listener, sin duplicados, actualizacion correcta.

---

OP: como hago el procesamiento de pagos en segundo plano?

IA: Para el procesamiento asincrono usamos `ExecutorService` con un hilo secundario. La clave aqui es que JavaFX solo permite actualizar la interfaz desde el JavaFX Application Thread, entonces necesitamos `Platform.runLater()` para regresar al hilo correcto cuando el procesamiento termine.

En `ServicioPago` creamos un `ExecutorService` de un solo hilo:

```java
private final ExecutorService ejecutor = Executors.newSingleThreadExecutor();
```

El metodo `ProcesarAsync()` recibe callbacks: uno para cuando el pago es exitoso y otro para cuando falla. Adentro del `submit()` simulamos el procesamiento con `Thread.sleep(1500)` y luego llamamos al callback correspondiente dentro de `Platform.runLater()`:

```java
ejecutor.submit(() -> {
    try {
        Thread.sleep(1500);
        if (Math.random() < 0.95) {
            pago.SetEstado(EstadoPago.COMPLETADO);
            javafx.application.Platform.runLater(() -> alTerminar.accept(pago));
        } else {
            pago.SetEstado(EstadoPago.FALLIDO);
            javafx.application.Platform.runLater(() -> alError.accept("Pago rechazado."));
        }
    } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
    }
});
```

En el `DialogoPago` mientras se procesa mostramos un `ProgressIndicator` animado y deshabilitamos el boton de pagar para evitar doble envio. Cuando llega el callback de exito o error actualizamos la etiqueta y restauramos el estado de la UI. Este mismo patron lo usamos en `ServicioReporte` para generar el archivo TXT sin congelar la ventana.

Es importante llamar `ejecutor.shutdown()` cuando se cierra la aplicacion. Por eso en `VentanaPrincipal` usamos `stage.setOnCloseRequest()` para cerrar ambos ejecutores correctamente.

---

OP: como compilo y genero el jar? me salen errores con los espacios en la ruta

IA: El problema con espacios en la ruta es uno de los mas frecuentes en Windows cuando se trabaja con scripts `.bat`. Intentamos varias aproximaciones hasta dar con la que funciona.

El primer intento fue generar un `sources.txt` con todas las rutas de los `.java` y pasarselo a `javac` con `@sources.txt`. El problema es que las rutas con espacios como `Practica 11` se partian en el archivo y `javac` las interpretaba como argumentos separados.

El segundo intento fue poner comillas alrededor de cada ruta en el `sources.txt`. Eso tampoco funciono porque Windows maneja las comillas dentro de archivos de texto de forma inconsistente segun la version.

La solucion que funciono fue eliminar completamente el `sources.txt` y usar `-sourcepath` en su lugar:

```bat
set "JDK=C:\Program Files\Java\jdk-21\bin"
set "JAVAFX=C:\Users\nesti\Documents\openjfx-21.0.11_windows-x64_bin-sdk\javafx-sdk-21.0.11\lib"

cd /d "%~dp0"

"%JDK%\javac" --module-path "%JAVAFX%" ^
  --add-modules javafx.controls,javafx.fxml ^
  -d build\classes ^
  -encoding UTF-8 ^
  -sourcepath src\main\java ^
  src\main\java\gympos\Main.java
```

`-sourcepath` le dice a `javac` que compile `Main.java` como punto de entrada y que resuelva automaticamente todas las dependencias buscando en `src\main\java`. Sin archivos intermedios, sin problemas de espacios.

El `%~dp0` es la variable especial de Windows que contiene la ruta completa de la carpeta donde esta el `.bat`, con la barra final incluida. El `cd /d "%~dp0"` nos mueve a esa carpeta antes de compilar, haciendo que todas las rutas relativas funcionen correctamente sin importar desde donde se ejecute el bat.

Para `jar` y `java` tambien hay que usar la ruta completa del JDK porque Oracle no siempre los agrega al PATH del sistema, solo agrega `java.exe` a traves de un symlink en `C:\Program Files\Common Files\Oracle\Java\javapath\` que no incluye `jar.exe`.

---

OP: al ejecutar el jar me sale graphics device initialization failed

IA: Este error significa que JavaFX no puede inicializar el motor de renderizado grafico. Tiene dos causas posibles y las dos estaban presentes.

La primera causa es que los archivos `.dll` nativos de JavaFX no estaban en la carpeta `lib`. Los archivos `.jar` contienen el codigo Java de JavaFX pero el renderizado grafico depende de librerias nativas de Windows como `prism_d3d.dll`, `prism_sw.dll`, `glass.dll` y `javafx_font.dll`. Sin esos archivos JavaFX no puede dibujar nada en pantalla.

La solucion es copiar todo el contenido de la carpeta `bin\` del SDK de JavaFX dentro de la carpeta `lib\` del proyecto, mezclando `.jar` y `.dll` en la misma carpeta.

La segunda causa es que Java no sabia donde buscar esos `.dll` en tiempo de ejecucion. Para indicarselo se usa el flag `-Djava.library.path`:

```bat
"%JDK%\java" --module-path "lib" ^
  --add-modules javafx.controls,javafx.fxml ^
  -Djava.library.path="lib" ^
  -jar GymPOS.jar
```

Con ambas cosas resueltas, los `.dll` en `lib\` y el flag apuntando a esa carpeta, el programa arranca correctamente. Para distribuir el proyecto a otros usuarios solo necesitan Java 21 instalado y la carpeta `lib\` con los `.jar` y `.dll` de JavaFX. El `ejecutar.bat` usa rutas relativas con `%~dp0` para que funcione en cualquier maquina sin modificar nada.

## #+END_QUOTE