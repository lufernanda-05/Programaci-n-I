package duosecreto.dto;

import java.io.Serializable;

public class Dificultad implements Serializable {
    private static final long serialVersionUID = 1L;

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

    public String getNombre()         { return nombre; }
    public int getNumeroParejas()     { return numeroParejas; }
    public int getTiempoMaximo()      { return tiempoMaximo; }
    public int getPuntosPorPareja()   { return puntosPorPareja; }

    public void setNombre(String nombre)               { this.nombre = nombre; }
    public void setNumeroParejas(int numeroParejas)    { this.numeroParejas = numeroParejas; }
    public void setTiempoMaximo(int tiempoMaximo)      { this.tiempoMaximo = tiempoMaximo; }
    public void setPuntosPorPareja(int puntosPorPareja){ this.puntosPorPareja = puntosPorPareja; }

    @Override
    public String toString() { return nombre; }
}
