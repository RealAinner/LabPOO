# Manual de Usuario — GymPOS
## Sistema de Gestion de Gimnasio — IronPeak Gym
**Version:** 1.0

---

## Tabla de Contenidos

1. Introduccion
2. Requisitos para ejecutar
3. Iniciar la aplicacion
4. Pantalla principal y navegacion
5. Caso de Uso 1 — Registrar un nuevo cliente
6. Caso de Uso 2 — Buscar un cliente
7. Caso de Uso 3 — Editar un cliente
8. Caso de Uso 4 — Eliminar un cliente
9. Caso de Uso 5 — Registrar una membresia
10. Caso de Uso 6 — Renovar una membresia
11. Caso de Uso 7 — Cancelar una membresia
12. Caso de Uso 8 — Procesar un pago
13. Caso de Uso 9 — Registrar entrada al gimnasio
14. Caso de Uso 10 — Registrar salida del gimnasio
15. Caso de Uso 11 — Agregar equipo al inventario
16. Caso de Uso 12 — Eliminar equipo del inventario
17. Caso de Uso 13 — Agregar una clase grupal
18. Caso de Uso 14 — Inscribir cliente a una clase
19. Caso de Uso 15 — Generar reporte TXT
20. Notificaciones automaticas
21. Mensajes de error frecuentes
22. Atajos de teclado y mouse

---

## 1. Introduccion

GymPOS es un sistema de gestion para gimnasios que permite administrar clientes,
membresias, pagos, control de acceso, inventario de equipos y clases grupales
desde una sola aplicacion de escritorio.

Toda la informacion se guarda automaticamente en disco cada vez que se realiza
una operacion. No es necesario hacer guardados manuales.

---

## 2. Requisitos para ejecutar

- Java 21 instalado en la computadora
- Carpeta `lib` con los archivos `.jar` y `.dll` de JavaFX SDK 21
- Archivo `GymPOS.jar` y `ejecutar.bat` en la misma carpeta que `lib`

---

## 3. Iniciar la aplicacion

1. Abre la carpeta del proyecto
2. Haz doble clic en el archivo `ejecutar.bat`
3. La ventana principal de GymPOS se abre automaticamente

La primera vez que inicia el sistema, se cargan automaticamente 20 clientes,
20 membresias, 20 pagos, 8 equipos, 5 clases grupales y 10 registros de acceso
de prueba para que puedas explorar el sistema de inmediato.

---

## 4. Pantalla principal y navegacion

Al abrir la aplicacion veras el nombre del gimnasio en el centro de la pantalla
y una barra de menu en la parte superior con las siguientes opciones:

| Menu       |         Opcion        |                Descripcion                   |
|------------|-----------------------|----------------------------------------------|
| Clientes   | Gestion de Clientes   | Alta, baja, edicion y busqueda de clientes   |
| Membresias | Sistema de Membresias | Registro, renovacion y cancelacion de planes |
| Acceso     | Control de Acceso     | Registro de entradas y salidas del gimnasio  |
| Inventario | Equipos               | Administracion del inventario de equipos     |
| Inventario | Clases Grupales       | Calendario e inscripciones a clases          |
| Reportes   | Generar Reporte       | Estadisticas y exportacion a archivo TXT     |
| Ayuda      | Acerca de...          | Informacion de la version del sistema        |

Haz clic en cualquier opcion del menu para navegar al modulo correspondiente.

---

## 5. Caso de Uso 1 — Registrar un nuevo cliente

**Objetivo:** Dar de alta a un nuevo cliente en el sistema.

**Pasos:**

1. Haz clic en **Clientes > Gestion de Clientes**
2. En el formulario inferior llena los campos:
   - **Nombre** — minimo 2 caracteres, maximo 50
   - **Apellido** — minimo 2 caracteres, maximo 50
   - **Correo** — debe tener formato valido (ejemplo@correo.com)
   - **Telefono** — exactamente 10 digitos numericos
3. Mientras escribes, cada campo cambia de color:
   - **Verde** — el dato es correcto
   - **Rojo** — el dato tiene un error, revisa el formato
4. Cuando todos los campos esten en verde haz clic en **Guardar**
5. El mensaje **"Cliente registrado correctamente"** aparece en verde
6. El nuevo cliente aparece en la tabla de inmediato

**Resultado esperado:** El cliente queda registrado con 0 puntos y estado activo.

**Posibles errores:**

|                     Mensaje                  |                      Causa                   |                       Solucion                      |
|----------------------------------------------|----------------------------------------------|-----------------------------------------------------|
| Corrige los campos marcados antes de guardar | Hay campos en rojo                           | Revisa el formato de cada campo                     |
| Minimo 2 caracteres                          | Nombre o apellido muy corto                  | Escribe al menos 2 letras                           |
| Formato de correo invalido                   | El correo no tiene @ o dominio               | Usa el formato correo@ejemplo.com                   |
| Exactamente 10 digitos numericos             | El telefono tiene letras o menos/mas digitos | Escribe solo los 10 digitos sin espacios ni guiones |

