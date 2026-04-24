package duosecreto.model;

public class EstadoJuego {
    private int     puntaje;
    private int     tiempoTranscurrido;
    private boolean finalizado;

    public EstadoJuego() {
        this.puntaje            = 0;
        this.tiempoTranscurrido = 0;
        this.finalizado         = false;
    }

    public int  getPuntaje()             { return puntaje; }
    public void setPuntaje(int p)        { this.puntaje = p; }
    public void sumarPuntaje(int p)      { this.puntaje += p; }

    public int  getTiempoTranscurrido()  { return tiempoTranscurrido; }
    public void incrementarTiempo()      { this.tiempoTranscurrido++; }

    public boolean isFinalizado()        { return finalizado; }
    public void    setFinalizado(boolean f){ this.finalizado = f; }
}
