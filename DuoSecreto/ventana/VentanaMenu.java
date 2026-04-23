package DuoSecreto.ventana;

import javax.swing.*;
import java.awt.*;

public class VentanaMenu extends JFrame {
    private JButton btnJugar;
    private JButton btnAdministracion;

    public VentanaMenu() {
        setTitle("Duo Secreto");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(2, 1, 20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        btnJugar = new JButton("Jugar");
        btnJugar.addActionListener(e -> abrirVentanaJuego());
        panel.add(btnJugar);

        btnAdministracion = new JButton("Administración");
        btnAdministracion.addActionListener(e -> abrirVentanaAdministracion());
        panel.add(btnAdministracion);

        add(panel, BorderLayout.CENTER);
    }

    private void abrirVentanaJuego() {
        new VentanaIngresoNickname().setVisible(true);
    }

    private void abrirVentanaAdministracion() {
        new VentanaAdministracion().setVisible(true);
    }
}
