package DuoSecreto.model;

public class Dificultad {
    private String nombre;
    private int numeroParejas;
    private int tiempoMaximo;
    private int puntosPorPareja;

    public Dificultad(String nombre, int numeroParejas, int tiempoMaximo, int puntosPorPareja) {
        this.nombre = nombre;
        this.numeroParejas = numeroParejas;
        this.tiempoMaximo = tiempoMaximo;
        this.puntosPorPareja = puntosPorPareja;
    }

    public String getNombre() {
        return nombre;
    }

    public int getNumeroParejas() {
        return numeroParejas;
    }

    public int getTiempoMaximo() {
        return tiempoMaximo;
    }

    public int getPuntosPorPareja() {
        return puntosPorPareja;
    }
}