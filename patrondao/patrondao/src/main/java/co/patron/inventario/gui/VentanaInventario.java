package co.patron.inventario.gui;

import co.patron.inventario.dao.OperacionesProducto;
import co.patron.inventario.entidades.Producto;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class VentanaInventario extends JFrame {
    private JTextField txtNombre, txtExistencias, txtPrecioCompra, txtEmpresa, txtFecha;
    private JComboBox<String> comboTipo;
    private JTable tablaProductos;
    private OperacionesProducto oper = new OperacionesProducto();

    public VentanaInventario() {
        setTitle("Gestión de Inventario");
        setSize(700, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel de Formulario
        JPanel panelForm = new JPanel(new GridLayout(7, 2));
        panelForm.add(new JLabel("Tipo:"));
        comboTipo = new JComboBox<>(new String[] { "VERDURAS", "BEBIDAS", "CARNES", "FRUTAS", "OTROS" });
        panelForm.add(comboTipo);
        panelForm.add(new JLabel("Nombre:"));
        panelForm.add(txtNombre = new JTextField());
        panelForm.add(new JLabel("Existencias:"));
        panelForm.add(txtExistencias = new JTextField());
        panelForm.add(new JLabel("Precio Compra:"));
        panelForm.add(txtPrecioCompra = new JTextField());
        panelForm.add(new JLabel("Empresa:"));
        panelForm.add(txtEmpresa = new JTextField());
        panelForm.add(new JLabel("Fecha Vencimiento (YYYY-MM-DD):"));
        panelForm.add(txtFecha = new JTextField());

        JButton btnGuardar = new JButton("Guardar Producto");
        panelForm.add(btnGuardar);

        // Tabla
        tablaProductos = new JTable();
        add(new JScrollPane(tablaProductos), BorderLayout.CENTER);
        add(panelForm, BorderLayout.NORTH);

        btnGuardar.addActionListener(e -> guardarProducto());
        cargarTabla();
    }

    private void guardarProducto() {
        try {
            Producto p = Producto.builder()
                    .tipo((String) comboTipo.getSelectedItem())
                    .nombre(txtNombre.getText())
                    .existencias(Integer.parseInt(txtExistencias.getText()))
                    .precioCompra(Double.parseDouble(txtPrecioCompra.getText()))
                    .empresaSurtidora(txtEmpresa.getText())
                    .fechaVencimiento(LocalDate.parse(txtFecha.getText()))
                    .build();

            p.calcularPrecioVenta(); // Calcula el 19%
            oper.guardar(p);
            cargarTabla();
            JOptionPane.showMessageDialog(this, "Producto guardado. Precio Venta: " + p.getPrecioVenta());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void cargarTabla() {
        List<Producto> lista = oper.obtenerOrdenados();
        String[] columnas = { "Tipo", "Nombre", "Existencias", "Precio Venta", "Empresa", "Vencimiento" };
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0);

        for (Producto p : lista) {
            modelo.addRow(new Object[] { p.getTipo(), p.getNombre(), p.getExistencias(),
                    p.getPrecioVenta(), p.getEmpresaSurtidora(), p.getFechaVencimiento() });
        }
        tablaProductos.setModel(modelo);
    }
}
