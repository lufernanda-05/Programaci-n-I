package DuoSecreto.dto;

import java.io.Serializable;

public class Resultado implements Serializable {
    private String nombreJugador;
    private int puntaje;
    private long tiempoJuego;

    public Resultado(String nombreJugador, int puntaje, long tiempoJuego) {
        this.nombreJugador = nombreJugador;
        this.puntaje = puntaje;
        this.tiempoJuego = tiempoJuego;
    }
    // -----

    public String getNombreJugador() {
        return nombreJugador;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public long getTiempoJuego() {
        return tiempoJuego;
    }
}
