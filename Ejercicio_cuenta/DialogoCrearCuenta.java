package Ejercicio_cuenta;

import javax.swing.*;
import java.awt.*;
import Ejercicio_cuenta.cuentas.Cuenta;
import Ejercicio_cuenta.cuentas.CuentaAhorros;
import Ejercicio_cuenta.cuentas.CuentaCorriente;

public class DialogoCrearCuenta extends JDialog {
    private Cuenta cuentaCreada = null;
    private JLabel lblNumeroCuenta;
    private JTextField txtSaldo;
    private String tipoCuenta;

    public DialogoCrearCuenta(JFrame parent, String tipoCuenta) {
        super(parent, "Crear Cuenta " + tipoCuenta, true);
        this.tipoCuenta = tipoCuenta;

        inicializarUI();
        setSize(450, 300);
        setLocationRelativeTo(parent);
        setResizable(false);
    }

    private void inicializarUI() {
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelPrincipal.setBackground(new Color(240, 240, 240));

        // Panel de contenido
        JPanel panelContenido = new JPanel(new GridLayout(3, 2, 10, 10));
        panelContenido.setBackground(new Color(240, 240, 240));

        // Título
        JLabel lblTitulo = new JLabel("Crear Cuenta de " + tipoCuenta);
        lblTitulo.setFont(new Font("Verdana", Font.BOLD, 16));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        // Número de Cuenta
        JLabel lblNumero = new JLabel("Número de Cuenta:");
        lblNumero.setFont(new Font("Arial", Font.BOLD, 12));

        lblNumeroCuenta = new JLabel();
        lblNumeroCuenta.setFont(new Font("Courier New", Font.BOLD, 14));
        lblNumeroCuenta.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        lblNumeroCuenta.setHorizontalAlignment(JLabel.CENTER);
        lblNumeroCuenta.setText("Presiona 'Generar'");

        panelContenido.add(lblNumero);
        panelContenido.add(lblNumeroCuenta);

        // Saldo Inicial
        JLabel lblSaldoLabel = new JLabel("Saldo Inicial ($):");
        lblSaldoLabel.setFont(new Font("Arial", Font.BOLD, 12));

        txtSaldo = new JTextField();
        txtSaldo.setFont(new Font("Arial", Font.PLAIN, 12));
        txtSaldo.setText("1000");

        panelContenido.add(lblSaldoLabel);
        panelContenido.add(txtSaldo);

        // Tipo de Cuenta
        JLabel lblTipoLabel = new JLabel("Tipo de Cuenta:");
        lblTipoLabel.setFont(new Font("Arial", Font.BOLD, 12));

        JLabel lblTipoValor = new JLabel(tipoCuenta);
        lblTipoValor.setFont(new Font("Courier New", Font.PLAIN, 12));
        lblTipoValor.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        panelContenido.add(lblTipoLabel);
        panelContenido.add(lblTipoValor);

        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panelBotones.setBackground(new Color(240, 240, 240));

        JButton btnGenerar = new JButton("Generar Número");
        btnGenerar.setFont(new Font("Arial", Font.PLAIN, 12));
        btnGenerar.addActionListener(e -> generarNumero());
        panelBotones.add(btnGenerar);

        JButton btnCrear = new JButton("Crear Cuenta");
        btnCrear.setFont(new Font("Arial", Font.PLAIN, 12));
        btnCrear.addActionListener(e -> crearCuenta());
        panelBotones.add(btnCrear);

        JButton btnCancelar = new JButton("Cancelar");
        btnCancelar.setFont(new Font("Arial", Font.PLAIN, 12));
        btnCancelar.addActionListener(e -> cancelar());
        panelBotones.add(btnCancelar);

        panelPrincipal.add(panelContenido, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);

        add(panelPrincipal);
    }

    // ✅ Generar número de cuenta
    private void generarNumero() {
        String numeroCuenta;
        if (tipoCuenta.equals("Ahorros")) {
            numeroCuenta = CuentaAhorros.generarNumeroCuentaAhorros();
        } else {
            numeroCuenta = CuentaCorriente.generarNumeroCuentaCorriente();
        }
        lblNumeroCuenta.setText(numeroCuenta);
    }

    // ✅ Crear cuenta
    private void crearCuenta() {
        String numero = lblNumeroCuenta.getText();

        if (numero.equals("Presiona 'Generar'")) {
            JOptionPane.showMessageDialog(this, "Debes generar un número de cuenta primero.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String saldoStr = txtSaldo.getText();
        if (saldoStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresa un saldo válido.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            double saldo = Double.parseDouble(saldoStr);
            if (saldo < 0) {
                JOptionPane.showMessageDialog(this, "El saldo no puede ser negativo.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (tipoCuenta.equals("Ahorros")) {
                cuentaCreada = new CuentaAhorros(numero, saldo);
            } else {
                cuentaCreada = new CuentaCorriente(numero, saldo);
            }

            JOptionPane.showMessageDialog(this,
                    "Cuenta creada exitosamente\n\nNúmero: " + numero + "\nSaldo: $" + String.format("%.2f", saldo),
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);

            setVisible(false);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ingresa un saldo numérico válido.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cancelar() {
        cuentaCreada = null;
        setVisible(false);
    }

    public Cuenta getCuentaCreada() {
        return cuentaCreada;
    }
}
