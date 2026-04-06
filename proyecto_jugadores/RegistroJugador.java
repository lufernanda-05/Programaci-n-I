package proyecto_jugadores;

import javax.swing.*;

public class RegistroJugador {
    public void mostrarRegistroJugador(Jugador jugador, Equipo equipo) {
        JFrame frame = new JFrame("Registro de Jugador");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel nombreLabel = new JLabel("Nombre: " + jugador.obtenerNombre());
        JLabel identificacionLabel = new JLabel("Identificación: " + jugador.obtenerIdentificacion());
        JLabel numeroReferenciaLabel = new JLabel("Número de Referencia: " + jugador.obtenerNumeroReferencia());
        JLabel posicionLabel = new JLabel("Posición: " + jugador.obtenerPosicion());
        JLabel equipoLabel = new JLabel("Equipo: " + equipo.obtenerNombre());
        JLabel nacionalidadLabel = new JLabel("Nacionalidad: " + jugador.obtenerNacionalidad());
        JLabel generoLabel = new JLabel("Género: " + jugador.obtenerGenero());
        JLabel edadLabel = new JLabel("Edad: " + jugador.obtenerEdad());

        panel.add(nombreLabel);
        panel.add(identificacionLabel);
        panel.add(numeroReferenciaLabel);
        panel.add(posicionLabel);
        panel.add(equipoLabel);
        panel.add(nacionalidadLabel);
        panel.add(generoLabel);
        panel.add(edadLabel);

        JButton limpiarButton = new JButton("registrar");
        limpiarButton.addActionListener(e -> {
            nombreLabel.setText("Nombre: ");
            identificacionLabel.setText("Identificación: ");
            numeroReferenciaLabel.setText("Número de Referencia: ");
            posicionLabel.setText("Posición: ");
            equipoLabel.setText("Equipo: ");
            nacionalidadLabel.setText("Nacionalidad: ");
            generoLabel.setText("Género: ");
            edadLabel.setText("Edad: ");
        });

        panel.add(limpiarButton);
        frame.add(panel);
        frame.setVisible(true);
    }
}
