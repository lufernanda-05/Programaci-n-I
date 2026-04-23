package DuoSecreto.ventana;

import DuoSecreto.dto.Dificultad;
import DuoSecreto.util.Administracion;

import javax.swing.*;
import java.awt.*;

public class VentanaAdministracion extends JFrame {
    private JTextField tfNombre;
    private JSpinner spnParejas;
    private JSpinner spnTiempo;
    private JSpinner spnPuntos;
    private JButton btnGuardar;

    public VentanaAdministracion() {
        setTitle("Administración de Dificultad");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Nombre:"));
        tfNombre = new JTextField();
        panel.add(tfNombre);

        panel.add(new JLabel("Número de Parejas:"));
        spnParejas = new JSpinner(new SpinnerNumberModel(10, 4, 20, 1));
        panel.add(spnParejas);

        panel.add(new JLabel("Tiempo Máximo (s):"));
        spnTiempo = new JSpinner(new SpinnerNumberModel(60, 30, 120, 10));
        panel.add(spnTiempo);

        panel.add(new JLabel("Puntos por Pareja:"));
        spnPuntos = new JSpinner(new SpinnerNumberModel(10, 5, 20, 1));
        panel.add(spnPuntos);

        btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> guardarDificultad());
        panel.add(btnGuardar);

        add(panel, BorderLayout.CENTER);
    }

    private void guardarDificultad() {
        String nombre = tfNombre.getText();
        int parejas = (int) spnParejas.getValue();
        int tiempo = (int) spnTiempo.getValue();
        int puntos = (int) spnPuntos.getValue();

        Dificultad dificultad = new Dificultad(nombre, parejas, tiempo, puntos);
        Administracion.guardarDificultad(dificultad);
        JOptionPane.showMessageDialog(this, "Dificultad guardada exitosamente.");
    }
}