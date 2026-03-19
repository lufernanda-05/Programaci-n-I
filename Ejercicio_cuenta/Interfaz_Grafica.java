package Ejercicio_cuenta;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Interfaz_Grafica extends JFrame {
    private CuentaAhorros cuenta1;
    private CuentaAhorros cuenta2;
    private ProcesadorTransferencias procesador;

    public Interfaz_Grafica() {
        // Inicializar cuentas
        cuenta1 = new CuentaAhorros("001", 1000);
        cuenta2 = new CuentaAhorros("002", 500);
        procesador = new ProcesadorTransferencias(new ServicioEmail());

        // Configurar ventana
        setTitle("Sistema de Gestión de Cuentas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(240, 240, 240));

        // Título
        JLabel titulo = new JLabel("Gestión de Cuentas Bancarias");
        titulo.setFont(new Font("Tahoma", Font.BOLD, 18));
        titulo.setHorizontalAlignment(JLabel.CENTER);
        panel.add(titulo);

        // Botón datos
        JButton btnDatos = new JButton("Ver Datos de Cuentas");
        btnDatos.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnDatos.addActionListener(e -> mostrarDatos());
        panel.add(btnDatos);

        // Botón para depositar
        JButton btnDepositar = new JButton("Depositar");
        btnDepositar.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnDepositar.addActionListener(e -> operarDepositar());
        panel.add(btnDepositar);

        // Botón para retirar
        JButton btnRetirar = new JButton("Retirar");
        btnRetirar.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnRetirar.addActionListener(e -> operarRetirar());
        panel.add(btnRetirar);

        // Botón para aplicar interés
        JButton btnInteres = new JButton("Aplicar Interés (1%)");
        btnInteres.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnInteres.addActionListener(e -> operarInteres());
        panel.add(btnInteres);

        // Botón para transferir
        JButton btnTransferir = new JButton("Transferir");
        btnTransferir.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnTransferir.addActionListener(e -> operarTransferir());
        panel.add(btnTransferir);

        add(panel);
        setVisible(true);
    }

    // Mostrar datos de las cuentas
    private void mostrarDatos() {
        String mensaje = String.format(
                "DATOS DE LAS CUENTAS\n\n" +
                        "Cuenta 1:\n" +
                        "  Número: %s\n" +
                        "  Saldo: $%.2f\n\n" +
                        "Cuenta 2:\n" +
                        "  Número: %s\n" +
                        "  Saldo: $%.2f",
                cuenta1.getNumero(), cuenta1.getSaldo(),
                cuenta2.getNumero(), cuenta2.getSaldo());
        JOptionPane.showMessageDialog(this, mensaje, "Datos de Cuentas", JOptionPane.INFORMATION_MESSAGE);
    }

    // Operación de depósito
    private void operarDepositar() {
        String[] opciones = { "Cuenta 1", "Cuenta 2" };
        int seleccion = JOptionPane.showOptionDialog(this, "¿A cuál cuenta deseas depositar?",
                "Seleccionar Cuenta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones,
                opciones[0]);

        if (seleccion == -1)
            return;

        String montoStr = JOptionPane.showInputDialog(this, "Ingresa el monto a depositar:", "0.0");
        if (montoStr == null || montoStr.isEmpty())
            return;

        try {
            double monto = Double.parseDouble(montoStr);
            if (monto <= 0) {
                JOptionPane.showMessageDialog(this, "El monto debe ser positivo.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            CuentaAhorros cuenta = (seleccion == 0) ? cuenta1 : cuenta2;
            cuenta.depositar(monto);

            String resultado = String.format(
                    "Depósito exitoso\n\n" +
                            "Cuenta: %s\n" +
                            "Monto depositado: $%.2f\n" +
                            "Nuevo saldo: $%.2f",
                    cuenta.getNumero(), monto, cuenta.getSaldo());
            JOptionPane.showMessageDialog(this, resultado, "Depósito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingresa un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Operación de retiro
    private void operarRetirar() {
        String[] opciones = { "Cuenta 1", "Cuenta 2" };
        int seleccion = JOptionPane.showOptionDialog(this, "¿De cuál cuenta deseas retirar?",
                "Seleccionar Cuenta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones,
                opciones[0]);

        if (seleccion == -1)
            return;

        String montoStr = JOptionPane.showInputDialog(this, "Ingresa el monto a retirar:", "0.0");
        if (montoStr == null || montoStr.isEmpty())
            return;

        try {
            double monto = Double.parseDouble(montoStr);
            if (monto <= 0) {
                JOptionPane.showMessageDialog(this, "El monto debe ser positivo.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            CuentaAhorros cuenta = (seleccion == 0) ? cuenta1 : cuenta2;
            double saldoAnterior = cuenta.getSaldo();

            cuenta.retirar(monto);

            if (cuenta.getSaldo() == saldoAnterior) {
                JOptionPane.showMessageDialog(this, "Saldo insuficiente.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String resultado = String.format(
                        "Retiro exitoso\n\n" +
                                "Cuenta: %s\n" +
                                "Monto retirado: $%.2f\n" +
                                "Nuevo saldo: $%.2f",
                        cuenta.getNumero(), monto, cuenta.getSaldo());
                JOptionPane.showMessageDialog(this, resultado, "Retiro", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingresa un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Aplicar interés
    private void operarInteres() {
        String[] opciones = { "Cuenta 1", "Cuenta 2" };
        int seleccion = JOptionPane.showOptionDialog(this, "¿A cuál cuenta aplicar interés?",
                "Seleccionar Cuenta", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones,
                opciones[0]);

        if (seleccion == -1)
            return;

        CuentaAhorros cuenta = (seleccion == 0) ? cuenta1 : cuenta2;
        double saldoAnterior = cuenta.getSaldo();
        cuenta.aplicarInteres();

        String resultado = String.format(
                "Interés aplicado (1%%)\n\n" +
                        "Cuenta: %s\n" +
                        "Saldo anterior: $%.2f\n" +
                        "Interés ganado: $%.2f\n" +
                        "Nuevo saldo: $%.2f",
                cuenta.getNumero(), saldoAnterior, cuenta.getSaldo() - saldoAnterior, cuenta.getSaldo());
        JOptionPane.showMessageDialog(this, resultado, "Interés", JOptionPane.INFORMATION_MESSAGE);
    }

    // Operación de transferencia
    private void operarTransferir() {
        String[] opciones = { "Cuenta 1 → Cuenta 2", "Cuenta 2 → Cuenta 1" };
        int seleccion = JOptionPane.showOptionDialog(this, "¿Cuál es la dirección de la transferencia?",
                "Seleccionar Transferencia", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones,
                opciones[0]);

        if (seleccion == -1)
            return;

        String montoStr = JOptionPane.showInputDialog(this, "Ingresa el monto a transferir:", "0.0");
        if (montoStr == null || montoStr.isEmpty())
            return;

        try {
            double monto = Double.parseDouble(montoStr);
            if (monto <= 0) {
                JOptionPane.showMessageDialog(this, "El monto debe ser positivo.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            CuentaAhorros origen = (seleccion == 0) ? cuenta1 : cuenta2;
            Cuenta destino = (seleccion == 0) ? cuenta2 : cuenta1;
            double saldoOrigenAnterior = origen.getSaldo();

            procesador.transferir(origen, destino, monto);

            if (origen.getSaldo() == saldoOrigenAnterior) {
                JOptionPane.showMessageDialog(this, "Saldo insuficiente.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String resultado = String.format(
                        "Transferencia exitosa\n\n" +
                                "Desde: %s (Saldo: $%.2f)\n" +
                                "Hacia: %s (Saldo: $%.2f)\n" +
                                "Monto: $%.2f",
                        origen.getNumero(), origen.getSaldo(),
                        destino.getNumero(), destino.getSaldo(),
                        monto);
                JOptionPane.showMessageDialog(this, resultado, "Transferencia", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingresa un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InterfazGrafica());
    }
}
