package ejercicioVotantes.Interaccion;

import java.util.Scanner;

public class Consola {
    private Scanner scanner;

    public Consola() {
        this.scanner = new Scanner(System.in);
    }

    public String leer(String prompt) {
        System.out.print(prompt);
        return this.scanner.nextLine();
    }

    // metodo para imprimir un mensaje en la consola con el void que no retorna nada
    // y recibe un mensaje como parametro para imprimirlo en la consola
    public void imprimir(String mensaje) {
        System.out.println(mensaje);
    }
}