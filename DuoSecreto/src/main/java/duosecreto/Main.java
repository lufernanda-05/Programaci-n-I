package duosecreto;

import duosecreto.ventana.VentanaMenu;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Look and feel del sistema para mejor integración en Windows/Mac/Linux
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ignored) {
        }

        SwingUtilities.invokeLater(() -> new VentanaMenu().setVisible(true));
    }
}
