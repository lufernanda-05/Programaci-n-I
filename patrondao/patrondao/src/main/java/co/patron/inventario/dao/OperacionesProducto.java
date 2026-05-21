package co.patron.inventario.dao;

import co.patron.inventario.entidades.Producto;
import co.patron.inventario.util.GestorConexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OperacionesProducto {

    public void guardar(Producto p) {
        String sql = "INSERT INTO productos (tipo, nombre, existencias, precio_compra, precio_venta, empresa_surtidora, fecha_vencimiento) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = GestorConexion.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, p.getTipo());
            ps.setString(2, p.getNombre());
            ps.setInt(3, p.getExistencias());
            ps.setDouble(4, p.getPrecioCompra());
            ps.setDouble(5, p.getPrecioVenta());
            ps.setString(6, p.getEmpresaSurtidora());
            ps.setDate(7, Date.valueOf(p.getFechaVencimiento()));

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Producto> obtenerOrdenados() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM productos ORDER BY tipo ASC, existencias ASC";

        try (Connection con = GestorConexion.obtenerConexion();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Producto p = Producto.builder()
                        .id(rs.getLong("id"))
                        .tipo(rs.getString("tipo"))
                        .nombre(rs.getString("nombre"))
                        .existencias(rs.getInt("existencias"))
                        .precioCompra(rs.getDouble("precio_compra"))
                        .precioVenta(rs.getDouble("precio_venta"))
                        .empresaSurtidora(rs.getString("empresa_surtidora"))
                        .fechaVencimiento(rs.getDate("fecha_vencimiento").toLocalDate())
                        .build();
                lista.add(p);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }
}
