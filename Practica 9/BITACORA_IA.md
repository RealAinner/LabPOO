## #+BEGIN_QUOTE

OP: necesito hacer un sistema de archivos en Java, que registre empleados
no se por donde empezar

IA: Lo primero es definir bien la clase principal, la de Empleado.
Antes de pensar en archivos, necesitas tener claro que datos vas a manejar
y como los vas a validar. Que campos crees que debe tener un empleado?

OP: pues id, nombre, puesto, salario y fecha de ingreso.

IA Bien. Ahora, la practica pide una clase serializable, asi que desde
el principio haz que Empleado implemente Serializable. Y ya que defines los
campos, agrega las validaciones en los setters, no las dejes para despues.
Que reglas tendria cada campo?

OP: el id que sea positivo, el nombre solo letras, el salario que no sea
negativo pero la fecha no se, como la valido?

IA: Usa LocalDate.parse(), si la cadena no tiene formato YYYY-MM-DD
lanza una excepcion que puedes atrapar. Algo asi:

    try {
        LocalDate.parse(fecha);
        return true;
    } catch (DateTimeParseException e) {
        return false;
    }

Con eso cubres la validacion de fecha sin librerias externas.

OP: ya tengo la clase empleado con sus validaciones y ahora como hago
lo de guardar y cargar el objeto binario?

IA: Dentro de la misma clase agrega dos metodos estaticos, Guardar y Cargar.
El primero usa ObjectOutputStream envolviendo un FileOutputStream, y el segundo
ObjectInputStream sobre FileInputStream. Ambos deben lanzar las excepciones
para que Main las maneje, no las atrapes ahi adentro.

OP: ahora la clase de gestion de archivos, la hago aparte?

IA: Si, mantén separadas las responsabilidades. Empleado solo sabe de si mismo,
GestorArchivos sabe de operaciones de disco. Ahi van lectura y escritura de texto,
binarios, directorios, el CSV y el backup.

OP: para leer el csv uso scanner o bufferedreader?

IA: BufferedReader. Scanner tiene mas overhead y BufferedReader con readLine()
es mas eficiente para archivos grandes porque lee bloques en memoria en lugar de
caracter por caracter. No olvides saltarte la primera linea que es el encabezado.

OP: y si una fila del csv tiene datos invalidos, detengo todo?

IA: No, envuelve la construccion del Empleado en un try-catch dentro del while
y si falla solo imprime un aviso y continuas con la siguiente fila. Asi el programa
no se cae por un dato sucio.

OP: el backup como lo hago para que tenga timestamp

IA: Con LocalDateTime y DateTimeFormatter. Defines el patron como
"yyyyMMdd_HHmmss", formateas la fecha actual y la concatenas al nombre del archivo
antes de copiarlo con Files.copy. Cada backup queda con un nombre unico.

OP: para el elemento propio pense en exportar a xml crees que tenga sentido?

IA: Tiene mucho sentido y es facil de justificar. Con StringBuilder construyes
el XML manualmente sin dependencias, y el formato es util cuando necesitas integrarte
con sistemas empresariales que esperan XML de forma nativa, algo que el CSV no puede
ofrecer directamente. Anota esa justificacion en el README.
