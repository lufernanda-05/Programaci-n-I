package duosecreto.ventana;

import duosecreto.util.Estilos;

import javax.swing.*;
import java.awt.*;

public class VentanaMenu extends JFrame {

    public VentanaMenu() {
        setTitle("Duo Secreto");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(480, 380);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel fondo = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                // Fondo degradado sutil
                GradientPaint gp = new GradientPaint(0, 0, Estilos.FONDO_PRINCIPAL,
                        0, getHeight(), new Color(25, 20, 50));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        fondo.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        // ── Logo / título ───────────────────────────────────────────────────
        JLabel lblTitulo = new JLabel("Duo Secreto", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblTitulo.setForeground(Estilos.ACENTO_HOVER);

        JLabel lblSub = new JLabel("Juego de memoria", SwingConstants.CENTER);
        lblSub.setFont(Estilos.FUENTE_NORMAL);
        lblSub.setForeground(Estilos.TEXTO_SECUNDARIO);

        JPanel panelTitulo = new JPanel(new GridLayout(2, 1, 0, 4));
        panelTitulo.setOpaque(false);
        panelTitulo.add(lblTitulo);
        panelTitulo.add(lblSub);

        // ── Botones ─────────────────────────────────────────────────────────
        JButton btnJugar = Estilos.botonPrimario("▶  Jugar");
        JButton btnAdmin = Estilos.botonSecundario("⚙  Administración");
        JButton btnSalir = Estilos.botonSecundario("✕  Salir");

        btnJugar.setPreferredSize(new Dimension(220, 44));
        btnAdmin.setPreferredSize(new Dimension(220, 44));
        btnSalir.setPreferredSize(new Dimension(220, 44));

        btnJugar.addActionListener(e -> {
            new VentanaIngresoNickname(this).setVisible(true);
            setVisible(false);
        });
        btnAdmin.addActionListener(e -> new VentanaAdministracion().setVisible(true));
        btnSalir.addActionListener(e -> System.exit(0));

        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false);
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));
        panelBotones.add(Box.createVerticalStrut(30));
        panelBotones.add(centrar(btnJugar));
        panelBotones.add(Box.createVerticalStrut(12));
        panelBotones.add(centrar(btnAdmin));
        panelBotones.add(Box.createVerticalStrut(12));
        panelBotones.add(centrar(btnSalir));

        fondo.add(panelTitulo,  BorderLayout.NORTH);
        fondo.add(panelBotones, BorderLayout.CENTER);

        JLabel lblVersion = new JLabel("v1.0  |  POO — 2025", SwingConstants.CENTER);
        lblVersion.setFont(Estilos.FUENTE_PEQUEÑA);
        lblVersion.setForeground(new Color(90, 88, 120));
        fondo.add(lblVersion, BorderLayout.SOUTH);

        setContentPane(fondo);
    }

    private JPanel centrar(JComponent c) {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        p.setOpaque(false);
        p.add(c);
        return p;
    }
}