---

## 6. Caso de Uso 2 — Buscar un cliente

**Objetivo:** Encontrar rapidamente un cliente en la tabla.

**Pasos:**

1. Haz clic en **Clientes > Gestion de Clientes**
2. En el campo **Buscar** de la parte superior escribe cualquier parte del:
   - Nombre del cliente
   - Apellido del cliente
   - Correo electronico
3. La tabla se actualiza instantaneamente mostrando solo los registros que coinciden

**Ejemplo:** Si escribes "car" la tabla mostrara todos los clientes cuyo nombre,
apellido o correo contenga esas letras.

4. Para ver todos los clientes nuevamente borra el texto del campo de busqueda

---

## 7. Caso de Uso 3 — Editar un cliente

**Objetivo:** Modificar los datos de un cliente existente.

**Pasos:**

1. Haz clic en **Clientes > Gestion de Clientes**
2. En la tabla haz **doble clic** sobre el cliente que deseas editar
   - Los datos del cliente se cargan automaticamente en el formulario
3. Modifica los campos que necesites
4. Verifica que todos los campos esten en verde
5. Haz clic en **Guardar**
6. El mensaje **"Cliente actualizado correctamente"** aparece en verde

**Nota:** Tambien puedes seleccionar el cliente y presionar **Enter** para cargarlo en el formulario.

---

## 8. Caso de Uso 4 — Eliminar un cliente

**Objetivo:** Dar de baja a un cliente del sistema.

**Pasos:**

1. Haz clic en **Clientes > Gestion de Clientes**
2. Haz clic una vez sobre el cliente que deseas eliminar para seleccionarlo
3. Presiona la tecla **Delete** en tu teclado
4. Aparece una ventana de confirmacion con el nombre del cliente
5. Haz clic en **OK** para confirmar la eliminacion
6. El cliente desaparece de la tabla

**Advertencia:** Esta accion no se puede deshacer. Verifica que sea el cliente
correcto antes de confirmar.

---

## 9. Caso de Uso 5 — Registrar una membresia

**Objetivo:** Asignar un plan de membresia a un cliente.

**Pasos:**

1. Haz clic en **Membresias > Sistema de Membresias**
2. En el formulario inferior escribe el **ID del cliente** en el campo correspondiente
   - Puedes consultar el ID en el modulo de Gestion de Clientes
3. Selecciona el **Plan** deseado en el combo:

|   Plan   | Precio base | Descuento | Precio final |
|----------|-------------|-----------|--------------|
| Basico   | $299.00     | 0%        | $299.00      |
| Estandar | $499.00     | 10%       | $449.10      |
| Premium  | $799.00     | 20%       | $639.20      |
| VIP      | $1,299.00   | 30%       | $909.30      |

4. Haz clic en **Registrar**
5. El mensaje **"Membresia registrada"** aparece en verde
6. La membresia aparece en la tabla con fecha de vencimiento a 30 dias

**Posibles errores:**

|               Mensaje                    |                Causa                  |                      Solucion                     |
|------------------------------------------|---------------------------------------|---------------------------------------------------|
| El cliente ya tiene una membresia activa | El cliente ya tiene un plan vigente   | Primero cancela la membresia actual o usa Renovar |
| ID de cliente invalido                   | El campo esta vacio o contiene letras | Escribe solo el numero del ID                     |

---

## 10. Caso de Uso 6 — Renovar una membresia

**Objetivo:** Extender por 30 dias mas la membresia de un cliente.

**Pasos:**

1. Haz clic en **Membresias > Sistema de Membresias**
2. Escribe el **ID del cliente** en el campo correspondiente
3. Haz clic en **Renovar**
4. La fecha de vencimiento se actualiza a 30 dias desde hoy
5. El estado cambia a **Activa** si estaba vencida

---

## 11. Caso de Uso 7 — Cancelar una membresia

**Objetivo:** Desactivar la membresia activa de un cliente.

**Pasos:**

1. Haz clic en **Membresias > Sistema de Membresias**
2. Escribe el **ID del cliente** en el campo correspondiente
3. Haz clic en **Cancelar Mem.**
4. La membresia cambia a estado **Cancelada** en la tabla

---

## 12. Caso de Uso 8 — Procesar un pago

**Objetivo:** Cobrar al cliente el monto de su membresia.

**Pasos:**

