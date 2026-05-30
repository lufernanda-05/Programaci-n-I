package co.patron.juego;

import co.patron.juego.gestion.GestorConexion;

public class Main {
    public static void main(String[] args) throws Exception {
        GestorConexion.inicializarDB();
        javax.swing.SwingUtilities.invokeLater(() -> new VentanaJuego());
    }
}