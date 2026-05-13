## #+BEGIN_QUOTE

OP: me pidieron hacer una practica de estructuras de coleccion en java, tengo que usar como 4 distintas pero no se bien cual usar para que cosa

IA: Hola, buena pregunta. La clave es elegir cada estructura segun lo que vas a hacer con los datos, no solo usarlas porque si. Por ejemplo, si vas a necesitar buscar rapido por un identificador unico como una matricula, un HashMap es ideal porque te da busqueda en O(1). Si solo necesitas una lista ordenada que puedas recorrer, un ArrayList funciona bien. Que tematica vas a usar para la practica?

OP: puse tematica de escuela, alumnos con matricula nombre carrera semestre y promedio

IA: Perfecto, con eso tienes buena variedad. Te sugiero esto: usa el ArrayList como tu lista principal de alumnos porque es compatible con Streams y puedes ordenarla facilmente. Luego usa un HashMap indexado por matricula para que cuando busques un alumno especifico no tengas que recorrer toda la lista. Para las carreras disponibles un HashSet funciona bien porque garantiza que no se repitan sin que tengas que hacer nada extra. Y si necesitas llevar un historial de operaciones, un LinkedList es bueno porque insertar al final siempre es O(1). Asi cubres las cuatro con sentido.

OP: ah ok y lo del comparable como lo hago? tengo que implementarlo en alumno?

IA: Si, Comparable va directo en la clase Alumno. Lo que haces es que la clase implemente Comparable<Alumno> y sobreescribes el metodo compareTo. La pregunta es: cual seria el orden natural de un alumno? Lo mas comun es por matricula porque es el identificador unico. Quedaria algo asi: return this.matricula.compareTo(otro.matricula);. Ese seria el orden por defecto cuando uses Collections.sort o Comparator.naturalOrder()`.

OP: y si quiero ordenar por promedio o por nombre tambien? eso va en la misma clase?

IA: No, para eso usas Comparator en lugar de Comparable. La diferencia es que Comparable define UN solo orden natural dentro de la clase, y Comparator es externo, lo defines aparte y puedes tener todos los que quieras. Te recomiendo hacer una clase aparte, algo como ComparadorAlumno, y ahi pones tus criterios como constantes estaticas. Por ejemplo uno PorPromedioDesc y otro PorNombre. Asi los reutilizas en cualquier parte del codigo sin repetirte.

OP: ya hice el gestor con las 4 estructuras, como seria lo de los streams? tengo que hacer metodos aparte o como

IA: Si, lo mejor es que el gestor tenga metodos que internamente usen Streams y te devuelvan el resultado ya filtrado u ordenado. Por ejemplo un metodo FiltrarPorCarrera que reciba el nombre de la carrera y te regrese un List<Alumno> con solo los de esa carrera. Dentro usas ListaAlumnos.stream().filter(...).collect(Collectors.toList()). Asi el que llame al metodo no tiene que saber nada de Streams, solo recibe la lista.

OP: me pidieron tambien una busqueda compuesta que combine dos criterios, no entiendo bien eso

IA: Es mas sencillo de lo que suena. En lugar de un solo .filter(), encadenas varios. Por ejemplo si quieres alumnos de una carrera especifica, en un semestre dado y con promedio mayor a cierto valor, pones tres filtros uno tras otro sobre el mismo stream. Java los aplica todos en un solo recorrido, no hace tres pasadas por la lista. Para tu tematica de escuela tiene mucho sentido filtrarlo asi para algo como candidatos a beca: que sean de la carrera X, esten en semestre Y y tengan promedio mayor a Z.

OP: y el iterador? en que parte lo pongo, porque con el for-each ya recorro todo no

IA: Si, el for-each internamente usa un iterador pero no lo ves. Para la practica conviene que tengas al menos un metodo donde uses el Iterator de forma explicita para que se note que sabes como funciona. Algo como: Iterator<Alumno> iter = ListaAlumnos.iterator(); y luego un while(iter.hasNext()) con iter.next(). Ponlo en un metodo tipo MostrarConIterador en el gestor y llamalo desde el menu. Asi cumples el requisito y queda claro.

OP: ya casi termino, me falta el analisis de tiempo, como mido eso en java?

IA: Con System.nanoTime(). Guardas el valor antes de la operacion en una variable inicio, ejecutas la operacion, luego haces System.nanoTime() - inicio y divides entre 1_000_000.0 para convertirlo a milisegundos. No uses System.currentTimeMillis() porque tiene menos precision. Para la practica puedes tener una opcion en el menu que ejecute cada operacion y imprima su tiempo, y de paso anotas la complejidad teorica de cada una como comentario o en el README.

OP: listo ya termine, salio todo, gracias

IA: Perfecto. Solo revisa que en el metodo de agregar alumno no te quede ninguna expresion aritmetica suelta sin asignar, eso da error de compilacion en Java. Y asegurate de que los datos de prueba sean al menos 15 registros bien distribuidos entre carreras y semestres para que los filtros y la busqueda compuesta tengan resultados visibles al probarlos. Suerte con la entrega.

## #+END_QUOTE