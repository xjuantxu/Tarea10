package biblioteca.modelo.negocio;

import biblioteca.modelo.dominio.Prestamo;
import biblioteca.modelo.dominio.Libro;
import biblioteca.modelo.dominio.Usuario;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Clase Préstamos (paquete negocio).
 * Gestiona la colección de préstamos de la biblioteca.
 * Permite realizar préstamos, devoluciones y listados.
 * Utiliza ArrayList para almacenamiento dinámico.
 */
public class Prestamos {

    // Atributo
    private List<Prestamo> prestamos;

    // Constructor
    public Prestamos() {
        prestamos = new ArrayList<>();
    }

    public Prestamo prestar(Libro libro, Usuario usuario, LocalDate fecha) throws IllegalArgumentException {

        if (libro == null || usuario == null || fecha == null) {
            throw new IllegalArgumentException("Datos inválidos");
        }

        Prestamo nuevo = new Prestamo(libro, usuario, fecha);

        if (prestamos.contains(nuevo)) {
            throw new IllegalArgumentException("El préstamo ya existe");
        }

        prestamos.add(new Prestamo(nuevo)); // copia profunda

        return nuevo;
    }

    public boolean devolver(Libro libro, Usuario usuario, LocalDate fecha) {

        Prestamo buscado = new Prestamo(libro, usuario, fecha);

        int index = prestamos.indexOf(buscado);

        if (index != -1) {
            Prestamo p = prestamos.get(index);

            if (!p.isDevuelto()) {
                p.devolver(fecha);
                return true;
            }
        }

        return false;
    }

    public List<Prestamo> todos() {

        List<Prestamo> resultado = new ArrayList<>();

        for (Prestamo p : prestamos) {
            resultado.add(new Prestamo(p));
        }

        Collections.sort(resultado);

        return resultado;
    }

    public List<Prestamo> todos(Usuario usuario) {

        List<Prestamo> resultado = new ArrayList<>();

        if (usuario == null)
            return resultado;

        for (Prestamo p : prestamos) {
            if (p.getUsuario().equals(usuario)) {
                resultado.add(new Prestamo(p));
            }
        }

        Collections.sort(resultado);

        return resultado;
    }
}