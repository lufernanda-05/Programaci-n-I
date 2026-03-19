package Ejercicio_cuenta;

import Ejercicio_cuenta.cuentas.Cuenta;
import Ejercicio_cuenta.interfases.Notificador;

public class ProcesadorTransferencia {
    private Notificador notificador;

    public ProcesadorTransferencia(Notificador notificador) {
        this.notificador = notificador;
    }

    public void transferir(IRetirable origen, Cuenta destino, double monto) {
        origen.retirar(monto);
        destino.depositar(monto);
        notificador.enviar("Transferencia de $" + monto + " exitosa.");
    }
}
