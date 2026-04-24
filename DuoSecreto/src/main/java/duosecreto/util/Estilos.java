package duosecreto.util;

import java.awt.*;

/** Paleta de colores y utilidades de estilo para toda la aplicación */
public class Estilos {

    public static final Color FONDO_PRINCIPAL  = new Color(18, 18, 30);
    public static final Color FONDO_PANEL      = new Color(28, 28, 45);
    public static final Color FONDO_CARTA      = new Color(40, 40, 65);
    public static final Color ACENTO           = new Color(108, 92, 231);
    public static final Color ACENTO_HOVER     = new Color(130, 116, 248);
    public static final Color TEXTO_PRIMARIO   = new Color(236, 236, 241);
    public static final Color TEXTO_SECUNDARIO = new Color(160, 158, 180);
    public static final Color EXITO            = new Color(0, 206, 158);
    public static final Color PELIGRO          = new Color(231, 76, 60);
    public static final Color CARTA_DORSO      = new Color(55, 52, 100);
    public static final Color CARTA_EMPAREJADA = new Color(0, 150, 110);
    public static final Color BORDE            = new Color(70, 68, 110);

    public static final Font FUENTE_TITULO    = new Font("Segoe UI", Font.BOLD,  22);
    public static final Font FUENTE_SUBTITULO = new Font("Segoe UI", Font.BOLD,  15);
    public static final Font FUENTE_NORMAL    = new Font("Segoe UI", Font.PLAIN, 13);
    public static final Font FUENTE_PEQUEÑA   = new Font("Segoe UI", Font.PLAIN, 11);
    public static final Font FUENTE_MONO      = new Font("Consolas", Font.BOLD,  18);

    /** Aplica colores oscuros base a un componente Swing */
    public static void aplicarEstiloOscuro(javax.swing.JComponent c) {
        c.setBackground(FONDO_PANEL);
        c.setForeground(TEXTO_PRIMARIO);
        c.setFont(FUENTE_NORMAL);
    }

    /** Crea un botón estilizado con el color de acento */
    public static javax.swing.JButton botonPrimario(String texto) {
        javax.swing.JButton btn = new javax.swing.JButton(texto) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? ACENTO_HOVER : ACENTO);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(160, 38));
        return btn;
    }

    /** Botón secundario (outline) */
    public static javax.swing.JButton botonSecundario(String texto) {
        javax.swing.JButton btn = new javax.swing.JButton(texto) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(getModel().isRollover() ? new Color(70, 68, 110) : FONDO_CARTA);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.setColor(BORDE);
                g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 12, 12);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setForeground(TEXTO_PRIMARIO);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(160, 38));
        return btn;
    }
}
