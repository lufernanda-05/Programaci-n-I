package actividad_objeto;

public class main {
    // crear un objeto estudiantes que imprima y ordene por numero de
    // identificacion, nombre, apellido, edad, genero, carrera, semestre, promedio
    public static void main(String[] args) {
        Estudiante estudiante1 = new Estudiante(12345, "Juan", "Perez", 20, "Masculino", "Ingenieria de Sistemas", 5,
                4.5);
        Estudiante estudiante2 = new Estudiante(54321, "Maria", "Gomez", 22, "Femenino", "Medicina", 7, 4.8);
        Estudiante estudiante3 = new Estudiante(67890, "Carlos", "Lopez", 19, "Masculino", "Derecho", 3, 4.2);
        Estudiante estudiante4 = new Estudiante(98765, "Ana", "Garcia", 21, "Femenino", "Arquitectura", 6, 4.7);
        Estudiante estudiante5 = new Estudiante(13579, "Luis", "Martinez", 23, "Masculino",
                "Administracion de Empresas", 8, 4.3);
        Estudiante[] estudiantes = { estudiante1, estudiante2, estudiante3, estudiante4, estudiante5 };
        System.out.println("Estudiantes sin ordenar:");
        for (Estudiante estudiante : estudiantes) {
            System.out.println(estudiante);
        }

        // ordenar por numero de identificacion
        System.out.println("\nEstudiantes ordenados por numero de identificacion:");
        for (int i = 0; i < estudiantes.length - 1; i++) {
            for (int x = 0; x < estudiantes.length - i - 1; x++) {
                if (estudiantes[x].toString().compareTo(estudiantes[x + 1].toString()) > 0) {
                    Estudiante temp = estudiantes[x];
                    estudiantes[x] = estudiantes[x + 1];
                    estudiantes[x + 1] = temp;
                }
            }
        }
        for (Estudiante estudiante11 : estudiantes) {
            System.out.println(estudiante11);
        }
    }
}
