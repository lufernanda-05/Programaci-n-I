
package calculadora;

import java.util.Scanner;

// Clase que maneja la lógica de la calculadora
public class Calculadora {
    private Operaciones operaciones;

    public Calculadora() {
        this.operaciones = new Operaciones();
    }

    private Scanner scanner = new Scanner(System.in);

    public void iniciar() {
        System.out.println("Bienvenido a la calculadora");

        boolean continuar = true;

        while (continuar) {
            System.out.println("\nSeleccione una operación:");
            System.out.println("1. Suma");
            System.out.println("2. Resta");
            System.out.println("3. Multiplicación");
            System.out.println("4. División");
            System.out.println("5. Potencia");
            System.out.println("6. Raíz Cuadrada");
            System.out.println("7. Salir");

            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    realizarOperacion("SUMA");
                    break;
                case 2:
                    realizarOperacion("RESTAS");
                    break;
                case 3:
                    realizarOperacion("MULTIPLICACION");
                    break;
                case 4:
                    realizarOperacion("DIVISION");
                    break;
                case 5:
                    realizarOperacion("POTENCIA");
                    break;
                case 6:
                    realizarRaizCuadrada();
                    break;
                case 7:
                    continuar = false;
                    System.out.println("Gracias por usar la calculadora");
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione otra.");
            }
        }
        scanner.close();
    }

    private void realizarOperacion(String tipo) {
        System.out.println("Inicio " + tipo);
        long num1 = leerNumeroPositivo();
        long num2 = leerNumeroPositivo();

        long resultado = 0;
        switch (tipo) {
            case "SUMA":
                resultado = operaciones.sumar(num1, num2);
                break;
            case "RESTAS":
                resultado = operaciones.restar(num1, num2);
                break;
            case "MULTIPLICACION":
                resultado = operaciones.multiplicar(num1, num2);
                break;
            case "DIVISION":
                resultado = operaciones.dividir(num1, num2);
                if (resultado == -1)
                    return; // Manejo de error
                break;
            case "POTENCIA":
                resultado = operaciones.potencia(num1, num2);
                break;
        }
        imprimir(resultado);
    }

    private void realizarRaizCuadrada() {
        System.out.println("Inicio RAIZ CUADRADA");
        long num1 = leerNumeroPositivo();
        long resultado = operaciones.raizCuadrada(num1);
        imprimir(resultado);
    }

    private long leerNumeroPositivo() {
        long numero;
        while (true) {
            System.out.print("Introduce un número positivo: ");
            numero = scanner.nextLong();
            if (numero >= 0) {
                break; // Salir del bucle si el número es positivo
            } else {
                System.out.println("Error: El número no puede ser negativo. Inténtalo de nuevo.");
            }
        }
        return numero;
    }

    public void imprimir(long numero) {
        System.out.println("Resultado es " + numero);
    }
}
