package biblioteca.modelo.dominio;

import java.util.Arrays;
import java.util.Objects;

/**
 * Clase Libro.
 * Representa un libro de la biblioteca.
 * Contiene ISBN, título, año, categoría y autores.
 */
public class Libro implements Comparable<Libro>{

    //Patrones ISBN y máximo de autores
    public static final String PATRON_ISBN = "\\d{10}|\\d{13}";
    public static final int MAX_AUTORES = 3;

    // Atributos
    private String isbn;
    private String titulo;
    private int anio;
    private Categoria categoria;
    private Autor[] autores;
    private int numAutores;

    // Constructor principal
    public Libro(String isbn, String titulo, int anio, Categoria categoria) {

        setIsbn(isbn);
        setTitulo(titulo);
        setAnio(anio);
        setCategoria(categoria);
        this.autores = new Autor[MAX_AUTORES];
        this.numAutores = 0;
    }

    // Constructor Copia
    public Libro(Libro libro) {
        setIsbn(libro.getISBN());
        setTitulo(libro.getTitulo());
        setAnio(libro.getAnio());
        setCategoria(libro.getCategoria());
        for (Autor a : libro.getAutores()) {
            addAutor(new Autor(a));
        }
    }

    // Getters y Setters
    public String getISBN() {
        return isbn;
    }
    public String getTitulo() {
        return titulo;
    }
    public int getAnio() {
        return anio;
    }
    public Categoria getCategoria() {
        return categoria;
    }
    public Autor[] getAutores() {
        Autor[] copia = new Autor[MAX_AUTORES];
        for (int i = 0; i < MAX_AUTORES; i++) {
            if (autores[i] != null) {
                copia[i] = new Autor(autores[i]);
            }
        }
        return copia;
    }

    public void setIsbn(String isbn) throws IllegalArgumentException {
        if (isbn == null) throw new IllegalArgumentException("ISBN no puede ser nulo");
        if (isbn.trim().isEmpty()) throw new IllegalArgumentException("ISBN no puede estar vacío");
        //Si el ISBN no sigue el patrón lanza una excepción
        if (!isbn.matches(PATRON_ISBN)) throw new IllegalArgumentException("ISBN no válido");
        this.isbn = isbn;
    }
    public void setTitulo(String titulo) throws IllegalArgumentException {
        if (titulo == null) throw new IllegalArgumentException("Título no puede ser nulo");
        if (titulo.trim().isEmpty()) throw new IllegalArgumentException("Título no puede estar vacío");
        this.titulo = titulo;
    }
    public void setAnio(int anio) throws IllegalArgumentException {
        if (anio <= 0) throw new IllegalArgumentException("Año inválido");
        this.anio = anio;
    }
    public void setCategoria(Categoria categoria) throws IllegalArgumentException {
        if (categoria == null) throw new IllegalArgumentException("Categoría no puede ser nulo");
        this.categoria = categoria;
    }

    //Métodos de gestión de autores

    public void addAutor(Autor autor) throws IllegalArgumentException {
        if (autor == null) {
            throw new IllegalArgumentException("Autor no puede ser nulo");
        }

        for (int i = 0; i < MAX_AUTORES; i++) {
            if (autores[i] == null) {
                autores[i] = new Autor(autor); // copia profunda
                return;
            }
        }

        throw new IllegalStateException("Máximo número de autores alcanzado");
    } //Añadir autor
    public void borrarAutor(Autor autor) {
        if (autor == null) return;

        for (int i = 0; i < MAX_AUTORES; i++) {
            if (autores[i] != null && autores[i].equals(autor)) {

                for (int j = i; j < MAX_AUTORES - 1; j++) {
                    autores[j] = autores[j + 1];
                }

                autores[MAX_AUTORES - 1] = null;

                return;
            }
        }
    }

    //Overrides
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Libro)) return false;
        Libro libro = (Libro) o;
        return Objects.equals(isbn, libro.isbn);
    }
    @Override
    public int hashCode() {
        return Objects.hash(isbn);
    }

    //ToString
    @Override
    public String toString() {
        return "Libro{" +
                "isbn='" + isbn + '\'' +
                ", titulo='" + titulo + '\'' +
                ", anio=" + anio +
                ", categoria=" + categoria +
                ", autores=" + Arrays.toString(autores) +
                '}';
    }

    @Override
    public int compareTo(Libro libro) {
        if (libro == null) {
            throw new IllegalArgumentException("No se puede comparar con null");
        }
        return this.getTitulo().compareToIgnoreCase(libro.getTitulo());
    }
}

