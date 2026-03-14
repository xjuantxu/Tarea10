package biblioteca.controlador;

import java.time.LocalDate;
import java.util.List;

import biblioteca.modelo.Modelo;
import biblioteca.vista.Vista;
import biblioteca.modelo.dominio.*;

/**
 * Clase Controlador.
 * Hace de intermediario entre la Vista y el Modelo.
 * Encapsula toda la lógica de la aplicación.
 */
public class Controlador {

    private Modelo modelo;
    private Vista vista;

    // Constructor
    public Controlador(Modelo modelo, Vista vista) {
        if (modelo == null || vista == null)
            throw new IllegalArgumentException("Modelo y Vista no pueden ser null");

        this.modelo = modelo;
        this.vista = vista;
        this.vista.setControlador(this);
    }

    // Comienza la aplicación
    public void comenzar() {
        modelo.comenzar();
        vista.comenzar();
    }

    // Termina la aplicación
    public void terminar() {
        modelo.terminar();
        vista.terminar();
        System.out.println("Termina Controlador");
    }

    // =========================
    // Métodos de usuarios
    // =========================

    public boolean alta(Usuario usuario) {
        if (modelo.buscarUsuario(usuario) != null) return false;
        modelo.altaUsuario(usuario);
        return true;
    }

    public boolean baja(Usuario usuario) {
        return modelo.bajaUsuario(usuario);
    }

    public Usuario buscar(Usuario usuario) {
        return modelo.buscarUsuario(usuario);
    }

    public List<Usuario> listadoUsuarios() {
        return modelo.listadoUsuarios();
    }

    // =========================
    // Métodos de libros
    // =========================

    public boolean alta(Libro libro) {
        if (modelo.buscarLibro(libro) != null) return false;
        modelo.altaLibro(libro);
        return true;
    }

    public boolean baja(Libro libro) {
        return modelo.bajaLibro(libro);
    }

    public Libro buscar(Libro libro) {
        return modelo.buscarLibro(libro);
    }

    public List<Libro> listadoLibros() {
        return modelo.listadoLibros();
    }

    // =========================
    // Métodos de préstamos
    // =========================

    public boolean prestar(Libro libro, Usuario usuario, LocalDate fecha) {
        return modelo.prestar(libro, usuario, fecha);
    }

    public boolean devolver(Libro libro, Usuario usuario, LocalDate fecha) {
        return modelo.devolver(libro, usuario, fecha);
    }

    public List<Prestamo> listadoPrestamos() {
        return modelo.listadoPrestamos();
    }

    public List<Prestamo> listadoPrestamos(Usuario usuario) {
        return modelo.listadoPrestamos(usuario);
    }
}