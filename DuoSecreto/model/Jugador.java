package DuoSecreto.model;

public class Jugador {
    private String nickname;
    private int puntaje;

    public Jugador(String nickname) {
        this.nickname = nickname;
        this.puntaje = 0;
    }

    public String getNickname() {
        return nickname;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }
}