1. Haz clic en **Membresias > Sistema de Membresias**
2. Escribe el **ID del cliente** y selecciona el **Plan**
3. Haz clic en **Pagar**
4. Se abre la ventana de **Procesar Pago** (la ventana principal queda bloqueada)
5. En el combo selecciona el **metodo de pago**:
   - Efectivo
   - Tarjeta
   - Transferencia
6. Haz clic en **Pagar Ahora**
7. Aparece un indicador girando mientras se procesa el pago (aproximadamente 2 segundos)
8. Si el pago es exitoso el mensaje **"Pago completado con exito"** aparece en verde
9. Haz clic en **Cerrar** para regresar al sistema

**Nota:** En caso de que el pago sea rechazado el mensaje aparece en rojo y
puedes intentarlo nuevamente sin cerrar la ventana.

---

## 13. Caso de Uso 9 — Registrar entrada al gimnasio

**Objetivo:** Registrar que un cliente ingreso al gimnasio.

**Pasos:**

1. Haz clic en **Acceso > Control de Acceso**
2. En la parte superior derecha se muestra el **aforo actual** (cuantas personas estan dentro)
3. Escribe el **ID del cliente** en el campo de texto
4. Haz clic en **Entrada** o presiona **Enter** en el teclado
5. El mensaje **"Entrada registrada"** aparece en verde
6. El registro aparece en la tabla con la hora de entrada y estado **En gimnasio**
7. El contador de aforo se actualiza automaticamente

**Nota:** Si el cliente ya esta registrado como adentro, el sistema mostrara
el mensaje **"El cliente ya se encuentra en el gimnasio"** y no duplicara el registro.

---

## 14. Caso de Uso 10 — Registrar salida del gimnasio

**Objetivo:** Registrar que un cliente salio del gimnasio.

**Pasos:**

1. Haz clic en **Acceso > Control de Acceso**
2. Escribe el **ID del cliente** en el campo de texto
   - Tambien puedes hacer **doble clic** sobre el registro en la tabla para cargar el ID automaticamente
3. Haz clic en **Salida**
4. El mensaje **"Salida registrada"** aparece en verde
5. El registro en la tabla se actualiza mostrando la hora de salida y estado **Salio**
6. El contador de aforo disminuye automaticamente

---

## 15. Caso de Uso 11 — Agregar equipo al inventario

**Objetivo:** Registrar un nuevo equipo en el inventario del gimnasio.

**Pasos:**

1. Haz clic en **Inventario > Equipos**
2. En el formulario inferior llena los campos:
   - **Nombre** — nombre descriptivo del equipo
   - **Categoria** — tipo de equipo (ejemplo: Cardio, Fuerza, Combate)
   - **Cantidad** — numero de unidades disponibles (debe ser mayor a 0)
   - **Estado** — selecciona el estado inicial del equipo:

| Estado        |          Significado             |
|---------------|----------------------------------|
| DISPONIBLE    | El equipo esta listo para usarse |
| EN_USO        | El equipo esta siendo utilizado  |
| MANTENIMIENTO | El equipo esta en reparacion     |
| DADO_DE_BAJA  | El equipo ya no esta en servicio |

3. Haz clic en **+ Agregar**
4. El equipo aparece en la tabla y los campos se limpian automaticamente

---

## 16. Caso de Uso 12 — Eliminar equipo del inventario

**Objetivo:** Remover un equipo del inventario.

**Pasos:**

1. Haz clic en **Inventario > Equipos**
2. Haz clic sobre el equipo que deseas eliminar para seleccionarlo
3. Presiona la tecla **Delete** en tu teclado
4. El equipo se elimina de la tabla inmediatamente

**Nota:** Puedes hacer **doble clic** sobre un equipo para cargar sus datos
en el formulario y visualizarlos antes de eliminar.

---

## 17. Caso de Uso 13 — Agregar una clase grupal

**Objetivo:** Agregar una nueva clase al calendario del gimnasio.

**Pasos:**

1. Haz clic en **Inventario > Clases Grupales**
2. En el formulario de clases llena los campos:
   - **Clase** — nombre de la clase (ejemplo: Zumba, Spinning, Yoga)
   - **Instructor** — nombre del instructor a cargo
   - **Fecha (ISO)** — fecha y hora en formato exacto: `2026-06-15T09:00`
   - **Capacidad** — numero maximo de personas permitidas
3. Haz clic en **Agregar Clase**
4. La clase aparece en la tabla con 0 inscritos

**Formato de fecha:** El formato debe ser `YYYY-MM-DDTHH:mm` sin espacios.
Ejemplos correctos:
- `2026-07-01T08:00` — 1 de julio de 2026 a las 8:00 AM
- `2026-12-25T10:30` — 25 de diciembre de 2026 a las 10:30 AM

---

