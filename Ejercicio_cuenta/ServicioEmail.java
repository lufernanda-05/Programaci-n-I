package Ejercicio_cuenta;

import Ejercicio_cuenta.interfases.Notificador;

public class ServicioEmail implements Notificador {
    public void enviar(String mensaje) {
        System.out.println("Enviando Email: " + mensaje);
    }
}