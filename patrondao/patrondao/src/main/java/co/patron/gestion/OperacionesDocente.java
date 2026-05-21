package co.patron.gestion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import co.patron.entidades.Docente;

public class OperacionesDocente implements OperacionesGeneric<Docente> {
    public void crear(Docente entidad) {
        String sql = "insert into docentes (nombre, identificacion) values (?, ?)";

        try (Connection con = GestorConexion.obtenerConexion();
                // lanza el sql
                PreparedStatement ps = con.prepareStatement(sql)) {
            // resive los 2 parametros
            ps.setString(1, entidad.getNombre());
            ps.setLong(2, entidad.getIdentificacion());

            ps.executeUpdate();
            System.out.println("Inserto ");

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }

    }

    public List<Docente> obtenerTodos() {
        List<Docente> docentes = new ArrayList<>();
        String sql = "select * from  docentes";
        try (Connection con = GestorConexion.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                Long identificacion = rs.getLong("identificacion");
                Docente docente = Docente
                        .builder()
                        .nombre(nombre)
                        .identificacion(identificacion)
                        .build();
                docentes.add(docente);
            }

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        }

        return docentes;
    }
}
