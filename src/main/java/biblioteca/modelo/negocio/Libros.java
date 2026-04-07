package biblioteca.modelo.negocio;

import biblioteca.modelo.dominio.Autor;
import biblioteca.modelo.dominio.Audiolibro;
import biblioteca.modelo.dominio.Categoria;
import biblioteca.modelo.dominio.Libro;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Libros {

    private static Libros instancia;
    private Connection conexion;

    private Libros() {
    }
    public static Libros getInstancia() {
        if (instancia == null) {
            instancia = new Libros();
        }
        return instancia;
    }
    public void comenzar() {
        conexion = MySQL.getInstancia().getConexion();
    }
    public void terminar() {
        conexion = null;
    }

    public void alta(Libro libro) {
        if (libro == null) {
            throw new IllegalArgumentException("El libro no puede ser nulo");
        }

        if (buscar(libro) != null) {
            throw new IllegalArgumentException("El libro ya existe");
        }

        String sqlLibro = """
                INSERT INTO libro (isbn, titulo, anio, categoria)
                VALUES (?, ?, ?, ?)
                """;

        String sqlAudiolibro = """
                INSERT INTO audiolibro (isbn, duracion_segundos, formato)
                VALUES (?, ?, ?)
                """;

        try (PreparedStatement psLibro = conexion.prepareStatement(sqlLibro)) {
            psLibro.setString(1, libro.getISBN());
            psLibro.setString(2, libro.getTitulo());
            psLibro.setInt(3, libro.getAnio());
            psLibro.setString(4, libro.getCategoria().name());
            psLibro.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                throw new IllegalArgumentException("El libro ya existe");
            }
            throw new RuntimeException("Error al insertar libro.", e);
        }

        if (libro instanceof Audiolibro audiolibro) {
            try (PreparedStatement psAudio = conexion.prepareStatement(sqlAudiolibro)) {
                psAudio.setString(1, audiolibro.getISBN());
                psAudio.setLong(2, audiolibro.getDuracion().getSeconds());
                psAudio.setString(3, audiolibro.getFormato());
                psAudio.executeUpdate();
            } catch (SQLException e) {

                if (e.getErrorCode() == 1062) {
                    throw new IllegalArgumentException("El audiolibro ya existe");
                }
                throw new RuntimeException("Error al insertar audiolibro.", e);
            }
        }

        for (Autor autor : libro.getAutores()) {
            if (autor != null) {
                int idAutor = obtenerOCrearIdAutor(autor);
                insertarLibroAutor(libro.getISBN(), idAutor);
            }
        }
    }

    public boolean baja(Libro libro) {
        if (libro == null) {
            return false;
        }

        String sql = "DELETE FROM libro WHERE isbn = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, libro.getISBN());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al borrar libro.", e);
        }
    }
    public Libro buscar(Libro libro) {
        if (libro == null) {
            return null;
        }

        String sqlLibro = """
                SELECT isbn, titulo, anio, categoria
                FROM libro
                WHERE isbn = ?
                """;

        try (PreparedStatement psLibro = conexion.prepareStatement(sqlLibro)) {
            psLibro.setString(1, libro.getISBN());

            try (ResultSet rsLibro = psLibro.executeQuery()) {
                if (!rsLibro.next()) {
                    return null;
                }

                String isbn = rsLibro.getString("isbn");
                String titulo = rsLibro.getString("titulo");
                int anio = rsLibro.getInt("anio");
                Categoria categoria = Categoria.valueOf(rsLibro.getString("categoria"));

                Libro resultado = crearLibro(isbn, titulo, anio, categoria);
                cargarAutores(resultado);

                if (resultado instanceof Audiolibro) {
                    return new Audiolibro((Audiolibro) resultado);
                }

                return new Libro(resultado);
            }
        } catch (SQLException e) {


            throw new RuntimeException("Error al buscar libro.", e);
        }
    }
    public List<Libro> todos() {
        List<Libro> libros = new ArrayList<>();

        String sql = """
                SELECT isbn
                FROM libro
                ORDER BY titulo
                """;

        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Libro libro = buscar(new Libro(rs.getString("isbn")));
                if (libro != null) {
                    libros.add(libro);
                }
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al listar libros.", e);
        }

        Collections.sort(libros);
        return libros;
    }

    private Libro crearLibro(String isbn, String titulo, int anio, Categoria categoria) throws SQLException {

        // Validación de ISBN
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("ISBN no puede ser nulo o vacío");
        }

        String sql = """
            SELECT duracion_segundos, formato
            FROM audiolibro
            WHERE isbn = ?
            """;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, isbn);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    long segundos = rs.getLong("duracion_segundos");
                    String formato = rs.getString("formato");

                    // ✔ La validación se delega al setter
                    return new Audiolibro(
                            isbn,
                            titulo,
                            anio,
                            categoria,
                            Duration.ofSeconds(segundos),
                            formato
                    );
                }
            }
        }

        return new Libro(isbn, titulo, anio, categoria);
    }
    private void cargarAutores(Libro libro) throws SQLException {
        String sql = """
                SELECT a.nombre, a.apellidos, a.nacionalidad
                FROM autor a
                JOIN libro_autor la ON a.idAutor = la.idAutor
                WHERE la.isbn = ?
                ORDER BY a.apellidos, a.nombre
                """;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, libro.getISBN());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Autor autor = new Autor(
                            rs.getString("nombre"),
                            rs.getString("apellidos"),
                            rs.getString("nacionalidad")
                    );
                    libro.addAutor(autor);
                }
            }
        }
    }
    private int obtenerOCrearIdAutor(Autor autor) {
        Integer idAutor = buscarIdAutor(autor);

        if (idAutor != null) {
            return idAutor;
        }

        String sql = """
                INSERT INTO autor (nombre, apellidos, nacionalidad)
                VALUES (?, ?, ?)
                """;

        try (PreparedStatement ps = conexion.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, autor.getNombre());
            ps.setString(2, autor.getApellidos());
            ps.setString(3, autor.getNacionalidad());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

            throw new RuntimeException("No se pudo obtener el id del autor.");

        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar autor.", e);
        }
    }
    private Integer buscarIdAutor(Autor autor) {
        String sql = """
                SELECT idAutor
                FROM autor
                WHERE nombre = ? AND apellidos = ? AND nacionalidad = ?
                """;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, autor.getNombre());
            ps.setString(2, autor.getApellidos());
            ps.setString(3, autor.getNacionalidad());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("idAutor");
                }
            }

            return null;

        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar autor.", e);
        }
    }
    private void insertarLibroAutor(String isbn, int idAutor) {
        String sql = """
                INSERT INTO libro_autor (isbn, idAutor)
                VALUES (?, ?)
                """;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, isbn);
            ps.setInt(2, idAutor);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error al relacionar libro y autor.", e);
        }
    }
}