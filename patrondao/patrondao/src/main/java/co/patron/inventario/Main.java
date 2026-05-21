package co.patron.inventario;

import co.patron.inventario.gui.VentanaInventario;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {
    public static void main(String[] args) {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            VentanaInventario ventana = new VentanaInventario();
            ventana.setLocationRelativeTo(null);
            ventana.setVisible(true);
        });
    }
}
