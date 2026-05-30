package co.patron.juego.gestion;

import co.patron.juego.entidades.Resultado;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class OperacionesJuego {
    public void guardar(Resultado r) throws SQLException {
        String sql = "INSERT INTO resultados VALUES (?, ?, ?, ?)";
        try (Connection conn = GestorConexion.obtenerConexion(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, r.getNickname());
            ps.setString(2, r.getNumeroGenerado());
            ps.setInt(3, r.getIntentos());
            ps.setLong(4, r.getTiempo());
            ps.executeUpdate();
        }
    }

    public List<Resultado> obtenerRanking() throws SQLException {
        List<Resultado> lista = new ArrayList<>();
        try (Connection conn = GestorConexion.obtenerConexion();
                ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM resultados")) {
            while (rs.next()) {
                lista.add(new Resultado(rs.getString(1), rs.getString(2), rs.getInt(3), rs.getLong(4)));
            }
        }
        // Ordenamiento con Lambda
        return lista.stream()
                .sorted(Comparator.comparingLong(Resultado::getTiempo)
                        .thenComparingInt(Resultado::getIntentos))
                .collect(Collectors.toList());
    }
}