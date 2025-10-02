package Vistas;

import Controlador.ControladorAlumnos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;


public class AgregarAlumnoVentana extends JFrame {
    Container panel;
    GridLayout gLayout;

    JLabel lblNombre = new JLabel("Nombre del alumno: ");
    JLabel lblDireccion = new JLabel("Dirección del alumno: ");
    JLabel lblestadoMatricula = new JLabel("Estado de la matrícula: ");
    JTextField txtNombre = new JTextField();
    JTextArea txtDireccion = new JTextArea();
    JScrollPane direccionScroll = new JScrollPane(txtDireccion);
    JComboBox<String> estadoMatriculaCombo = new JComboBox<>(); // Especificar el tipo de datos del JComboBox

    JButton cancelarButton = new JButton();
    JButton aceptarButton = new JButton();

    private JPanel centroPanel = new JPanel();
    Font fuenteBotones = new Font("SansSerif", Font.BOLD, 12);

    ControladorAlumnos ControladorAlumnos = new ControladorAlumnos();
    //private JTable tablaAlumnos;

    private VentanaPrincipal ventanaPrincipal;

    public AgregarAlumnoVentana(VentanaPrincipal ventanaPrincipal) {
        this.ventanaPrincipal = ventanaPrincipal;
        agregarAlumno();
        iniciarEventos();
        initGUI();
        cargarRecursos();
    }


    private void initGUI() {
        //txtDireccion = new JTextArea();
        txtDireccion.setBorder(BorderFactory.createBevelBorder(1));

        JPanel alumnoPanel = new JPanel(new GridBagLayout());
        alumnoPanel.add(lblNombre, new GridBagConstraints(0, 1, 1, 1, 0.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.VERTICAL, new Insets(12, 12, 0, 0), 0, 0));
        alumnoPanel.add(txtNombre, new GridBagConstraints(1, 1, 3, 1, 100.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(12, 12, 0, 12), 0, 0));
        alumnoPanel.add(lblDireccion, new GridBagConstraints(0, 2, 1, 1, 0.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.VERTICAL, new Insets(12, 12, 0, 0), 0, 0));
        alumnoPanel.add(direccionScroll, new GridBagConstraints(1, 2, 4, 4, 100.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(12, 12, 0, 12), 0, 0));
        alumnoPanel.add(lblestadoMatricula, new GridBagConstraints(0, 6, 1, 1, 0.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.VERTICAL, new Insets(12, 12, 0, 0), 0, 0));
        alumnoPanel.add(estadoMatriculaCombo, new GridBagConstraints(1, 6, 3, 1, 100.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(12, 12, 0, 12), 0, 0));

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(alumnoPanel, BorderLayout.NORTH);
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
    }

    private void centrarVentana() {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension tamanoPantalla = kit.getScreenSize();
        setLocation(tamanoPantalla.width / 2 - getWidth() / 2, tamanoPantalla.height / 2 - getHeight() / 2);
    }

    public void iniciarEventos() {
        cancelarButton.addActionListener(e -> dispose());
        aceptarButton.addActionListener(e -> agregarAlumno());
    }


    private void agregarAlumno() {
        String nombre = txtNombre.getText();
        String direccion = txtDireccion.getText();
        String estadoMatricula = (String) estadoMatriculaCombo.getSelectedItem(); // Cast a String


        // Validar que el estado de la matrícula no sea nulo
        if (estadoMatricula == null || estadoMatricula.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un estado de matrícula.");
            return; // Salir del método si no hay un estado de matrícula seleccionado
        }


        //Validar que el nombre y la dirección no estén vacíos
        if (nombre.trim().isEmpty() || direccion.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre y la dirección no pueden estar vacíos.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Salir del método si el nombre o la dirección están vacíos
        }

        // Insertar el alumno en la base de datos
        int resultado = ControladorAlumnos.insertarAlumno(nombre, direccion, estadoMatricula);
        if (resultado > 0) {
            JOptionPane.showMessageDialog(this, "Alumno agregado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            //recargar datos sin duplicar
            TablaAlumnosModel modeloTabla = (TablaAlumnosModel) ventanaPrincipal.tablaAlumnos.getModel();
            modeloTabla.recargarDatos(); // Llamamos al método para refrescar la tabla

            this.ventanaPrincipal.tablaAlumnos.setModel(new TablaAlumnosModel());
            dispose(); // Cerrar la ventana después de agregar el alumno

        } else {
            JOptionPane.showMessageDialog(this, "Error al agregar alumno");
        }
    }


    private void cargarRecursos() {
        estadoMatriculaCombo.removeAllItems();//Evita duplicados: limpia el JComboBox antes de agregar los valores
        // ComboEstado
        estadoMatriculaCombo.addItem("Provisional");
        estadoMatriculaCombo.addItem("Confirmada");
        estadoMatriculaCombo.addItem("Cancelada");

        // Ajustar botones
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
        this.setTitle("Nuevo Alumno");
    }
}
