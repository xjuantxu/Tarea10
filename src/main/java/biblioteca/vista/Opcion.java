package biblioteca.vista;

/**
 * Enumeración Opcion.
 * Representa las opciones del menú principal de la biblioteca.
 */
    public enum Opcion {

        INSERTAR_USUARIO("Insertar usuario"),
        BORRAR_USUARIO("Borrar usuario"),
        MOSTRAR_USUARIOS("Mostrar usuarios"),
        INSERTAR_LIBRO("Insertar libro"),
        BORRAR_LIBRO("Borrar libro"),
        MOSTRAR_LIBROS("Mostrar libros"),
        NUEVO_PRESTAMO("Nuevo préstamo"),
        DEVOLVER_PRESTAMO("Devolver préstamo"),
        MOSTRAR_PRESTAMOS("Mostrar todos los préstamos"),
        MOSTRAR_PRESTAMOS_USUARIOS("Mostrar préstamos de un usuario"),
        SALIR("Salir");

        // atributo para generar la descripción de cada opción
        private final String descripcion;

        // Constructor
        private Opcion(String descripcion) {
            this.descripcion = descripcion;
        }

        @Override
        public String toString() {
            return descripcion;
        }
    }