## 18. Caso de Uso 14 — Inscribir cliente a una clase

**Objetivo:** Registrar a un cliente en una clase grupal.

**Pasos:**

1. Haz clic en **Inventario > Clases Grupales**
2. En la tabla busca la clase deseada
   - Puedes hacer **doble clic** sobre la clase para cargar su ID automaticamente
3. En el formulario de inscripcion escribe:
   - **ID Clase** — el ID de la clase (columna ID en la tabla)
   - **ID Cliente** — el ID del cliente a inscribir
4. Haz clic en **Inscribir**
5. El contador de **Inscritos/Cap.** en la tabla se actualiza

**Posibles errores:**

|            Mensaje        |                     Causa                         |             Solucion               |
|---------------------------|---------------------------------------------------|------------------------------------|
| Sin lugares o ya inscrito | La clase esta llena o el cliente ya esta inscrito | Verifica el aforo o usa otra clase |
| Clase no encontrada       | El ID de clase no existe                          | Verifica el ID en la tabla         |

---

## 19. Caso de Uso 15 — Generar reporte TXT

**Objetivo:** Exportar un reporte completo del estado del gimnasio a un archivo de texto.

**Pasos:**

1. Haz clic en **Reportes > Generar Reporte**
2. El panel muestra las estadisticas actuales del sistema:
   - Total de clientes registrados
   - Membresias activas
   - Total recaudado en pagos completados
3. Haz clic en **Generar Reporte TXT**
4. La barra de progreso se activa indicando que el archivo se esta generando
5. Cuando termina, el mensaje muestra la ruta exacta del archivo generado
6. El archivo se guarda en la carpeta `reportes/` con la fecha y hora en el nombre

**Ejemplo de nombre de archivo:**
```
reportes/reporte_2026-05-16_14-30.txt
```

**Contenido del reporte:**
- Lista completa de clientes con puntos
- Lista de membresias con estado y fecha de vencimiento
- Lista de pagos con metodo, monto y estado
- Total recaudado en pagos completados

---

## 20. Notificaciones automaticas

El sistema genera alertas automaticas en las siguientes situaciones:

**Membresias proximas a vencer:**
Al abrir el modulo de Membresias, si existen clientes cuya membresia vence
en los proximos 5 dias, aparece una ventana de advertencia con la lista de
clientes afectados y los dias que les quedan.

Esta alerta se puede desactivar cambiando el valor `notificacion.diasAntes`
en el archivo `gympos.properties`.

---

## 21. Mensajes de error frecuentes

|                Mensaje                    |        Modulo      |                     Causa                          |
|-------------------------------------------|--------------------|----------------------------------------------------|
| ID de cliente invalido                    | Membresias, Acceso | El campo de ID esta vacio o contiene texto         |
| Cliente con ID X no encontrado            | Membresias         | El ID ingresado no existe en el sistema            |
| El cliente ya tiene una membresia activa  | Membresias         | Intentar registrar dos membresias al mismo cliente |
| El cliente ya se encuentra en el gimnasio | Acceso             | Registrar entrada dos veces sin salida intermedia  |
| El cliente no tiene entrada activa        | Acceso             | Registrar salida sin haber registrado entrada      |
| Sin lugares o ya inscrito                 | Clases             | La clase esta llena o el cliente ya fue inscrito   |
| Datos invalidos. Fecha: 2026-06-01T09:00  | Clases             | El formato de fecha no es correcto                 |
| Cantidad invalida                         | Inventario         | El campo cantidad esta vacio o contiene letras     |
| Nombre invalido                           | Inventario         | El campo nombre esta vacio                         |

---

## 22. Atajos de teclado y mouse

|           Accion             |   Modulo   |           Como hacerlo              |
|------------------------------|------------|-------------------------------------|
| Cargar cliente en formulario | Clientes   | Doble clic en la fila               |
| Cargar cliente en formulario | Clientes   | Seleccionar fila y presionar Enter  |
| Eliminar cliente             | Clientes   | Seleccionar fila y presionar Delete |
| Guardar formulario           | Clientes   | Clic en Guardar o Enter en el boton |
| Registrar entrada rapida     | Acceso     | Escribir ID y presionar Enter       |
| Cargar ID de registro        | Acceso     | Doble clic en la fila               |
| Ver datos de equipo          | Inventario | Doble clic en la fila               |
| Eliminar equipo              | Inventario | Seleccionar fila y presionar Delete |
| Cargar ID de clase           | Clases     | Doble clic en la fila               |
| Filtrar en cualquier tabla   | Todos      | Escribir en el campo Buscar         |
| Limpiar filtro               | Todos      | Borrar el texto del campo Buscar    |
| Ordenar tabla por columna    | Todos      | Clic en el encabezado de la columna |
