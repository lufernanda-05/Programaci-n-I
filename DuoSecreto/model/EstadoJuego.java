package DuoSecreto.model;

public class EstadoJuego {
    private int puntaje;
    private int tiempo;
    private boolean finalizado;

    public EstadoJuego() {
        this.puntaje = 0;
        this.tiempo = 0;
        this.finalizado = false;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(int puntaje) {
        this.puntaje = puntaje;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public boolean isFinalizado() {
        return finalizado;
    }

    public void setFinalizado(boolean finalizado) {
        this.finalizado = finalizado;
    }
}