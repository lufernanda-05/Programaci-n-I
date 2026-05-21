package co.patron.gestion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import co.patron.entidades.Estudiante;

public class OperacionesEstudiante {
    public void crear(Estudiante estudiante) {
        String sql = "INSERT INTO estudiantes (cedula, nombres, apellidos) VALUES (?, ?, ?)";
        try (Connection con = GestorConexion.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, estudiante.getCedula());
            ps.setString(2, estudiante.getNombres());
            ps.setString(3, estudiante.getApellidos());
            ps.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<Estudiante> obtenerTodos() {
        List<Estudiante> lista = new ArrayList<>();
        String sql = "SELECT cedula, nombres, apellidos FROM estudiantes";
        try (Connection conn = GestorConexion.obtenerConexion();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Estudiante e = new Estudiante();
                e.setCedula(rs.getLong("cedula"));
                e.setNombres(rs.getString("nombres")); // <--- ¿Aquí imprime algo si haces un syso?
                e.setApellidos(rs.getString("apellidos"));
                lista.add(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    public Estudiante obtenerPorCedula(long estudianteCedula) {
        String sql = "SELECT * FROM estudiantes WHERE cedula = ?";
        try (Connection con = GestorConexion.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setLong(1, estudianteCedula);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    long cedula = rs.getLong("cedula");
                    String nombres = rs.getString("nombres");
                    String apellidos = rs.getString("apellidos");
                    Estudiante estudiante = new Estudiante();
                    estudiante.setCedula(cedula);
                    estudiante.setNombres(nombres);
                    estudiante.setApellidos(apellidos);
                    return estudiante;
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void guardar(Estudiante e) {
        // Asegúrate de que el SQL tenga tres signos de interrogación
        String sql = "INSERT INTO estudiantes (cedula, nombres, apellidos) VALUES (?, ?, ?)";
        try (Connection conn = GestorConexion.obtenerConexion();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, e.getCedula());
            pstmt.setString(2, e.getNombres());
            pstmt.setString(3, e.getApellidos());
            pstmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
