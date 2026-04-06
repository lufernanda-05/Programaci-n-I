package proyecto_jugadores;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Ingrese el nombre del equipo: ");
        String nombreEquipo = scanner.nextLine();
        Equipo equipo = new Equipo(nombreEquipo);

        System.out.print("Ingrese el nombre del jugador: ");
        String nombreJugador = scanner.nextLine();
        System.out.print("Ingrese la identificación del jugador: ");
        String identificacionJugador = scanner.nextLine();
        System.out.print("Ingrese el número de referencia del jugador: ");
        int numeroReferenciaJugador = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        System.out.print("Ingrese la posición del jugador: ");
        String posicionJugador = scanner.nextLine();
        System.out.print("Ingrese la nacionalidad del jugador: ");
        String nacionalidadJugador = scanner.nextLine();
        System.out.print("Ingrese el género del jugador (Masculino/Femenino): ");
        String generoJugador = scanner.nextLine();
        System.out.print("Ingrese la edad del jugador: ");
        int edadJugador = scanner.nextInt();

        Jugador jugador = new Jugador(nombreJugador, identificacionJugador, numeroReferenciaJugador, posicionJugador,
                equipo, nacionalidadJugador, generoJugador, edadJugador);
        jugador.cambiarEquipo(equipo);

        RegistroJugador ui = new RegistroJugador();
        ui.mostrarRegistroJugador(jugador, equipo);
    }
}
