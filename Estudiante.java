public class Estudiante {
    private int id;
    private String nombre;

    // Constructor
    public Estudiante(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {// sobrescribimos el método toString para mostrar la información del estudiante
        return "Estudiante{id=" + id + ", nombre='" + nombre + "'}";
    }
}
