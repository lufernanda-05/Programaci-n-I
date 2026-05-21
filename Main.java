import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Crear una lista de estudiantes
        List<Estudiante> estudiantes = new ArrayList<>();
        estudiantes.add(new Estudiante(20, "Juan"));
        estudiantes.add(new Estudiante(1, "María"));
        estudiantes.add(new Estudiante(5, "Carlos"));
        estudiantes.add(new Estudiante(4, "Ana"));

        // Ordenar la lista por ID
        estudiantes.sort((e1, e2) -> Integer.compare(e1.getId(), e2.getId()));

        // Mostrar lista ordenada
        for (Estudiante estudiante : estudiantes) {
            System.out.println(estudiante);
        }
    }
}
