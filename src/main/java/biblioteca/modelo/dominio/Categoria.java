package biblioteca.modelo.dominio;

/**
 * Enumeración de categorías de libros disponibles en la biblioteca.
 */
public enum Categoria {
    NOVELA("Novela"),
    ENSAYO("Ensayo"),
    INFANTIL("Infantil"),
    COMIC("Cómic"),
    POESIA("Poesía"),
    TECNICO("Técnico"),
    OTROS("Otros");

    // atributo para generar la descripción de cada opción
    private final String descripcion;

    // Constructor
    private Categoria(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return descripcion;
    }
}

