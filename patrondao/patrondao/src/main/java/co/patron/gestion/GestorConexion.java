package co.patron.gestion;

import co.patron.entidades.Estudiante;
import co.patron.entidades.Materia;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import org.postgresql.Driver;

public class GestorConexion {

    public static Connection obtenerConexion() throws ClassNotFoundException, SQLException {
        String usuario = "postgres";
        String clave = "518349276";
        String url = "jdbc:postgresql://localhost:5432/postgres";

        Class.forName("org.postgresql.Driver");

        return DriverManager.getConnection(url, usuario, clave);
    }

    public static void inicializarBaseDeDatos(Estudiante estudiante, Materia materia) {
        String sqlEstudiante = "CREATE TABLE IF NOT EXISTS estudiantes (" +
                "cedula BIGINT PRIMARY KEY, " +
                "nombres VARCHAR(100), " +
                "apellidos VARCHAR(100))";

        String sqlMaterias = "CREATE TABLE IF NOT EXISTS materias (" +
                "codigo VARCHAR(50) PRIMARY KEY, " +
                "nombre VARCHAR(100), " +
                "semestre VARCHAR(20), " +
                "estudiante_cedula BIGINT REFERENCES estudiantes(cedula))";

        String insertEstudiante = "INSERT INTO estudiantes (cedula, nombres, apellidos) VALUES (?, ?, ?) ON CONFLICT (cedula) DO NOTHING";
        // CAMBIO: Añadido ON CONFLICT para materias
        String insertMateria = "INSERT INTO materias (codigo, nombre, semestre, estudiante_cedula) VALUES (?, ?, ?, ?) ON CONFLICT (codigo) DO NOTHING";

        try (Connection conn = GestorConexion.obtenerConexion()) {
            conn.setAutoCommit(false);
            try (Statement stmt = conn.createStatement();
                    PreparedStatement pstmtEst = conn.prepareStatement(insertEstudiante);
                    PreparedStatement pstmtMat = conn.prepareStatement(insertMateria)) {

                stmt.execute(sqlEstudiante);
                stmt.execute(sqlMaterias);

                // Estudiante
                pstmtEst.setLong(1, estudiante.getCedula());
                pstmtEst.setString(2, estudiante.getNombres());
                pstmtEst.setString(3, estudiante.getApellidos());
                pstmtEst.executeUpdate();

                // Materia
                pstmtMat.setLong(1, materia.getCodigo());
                pstmtMat.setString(2, materia.getNombre());
                pstmtMat.setString(3, materia.getSemestre());
                pstmtMat.setLong(4, estudiante.getCedula());
                pstmtMat.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        } catch (Exception e) {
            throw new RuntimeException("Error en BD: " + e.getMessage());
        }
    }
}