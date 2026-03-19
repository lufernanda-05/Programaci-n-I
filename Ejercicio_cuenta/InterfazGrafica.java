package Ejercicio_cuenta;

import javax.swing.*;
import java.awt.*;
import Ejercicio_cuenta.cuentas.Cuenta;
import Ejercicio_cuenta.cuentas.CuentaAhorros;
import Ejercicio_cuenta.cuentas.CuentaCorriente;
import Ejercicio_cuenta.interfases.Iretirable;

public class InterfazGrafica extends JFrame {
    private Cuenta cuenta1;
    private Cuenta cuenta2;
    private ProcesadorTransferencia procesador;
    private JLabel lblCuenta1;
    private JLabel lblCuenta2;
    private JPanel panelBotones;

    public InterfazGrafica() {

        mostrarDialogoCrearCuentas();

        if (cuenta1 == null || cuenta2 == null) {
            System.exit(0);
        }

        procesador = new ProcesadorTransferencia(new ServicioEmail());

        // Configurar ventana
        setTitle("Sistema de Gestión de Cuentas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setResizable(true);
        setMinimumSize(new Dimension(700, 500));

        // Panel principal con BorderLayout
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelPrincipal.setBackground(new Color(240, 240, 240));

        // NORTE: Título
        JLabel titulo = new JLabel("<< Elija la operación a realizar >>");
        titulo.setFont(new Font("Verdana", Font.BOLD, 20));
        titulo.setHorizontalAlignment(JLabel.CENTER);
        panelPrincipal.add(titulo, BorderLayout.NORTH);

        // CENTRO: Panel con información de cuentas
        JPanel panelInfo = crearPanelInfo();
        panelPrincipal.add(panelInfo, BorderLayout.CENTER);

        // SUR: Panel con botones
        panelBotones = crearPanelBotones();
        JScrollPane scrollBotones = new JScrollPane(panelBotones);
        scrollBotones.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollBotones.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        panelPrincipal.add(scrollBotones, BorderLayout.SOUTH);

        add(panelPrincipal);
        setVisible(true);
    }

    // cuentas
    private void mostrarDialogoCrearCuentas() {
        JPanel panelSeleccion = new JPanel(new GridLayout(2, 2, 15, 15));
        panelSeleccion.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelSeleccion.setBackground(new Color(240, 240, 240));

        // Selección Cuenta 1
        JLabel lblTipo1 = new JLabel("Crear Cuenta 1:");
        lblTipo1.setFont(new Font("Arial", Font.BOLD, 13));

        JButton btnCuenta1Ahorros = new JButton("Cuenta de Ahorros");
        btnCuenta1Ahorros.setFont(new Font("Arial", Font.PLAIN, 12));
        btnCuenta1Ahorros.addActionListener(e -> {
            DialogoCrearCuenta dialogo = new DialogoCrearCuenta(this, "Ahorros");
            dialogo.setVisible(true);
            cuenta1 = dialogo.getCuentaCreada();
        });

        JButton btnCuenta1Corriente = new JButton("Cuenta Corriente");
        btnCuenta1Corriente.setFont(new Font("Arial", Font.PLAIN, 12));
        btnCuenta1Corriente.addActionListener(e -> {
            DialogoCrearCuenta dialogo = new DialogoCrearCuenta(this, "Corriente");
            dialogo.setVisible(true);
            cuenta1 = dialogo.getCuentaCreada();
        });

        // Selección Cuenta 2
        JLabel lblTipo2 = new JLabel("Crear Cuenta 2:");
        lblTipo2.setFont(new Font("Arial", Font.BOLD, 13));

        JButton btnCuenta2Ahorros = new JButton("Cuenta de Ahorros");
        btnCuenta2Ahorros.setFont(new Font("Arial", Font.PLAIN, 12));
        btnCuenta2Ahorros.addActionListener(e -> {
            DialogoCrearCuenta dialogo = new DialogoCrearCuenta(this, "Ahorros");
            dialogo.setVisible(true);
            cuenta2 = dialogo.getCuentaCreada();
        });

        JButton btnCuenta2Corriente = new JButton("Cuenta Corriente");
        btnCuenta2Corriente.setFont(new Font("Arial", Font.PLAIN, 12));
        btnCuenta2Corriente.addActionListener(e -> {
            DialogoCrearCuenta dialogo = new DialogoCrearCuenta(this, "Corriente");
            dialogo.setVisible(true);
            cuenta2 = dialogo.getCuentaCreada();
        });

        panelSeleccion.add(lblTipo1);
        JPanel panelBotones1 = new JPanel(new FlowLayout());
        panelBotones1.add(btnCuenta1Ahorros);
        panelBotones1.add(btnCuenta1Corriente);
        panelSeleccion.add(panelBotones1);

        panelSeleccion.add(lblTipo2);
        JPanel panelBotones2 = new JPanel(new FlowLayout());
        panelBotones2.add(btnCuenta2Ahorros);
        panelBotones2.add(btnCuenta2Corriente);
        panelSeleccion.add(panelBotones2);

        int resultado = JOptionPane.showConfirmDialog(
                null,
                panelSeleccion,
                "Crear Cuentas Bancarias",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        if (resultado != JOptionPane.OK_OPTION) {
            System.exit(0);
        }
    }

    // ✅ Panel con información de cuentas (lado izquierdo)
    private JPanel crearPanelInfo() {
        JPanel panelInfo = new JPanel(new GridLayout(2, 1, 10, 10));
        panelInfo.setBorder(BorderFactory.createTitledBorder("Información de Cuentas"));
        panelInfo.setBackground(new Color(255, 255, 255));

        // Información Cuenta 1
        lblCuenta1 = new JLabel();
        lblCuenta1.setFont(new Font("Courier New", Font.PLAIN, 12));
        lblCuenta1.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        actualizarLabelCuenta1();
        panelInfo.add(lblCuenta1);

        // Información Cuenta 2
        lblCuenta2 = new JLabel();
        lblCuenta2.setFont(new Font("Courier New", Font.PLAIN, 12));
        lblCuenta2.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        actualizarLabelCuenta2();
        panelInfo.add(lblCuenta2);

        return panelInfo;
    }

    // Actualizar información de Cuenta 1
    private void actualizarLabelCuenta1() {
        String tipo = cuenta1 instanceof CuentaAhorros ? "Ahorros" : "Corriente";
        String info = "<html><b>Cuenta 1 (" + tipo + ")</b><br>" +
                "Número: " + cuenta1.getNumero() + "<br>" +
                "Saldo: $" + String.format("%.2f", cuenta1.getSaldo()) + "</html>";
        lblCuenta1.setText(info);
    }

    // Actualizar información de Cuenta 2
    private void actualizarLabelCuenta2() {
        String tipo = cuenta2 instanceof CuentaAhorros ? "Ahorros" : "Corriente";
        String info = "<html><b>Cuenta 2 (" + tipo + ")</b><br>" +
                "Número: " + cuenta2.getNumero() + "<br>" +
                "Saldo: $" + String.format("%.2f", cuenta2.getSaldo()) + "</html>";
        lblCuenta2.setText(info);
    }

    // ✅ Panel con botones (lado derecho, con scroll)
    private JPanel crearPanelBotones() {
        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Operaciones"));
        panel.setBackground(new Color(240, 240, 240));

        // Botón para ver datos
        JButton btnDatos = new JButton("Ver Datos de Cuentas");
        btnDatos.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        btnDatos.addActionListener(e -> mostrarDatos());
        panel.add(btnDatos);

        // Botón para depositar
        JButton btnDepositar = new JButton("Depositar");
        btnDepositar.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        btnDepositar.addActionListener(e -> operarDepositar());
        panel.add(btnDepositar);

        // Botón para retirar
        JButton btnRetirar = new JButton("Retirar");
        btnRetirar.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        btnRetirar.addActionListener(e -> operarRetirar());
        panel.add(btnRetirar);

        // Botón para aplicar interés (solo para CuentaAhorros)
        JButton btnInteres = new JButton("Aplicar Interés (1%)");
        btnInteres.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        btnInteres.addActionListener(e -> operarInteres());
        panel.add(btnInteres);

        // Botón para transferir
        JButton btnTransferir = new JButton("Transferir");
        btnTransferir.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        btnTransferir.addActionListener(e -> operarTransferir());
        panel.add(btnTransferir);

        return panel;
    }

    // Mostrar datos de las cuentas
    private void mostrarDatos() {
        String tipo1 = cuenta1 instanceof CuentaAhorros ? "Ahorros" : "Corriente";
        String tipo2 = cuenta2 instanceof CuentaAhorros ? "Ahorros" : "Corriente";

        String mensaje = String.format(
                "DATOS DE LAS CUENTAS\n\n" +
                        "Cuenta 1 (%s):\n" +
                        "  Número: %s\n" +
                        "  Saldo: $%.2f\n\n" +
                        "Cuenta 2 (%s):\n" +
                        "  Número: %s\n" +
                        "  Saldo: $%.2f",
                tipo1, cuenta1.getNumero(), cuenta1.getSaldo(),
                tipo2, cuenta2.getNumero(), cuenta2.getSaldo());
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

            Cuenta cuenta = (seleccion == 0) ? cuenta1 : cuenta2;
            cuenta.depositar(monto);

            String resultado = String.format(
                    "Depósito exitoso\n\n" +
                            "Cuenta: %s\n" +
                            "Monto depositado: $%.2f\n" +
                            "Nuevo saldo: $%.2f",
                    cuenta.getNumero(), monto, cuenta.getSaldo());
            JOptionPane.showMessageDialog(this, resultado, "Depósito", JOptionPane.INFORMATION_MESSAGE);

            // ✅ Actualizar labels
            if (seleccion == 0)
                actualizarLabelCuenta1();
            else
                actualizarLabelCuenta2();

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

            Cuenta cuenta = (seleccion == 0) ? cuenta1 : cuenta2;
            double saldoAnterior = cuenta.getSaldo();

            if (cuenta instanceof IRetirable) {
                ((IRetirable) cuenta).retirar(monto);
            }

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

                // ✅ Actualizar labels
                if (seleccion == 0)
                    actualizarLabelCuenta1();
                else
                    actualizarLabelCuenta2();
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

        Cuenta cuenta = (seleccion == 0) ? cuenta1 : cuenta2;

        // Verificar si es CuentaAhorros
        if (!(cuenta instanceof CuentaAhorros)) {
            JOptionPane.showMessageDialog(this, "Solo las cuentas de ahorros pueden generar intereses.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        CuentaAhorros cuentaAhorros = (CuentaAhorros) cuenta;
        double saldoAnterior = cuentaAhorros.getSaldo();
        cuentaAhorros.aplicarInteres();

        String resultado = String.format(
                "Interés aplicado (1%%)\n\n" +
                        "Cuenta: %s\n" +
                        "Saldo anterior: $%.2f\n" +
                        "Interés ganado: $%.2f\n" +
                        "Nuevo saldo: $%.2f",
                cuentaAhorros.getNumero(), saldoAnterior, cuentaAhorros.getSaldo() - saldoAnterior,
                cuentaAhorros.getSaldo());
        JOptionPane.showMessageDialog(this, resultado, "Interés", JOptionPane.INFORMATION_MESSAGE);

        // ✅ Actualizar labels
        if (seleccion == 0)
            actualizarLabelCuenta1();
        else
            actualizarLabelCuenta2();
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

            Cuenta origen = (seleccion == 0) ? cuenta1 : cuenta2;
            Cuenta destino = (seleccion == 0) ? cuenta2 : cuenta1;
            double saldoOrigenAnterior = origen.getSaldo();

            if (origen instanceof IRetirable) {
                ((IRetirable) origen).retirar(monto);
                destino.depositar(monto);
                procesador.transferir((IRetirable) origen, destino, monto);

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

                    // ✅ Actualizar labels
                    actualizarLabelCuenta1();
                    actualizarLabelCuenta2();
                }
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingresa un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Main
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InterfazGrafica());
    }
}
