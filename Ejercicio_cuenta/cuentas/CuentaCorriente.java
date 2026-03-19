package Ejercicio_cuenta.cuentas;

import Ejercicio_cuenta.interfases.Iretirable;

public class CuentaCorriente extends Cuenta implements Iretirable {
    private double limiteDescubierto;

    public CuentaCorriente(String numero, double saldo) {
        super(numero, saldo);
        this.limiteDescubierto = 500; // Límite de descubierto
    }

    @Override
    public void retirar(double monto) {
        if (monto <= saldo + limiteDescubierto) {
            saldo -= monto;
        }
    }

    public double getLimiteDescubierto() {
        return limiteDescubierto;
    }

    public static String generarNumeroCuentaCorriente() {
        String prefijo = "8524";
        String numeros = String.valueOf((int) (Math.random() * 10000));
        // Asegurar que tenga 4 dígitos
        numeros = String.format("%04d", Integer.parseInt(numeros));
        return prefijo + numeros;
    }
}
