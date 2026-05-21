package co.patron;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import co.patron.entidades.Estudiante;
import co.patron.entidades.Materia;
import co.patron.gestion.OperacionesDocente;
import co.patron.gestion.OperacionesMateria;
import co.patron.gestion.OperacionesEstudiante;
import java.util.List;
import java.util.stream.Collectors;
import java.awt.*;

public class Ventana extends JFrame {
    private JTable tablaEstudiantes, tablaMaterias;
    private OperacionesEstudiante operacionesEstudiante = new OperacionesEstudiante();
    private OperacionesMateria operacionesMateria = new OperacionesMateria();

    public Ventana() {
        setTitle("Gestión de Estudiantes y Materias");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- FORMULARIO ---
        JPanel panelForm = new JPanel(new GridLayout(2, 6));
        JTextField txtNombre = new JTextField(10);
        JTextField txtApellido = new JTextField(10);
        JTextField txtCedula = new JTextField(10);
        JComboBox<String> comboMaterias = new JComboBox<>(
                new String[] { "Matemáticas", "Español", "Ciencias", "Informática" });
        JTextField txtSemestre = new JTextField(5);
        JButton btnGuardar = new JButton("Guardar");

        panelForm.add(new JLabel("Nombre:"));
        panelForm.add(txtNombre);
        panelForm.add(new JLabel("Apellido:"));
        panelForm.add(txtApellido);
        panelForm.add(new JLabel("Cédula:"));
        panelForm.add(txtCedula);
        panelForm.add(new JLabel("Materia:"));
        panelForm.add(comboMaterias);
        panelForm.add(new JLabel("Semestre:"));
        panelForm.add(txtSemestre);
        panelForm.add(btnGuardar);
        add(panelForm, BorderLayout.NORTH);

        // --- TABLAS ---
        JPanel panelTablas = new JPanel(new GridLayout(2, 1));
        tablaEstudiantes = new JTable();
        tablaMaterias = new JTable();
        panelTablas.add(new JScrollPane(tablaEstudiantes));
        panelTablas.add(new JScrollPane(tablaMaterias));
        add(panelTablas, BorderLayout.CENTER);

        btnGuardar.addActionListener(e -> {
            try {
                // 1. Crear el objeto estudiante
                Estudiante est = Estudiante.builder()
                        .nombres(txtNombre.getText())
                        .apellidos(txtApellido.getText())
                        .cedula(Long.parseLong(txtCedula.getText()))
                        .build();

                // 2. Guardar estudiante primero
                operacionesEstudiante.guardar(est);

                // 3. Crear materia pasando el objeto 'est' completo
                Materia mat = Materia.builder()
                        .codigo((long) (Math.random() * 10000))
                        .nombre((String) comboMaterias.getSelectedItem())
                        .semestre(txtSemestre.getText())
                        .estudiante(est) // <--- Aquí vinculas el estudiante
                        .build();

                operacionesMateria.crear(mat);

                cargarDatosEstudiantes();
                cargarDatosMaterias();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });
        cargarDatosEstudiantes();
        cargarDatosMaterias();
    }

    private void cargarDatosMaterias() {
        List<Materia> materias = operacionesMateria.obtenerTodos();
        DefaultTableModel modelo = new DefaultTableModel(new Object[] { "Código", "Materia", "Semestre", "Estudiante" },
                0);

        for (Materia m : materias) {
            String nombreCompleto = "Sin asignar";
            if (m.getEstudiante() != null && m.getEstudiante().getNombres() != null) {
                String nom = m.getEstudiante().getNombres();
                String ape = (m.getEstudiante().getApellidos() != null) ? m.getEstudiante().getApellidos() : "";
                nombreCompleto = nom + " " + ape;
            }
            modelo.addRow(new Object[] { m.getCodigo(), m.getNombre(), m.getSemestre(), nombreCompleto });
        }
        tablaMaterias.setModel(modelo);
    }

    private void cargarDatosEstudiantes() {
        List<Estudiante> estudiantes = operacionesEstudiante.obtenerTodos();
        DefaultTableModel modelo = new DefaultTableModel(new Object[] { "Nombre", "Apellido", "Identificación" }, 0);
        for (Estudiante est : estudiantes) {
            modelo.addRow(new Object[] { est.getNombres(), est.getApellidos(), est.getCedula() });
        }
        tablaEstudiantes.setModel(modelo);
    }
}