## Practica 8
En esta practica use la tematica de una escuela de manera simplificada para poder mostrar
lo que se pide en esta practica, a continuacion explico un poco lo hecho:

La clase gestora GestorEscolar utiliza cuatro estructuras de coleccion distintas:
    ArrayList: Lista principal de alumnos, permite acceso por indice y es compatible con Streams.
    LinkedList: Historial de operaciones, las inserciones siempre son al final en O(1).
    HashMap: Indice de alumnos por matricula, permite busqueda directa en O(1) promedio.
    HashSet: Carreras registradas, garantiza unicidad sin esfuerzo adicional.

La clase Alumno implementa Comparable para definir un orden natural por matricula,
y la clase ComparadorAlumno ofrece dos Comparators adicionales: por promedio descendente
y por nombre ascendente, utiles para ordenamientos especificos como cuadros de honor o listas oficiales.

El elemento de decision propia es la busqueda compuesta en el metodo BusquedaCompuesta,
que filtra simultaneamente por carrera, semestre y promedio minimo usando Streams encadenados.
En un sistema real, esta consulta serviria para identificar candidatos a becas o reconocimientos
por cohorte academica, evitando tener que hacer multiples consultas separadas para llegar
al mismo resultado.
