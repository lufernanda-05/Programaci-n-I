package DuoSecreto.ventana;

import DuoSecreto.model.Jugador;
import DuoSecreto.model.Dificultad;
import DuoSecreto.model.Juego;
import DuoSecreto.model.carta;
import DuoSecreto.util.UtilidadTiempo;
import DuoSecreto.util.Administracion;
import DuoSecreto.dto.Resultado;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class VentanaJuego extends JFrame {
    private Juego juego;
    private JLabel lblTiempo;
    private JLabel lblPuntaje;
    private JPanel panelCartas;
    private Timer timer;
    private long tiempoInicio;

    public VentanaJuego(Dificultad dificultad, Jugador jugador) {
        setTitle("Duo Secreto");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Inicializar el juego
        juego = new Juego(dificultad, Juego.crearCartas(dificultad.getNumeroParejas()), jugador);

        // Crear la interfaz
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel panelSuperior = new JPanel(new GridLayout(1, 2, 10, 10));
        lblTiempo = new JLabel("Tiempo: 0 s");
        lblPuntaje = new JLabel("Puntaje: 0");
        panelSuperior.add(lblTiempo);
        panelSuperior.add(lblPuntaje);
        panel.add(panelSuperior, BorderLayout.NORTH);

        panelCartas = new JPanel(new GridLayout(4, 5, 10, 10));
        for (carta carta : juego.getCartas()) {
            JButton btnCarta = new JButton(new ImageIcon(carta.getImagen()));
            btnCarta.addActionListener(e -> manejarcartaSeleccionada(carta));
            panelCartas.add(btnCarta);
        }
        panel.add(panelCartas, BorderLayout.CENTER);

        add(panel, BorderLayout.CENTER);

        // Iniciar el juego
        iniciarJuego();
    }

    private void iniciarJuego() {
        tiempoInicio = System.currentTimeMillis();
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                actualizarEstadoJuego();
            }
        }, 1000, 1000);
    }

    private void actualizarEstadoJuego() {
        juego.actualizarTiempo();
        lblTiempo.setText("Tiempo: " + juego.getEstadoJuego().getTiempo() + " s");
        lblPuntaje.setText("Puntaje: " + juego.getEstadoJuego().getPuntaje());

        if (juego.comprobarFinJuego()) {
            timer.cancel();
            long tiempoJuego = UtilidadTiempo.obtenerTiempoTranscurrido(tiempoInicio);
            Jugador jugador = juego.getJugador();
            jugador.setPuntaje(juego.getEstadoJuego().getPuntaje());
            Administracion.guardarResultado(new Resultado(jugador.getNickname(), jugador.getPuntaje(), tiempoJuego));
            JOptionPane.showMessageDialog(this,
                    "¡Felicitaciones! Has completado el juego en " + juego.getEstadoJuego().getTiempo()
                            + " segundos con un puntaje de " + juego.getEstadoJuego().getPuntaje() + " puntos.");
            dispose();
        }
    }

    private void manejarcartaSeleccionada(carta carta) {
        juego.seleccionarCarta(carta);
        actualizarPanelCartas();
    }

    private void actualizarPanelCartas() {
        panelCartas.removeAll();
        for (carta carta : juego.getCartas()) {
            JButton btnCarta = new JButton(new ImageIcon(carta.getImagen()));
            btnCarta.addActionListener(e -> manejarcartaSeleccionada(carta));
            panelCartas.add(btnCarta);
        }
        panelCartas.revalidate();
        panelCartas.repaint();
    }
}