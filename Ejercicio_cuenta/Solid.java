package Ejercicio_cuenta;

// 1. SRP: Clase enfocada solo en los datos y lógica básica de la cuenta
class Cuenta {
    private String numero;
    protected double saldo;

    public Cuenta(String numero, double saldo) {
        this.numero = numero;
        this.saldo = saldo;
    }

    public void depositar(double monto) {
        saldo += monto;
    }

    public double getSaldo() {
        return saldo;
    }

    public String getNumero() {
        return numero;
    }
}

// 4. ISP: Interfaces pequeñas y específicas
interface IRetirable {
    void retirar(double monto);
}

interface IInteres {
    void aplicarInteres();
}

// 3. LSP: La CuentaAhorros puede sustituir a Cuenta sin romper el programa
class CuentaAhorros extends Cuenta implements IRetirable, IInteres {
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
}

// 5. DIP: Dependemos de abstracciones (Notificador), no de implementaciones
// (Email)
interface Notificador {
    void enviar(String mensaje);
}

class ServicioEmail implements Notificador {
    public void enviar(String mensaje) {
        System.out.println("Enviando Email: " + mensaje);
    }
}

// 2. OCP: El procesador está cerrado a cambios pero abierto a nuevos
// notificadores
class ProcesadorTransferencias {
    private Notificador notificador;

    public ProcesadorTransferencias(Notificador notificador) {
        this.notificador = notificador;
    }

    public void transferir(IRetirable origen, Cuenta destino, double monto) {
        origen.retirar(monto);
        destino.depositar(monto);
        notificador.enviar("Transferencia de $" + monto + " exitosa.");
    }
}
