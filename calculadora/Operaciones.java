package calculadora;

//clase que implementa las operaciones basicas de la calculadora
public class Operaciones {
    public long sumar(long a, long b) {
        return a + b;
    }

    public long restar(long a, long b) {
        return a - b;
    }

    public long multiplicar(long a, long b) {
        long resultado = 0;
        for (long i = 0; i < b; i++) {
            resultado += a;
        }
        return resultado;
    }

    public long dividir(long a, long b) {
        if (b == 0) {
            System.out.println("Error: División por cero no permitida.");
            return -1; // Indicador de error
        }
        return a / b;
    }

    public long potencia(long a, long b) {
        long resultado = 1;
        for (long i = 0; i < b; i++) {
            resultado *= a;
        }
        return resultado;
    }

    public long raizCuadrada(long a) {
        return (long) Math.sqrt(a);
    }
}
