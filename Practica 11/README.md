# GymPOS - Sistema de Gestion de Gimnasio

## Descripcion General
Aplicacion de escritorio JavaFX para gestion completa de un gimnasio.
Nombre del gimnasio configurado: **IronPeak Gym** (modificable en `gympos.properties`).

---

## Estructura de Packages

gympos/
  Main.java                       Punto de entrada JavaFX (desde aqui se ejecuta)
  config/
    Configuracion.java            Singleton de configuracion
  excepcion/
    GymPOSException.java          Excepcion base
    ClienteNoEncontradoException  Excepcion de dominio
    ValidacionException           Excepcion de validacion
    PagoException                 Excepcion de pago
  modelo/
    cliente/   Cliente.java
    membresia/ Membresia.java, TipoMembresia.java
    pago/      Pago.java
    acceso/    RegistroAcceso.java
    equipo/    Equipo.java
    clase/     ClaseGrupal.java
  servicio/
    ServicioCliente.java          CRUD clientes
    ServicioMembresia.java        Gestion membresias + renovacion automatica
    ServicioPago.java             Procesamiento async con ExecutorService
    ServicioAcceso.java           Control entrada/salida
    ServicioEquipo.java           Inventario
    ServicioClase.java            Calendario clases
    ServicioReporte.java          Generacion TXT async
  controlador/
    ClienteController.java        MVC clientes
    MembresiaController.java      MVC membresias
  vista/
    VentanaPrincipal.java         Ventana principal con menu
    cliente/
      PanelClientes.java          CRUD + tabla + filtro
      VistaCliente.java           Interfaz MVC
    membresia/
      PanelMembresias.java        CRUD + notificaciones
      VistaMembresia.java         Interfaz MVC
    acceso/
      PanelAcceso.java            Entrada/salida + aforo
    inventario/
      PanelInventario.java        CRUD equipos
    calendario/
      PanelCalendario.java        Clases + inscripciones
    reporte/
      PanelReportes.java          Estadisticas + generacion TXT
    dialogo/
      DialogoPago.java            Modal personalizado de pago
    componente/
      CampoValidado.java          Subclase TextField con validacion
      BotonIcono.java             Subclase Button con estilos
  util/
    Serializador.java             Persistencia con ObjectStream
    Validador.java                Reglas de validacion
    DatosIniciales.java           Seed data 20+ registros

**Total: 27 clases** organizadas en 10 packages.

---

## Filtrado Interactivo — Implementacion

### Clase JavaFX utilizada: FilteredList<T> y SortedList<T>

En cada panel con tabla, el filtrado funciona asi:

java

//1.Lista observable con todos los datos
ObservableList<Cliente> datos = FXCollections.observableArrayList(clientes);

//2.FilteredList envuelve la lista original
FilteredList<Cliente> DatosFiltrados = new FilteredList<>(datos, p -> true);

//3.El TextField actualiza el predicado en cada keystroke
campoBusqueda.textProperty().addListener((obs, viejo, nuevo) -> {
    DatosFiltrados.setPredicate(c -> {
        if(nuevo == null || nuevo.isBlank()) return true;
        String filtro = nuevo.toLowerCase();
        return c.GetNombre().toLowerCase().contains(filtro)
            || c.GetApellido().toLowerCase().contains(filtro)
            || c.GetCorreo().toLowerCase().contains(filtro);
    });
});

