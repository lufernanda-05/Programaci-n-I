package duosecreto.ventana;

import duosecreto.dto.Dificultad;
import duosecreto.dto.Resultado;
import duosecreto.util.Estilos;
import duosecreto.util.GestorDatos;
import duosecreto.util.UtilidadArchivos;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

public class VentanaAdministracion extends JFrame {

    private List<Dificultad> dificultades;

    public VentanaAdministracion() {
        setTitle("Administración — Duo Secreto");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(820, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        dificultades = GestorDatos.cargarDificultades();

        JPanel raiz = new JPanel(new BorderLayout());
        raiz.setBackground(Estilos.FONDO_PRINCIPAL);
        setContentPane(raiz);

        // ── Encabezado ─────────────────────────────────────────────────────
        JLabel titulo = new JLabel("  ⚙  Administración", SwingConstants.LEFT);
        titulo.setFont(Estilos.FUENTE_TITULO);
        titulo.setForeground(Estilos.ACENTO_HOVER);
        titulo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Estilos.BORDE),
                new EmptyBorder(14, 20, 14, 20)));
        titulo.setOpaque(true);
        titulo.setBackground(Estilos.FONDO_PANEL);
        raiz.add(titulo, BorderLayout.NORTH);

        // ── Tabs ───────────────────────────────────────────────────────────
        JTabbedPane tabs = new JTabbedPane();
        tabs.setBackground(Estilos.FONDO_PRINCIPAL);
        tabs.setForeground(Estilos.TEXTO_PRIMARIO);
        tabs.setFont(Estilos.FUENTE_NORMAL);
        tabs.addTab("🎯 Dificultades", construirTabDificultades());
        tabs.addTab("🖼 Imágenes",     construirTabImagenes());
        tabs.addTab("🏆 Top 10",        construirTabTop10());
        raiz.add(tabs, BorderLayout.CENTER);
    }

    // ══════════════════════════════════════════════════════════════════════
    //  TAB 1 — Dificultades
    // ══════════════════════════════════════════════════════════════════════

    private JPanel construirTabDificultades() {
        JPanel p = panel();

        // ── Tabla de dificultades existentes ───────────────────────────────
        String[] cols = {"Nombre", "Parejas", "Tiempo (s)", "Puntos/pareja"};
        DefaultTableModel modelo = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        for (Dificultad d : dificultades) {
            modelo.addRow(new Object[]{d.getNombre(), d.getNumeroParejas(),
                    d.getTiempoMaximo(), d.getPuntosPorPareja()});
        }
        JTable tabla = tablaOscura(modelo);
        JScrollPane scroll = scrollOscuro(tabla);

        // ── Formulario nueva / editar ──────────────────────────────────────
        JPanel form = new JPanel(new GridLayout(4, 2, 8, 8));
        form.setOpaque(false);
        form.setBorder(new EmptyBorder(12, 0, 0, 0));

        JTextField tfNombre = campo();
        JSpinner   spParejas = spinner(2, 2, 20, 1);
        JSpinner   spTiempo  = spinner(60, 20, 300, 10);
        JSpinner   spPuntos  = spinner(10, 5, 100, 5);

        form.add(etiqueta("Nombre:"));     form.add(tfNombre);
        form.add(etiqueta("Parejas:"));    form.add(spParejas);
        form.add(etiqueta("Tiempo (s):")); form.add(spTiempo);
        form.add(etiqueta("Puntos/pareja:")); form.add(spPuntos);

        // Pre-rellenar al seleccionar fila
        tabla.getSelectionModel().addListSelectionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila >= 0 && fila < dificultades.size()) {
                Dificultad d = dificultades.get(fila);
                tfNombre.setText(d.getNombre());
                spParejas.setValue(d.getNumeroParejas());
                spTiempo .setValue(d.getTiempoMaximo());
                spPuntos .setValue(d.getPuntosPorPareja());
            }
        });

        JButton btnAgregar  = Estilos.botonPrimario("+ Agregar");
        JButton btnActualizar = Estilos.botonSecundario("✏ Actualizar");
        JButton btnEliminar = Estilos.botonSecundario("✕ Eliminar");

        btnAgregar.addActionListener(e -> {
            String nom = tfNombre.getText().trim();
            if (nom.isEmpty()) { error("El nombre no puede estar vacío."); return; }
            if (dificultades.size() >= 10) { error("Máximo 10 dificultades."); return; }
            if (dificultades.stream().anyMatch(d -> d.getNombre().equalsIgnoreCase(nom))) {
                error("Ya existe una dificultad con ese nombre."); return;
            }
            Dificultad nueva = new Dificultad(nom,
                    (int) spParejas.getValue(),
                    (int) spTiempo .getValue(),
                    (int) spPuntos .getValue());
            dificultades.add(nueva);
            GestorDatos.guardarDificultades(dificultades);
            modelo.addRow(new Object[]{nueva.getNombre(), nueva.getNumeroParejas(),
                    nueva.getTiempoMaximo(), nueva.getPuntosPorPareja()});
            tfNombre.setText("");
            ok("Dificultad \"" + nom + "\" agregada.");
        });

        btnActualizar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila < 0) { error("Selecciona una dificultad."); return; }
            Dificultad d = dificultades.get(fila);
            d.setNombre(tfNombre.getText().trim());
            d.setNumeroParejas((int) spParejas.getValue());
            d.setTiempoMaximo((int) spTiempo .getValue());
            d.setPuntosPorPareja((int) spPuntos.getValue());
            GestorDatos.guardarDificultades(dificultades);
            modelo.setValueAt(d.getNombre(),          fila, 0);
            modelo.setValueAt(d.getNumeroParejas(),   fila, 1);
            modelo.setValueAt(d.getTiempoMaximo(),    fila, 2);
            modelo.setValueAt(d.getPuntosPorPareja(), fila, 3);
            ok("Dificultad actualizada.");
        });

        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila < 0) { error("Selecciona una dificultad."); return; }
            if (dificultades.size() <= 1) { error("Debe existir al menos 1 dificultad."); return; }
            dificultades.remove(fila);
            GestorDatos.guardarDificultades(dificultades);
            modelo.removeRow(fila);
        });

        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        botones.setOpaque(false);
        botones.add(btnAgregar);
        botones.add(btnActualizar);
        botones.add(btnEliminar);

        p.add(scroll,   BorderLayout.CENTER);
        p.add(form,     BorderLayout.EAST);
        p.add(botones,  BorderLayout.SOUTH);
        return p;
    }

    // ══════════════════════════════════════════════════════════════════════
    //  TAB 2 — Imágenes
    // ══════════════════════════════════════════════════════════════════════

    private JPanel construirTabImagenes() {
        JPanel p = panel();
        p.setLayout(new BorderLayout(10, 10));

        // Lista de imágenes cargadas
        DefaultListModel<String> listModel = new DefaultListModel<>();
        GestorDatos.cargarRutasImagenes().forEach(listModel::addElement);

        JList<String> lista = new JList<>(listModel);
        lista.setBackground(Estilos.FONDO_CARTA);
        lista.setForeground(Estilos.TEXTO_PRIMARIO);
        lista.setFont(Estilos.FUENTE_NORMAL);
        lista.setCellRenderer(new ImagenCellRenderer());
        lista.setSelectionBackground(Estilos.ACENTO);
        JScrollPane scroll = scrollOscuro(lista);

        // Preview
        JLabel lblPreview = new JLabel("Vista previa", SwingConstants.CENTER);
        lblPreview.setForeground(Estilos.TEXTO_SECUNDARIO);
        lblPreview.setFont(Estilos.FUENTE_NORMAL);
        lblPreview.setPreferredSize(new Dimension(200, 200));
        lblPreview.setOpaque(true);
        lblPreview.setBackground(Estilos.FONDO_CARTA);
        lblPreview.setBorder(BorderFactory.createLineBorder(Estilos.BORDE, 1, true));

        lista.addListSelectionListener(e -> {
            String ruta = lista.getSelectedValue();
            if (ruta != null) {
                try {
                    BufferedImage img = ImageIO.read(new File(ruta));
                    if (img != null) {
                        ImageIcon ic = new ImageIcon(img.getScaledInstance(190, 190, Image.SCALE_SMOOTH));
                        lblPreview.setIcon(ic);
                        lblPreview.setText("");
                        return;
                    }
                } catch (Exception ex) { /* fall-through */ }
            }
            lblPreview.setIcon(null);
            lblPreview.setText("Vista previa");
        });

        // Panel derecho
        JPanel der = new JPanel(new BorderLayout(0, 8));
        der.setOpaque(false);
        der.setPreferredSize(new Dimension(220, 0));
        der.add(lblPreview, BorderLayout.NORTH);

        JLabel lblCuenta = new JLabel("Total: " + listModel.size() + " imagen(es)");
        lblCuenta.setForeground(Estilos.TEXTO_SECUNDARIO);
        lblCuenta.setFont(Estilos.FUENTE_PEQUEÑA);
        der.add(lblCuenta, BorderLayout.CENTER);

        // Botones
        JButton btnCargar   = Estilos.botonPrimario("+ Cargar imagen");
        JButton btnEliminar = Estilos.botonSecundario("✕ Eliminar");
        btnCargar.setPreferredSize(new Dimension(190, 38));
        btnEliminar.setPreferredSize(new Dimension(190, 38));

        btnCargar.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            fc.setMultiSelectionEnabled(true);
            fc.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                    "Imágenes (PNG, JPG, GIF)", "png", "jpg", "jpeg", "gif"));
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                int agregadas = 0;
                for (File f : fc.getSelectedFiles()) {
                    try {
                        String destino = UtilidadArchivos.copiarImagen(f);
                        GestorDatos.agregarImagen(destino);
                        listModel.addElement(destino);
                        agregadas++;
                    } catch (Exception ex) {
                        error("No se pudo cargar: " + f.getName());
                    }
                }
                lblCuenta.setText("Total: " + listModel.size() + " imagen(es)");
                if (agregadas > 0) ok(agregadas + " imagen(es) cargada(s).");
            }
        });

        btnEliminar.addActionListener(e -> {
            String sel = lista.getSelectedValue();
            if (sel == null) { error("Selecciona una imagen."); return; }
            GestorDatos.eliminarImagen(sel);
            listModel.removeElement(sel);
            lblPreview.setIcon(null);
            lblPreview.setText("Vista previa");
            lblCuenta.setText("Total: " + listModel.size() + " imagen(es)");
        });

        JPanel botones = new JPanel(new GridLayout(2, 1, 0, 8));
        botones.setOpaque(false);
        botones.setBorder(new EmptyBorder(8, 0, 0, 0));
        botones.add(btnCargar);
        botones.add(btnEliminar);
        der.add(botones, BorderLayout.SOUTH);

        p.add(scroll, BorderLayout.CENTER);
        p.add(der,    BorderLayout.EAST);
        return p;
    }

    // ══════════════════════════════════════════════════════════════════════
    //  TAB 3 — Top 10
    // ══════════════════════════════════════════════════════════════════════

    private JPanel construirTabTop10() {
        JPanel p = panel();
        p.setLayout(new BorderLayout(0, 10));

        JComboBox<String> cbDif = new JComboBox<>();
        dificultades.forEach(d -> cbDif.addItem(d.getNombre()));
        cbDif.setBackground(Estilos.FONDO_CARTA);
        cbDif.setForeground(Estilos.TEXTO_PRIMARIO);
        cbDif.setFont(Estilos.FUENTE_NORMAL);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        top.setOpaque(false);
        top.add(etiqueta("Dificultad:  "));
        top.add(cbDif);
        p.add(top, BorderLayout.NORTH);

        String[] cols = {"#", "Nickname", "Tiempo", "Puntos", "Partidas"};
        DefaultTableModel modelo = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable tabla = tablaOscura(modelo);
        tabla.getColumnModel().getColumn(0).setMaxWidth(32);

        // Medalla colores para top 3
        tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            final Color[] medallas = {new Color(255, 215, 0), new Color(192, 192, 192),
                    new Color(205, 127, 50)};
            @Override public Component getTableCellRendererComponent(JTable t, Object v,
                    boolean sel, boolean foc, int row, int col) {
                super.getTableCellRendererComponent(t, v, sel, foc, row, col);
                setBackground(sel ? Estilos.ACENTO :
                        (row < 3 ? medallas[row].darker().darker() : Estilos.FONDO_CARTA));
                setForeground(row < 3 ? medallas[row] : Estilos.TEXTO_PRIMARIO);
                setHorizontalAlignment(col == 0 ? CENTER : LEFT);
                setBorder(new EmptyBorder(4, 8, 4, 8));
                return this;
            }
        });

        Runnable cargar = () -> {
            modelo.setRowCount(0);
            String selDif = (String) cbDif.getSelectedItem();
            if (selDif == null) return;
            List<Resultado> top10 = GestorDatos.top10(selDif);
            int pos = 1;
            for (Resultado r : top10) {
                modelo.addRow(new Object[]{pos++, r.getNickname(),
                        r.getTiempoFormateado(), r.getPuntaje(),
                        r.getPartidasConsecutivas()});
            }
            if (top10.isEmpty()) {
                modelo.addRow(new Object[]{"—", "Sin registros", "—", "—", "—"});
            }
        };

        cbDif.addActionListener(e -> cargar.run());
        cargar.run();

        p.add(scrollOscuro(tabla), BorderLayout.CENTER);

        JButton btnRefrescar = Estilos.botonSecundario("↺ Refrescar");
        btnRefrescar.addActionListener(e -> cargar.run());
        JPanel sur = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        sur.setOpaque(false);
        sur.add(btnRefrescar);
        p.add(sur, BorderLayout.SOUTH);
        return p;
    }

    // ══════════════════════════════════════════════════════════════════════
    //  Helpers
    // ══════════════════════════════════════════════════════════════════════

    private JPanel panel() {
        JPanel p = new JPanel(new BorderLayout(8, 8));
        p.setBackground(Estilos.FONDO_PRINCIPAL);
        p.setBorder(new EmptyBorder(16, 20, 16, 20));
        return p;
    }

    private JLabel etiqueta(String t) {
        JLabel l = new JLabel(t);
        l.setFont(Estilos.FUENTE_PEQUEÑA);
        l.setForeground(Estilos.TEXTO_SECUNDARIO);
        return l;
    }

    private JTextField campo() {
        JTextField tf = new JTextField();
        tf.setBackground(Estilos.FONDO_CARTA);
        tf.setForeground(Estilos.TEXTO_PRIMARIO);
        tf.setCaretColor(Estilos.TEXTO_PRIMARIO);
        tf.setFont(Estilos.FUENTE_NORMAL);
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Estilos.BORDE, 1, true),
                new EmptyBorder(4, 8, 4, 8)));
        return tf;
    }

    private JSpinner spinner(int val, int min, int max, int step) {
        JSpinner sp = new JSpinner(new SpinnerNumberModel(val, min, max, step));
        sp.setBackground(Estilos.FONDO_CARTA);
        sp.setForeground(Estilos.TEXTO_PRIMARIO);
        sp.setFont(Estilos.FUENTE_NORMAL);
        ((JSpinner.DefaultEditor) sp.getEditor()).getTextField()
                .setBackground(Estilos.FONDO_CARTA);
        ((JSpinner.DefaultEditor) sp.getEditor()).getTextField()
                .setForeground(Estilos.TEXTO_PRIMARIO);
        return sp;
    }

    private JTable tablaOscura(DefaultTableModel modelo) {
        JTable t = new JTable(modelo);
        t.setBackground(Estilos.FONDO_CARTA);
        t.setForeground(Estilos.TEXTO_PRIMARIO);
        t.setFont(Estilos.FUENTE_NORMAL);
        t.setRowHeight(30);
        t.setShowGrid(false);
        t.setIntercellSpacing(new Dimension(0, 1));
        t.getTableHeader().setBackground(Estilos.FONDO_PANEL);
        t.getTableHeader().setForeground(Estilos.ACENTO_HOVER);
        t.getTableHeader().setFont(Estilos.FUENTE_SUBTITULO);
        t.setSelectionBackground(Estilos.ACENTO);
        t.setSelectionForeground(Color.WHITE);
        return t;
    }

    private JScrollPane scrollOscuro(Component c) {
        JScrollPane sp = new JScrollPane(c);
        sp.getViewport().setBackground(Estilos.FONDO_CARTA);
        sp.setBorder(BorderFactory.createLineBorder(Estilos.BORDE, 1, true));
        return sp;
    }

    private void error(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void ok(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Listo", JOptionPane.INFORMATION_MESSAGE);
    }

    // ── Renderer para lista de imágenes ───────────────────────────────────
    private static class ImagenCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value,
                int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            String ruta = value.toString();
            setText((index + 1) + ". " + new File(ruta).getName());
            setBackground(isSelected ? Estilos.ACENTO : Estilos.FONDO_CARTA);
            setForeground(isSelected ? Color.WHITE : Estilos.TEXTO_PRIMARIO);
            setBorder(new EmptyBorder(6, 10, 6, 10));
            return this;
        }
    }
}
