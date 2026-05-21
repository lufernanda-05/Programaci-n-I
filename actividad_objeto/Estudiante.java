package actividad_objeto;

public class Estudiante {

    private int id;
    private String nombre;
    private int edad;
    private String genero;

    public Estudiante(int id, String nombre, String apellido, int edad, String genero, String carrera, int semestre,
            double promedio) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.genero = genero;
    }

    public Estudiante(int i, String string, int j, String string2, String string3, int k, double d) {
    }

    @Override // imprimimos los datos del estudiante
    public String toString() {
        return "Estudiante{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", genero='" + genero + '\'' +
                '}';
    }
}
