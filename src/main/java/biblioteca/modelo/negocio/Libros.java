package biblioteca.modelo.negocio;

import biblioteca.modelo.dominio.Audiolibro;
import biblioteca.modelo.dominio.Libro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Clase Libros (paquete negocio).
 * Gestiona la colección de libros de la biblioteca.
 * Permite alta, baja, búsqueda y listado de libros.
 * Utiliza ArrayList para almacenamiento dinámico.
 */
public class Libros {

    // Atributo
    private List<Libro> libros;

    // Constructor
    public Libros() {
        libros = new ArrayList<>();
    }


    public void alta(Libro libro) throws IllegalArgumentException {
        if (libro == null) {
            throw new IllegalArgumentException("El libro no puede ser nulo");
        }
        if (libros.contains(libro)) {
            throw new IllegalArgumentException("El libro ya existe");
        }

        libros.add(new Libro(libro)); // copia profunda
    }

    public boolean baja(Libro libro) {
        return libro != null && libros.remove(libro);
    }

    public Libro buscar(Libro libro) {

        if (libro == null)
            return null;

        int index = libros.indexOf(libro);

        if (index != -1) {

            Libro encontrado = libros.get(index);

            if (encontrado instanceof Audiolibro) {
                return new Audiolibro((Audiolibro) encontrado);
            }

            return new Libro(encontrado);
        }

        return null;
    }


    public List<Libro> todos() {

        List<Libro> copia = new ArrayList<>();

        for (Libro l : libros) {

            if (l instanceof Audiolibro) {
                copia.add(new Audiolibro((Audiolibro) l));
            } else {
                copia.add(new Libro(l));
            }

        }

        Collections.sort(copia);

        return copia;
    }
}