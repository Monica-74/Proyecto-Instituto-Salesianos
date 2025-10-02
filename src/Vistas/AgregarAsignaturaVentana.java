package Vistas;

import Controlador.ControladorAsignaturas;

import javax.swing.*;
import java.awt.*;



public class AgregarAsignaturaVentana extends JFrame {
    Container panel;
    GridLayout gLayout;
    private JTextField txtId = new JTextField();
    JLabel lblId = new JLabel("Id");
    private JTextField txtNombre = new JTextField();
    JLabel lblNombre = new JLabel("Nombre de la Asignatura: ");
    private JTextField txtCurso = new JTextField();
    JLabel lblCurso = new JLabel("Nombre del Curso: ");
    JButton cancelarButton = new JButton("Cancelar");

    JButton aceptarButton = new JButton("Aceptar");
    private JPanel centroPanel = new JPanel();

    Font fuenteBotones = new Font("SansSerif", Font.BOLD, 12);
    ControladorAsignaturas controladorAsignaturas = new ControladorAsignaturas();


    private VentanaPrincipal ventanaPrincipal;

    public AgregarAsignaturaVentana(VentanaPrincipal ventanaPrincipal) {
        this.ventanaPrincipal = ventanaPrincipal;
        agregarAsignatura();
        initGUI();
        iniciarEventos();
        cargarRecursos();
    }

    private void initGUI() {

        JPanel asignaturaPanel = new JPanel(new GridBagLayout());
//        asignaturaPanel.add(lblId, new GridBagConstraints(0, 2, 1, 1, 0.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.VERTICAL, new Insets(12, 12, 0, 0), 0, 0));
//        asignaturaPanel.add(txtId, new GridBagConstraints(1, 2, 4, 4, 100.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(12, 12, 0, 12), 0, 0));
        asignaturaPanel.add(lblNombre, new GridBagConstraints(0, 1, 1, 1, 0.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.VERTICAL, new Insets(12, 12, 0, 0), 0, 0));
        asignaturaPanel.add(txtNombre, new GridBagConstraints(1, 1, 3, 1, 100.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(12, 12, 0, 12), 0, 0));
        asignaturaPanel.add(lblCurso, new GridBagConstraints(0, 2, 1, 1, 0.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.VERTICAL, new Insets(12, 12, 0, 0), 0, 0));
        asignaturaPanel.add(txtCurso, new GridBagConstraints(1, 2, 4, 4, 100.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(12, 12, 0, 12), 0, 0));


        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(asignaturaPanel, BorderLayout.NORTH);
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JPanel botonesPanel = new JPanel();
        botonesPanel.add(aceptarButton);
        botonesPanel.add(cancelarButton);
        getContentPane().add(botonesPanel, BorderLayout.SOUTH);

        this.setSize(500, 550);
        this.setResizable(false);
        iniciarEventos();
        cargarRecursos();
        centrarVentana();
        //setTitle("Nueva Asignatura");
    }

    private void centrarVentana() {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension tamanoPantalla = kit.getScreenSize();
        setLocation(tamanoPantalla.width / 2 - getWidth() / 2, tamanoPantalla.height / 2 - getHeight() / 2);
    }

    public void iniciarEventos() {
        cancelarButton.addActionListener(e -> dispose());
        aceptarButton.addActionListener(e -> agregarAsignatura());
    }
    public void agregarAsignatura() {
            String nombre = txtNombre.getText();
            String curso = txtCurso.getText();

            if (nombre.trim().isEmpty() || curso.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Los campos no pueden estar vacíos.", "Error", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            int resultado = ControladorAsignaturas.insertarAsignatura( nombre, curso);

            if (resultado > 0) {
                JOptionPane.showMessageDialog(this, "Asignatura agregada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                TablaAsignaturasModel modeloTabla = (TablaAsignaturasModel) ventanaPrincipal.tablaAsignaturas.getModel();
                modeloTabla.recargarDatos();
                this.ventanaPrincipal.tablaAsignaturas.setModel(new TablaAsignaturasModel());
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Error al agregar asignatura");
            }

    }

        private void cargarRecursos() {
        aceptarButton.setText("Aceptar");
        aceptarButton.setIcon(new ImageIcon("lib/imagenes/stock_apply_24.gif"));
        aceptarButton.setPreferredSize(new Dimension(100, 30)); // Tamaño preferido
        aceptarButton.setMargin(new Insets(5, 10, 5, 10));      // Margen interno
        aceptarButton.setHorizontalTextPosition(SwingConstants.CENTER); // Centrar texto
        aceptarButton.setVerticalTextPosition(SwingConstants.CENTER);
        aceptarButton.setFont(fuenteBotones);

        cancelarButton.setText("Cancelar");
        cancelarButton.setIcon(new ImageIcon("lib/imagenes/stock_cancel_24.gif"));
        cancelarButton.setPreferredSize(new Dimension(100, 30)); // Tamaño preferido
        cancelarButton.setMargin(new Insets(5, 10, 5, 10));      // Margen interno
        cancelarButton.setHorizontalTextPosition(SwingConstants.CENTER); // Centrar texto
        cancelarButton.setVerticalTextPosition(SwingConstants.CENTER);
        cancelarButton.setFont(fuenteBotones);

        // Tamaño del campo de texto
        txtNombre.setSize(25, 300);
        this.setTitle("Nueva Asignatura");
    }
}
