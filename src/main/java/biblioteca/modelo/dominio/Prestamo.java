package biblioteca.modelo.dominio;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Clase Prestamo.
 * Representa el préstamo de un libro a un usuario.
 * Contiene libro, usuario, fecha de inicio, fecha de fin y estado de devolución.
 */
public class Prestamo implements Comparable<Prestamo> {

    // Atributos
    private Libro libro;
    private Usuario usuario;
    private LocalDate inicio;
    private LocalDate fin;
    private boolean devuelto;

    // Constructor Principal
    public Prestamo(Libro libro, Usuario usuario, LocalDate inicio) {
        if (libro == null)
            throw new IllegalArgumentException("Libro no puede ser nulo");
        if (usuario == null)
            throw new IllegalArgumentException("Usuario no puede ser nulo");
        if (inicio == null)
            throw new IllegalArgumentException("Fecha no puede ser nula");
        this.libro = new Libro(libro);       // copia profunda
        this.usuario = new Usuario(usuario);
        this.inicio = inicio;
        this.fin = null;
        this.devuelto = false;
    }

    // Constructor copia
    public Prestamo(Prestamo otro) {
        this.libro = new Libro(otro.getLibro());
        this.usuario = new Usuario(otro.getUsuario());
        this.inicio = otro.getInicio();
        this.fin = otro.getFin();
        this.devuelto = otro.isDevuelto();
    }

    //Getters y setters
    public Libro getLibro() {
        return new Libro(libro);
    }
    public Usuario getUsuario() {
        return new Usuario(usuario);
    }
    public LocalDate getInicio() {
        return inicio;
    }
    public LocalDate getFin() {
        return fin;
    }
    public boolean isDevuelto() {
        return devuelto;
    }


    public void devolver(LocalDate fecha) {
        if (!devuelto) {
            this.fin = fecha;
            this.devuelto = true;
        }
    } //Metodo de devolver libro

    //Overrides
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;

        if (!(obj instanceof Prestamo))
            return false;

        Prestamo other = (Prestamo) obj;

        return libro.equals(other.libro) &&
                usuario.equals(other.usuario) &&
                inicio.equals(other.inicio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(libro, usuario, inicio);
    }

    //ToString
    @Override
    public String toString() {
        return "Prestamo{" +
                "libro=" + libro +
                ", usuario=" + usuario +
                ", inicio=" + inicio +
                ", fin=" + fin +
                ", devuelto=" + devuelto +
                '}';
    }

    @Override
    public int compareTo(Prestamo prestamo) {

        // Primero por fecha (descendente)
        int comparacionFecha = prestamo.getInicio().compareTo(this.getInicio());

        if (comparacionFecha != 0) {
            return comparacionFecha;
        }

        // Si la fecha es igual → ordenar por nombre del usuario
        return this.getUsuario().getNombre()
                .compareToIgnoreCase(prestamo.getUsuario().getNombre());
    }
}