//4. SortedList mantiene el orden de columnas de la tabla
SortedList<Cliente> DatosOrdenados = new SortedList<>(DatosFiltrados);
DatosOrdenados.comparatorProperty().bind(tabla.comparatorProperty());
tabla.setItems(DatosOrdenados);
```

El criterio de filtrado en cada modulo:
- **Clientes**: nombre, apellido o correo
- **Membresias**: ID de cliente o tipo de plan
- **Acceso**: ID de cliente
- **Inventario**: nombre o categoria del equipo
- **Clases**: nombre de clase o instructor

---

## Compilacion y Ejecucion

### Requisitos
- Java 17+
- JavaFX SDK 17+ (descarga en https://openjfx.io)
- Apache Ant (para build.xml)

### Con Ant
bash
# Edita build.xml y ajusta la ruta javafx.lib
ant jar
java --module-path ~/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml -jar dist/GymPOS.jar


### Sin Ant (compilacion manual)
bash
# Compilar
javac --module-path /ruta/javafx/lib --add-modules javafx.controls \
      -d build/classes $(find src -name "*.java")

# Copiar recursos
cp -r src/main/resources/* build/classes/

# Ejecutar
java --module-path /ruta/javafx/lib --add-modules javafx.controls \
     -cp build/classes gympos.App

---

## Archivo de Configuracion

gympos.properties (se crea automaticamente en el directorio de ejecucion):

| Clave                  | Valor por defecto | Descripcion                         |
|------------------------|-------------------|-------------------------------------|
| gym.nombre             | IronPeak Gym      | Nombre mostrado en la interfaz      |
| gym.version            | 1.0               | Version del sistema                 |
| datos.ruta             | datos/            | Carpeta de archivos de persistencia |
| puntos.porPago         | 10                | Puntos otorgados por cada pago      |
| notificacion.diasAntes | 5                 | Dias antes del vencimiento para alertar |

---

## Persistencia de Datos

Se usa serializacion Java (ObjectOutputStream / ObjectInputStream).
Los archivos .ser se guardan en la carpeta datos/ relativa al directorio de ejecucion:

- datos/clientes.ser
- datos/membresias.ser
- datos/pagos.ser
- datos/accesos.ser
- datos/equipos.ser
- datos/clases.ser

Si no existen, se cargan automaticamente los 20+ registros de prueba de DatosIniciales

---

## Multithreading

| Clase            | Uso                                                        |
|------------------|------------------------------------------------------------|
| ServicioPago     | ExecutorService para procesar pagos en hilo secundario     |
| ServicioReporte  | ExecutorService para generar TXT sin bloquear la UI        |

Ambos usan Platform.runLater() para actualizar la interfaz desde el hilo secundario.

---

## Componentes Personalizados

| Clase          | Extiende   | Comportamiento propio                              |
|----------------|------------|----------------------------------------------------|
| CampoValidado  | TextField  | Valida con Predicate y aplica CSS en tiempo real   |
| BotonIcono     | Button     | Estilo visual segun TipoBoton (enum interno)       |

---

## Manejo de Excepciones

| Excepcion                      | Cuando se lanza                              |
|-------------------------------|----------------------------------------------|
| GymPOSException               | Base de todas las excepciones del dominio    |
| ClienteNoEncontradoException  | ID de cliente no existe                      |
| ValidacionException           | Campo no pasa la regla de validacion         |
| PagoException                 | Error en procesamiento de pago               |

---

## Manual de Usuario (Casos de Uso)

### Registrar un cliente
1. Menu **Clientes > Gestion de Clientes**
2. Llenar campos Nombre, Apellido, Correo, Telefono (validacion en tiempo real)
3. Click **Guardar**

### Editar un cliente
1. Doble click sobre la fila en la tabla
2. Modificar campos
3. Click **Guardar**

### Buscar en la tabla
- Escribe en el campo **Buscar** y la tabla se filtra instantaneamente

### Registrar membresia
1. Menu **Membresias > Sistema de Membresias**
2. Ingresar ID de cliente y seleccionar plan
3. Click **Registrar**

### Procesar pago
1. En panel de Membresias, ingresar ID cliente y plan
2. Click **Pagar** — se abre el dialogo modal
3. Seleccionar metodo de pago y click **Pagar Ahora**
4. El sistema procesa en segundo plano y muestra el resultado

### Registrar entrada/salida
1. Menu **Acceso > Control de Acceso**
2. Ingresar ID del cliente
3. Click **Entrada** o **Salida**
4. El aforo actual se actualiza automaticamente

### Generar reporte
1. Menu **Reportes > Generar Reporte**
2. Click **Generar Reporte TXT**
3. El archivo se guarda en la carpeta `reportes/` con marca de tiempo

---

## Diagrama UML (resumen de relaciones)

App --> VentanaPrincipal
VentanaPrincipal --> Panel* (uno por modulo)
Panel* --> *Controller --> Servicio* --> Serializador
Servicio* --> Modelo*
Panel* implementa Vista* (interfaz MVC)
CampoValidado extends TextField
BotonIcono extends Button
DialogoPago --> ServicioPago (async ExecutorService)
ServicioReporte (async ExecutorService)
