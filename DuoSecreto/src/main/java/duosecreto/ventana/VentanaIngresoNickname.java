package duosecreto.ventana;

import duosecreto.dto.Dificultad;
import duosecreto.model.Jugador;
import duosecreto.util.Estilos;
import duosecreto.util.GestorDatos;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class VentanaIngresoNickname extends JDialog {

    private final JFrame     ventanaPadre;
    private JTextField       tfNickname;
    private JComboBox<Dificultad> cbDificultad;

    public VentanaIngresoNickname(JFrame padre) {
        super(padre, "Nuevo Juego", true);
        this.ventanaPadre = padre;

        setSize(400, 320);
        setLocationRelativeTo(padre);
        setResizable(false);

        JPanel panel = new JPanel(new BorderLayout(0, 0)) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Estilos.FONDO_PRINCIPAL);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        // ── Título ────────────────────────────────────────────────────────
        JLabel lblTitulo = new JLabel("Iniciar partida", SwingConstants.CENTER);
        lblTitulo.setFont(Estilos.FUENTE_TITULO);
        lblTitulo.setForeground(Estilos.TEXTO_PRIMARIO);
        lblTitulo.setBorder(new EmptyBorder(24, 0, 8, 0));
        panel.add(lblTitulo, BorderLayout.NORTH);

        // ── Formulario ────────────────────────────────────────────────────
        JPanel form = new JPanel(new GridLayout(4, 1, 0, 10));
        form.setOpaque(false);
        form.setBorder(new EmptyBorder(10, 40, 10, 40));

        // Nickname
        JLabel lblNick = etiqueta("Nickname");
        tfNickname = new JTextField();
        estilizarCampo(tfNickname);

        // Dificultad
        JLabel lblDif = etiqueta("Dificultad");
        List<Dificultad> dificultades = GestorDatos.cargarDificultades();
        cbDificultad = new JComboBox<>(dificultades.toArray(new Dificultad[0]));
        estilizarCombo(cbDificultad);

        form.add(lblNick);
        form.add(tfNickname);
        form.add(lblDif);
        form.add(cbDificultad);
        panel.add(form, BorderLayout.CENTER);

        // ── Botón Jugar ───────────────────────────────────────────────────
        JButton btnJugar  = Estilos.botonPrimario("▶  Jugar");
        JButton btnVolver = Estilos.botonSecundario("← Volver");
        btnJugar.setPreferredSize(new Dimension(150, 40));
        btnVolver.setPreferredSize(new Dimension(120, 40));

        btnJugar.addActionListener(e -> intentarIniciar());
        btnVolver.addActionListener(e -> { dispose(); ventanaPadre.setVisible(true); });

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 16));
        panelBotones.setOpaque(false);
        panelBotones.add(btnVolver);
        panelBotones.add(btnJugar);
        panel.add(panelBotones, BorderLayout.SOUTH);

        setContentPane(panel);
    }

    private void intentarIniciar() {
        String nick = tfNickname.getText().trim();
        if (nick.isEmpty()) {
            mostrarError("El nickname no puede estar vacío.");
            return;
        }
        if (nick.length() < 3) {
            mostrarError("El nickname debe tener al menos 3 caracteres.");
            return;
        }
        if (GestorDatos.nicknameExiste(nick)) {
            mostrarError("El nickname \"" + nick + "\" ya está registrado.\nElige otro.");
            return;
        }

        Dificultad dif = (Dificultad) cbDificultad.getSelectedItem();
        if (dif == null) dif = GestorDatos.cargarDificultades().get(0);

        List<String> imagenes = GestorDatos.cargarRutasImagenes();
        if (imagenes.isEmpty()) {
            int op = JOptionPane.showConfirmDialog(this,
                    "No hay imágenes cargadas.\n¿Deseas jugar con imágenes de muestra?",
                    "Sin imágenes", JOptionPane.YES_NO_OPTION);
            if (op != JOptionPane.YES_OPTION) return;
        }

        GestorDatos.registrarNickname(nick);
        Jugador jugador = new Jugador(nick);

        dispose();
        VentanaJuego vj = new VentanaJuego(ventanaPadre, jugador, dif);
        vj.setVisible(true);
    }

    // ── Helpers de estilo ─────────────────────────────────────────────────
    private JLabel etiqueta(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(Estilos.FUENTE_PEQUEÑA);
        l.setForeground(Estilos.TEXTO_SECUNDARIO);
        return l;
    }

    private void estilizarCampo(JTextField tf) {
        tf.setBackground(Estilos.FONDO_CARTA);
        tf.setForeground(Estilos.TEXTO_PRIMARIO);
        tf.setCaretColor(Estilos.TEXTO_PRIMARIO);
        tf.setFont(Estilos.FUENTE_NORMAL);
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Estilos.BORDE, 1, true),
                new EmptyBorder(6, 10, 6, 10)));
    }

    private void estilizarCombo(JComboBox<?> cb) {
        cb.setBackground(Estilos.FONDO_CARTA);
        cb.setForeground(Estilos.TEXTO_PRIMARIO);
        cb.setFont(Estilos.FUENTE_NORMAL);
        cb.setBorder(BorderFactory.createLineBorder(Estilos.BORDE, 1, true));
    }

    private void mostrarError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
