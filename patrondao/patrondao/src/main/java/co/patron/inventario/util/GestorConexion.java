package co.patron.inventario.util;

import java.sql.Connection;
import java.sql.DriverManager;

public class GestorConexion {
    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String USER = "postgres";
    private static final String PASS = "518349276";

    public static Connection obtenerConexion() throws Exception {
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
