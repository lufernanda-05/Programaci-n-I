package duosecreto.dto;

import java.io.Serializable;

public class Resultado implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nickname;
    private int puntaje;
    private long tiempoSegundos;
    private String nombreDificultad;
    private int partidasConsecutivas;

    public Resultado(String nickname, int puntaje, long tiempoSegundos,
                     String nombreDificultad, int partidasConsecutivas) {
        this.nickname             = nickname;
        this.puntaje              = puntaje;
        this.tiempoSegundos       = tiempoSegundos;
        this.nombreDificultad     = nombreDificultad;
        this.partidasConsecutivas = partidasConsecutivas;
    }

    public String getNickname()            { return nickname; }
    public int    getPuntaje()             { return puntaje; }
    public long   getTiempoSegundos()      { return tiempoSegundos; }
    public String getNombreDificultad()    { return nombreDificultad; }
    public int    getPartidasConsecutivas(){ return partidasConsecutivas; }

    public String getTiempoFormateado() {
        long min = tiempoSegundos / 60;
        long seg = tiempoSegundos % 60;
        return String.format("%02d:%02d", min, seg);
    }
}
