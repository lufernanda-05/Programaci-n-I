package duosecreto.model;

public class Jugador {
    private String nickname;
    private int    puntajeAcumulado;
    private int    partidasConsecutivas;

    public Jugador(String nickname) {
        this.nickname             = nickname;
        this.puntajeAcumulado     = 0;
        this.partidasConsecutivas = 0;
    }

    public String getNickname()              { return nickname; }
    public int    getPuntajeAcumulado()      { return puntajeAcumulado; }
    public int    getPartidasConsecutivas()  { return partidasConsecutivas; }

    public void sumarPuntaje(int puntos)     { this.puntajeAcumulado += puntos; }
    public void incrementarPartidas()        { this.partidasConsecutivas++; }
    public void reiniciarPuntaje()           { this.puntajeAcumulado = 0; this.partidasConsecutivas = 0; }
}
