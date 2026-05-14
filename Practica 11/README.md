# GymPOS - Sistema de Gestion de Gimnasio

## ZIP 1 — Estructura Base y Modelos

### Descripcion
Commit inicial con la estructura completa del proyecto, modelos de dominio, excepciones personalizadas, utilidades de persistencia y datos de prueba.

### Estructura de Packages

```
gympos/
  modelo/
    cliente/   - Cliente.java
    membresia/ - Membresia.java, TipoMembresia.java
    pago/      - Pago.java
    acceso/    - RegistroAcceso.java
    equipo/    - Equipo.java
    clase/     - ClaseGrupal.java
  excepcion/   - GymPOSException, ClienteNoEncontradoException,
                 ValidacionException, PagoException
  config/      - Configuracion.java (Singleton)
  util/        - Serializador.java, Validador.java, DatosIniciales.java
  vista/       - (ZIP 2)
  controlador/ - (ZIP 2)
  servicio/    - (ZIP 2+)
```

### Clases incluidas en este ZIP
1. Cliente
2. Membresia
3. TipoMembresia (enum)
4. Pago
5. RegistroAcceso
6. Equipo
7. ClaseGrupal
8. GymPOSException
9. ClienteNoEncontradoException
10. ValidacionException
11. PagoException
12. Configuracion
13. Serializador
14. Validador
15. DatosIniciales

### Datos de prueba
`DatosIniciales` genera automaticamente:
- 20 clientes con nombre, correo, telefono y puntos
- 20 membresias (tipos variados)
- 20 pagos completados
- 8 equipos de gimnasio
- 5 clases grupales
- 10 registros de acceso

### Persistencia
Se usa `Serializador` con `ObjectOutputStream`/`ObjectInputStream` para guardar/cargar listas en archivos `.ser` dentro de la carpeta `datos/`.

### Configuracion
`gympos.properties` contiene el nombre del gimnasio, rutas y parametros configurables.
