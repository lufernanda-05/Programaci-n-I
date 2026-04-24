package duosecreto.ventana;

import duosecreto.dto.Dificultad;
import duosecreto.dto.Resultado;
import duosecreto.model.Carta;
import duosecreto.model.EstadoCarta;
import duosecreto.model.Juego;
import duosecreto.model.Jugador;
import duosecreto.util.Estilos;
import duosecreto.util.GestorDatos;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

public class VentanaJuego extends JFrame {

    private final JFrame   ventanaMenu;
    private       Jugador  jugador;
    private       Dificultad dificultad;
    private       Juego    juego;

    // ── UI ─────────────────────────────────────────────────────────────────
    private JLabel   lblTiempo;
    private JLabel   lblPuntaje;
    private JLabel   lblPartidas;
    private JLabel   lblDificultad;
    private JPanel   panelCartas;
    private JButton  btnReiniciar;

    // ── Timer Swing ────────────────────────────────────────────────────────
    private javax.swing.Timer timerJuego;
    private int tiempoRestante;

    // ── Imágenes dorso ────────────────────────────────────────────────────
    private static final BufferedImage IMG_DORSO = crearImgDorso();

    public VentanaJuego(JFrame ventanaMenu, Jugador jugador, Dificultad dificultad) {
        this.ventanaMenu = ventanaMenu;
        this.jugador     = jugador;
        this.dificultad  = dificultad;

        setTitle("Duo Secreto — " + jugador.getNickname());
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override public void windowClosing(java.awt.event.WindowEvent e) { salir(); }
        });
        setSize(900, 680);
        setLocationRelativeTo(null);

        construirUI();
        iniciarNuevaPartida();
    }

    // ── Construcción de UI ────────────────────────────────────────────────

    private void construirUI() {
        JPanel raiz = new JPanel(new BorderLayout(0, 0));
        raiz.setBackground(Estilos.FONDO_PRINCIPAL);
        setContentPane(raiz);

        raiz.add(construirPanelSuperior(), BorderLayout.NORTH);

        panelCartas = new JPanel();
        panelCartas.setBackground(Estilos.FONDO_PRINCIPAL);
        panelCartas.setBorder(new EmptyBorder(10, 20, 10, 20));
        raiz.add(panelCartas, BorderLayout.CENTER);

        raiz.add(construirPanelInferior(), BorderLayout.SOUTH);
    }

    private JPanel construirPanelSuperior() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Estilos.FONDO_PANEL);
        p.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Estilos.BORDE),
                new EmptyBorder(12, 20, 12, 20)));

        // Nombre + dificultad
        lblDificultad = new JLabel();
        lblDificultad.setFont(Estilos.FUENTE_SUBTITULO);
        lblDificultad.setForeground(Estilos.ACENTO_HOVER);

        JLabel lblNick = new JLabel("👤 " + jugador.getNickname());
        lblNick.setFont(Estilos.FUENTE_NORMAL);
        lblNick.setForeground(Estilos.TEXTO_SECUNDARIO);

        JPanel izq = new JPanel(new GridLayout(2, 1));
        izq.setOpaque(false);
        izq.add(lblDificultad);
        izq.add(lblNick);

        // Estadísticas centrales
        lblTiempo  = stat("⏱ --:--");
        lblPuntaje = stat("★ 0 pts");
        lblPartidas = stat("🎮 Partida 1");

        JPanel centro = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        centro.setOpaque(false);
        centro.add(lblTiempo);
        centro.add(lblPuntaje);
        centro.add(lblPartidas);

        p.add(izq,    BorderLayout.WEST);
        p.add(centro, BorderLayout.CENTER);
        return p;
    }

    private JPanel construirPanelInferior() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.CENTER, 16, 12));
        p.setBackground(Estilos.FONDO_PANEL);
        p.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Estilos.BORDE));

        btnReiniciar = Estilos.botonSecundario("↺ Reiniciar");
        JButton btnSalir = Estilos.botonSecundario("✕ Salir al menú");

        btnReiniciar.addActionListener(e -> reiniciar());
        btnSalir.addActionListener(e -> salir());

        p.add(btnReiniciar);
        p.add(btnSalir);
        return p;
    }

    private JLabel stat(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(Estilos.FUENTE_MONO);
        l.setForeground(Estilos.TEXTO_PRIMARIO);
        return l;
    }

    // ── Lógica de juego ───────────────────────────────────────────────────

    private void iniciarNuevaPartida() {
        detenerTimer();

        List<String> rutas = GestorDatos.cargarRutasImagenes();
        List<Carta> cartas = Juego.crearCartas(rutas, dificultad.getNumeroParejas(),
                this::cargarImagen);

        juego = new Juego(dificultad, cartas, jugador);
        juego.setOnActualizarTablero(v -> SwingUtilities.invokeLater(this::renderizarCartas));
        juego.setOnJuegoTerminado(v   -> SwingUtilities.invokeLater(this::alTerminarJuego));

        tiempoRestante = dificultad.getTiempoMaximo();

        actualizarEncabezado();
        renderizarCartas();
        iniciarTimer();
    }

    private void iniciarTimer() {
        timerJuego = new javax.swing.Timer(1000, e -> {
            juego.incrementarTiempo();
            tiempoRestante--;

            int seg = dificultad.getTiempoMaximo() - juego.getEstadoJuego().getTiempoTranscurrido();
            lblTiempo.setText(String.format("⏱ %02d:%02d", seg / 60, seg % 60));
            lblTiempo.setForeground(seg <= 10 ? Estilos.PELIGRO : Estilos.TEXTO_PRIMARIO);

            if (tiempoRestante <= 0) {
                detenerTimer();
                alAgotarTiempo();
            }
        });
        timerJuego.start();
    }

    private void detenerTimer() {
        if (timerJuego != null && timerJuego.isRunning()) timerJuego.stop();
    }

    private void actualizarEncabezado() {
        lblDificultad.setText("Duo Secreto — " + dificultad.getNombre());
        lblPuntaje.setText("★ " + (jugador.getPuntajeAcumulado()
                + juego.getEstadoJuego().getPuntaje()) + " pts");
        lblPartidas.setText("🎮 Partida " + (jugador.getPartidasConsecutivas() + 1));
    }

    // ── Renderizado del tablero ────────────────────────────────────────────

    private void renderizarCartas() {
        panelCartas.removeAll();
        List<Carta> cartas = juego.getCartas();
        int total = cartas.size();

        int cols = calcularColumnas(total);
        int rows = (int) Math.ceil((double) total / cols);
        panelCartas.setLayout(new GridLayout(rows, cols, 8, 8));

        Dimension areaTablacion = new Dimension(
                (panelCartas.getWidth()  - (cols + 1) * 8) / cols,
                (panelCartas.getHeight() - (rows + 1) * 8) / rows);
        if (areaTablacion.width  < 60) areaTablacion.width  = 90;
        if (areaTablacion.height < 60) areaTablacion.height = 90;

        for (Carta carta : cartas) {
            panelCartas.add(crearBotonCarta(carta, areaTablacion));
        }
        panelCartas.revalidate();
        panelCartas.repaint();

        // Actualizar puntaje en encabezado
        lblPuntaje.setText("★ " + (jugador.getPuntajeAcumulado()
                + juego.getEstadoJuego().getPuntaje()) + " pts");
    }

    private JButton crearBotonCarta(Carta carta, Dimension tamano) {
        JButton btn = new JButton() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

                Color fondo;
                EstadoCarta estado = carta.getEstado();
                if (estado == EstadoCarta.OCULTA) {
                    fondo = getModel().isRollover() ? new Color(65, 62, 115) : Estilos.CARTA_DORSO;
                } else if (estado == EstadoCarta.DESCUBIERTA) {
                    fondo = Estilos.ACENTO;
                } else {
                    fondo = Estilos.CARTA_EMPAREJADA;
                }
                g2.setColor(fondo);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 14, 14);

                if (carta.getEstado() == EstadoCarta.OCULTA) {
                    // Dorso con patrón
                    g2.setColor(new Color(255, 255, 255, 18));
                    for (int i = 0; i < getWidth(); i += 12) {
                        g2.drawLine(i, 0, 0, i);
                        g2.drawLine(getWidth(), i, i, getHeight());
                    }
                    g2.setColor(new Color(255, 255, 255, 40));
                    g2.setFont(new Font("Segoe UI", Font.BOLD, Math.min(getWidth(), getHeight()) / 3));
                    FontMetrics fm = g2.getFontMetrics();
                    String s = "?";
                    g2.drawString(s, (getWidth() - fm.stringWidth(s)) / 2,
                            (getHeight() + fm.getAscent()) / 2 - 2);
                } else {
                    // Mostrar imagen
                    BufferedImage img = carta.getImagen();
                    if (img != null) {
                        int pw = getWidth() - 8, ph = getHeight() - 8;
                        double scaleX = (double) pw / img.getWidth();
                        double scaleY = (double) ph / img.getHeight();
                        double scale  = Math.min(scaleX, scaleY);
                        int iw = (int)(img.getWidth()  * scale);
                        int ih = (int)(img.getHeight() * scale);
                        g2.drawImage(img, (getWidth()  - iw) / 2,
                                         (getHeight() - ih) / 2, iw, ih, null);
                    } else {
                        // Sin imagen — mostrar ícono genérico
                        g2.setColor(Color.WHITE);
                        g2.setFont(new Font("Segoe UI", Font.BOLD,
                                Math.min(getWidth(), getHeight()) / 3));
                        String s = String.valueOf(carta.getId() + 1);
                        FontMetrics fm = g2.getFontMetrics();
                        g2.drawString(s, (getWidth() - fm.stringWidth(s)) / 2,
                                (getHeight() + fm.getAscent()) / 2 - 2);
                    }
                    // Borde de estado
                    g2.setColor(carta.getEstado() == EstadoCarta.EMPAREJADA
                            ? Estilos.EXITO : Color.WHITE);
                    g2.setStroke(new BasicStroke(2));
                    g2.drawRoundRect(1, 1, getWidth()-2, getHeight()-2, 14, 14);
                }
                g2.dispose();
            }
        };

        btn.setOpaque(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setPreferredSize(tamano);
        btn.setCursor(carta.getEstado() == EstadoCarta.EMPAREJADA
                ? Cursor.getDefaultCursor()
                : new Cursor(Cursor.HAND_CURSOR));

        if (carta.getEstado() != EstadoCarta.EMPAREJADA) {
            btn.addActionListener(e -> juego.seleccionarCarta(carta));
            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override public void mouseEntered(java.awt.event.MouseEvent e) { btn.repaint(); }
                @Override public void mouseExited (java.awt.event.MouseEvent e) { btn.repaint(); }
            });
        }
        return btn;
    }

    private int calcularColumnas(int total) {
        if (total <= 8)  return 4;
        if (total <= 12) return 4;
        if (total <= 16) return 4;
        if (total <= 20) return 5;
        return 5;
    }

    // ── Fin de juego ──────────────────────────────────────────────────────

    private void alTerminarJuego() {
        detenerTimer();
        long tiempoUsado = juego.getEstadoJuego().getTiempoTranscurrido();
        int  puntajePartida = juego.getEstadoJuego().getPuntaje();

        jugador.sumarPuntaje(puntajePartida);
        jugador.incrementarPartidas();

        // Guardar resultado
        Resultado res = new Resultado(
                jugador.getNickname(),
                jugador.getPuntajeAcumulado(),
                tiempoUsado,
                dificultad.getNombre(),
                jugador.getPartidasConsecutivas());
        GestorDatos.guardarResultado(res);

        // Diálogo de fin
        String msg = String.format(
                "<html><center>"
                + "<b>¡Completaste el juego!</b><br><br>"
                + "Tiempo: <b>%02d:%02d</b><br>"
                + "Puntos esta partida: <b>%d</b><br>"
                + "Puntaje total: <b>%d</b><br><br>"
                + "¿Qué deseas hacer?</center></html>",
                tiempoUsado / 60, tiempoUsado % 60,
                puntajePartida,
                jugador.getPuntajeAcumulado());

        String[] opciones = {"▶ Continuar", "↺ Reiniciar", "✕ Salir al menú"};
        int op = JOptionPane.showOptionDialog(this, msg, "¡Felicitaciones!",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE,
                null, opciones, opciones[0]);

        if (op == 0) {           // Continuar: mismo jugador, misma dificultad
            iniciarNuevaPartida();
        } else if (op == 1) {   // Reiniciar: puntaje en 0, puede cambiar dificultad
            reiniciar();
        } else {                 // Salir
            salir();
        }
    }

    private void alAgotarTiempo() {
        String msg = String.format(
                "<html><center><b>¡Se acabó el tiempo!</b><br><br>"
                + "Dificultad: %s<br>Puntaje esta partida: %d<br><br>"
                + "¿Qué deseas hacer?</center></html>",
                dificultad.getNombre(),
                juego.getEstadoJuego().getPuntaje());

        String[] opciones = {"↺ Reiniciar", "✕ Salir al menú"};
        int op = JOptionPane.showOptionDialog(this, msg, "Tiempo agotado",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE,
                null, opciones, opciones[0]);

        if (op == 0) reiniciar();
        else         salir();
    }

    /** Reiniciar: puntaje=0, puede cambiar dificultad */
    private void reiniciar() {
        detenerTimer();
        jugador.reiniciarPuntaje();

        // Preguntar si cambia dificultad
        List<duosecreto.dto.Dificultad> difs = GestorDatos.cargarDificultades();
        String[] nombres = difs.stream().map(Dificultad::getNombre).toArray(String[]::new);
        String elegida = (String) JOptionPane.showInputDialog(this,
                "Selecciona la dificultad:", "Reiniciar",
                JOptionPane.QUESTION_MESSAGE, null, nombres, dificultad.getNombre());
        if (elegida != null) {
            dificultad = difs.stream()
                    .filter(d -> d.getNombre().equals(elegida))
                    .findFirst().orElse(dificultad);
        }
        iniciarNuevaPartida();
    }

    private void salir() {
        detenerTimer();
        dispose();
        if (ventanaMenu != null) ventanaMenu.setVisible(true);
    }

    // ── Carga de imágenes ─────────────────────────────────────────────────

    private BufferedImage cargarImagen(String ruta) {
        try {
            File f = new File(ruta);
            if (f.exists()) return ImageIO.read(f);
        } catch (Exception ignored) {}
        return null;
    }

    private static BufferedImage crearImgDorso() {
        BufferedImage img = new BufferedImage(80, 80, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setColor(Estilos.CARTA_DORSO);
        g2.fillRect(0, 0, 80, 80);
        g2.setColor(Estilos.ACENTO);
        g2.setFont(new Font("Segoe UI", Font.BOLD, 36));
        g2.drawString("?", 24, 54);
        g2.dispose();
        return img;
    }
}
