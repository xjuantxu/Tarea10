package biblioteca.modelo.negocio;

import biblioteca.modelo.dominio.Direccion;
import biblioteca.modelo.dominio.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Usuarios {

    private static Usuarios instancia;
    private Connection conexion;

    private Usuarios() {
    }

    public static Usuarios getInstancia() {
        if (instancia == null) {
            instancia = new Usuarios();
        }
        return instancia;
    }

    public void comenzar() {
        conexion = MySQL.getInstancia().getConexion();
    }

    public void terminar() {
        conexion = null;
    }

    public void alta(Usuario usuario) {
        if (usuario == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo");
        }

        if (buscar(usuario) != null) {
            throw new IllegalArgumentException("El usuario ya existe");
        }

        String sqlUsuario = """
                INSERT INTO usuario (dni, nombre, email)
                VALUES (?, ?, ?)
                """;

        String sqlDireccion = """
                INSERT INTO direccion (dni, via, numero, cp, localidad)
                VALUES (?, ?, ?, ?, ?)
                """;

        try (PreparedStatement psUsuario = conexion.prepareStatement(sqlUsuario);
             PreparedStatement psDireccion = conexion.prepareStatement(sqlDireccion)) {

            psUsuario.setString(1, usuario.getDni());
            psUsuario.setString(2, usuario.getNombre());
            psUsuario.setString(3, usuario.getEmail());
            psUsuario.executeUpdate();

            Direccion direccion = usuario.getDireccion();
            psDireccion.setString(1, usuario.getDni());
            psDireccion.setString(2, direccion.getVia());
            psDireccion.setString(3, direccion.getNumero());
            psDireccion.setString(4, direccion.getCp());
            psDireccion.setString(5, direccion.getLocalidad());
            psDireccion.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar usuario.", e);
        }
    }

    public boolean baja(Usuario usuario) {
        if (usuario == null) {
            return false;
        }

        String sql = "DELETE FROM usuario WHERE dni = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, usuario.getDni());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error al borrar usuario.", e);
        }
    }

    public Usuario buscar(Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        String sql = """
                SELECT u.dni, u.nombre, u.email,
                       d.via, d.numero, d.cp, d.localidad
                FROM usuario u
                JOIN direccion d ON u.dni = d.dni
                WHERE u.dni = ?
                """;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, usuario.getDni());

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return crearUsuario(rs);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al buscar usuario.", e);
        }

        return null;
    }

    public List<Usuario> todos() {
        List<Usuario> usuarios = new ArrayList<>();

        String sql = """
                SELECT u.dni, u.nombre, u.email,
                       d.via, d.numero, d.cp, d.localidad
                FROM usuario u
                JOIN direccion d ON u.dni = d.dni
                ORDER BY u.nombre
                """;

        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                usuarios.add(crearUsuario(rs));
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error al mostrar usuarios.", e);
        }
        return usuarios;
    }

    private Usuario crearUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario(
                rs.getString("dni"),
                rs.getString("nombre")
        );

        usuario.setEmail(rs.getString("email"));

        Direccion direccion = new Direccion(
                rs.getString("via"),
                rs.getString("numero"),
                rs.getString("cp"),
                rs.getString("localidad")
        );

        usuario.setDireccion(direccion);
        return usuario;
    }
}