package DuoSecreto.ventana;

import DuoSecreto.model.Dificultad;
import DuoSecreto.model.Jugador;
import DuoSecreto.util.Administracion;
import javax.swing.*;
import java.awt.*;

public class VentanaIngresoNickname extends JFrame {
    private JTextField tfNickname;
    private JButton btnJugar;

    public VentanaIngresoNickname() {
        setTitle("Ingreso de Nickname");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(300, 150);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(2, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Ingresa tu nickname:"));

        tfNickname = new JTextField();
        panel.add(tfNickname);

        btnJugar = new JButton("Jugar");
        btnJugar.addActionListener(e -> iniciarJuego());
        panel.add(btnJugar);

        add(panel, BorderLayout.CENTER);
    }

    private void iniciarJuego() {
        String nickname = tfNickname.getText();
        Jugador jugador = new Jugador(nickname);

        Dificultad dificultad = new Dificultad("Fácil", 10, 60, 10);

        new VentanaJuego(dificultad, jugador).setVisible(true);
        dispose();
    }

}