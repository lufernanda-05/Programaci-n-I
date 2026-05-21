package co.patron.gestion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import co.patron.entidades.Estudiante;
import co.patron.entidades.Materia;
import java.sql.*;

public class OperacionesMateria {
    public void crear(Materia materia) {
        String sql = "INSERT INTO materias (codigo, nombre, semestre, estudiante_cedula) VALUES (?, ?, ?, ?)";
        try (Connection con = GestorConexion.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, materia.getCodigo());
            ps.setString(2, materia.getNombre());
            ps.setString(3, materia.getSemestre());
            if (materia.getEstudiante() != null) {
                ps.setLong(4, materia.getEstudiante().getCedula());
            } else {
                ps.setNull(4, java.sql.Types.BIGINT);
            }

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Materia> obtenerTodos() {
        List<Materia> materias = new ArrayList<>();
        String sql = "SELECT m.codigo, m.nombre, m.semestre, m.estudiante_cedula, e.nombres, e.apellidos " +
                "FROM materias m LEFT JOIN estudiantes e ON m.estudiante_cedula = e.cedula";

        try (Connection con = GestorConexion.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Materia m = new Materia();
                m.setCodigo(rs.getLong("codigo"));
                m.setNombre(rs.getString("nombre"));
                m.setSemestre(rs.getString("semestre"));

                // Aquí está el truco:
                Estudiante e = new Estudiante();
                e.setNombres(rs.getString("nombres"));
                e.setApellidos(rs.getString("apellidos")); // <--- Asegúrate de leer esto

                m.setEstudiante(e);
                materias.add(m);

                // Debug para verificar que sí está leyendo el apellido
                System.out.println("Cargando materia: " + m.getNombre() +
                        " con estudiante: " + e.getNombres() + " " + e.getApellidos());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return materias;
    }

}