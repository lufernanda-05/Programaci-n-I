package DuoSecreto.model;

import java.util.ArrayList;
import java.util.List;

public class Ranking {
    private List<Jugador> jugadores;

    public Ranking() {
        this.jugadores = new ArrayList<>();
    }

    // -------------
    public List<Jugador> getJugadores() {
        return jugadores;
    }

    public void agregarJugador(Jugador jugador) {
        jugadores.add(jugador);
    }
}