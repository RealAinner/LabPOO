# Practica 11
## GymPOS - Sistema de Gestion de Gimnasio
### Clases incluidas en esta practica
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
"DatosIniciales" genera automaticamente:
- 20 clientes con nombre, correo, telefono y puntos
- 20 membresias (tipos variados)
- 20 pagos completados
- 8 equipos de gimnasio
- 5 clases grupales
- 10 registros de acceso

### Persistencia
Se usa "Serializador" con "ObjectOutputStream"/"ObjectInputStream" para guardar/cargar listas en archivos ".ser" dentro de la carpeta "datos/".

### Configuracion
"gympos.properties" contiene el nombre del gimnasio, rutas y parametros configurables.
