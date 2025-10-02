package Vistas;

import Controlador.ControladorAlumnos;
import Controlador.ControladorAsignaturas;
import Controlador.ControladorMatriculas;
import ModeloBBDD.Alumno;
import ModeloBBDD.Asignatura;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AgregarMatriculaVentana extends JFrame {
    Container panel;
    GridLayout gLayout;

    JLabel lblId = new JLabel("Id de Matricula");

    JLabel lblNombreAsignatura = new JLabel("Nombre de la Asignatura: ");
    JLabel lblNombreAlumno = new JLabel("Nombre del alumno: ");
    JComboBox cboNombreAsignatura = new JComboBox();
    JComboBox cboNombreAlummno = new JComboBox();

    JLabel lblNota = new JLabel("Nota del alumno: ");
    JTextField txtNota = new JTextField();



    //JTextField txtIdAsignatura = new JTextField();

    JButton cancelarButton = new JButton();
    JButton aceptarButton = new JButton();

    private JPanel centroPanel = new JPanel();
    Font fuenteBotones = new Font("SansSerif", Font.BOLD, 12);

    ControladorMatriculas controladorMatriculas= new ControladorMatriculas();
    ControladorAsignaturas controladoAsignaturas= new ControladorAsignaturas();
    ControladorAlumnos controladorAlumnos= new ControladorAlumnos();
    //private JTable tablaAlumnos;

    private VentanaPrincipal ventanaPrincipal;

    public AgregarMatriculaVentana(VentanaPrincipal ventanaPrincipal) {
        this.ventanaPrincipal = ventanaPrincipal;
        agregarMatricula();
        iniciarEventos();
        initGUI();
        cargarRecursos();
    }


    private void initGUI() {
        //txtNota = new JTextArea();
       // txtNota.setBorder(BorderFactory.createBevelBorder(1));

        JPanel alumnoPanel = new JPanel(new GridBagLayout());

        alumnoPanel.add(lblNombreAlumno, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.VERTICAL, new Insets(12, 12, 0, 0), 0, 0));

        alumnoPanel.add(cboNombreAlummno, new GridBagConstraints(1, 2, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(12, 12, 0, 12), 0, 0));

        alumnoPanel.add(lblNombreAsignatura, new GridBagConstraints(0, 3, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.VERTICAL, new Insets(12, 12, 0, 0), 0, 0));

        alumnoPanel.add(cboNombreAsignatura, new GridBagConstraints(1, 3, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(12, 12, 0, 12), 0, 0));

        alumnoPanel.add(lblNota, new GridBagConstraints(0, 4, 1, 1, 0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.VERTICAL, new Insets(12, 12, 0, 0), 0, 0));

        alumnoPanel.add(txtNota, new GridBagConstraints(1, 4, 1, 1, 1.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL, new Insets(12, 12, 0, 12), 0, 0));



        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(alumnoPanel, BorderLayout.NORTH);
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JPanel botonesPanel = new JPanel();
        botonesPanel.add(aceptarButton);
        botonesPanel.add(cancelarButton);
        getContentPane().add(botonesPanel, BorderLayout.SOUTH);

        this.setSize(500, 550);
        this.setResizable(false);
        cargarDatosEnCombos();
        iniciarEventos();
        cargarRecursos();
        centrarVentana();
    }

    private void cargarDatosEnCombos() {
        //Aquí voy a realizar 2 consutlas para consultar los alumnos y las asignaturas existentes
        ArrayList<Alumno> listaAlumnos = controladorAlumnos.obtenerAlumnos();
        ArrayList<Asignatura> listaAsignaturas = ControladorAsignaturas.obtenerAsignaturas();

        for(Alumno alu : listaAlumnos) {
            cboNombreAlummno.addItem(alu);
        }

        for(Asignatura asig : listaAsignaturas) {
            cboNombreAsignatura.addItem(asig);
        }


        //Cargar el combo de asignaturas de igual forma
    }

    private void centrarVentana() {
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension tamanoPantalla = kit.getScreenSize();
        setLocation(tamanoPantalla.width / 2 - getWidth() / 2, tamanoPantalla.height / 2 - getHeight() / 2);
    }

    public void iniciarEventos() {
        cancelarButton.addActionListener(e -> dispose());
        aceptarButton.addActionListener(e -> {
            agregarMatricula();
        });
    }


    private void agregarMatricula() {
        Alumno alumnoSeleccionado = (Alumno)cboNombreAlummno.getSelectedItem();
        if (alumnoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar un alumno", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int idAlumnoSeleccionado = alumnoSeleccionado.getId();

        //Haría lo mismo con la asignatura
        Asignatura asignaturaSeleccionada = (Asignatura) cboNombreAsignatura.getSelectedItem();
        if (asignaturaSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Debes seleccionar una asignatura", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int idAsignaturaSeleccionada = asignaturaSeleccionada.getId();

        //cuado tengas el id de alumno, id de asignatura y la nota

        double notaIntroducida = Double.parseDouble(txtNota.getText());


        // Insertar el alumno en la base de datos
        int resultado = controladorMatriculas.insertarMatricula(idAlumnoSeleccionado, idAsignaturaSeleccionada, notaIntroducida);
        if (resultado > 0) {
            JOptionPane.showMessageDialog(this, "Matricula agregada exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            TablaMatriculasModel modeloTabla = (TablaMatriculasModel) VentanaPrincipal.tablaMatriculas.getModel();
            modeloTabla.recargarDatos(); // Llamamos al método para refrescar la tabla
            VentanaPrincipal.tablaMatriculas.setModel(new TablaMatriculasModel());
            dispose(); // Cerrar la ventana después de agregar el alumno

        } else {
            JOptionPane.showMessageDialog(this, "Error al agregar matricula");
        }
    }

    private void cargarRecursos() {

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
        cboNombreAlummno.setSize(25, 300);
        this.setTitle("Nueva Matricula");
    }
}


