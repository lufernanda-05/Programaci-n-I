package DuoSecreto.model;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;

public class Juego {
    private Dificultad dificultad;
    private List<carta> cartas;
    private EstadoJuego estadoJuego;
    private Jugador jugador;
    private carta cartaSeleccionada1;
    private carta cartaSeleccionada2;

    public Juego(Dificultad dificultad, List<carta> cartas, Jugador jugador) {
        this.dificultad = dificultad;
        this.cartas = cartas;
        this.estadoJuego = new EstadoJuego();
        this.jugador = jugador;
        this.cartaSeleccionada1 = null;
        this.cartaSeleccionada2 = null;
    }

    public Dificultad getDificultad() {
        return dificultad;
    }

    public List<carta> getCartas() {
        return cartas;
    }

    public EstadoJuego getEstadoJuego() {
        return estadoJuego;
    }

    public Jugador getJugador() {
        return jugador;
    }

    public void seleccionarCarta(carta carta) {
        if (carta.getEstado() == EstadoCarta.OCULTA) {
            descubrircarta(carta);
            if (cartaSeleccionada1 == null) {
                cartaSeleccionada1 = carta;
            } else {
                cartaSeleccionada2 = carta;
                comprobarPareja();
            }
        }
    }

    private void descubrircarta(carta carta) {
        carta.setEstado(EstadoCarta.DESCUBIERTA);
    }

    private void emparejarCartas() {
        cartaSeleccionada1.setEstado(EstadoCarta.EMPAREJADA);
        cartaSeleccionada2.setEstado(EstadoCarta.EMPAREJADA);
        estadoJuego.setPuntaje(estadoJuego.getPuntaje() + dificultad.getPuntosPorPareja());
        cartaSeleccionada1 = null;
        cartaSeleccionada2 = null;
    }

    private void comprobarPareja() {
        if (cartaSeleccionada1.getId() == cartaSeleccionada2.getId()) {
            emparejarCartas();
        } else {
            // Ocultar las cartas después de un breve tiempo
        }
    }

    public void actualizarTiempo() {
        estadoJuego.setTiempo(estadoJuego.getTiempo() + 1);
    }

    public boolean comprobarFinJuego() {
        int cartasEmparejadas = 0;
        for (carta carta : cartas) {
            if (carta.getEstado() == EstadoCarta.EMPAREJADA) {
                cartasEmparejadas++;
            }
        }
        estadoJuego.setFinalizado(cartasEmparejadas == cartas.size());
        return estadoJuego.isFinalizado();
    }

    public static List<carta> crearCartas(int numeroParejas) {
        List<carta> cartas = new ArrayList<>();
        for (int i = 0; i < numeroParejas; i++) {
            cartas.add(new carta(i, "imagen" + i));
            cartas.add(new carta(i, "imagen" + i));
        }
        Collections.shuffle(cartas);
        return cartas;
    }
}