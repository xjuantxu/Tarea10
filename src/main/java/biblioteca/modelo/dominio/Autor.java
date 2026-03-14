package biblioteca.modelo.dominio;

import java.util.Objects;

/**
 * Clase Autor.
 * Representa un autor de un libro, con nombre, apellidos y nacionalidad.
 */
public class Autor implements Comparable<Autor> {

    // Atributos
    private String nombre;
    private String apellidos;
    private String nacionalidad;

    // Constructor
    public Autor(String nombre, String apellidos, String nacionalidad) {
        setNombre(nombre);
        setApellidos(apellidos);
        setNacionalidad(nacionalidad);
    }

    // Constructor copia
    public Autor(Autor autor) {
        setNombre(autor.getNombre());
        setApellidos(autor.getApellidos());
        setNacionalidad(autor.getNacionalidad());
    }

    // Getters y setters
    public String getNombre() {

        return nombre;
    }
    public String getApellidos() {
        return apellidos;
    }
    public String getNacionalidad() {

        return nacionalidad;
    }

    public void setNombre(String nombre) throws IllegalArgumentException {
        if (nombre == null) throw new IllegalArgumentException("Nombre no puede ser nulo");
        if (nombre.trim().isEmpty()) throw new IllegalArgumentException("Nombre no puede estar vacío");

        this.nombre = nombre;
    }
    public void setApellidos(String apellidos) throws IllegalArgumentException {
        if (apellidos == null) throw new IllegalArgumentException("Apellidos no puede ser nulo");
        if (apellidos.trim().isEmpty()) throw new IllegalArgumentException("Apellidos no puede estar vacío");
        this.apellidos = apellidos;
    }
    public void setNacionalidad(String nacionalidad) throws IllegalArgumentException {
        if (nacionalidad == null) throw new IllegalArgumentException("Nacionalidad no puede ser nulo");
        if (nacionalidad.trim().isEmpty()) throw new IllegalArgumentException("Nacionalidad no puede estar vacío");
        this.nacionalidad = nacionalidad;
    }

    // Overrides
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Autor)) return false;
        Autor autor = (Autor) o;
        return Objects.equals(nombre, autor.nombre)
                && Objects.equals(apellidos, autor.apellidos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nombre, apellidos);
    }

    // ToString
    @Override
    public String toString() {
        return nombre + " " + apellidos + " (" + nacionalidad + ")";
    }

    @Override
    public int compareTo(Autor objeto) {
        return 0;
    }
}

