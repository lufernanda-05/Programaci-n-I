package proyecto_jugadores;

public class Jugador {
    private String nombre;
    private String identificacion;
    private int numeroReferencia;
    private String posicion;
    private Equipo equipo;
    private String nacionalidad;
    private String genero;
    private int edad;

    public Jugador(String nombre, String identificacion, int numeroReferencia, String posicion, Equipo equipo,
            String nacionalidad, String genero, int edad) {
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.numeroReferencia = numeroReferencia;
        this.posicion = posicion;
        this.equipo = equipo;
        this.nacionalidad = nacionalidad;
        this.genero = genero;
        this.edad = edad;

    }

    // encapsulamiento
    public void cambiarEquipo(Equipo equipo) {
        this.equipo = equipo;
    }

    public String obtenerNombre() {
        return this.nombre;
    }

    public String obtenerIdentificacion() {
        return this.identificacion;
    }

    public int obtenerNumeroReferencia() {
        return this.numeroReferencia;
    }

    public String obtenerPosicion() {
        return this.posicion;
    }

    public Equipo obtenerEquipo() {
        return this.equipo;
    }

    public String obtenerNacionalidad() {
        return this.nacionalidad;
    }

    public String obtenerGenero() {
        return this.genero;
    }

    public int obtenerEdad() {
        return this.edad;
    }
}