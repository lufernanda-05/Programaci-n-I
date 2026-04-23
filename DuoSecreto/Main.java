package DuoSecreto;

import DuoSecreto.ventana.VentanaIngresoNickname;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaIngresoNickname().setVisible(true);
        });
    }
}