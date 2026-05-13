## Practica 9
En esta practica use la tematica de una empresa de registro de empleados para mostrar
lo que se pide, a continuacion explico un poco lo hecho:

La clase Empleado implementa Serializable para poder guardarse y cargarse en formato binario
con los metodos Guardar y Cargar. Cada campo tiene su propia validacion: el ID debe ser un
entero positivo, el nombre solo acepta letras y espacios entre 2 y 50 caracteres, el puesto
no puede estar vacio ni superar 40 caracteres, el salario debe ser mayor o igual a cero,
y la fecha de ingreso debe seguir el formato ISO YYYY-MM-DD.

La clase GestorArchivos centraliza todas las operaciones de archivo del sistema. Para texto
utiliza FileWriter y BufferedReader, para binarios usa FileOutputStream y Files.readAllBytes,
y para directorios emplea la clase File con mkdirs y list. La lectura del CSV omite el
encabezado automaticamente y salta filas con datos invalidos sin detener la ejecucion.
El backup automatico usa LocalDateTime con DateTimeFormatter para generar un timestamp
en el nombre del archivo, garantizando que cada respaldo sea unico.

La clase Main funciona como interfaz de linea de comandos con un menu de nueve opciones.
Al iniciar, carga automaticamente el CSV del directorio de datos si existe, y todas las
operaciones de agregar empleados pasan por las mismas validaciones de la clase Empleado
para mantener consistencia en los datos.

El elemento de decision propia es la exportacion a XML construida con StringBuilder sin
librerias externas. Frente al CSV, el XML resulta util cuando el sistema necesita integrarse
con plataformas empresariales como SAP o sistemas que consumen servicios SOAP, ya que estos
esperan datos en ese formato de manera nativa. Ademas, si el modelo de empleado crece para
incluir subelementos como historial de puestos o contactos de emergencia, el XML escala
de forma natural con nodos anidados, algo que el CSV no puede representar sin perder claridad.
