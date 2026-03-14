package biblioteca.modelo.dominio;

import java.util.Objects;
/**
 * Clase Usuario.
 * Representa un usuario de la biblioteca.
 * Contiene DNI, nombre, email y dirección.
 */

public class Usuario implements Comparable<Usuario> {
    // Patron para DNI y email
    public static final String DNI_PATRON = "\\d{8}[A-Z]";
    public static final String EMAIL_BASICO = ".+@.+\\..+";

    // Atributos
    private String dni;
    private String nombre;
    private String email;
    private Direccion direccion;

    // Constructor principal
    public Usuario(String dni, String nombre) {
        setDni(dni);
        setNombre(nombre);
    }

    // Constructor dni para buscar por DNI
    public Usuario(String dni) {
        this(dni, "Temporal");
    }

    // Constructor copia
    public Usuario(Usuario otro) {
        setDni(otro.getDni());
        setNombre(otro.getNombre());
        setEmail(otro.getEmail());

        // Copia profunda de la dirección
        if (otro.getDireccion() != null) {
            this.direccion = new Direccion(otro.getDireccion());
        }
    }

    // Getters y Setters
    public String getDni() {
        return dni;
    }
    public String getNombre() {
        return nombre;
    }
    public String getEmail() {
        return email;
    }
    public Direccion getDireccion() {
        return direccion;
    }

    public void setDni(String dni) {
        if (dni == null) throw new IllegalArgumentException("DNI no puede ser nulo");
        if (dni.trim().isEmpty()) throw new IllegalArgumentException("DNI no puede estar vacío");
        //Verifica que el DNI siga el patrón especificado y lanza una excepción si falla.
        if (!dni.matches(DNI_PATRON)) throw new IllegalArgumentException("DNI inválido");

        this.dni = dni;
    }
    public void setNombre(String nombre) throws IllegalArgumentException {
        if (nombre == null) throw new IllegalArgumentException("Nombre no puede ser nulo");
        if (nombre.trim().isEmpty()) throw new IllegalArgumentException("Nombre no puede estar vacío");
        this.nombre = nombre;
    }
    public void setEmail(String email) throws IllegalArgumentException {
        if (email == null) throw new IllegalArgumentException("Email no puede ser nulo");
        if (email.trim().isEmpty()) throw new IllegalArgumentException("Email no puede estar vacío");
        //Si el email no sigue el patrón establecido, lanza una excepción.
        if (!email.matches(EMAIL_BASICO)) throw new IllegalArgumentException("Email inválido");
        this.email = email;
    }
    public void setDireccion(Direccion direccion) throws IllegalArgumentException {
        this.direccion = direccion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(dni, usuario.dni);
    }
    @Override
    public int hashCode() {
        return Objects.hash(dni);
    }

    @Override
    public String toString() {
        return "Usuario: " + nombre + " (" + dni + ") - " + email +
                "\nDirección: " + direccion;
    }
    @Override
    public int compareTo(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("No se puede comparar con null");
        }
        return this.getNombre().compareToIgnoreCase(usuario.getNombre());
    }
}