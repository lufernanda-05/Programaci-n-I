package ejercicioVotantes;

//importamos los otros metodos de las otras clases para usarlos en el main
import ejercicioVotantes.Interaccion.Consola;
import ejercicioVotantes.datosprincipales.Votante;
import ejercicioVotantes.util.Procesos;

import java.time.LocalDate;

//ejecuta el programa y se traen los datos.
public class Main {
    public static void main(String[] args) {
        Consola consola = new Consola();
        Procesos procesos = new Procesos();
        System.out.println("Bienvenido al sistema de votantes.");
        String nombre = consola.leer("Ingrese el nombre: ");
        String apellido = consola.leer("Ingrese el apellido: ");
        long numeroIdentificacion = Long.parseLong(consola.leer("Ingrese el número de identificación: "));
        LocalDate fechaNacimiento = procesos
                .convertirStringDate(consola.leer("Ingrese la fecha de nacimiento (dia-mes-año): "));

        Votante votante = new Votante(nombre, apellido, numeroIdentificacion, fechaNacimiento);
        int edad = votante.calcularEdad();
        String mensaje = (edad >= 18) ? "Usted es apto para votar." : " usted no es apto para votar.";
        // ddaos del votante y el mensaje de si puede votar o no dependiendo de la edad
        // del votante
        consola.imprimir("\nDetalles del votante:");
        consola.imprimir("Nombre: " + votante.obtenerNombre());
        consola.imprimir("Apellido: " + votante.obtenerApellido());
        consola.imprimir("Número de identificación: " + votante.obtenerNumeroIdentificacion());
        consola.imprimir("Edad: " + edad + " años.");
        consola.imprimir(mensaje);
    }
}