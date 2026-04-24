package duosecreto.model;

import duosecreto.dto.Dificultad;

import javax.swing.Timer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class Juego {

    private final Dificultad   dificultad;
    private final List<Carta>  cartas;
    private final EstadoJuego  estadoJuego;
    private final Jugador      jugador;

    private Carta cartaSeleccionada1;
    private Carta cartaSeleccionada2;
    private boolean bloqueado;

    // Callback que la ventana registra para redibujar el tablero
    private Consumer<Void> onActualizarTablero;
    // Callback al completar el juego
    private Consumer<Void> onJuegoTerminado;

    public Juego(Dificultad dificultad, List<Carta> cartas, Jugador jugador) {
        this.dificultad  = dificultad;
        this.cartas      = cartas;
        this.estadoJuego = new EstadoJuego();
        this.jugador     = jugador;
        this.bloqueado   = false;
    }

    // ── Callbacks ────────────────────────────────────────────────────────────
    public void setOnActualizarTablero(Consumer<Void> cb) { this.onActualizarTablero = cb; }
    public void setOnJuegoTerminado(Consumer<Void> cb)    { this.onJuegoTerminado    = cb; }

    // ── Getters ───────────────────────────────────────────────────────────────
    public Dificultad  getDificultad()  { return dificultad; }
    public List<Carta> getCartas()      { return cartas; }
    public EstadoJuego getEstadoJuego() { return estadoJuego; }
    public Jugador     getJugador()     { return jugador; }

    // ── Lógica de selección ───────────────────────────────────────────────────
    public void seleccionarCarta(Carta carta) {
        if (bloqueado) return;
        if (carta.getEstado() != EstadoCarta.OCULTA) return;
        if (carta == cartaSeleccionada1) return;

        carta.setEstado(EstadoCarta.DESCUBIERTA);

        if (cartaSeleccionada1 == null) {
            cartaSeleccionada1 = carta;
            notificarTablero();
        } else {
            cartaSeleccionada2 = carta;
            notificarTablero();
            bloqueado = true;

            if (cartaSeleccionada1.getId() == cartaSeleccionada2.getId()) {
                // ¡Pareja encontrada!
                Timer t = new Timer(600, e -> {
                    cartaSeleccionada1.setEstado(EstadoCarta.EMPAREJADA);
                    cartaSeleccionada2.setEstado(EstadoCarta.EMPAREJADA);
                    estadoJuego.sumarPuntaje(dificultad.getPuntosPorPareja());
                    cartaSeleccionada1 = null;
                    cartaSeleccionada2 = null;
                    bloqueado = false;
                    notificarTablero();
                    verificarFinJuego();
                });
                t.setRepeats(false);
                t.start();
            } else {
                // No coinciden → ocultar después de 1 s
                Timer t = new Timer(1000, e -> {
                    cartaSeleccionada1.setEstado(EstadoCarta.OCULTA);
                    cartaSeleccionada2.setEstado(EstadoCarta.OCULTA);
                    cartaSeleccionada1 = null;
                    cartaSeleccionada2 = null;
                    bloqueado = false;
                    notificarTablero();
                });
                t.setRepeats(false);
                t.start();
            }
        }
    }

    public void incrementarTiempo() {
        estadoJuego.incrementarTiempo();
    }

    private void verificarFinJuego() {
        boolean todas = cartas.stream()
                .allMatch(c -> c.getEstado() == EstadoCarta.EMPAREJADA);
        if (todas) {
            estadoJuego.setFinalizado(true);
            if (onJuegoTerminado != null) onJuegoTerminado.accept(null);
        }
    }

    private void notificarTablero() {
        if (onActualizarTablero != null) onActualizarTablero.accept(null);
    }

    // ── Factory ───────────────────────────────────────────────────────────────
    public static List<Carta> crearCartas(List<String> rutasDisponibles, int numeroParejas,
                                          java.util.function.Function<String, java.awt.image.BufferedImage> loader) {
        List<String> pool = new ArrayList<>(rutasDisponibles);
        Collections.shuffle(pool);
        // Tomar las primeras numeroParejas (o menos si no hay suficientes)
        int usar = Math.min(numeroParejas, pool.size());
        List<Carta> cartas = new ArrayList<>();
        for (int i = 0; i < usar; i++) {
            String ruta  = pool.get(i);
            java.awt.image.BufferedImage img = loader.apply(ruta);
            cartas.add(new Carta(i, ruta, img));
            cartas.add(new Carta(i, ruta, img));
        }
        Collections.shuffle(cartas);
        return cartas;
    }
}
