package co.patron.juego;

import co.patron.juego.entidades.Resultado;
import co.patron.juego.gestion.OperacionesJuego;
import co.patron.juego.logica.JuegoLogica;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class VentanaJuego extends JFrame {
    private JTextField txtIntento = new JTextField(10);
    private JTextArea areaLog = new JTextArea(10, 30);
    private JButton btnProbar = new JButton("Probar");

    private String secreto = new JuegoLogica().generarNumero();
    private int intentos = 0;
    private long inicio;
    private OperacionesJuego ops = new OperacionesJuego();
    private String nombreJugador;

    public VentanaJuego() {
        setTitle("Juego Picas y Fijas");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel panelNorte = new JPanel();
        panelNorte.add(new JLabel("Tu intento:"));
        panelNorte.add(txtIntento);
        panelNorte.add(btnProbar);
        add(panelNorte, BorderLayout.NORTH);
        add(new JScrollPane(areaLog), BorderLayout.CENTER);
        JButton btnRanking = new JButton("Ver Ranking");
        add(btnRanking, BorderLayout.SOUTH);
        txtIntento.setEnabled(false);
        btnProbar.setEnabled(false);

        btnProbar.addActionListener(e -> {
            String intento = txtIntento.getText();
            if (intento.length() != 4) {
                JOptionPane.showMessageDialog(this, "Por favor, digita exactamente 4 números.");
                return;
            }

            intentos++;
            String res = new JuegoLogica().evaluar(secreto, intento);
            areaLog.append("Intento " + intentos + ": " + intento + " -> " + res + "\n");
            txtIntento.setText("");

            if (res.equals("4 Fijas 0 Picas")) {
                try {
                    ops.guardar(new Resultado(nombreJugador, secreto, intentos,
                            (System.currentTimeMillis() - inicio) / 1000));
                    areaLog.append("--- Felicidades " + nombreJugador + ", GANASTE =) ---\n");
                    btnProbar.setEnabled(false);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error al guardar en BD: " + ex.getMessage());
                }
            }
        });

        btnRanking.addActionListener(e -> mostrarRanking());

        pack();
        setVisible(true);
        solicitarNombreYIniciar();
    }

    private void solicitarNombreYIniciar() {
        String nick = "";
        while (nick == null || nick.trim().isEmpty()) {
            nick = JOptionPane.showInputDialog(this, "¡Bienvenido! Ingresa tu Nickname para jugar:");
            if (nick == null)
                System.exit(0);
        }
        this.nombreJugador = nick;
        this.inicio = System.currentTimeMillis();
        System.out.println("DEBUG - El número secreto es: " + secreto);
        txtIntento.setEnabled(true);
        btnProbar.setEnabled(true);
    }

    private void mostrarRanking() {
        JFrame frameRanking = new JFrame("Ranking");
        String[] columnas = { "Nick", "Numero", "Intentos", "Tiempo (s)" };
        DefaultTableModel model = new DefaultTableModel(columnas, 0);
        try {
            List<Resultado> lista = ops.obtenerRanking();
            for (Resultado r : lista) {
                model.addRow(new Object[] { r.getNickname(), r.getNumeroGenerado(), r.getIntentos(), r.getTiempo() });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        frameRanking.add(new JScrollPane(new JTable(model)));
        frameRanking.setSize(400, 300);
        frameRanking.setVisible(true);
    }
}
