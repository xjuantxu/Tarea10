package biblioteca.modelo.negocio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQL {

    public static final String host = "localhost";
    public static final String esquema = "biblioteca";
    public static final String usuario = "admin";
    public static final String pass = "biblioteca-2026";

    private static MySQL instancia;
    private Connection conexion;

    private MySQL() {
        establecerConexion();
    }

    public static MySQL getInstancia() {
        if (instancia == null) {
            instancia = new MySQL();
        }
        return instancia;
    }

    private void establecerConexion() {
        try {
            String url = "jdbc:mysql://" + host + "/" + esquema + "?useSSL=false&serverTimezone=UTC";

            conexion = DriverManager.getConnection(url, usuario, pass);

        } catch (SQLException e) {
            throw new RuntimeException("Error al conectar con la base de datos: " + e.getMessage(), e);
        }
    }

    public Connection getConexion() {
        return conexion;
    }

    public void cerrarConexion() {
        try {
            if (conexion != null && !conexion.isClosed()) {
                conexion.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al cerrar la conexion: " + e.getMessage());
        }
    }
}