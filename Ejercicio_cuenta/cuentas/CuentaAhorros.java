package Ejercicio_cuenta.cuentas;

import Ejercicio_cuenta.interfases.Intereses;
import Ejercicio_cuenta.interfases.Iretirable;

public class CuentaAhorros extends Cuenta implements Iretirable, Intereses {
    public CuentaAhorros(String numero, double saldo) {
        super(numero, saldo);
    }

    @Override
    public void retirar(double monto) {
        if (monto <= saldo)
            saldo -= monto;
    }

    @Override
    public void aplicarInteres() {
        saldo += saldo * 0.01;
    }

    public static String generarNumeroCuentaAhorros() {
        String prefijo = "4524";
        String numeros = String.valueOf((int) (Math.random() * 10000));
        // Asegurar que tenga 4 dígitos
        numeros = String.format("%04d", Integer.parseInt(numeros));
        return prefijo + numeros;
    }
}
