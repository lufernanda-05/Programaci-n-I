package co.patron.juego.gestion;

import java.sql.*;

public class GestorConexion {
    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection("jdbc:h2:~/picasfijas", "sa", "");
    }

    public static void inicializarDB() throws SQLException {
        try (Connection conn = obtenerConexion(); Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS resultados (" +
                    "nickname VARCHAR(50) PRIMARY KEY, " +
                    "numero_generado VARCHAR(4), " +
                    "intentos INT, " +
                    "tiempo BIGINT)");
        }
    }
}