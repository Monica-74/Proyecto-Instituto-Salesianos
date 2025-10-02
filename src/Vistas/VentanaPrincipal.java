package Vistas;


import Controlador.ControladorAlumnos;
import ModeloBBDD.Alumno;
import ModeloBBDD.Asignatura;
import ModeloBBDD.DAOInstituto;
import ModeloBBDD.Matricula;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import static ModeloBBDD.DAOInstituto.connect;


public class VentanaPrincipal extends JFrame{

    final static int NUMERO_OPCIONES = 2;
    //Elementos del formulario
    Container panelContenedor;
    JButton btnCentro = new JButton("Boton centro");
    BorderLayout layout = new BorderLayout();
    Font fuenteMenu = new Font("Courier", Font.BOLD, 14);
    JPanel pnEste = new JPanel();

    //
    //Para el norte y el sur
    JPanel pnNorte = new JPanel();
    JButton boton2 = new JButton("Boton 2");
    JButton boton3 = new JButton("Boton 3");
    JTextArea txtSur;

    //El panel de pestannas del centro
    JTabbedPane pnPestannas = new JTabbedPane();
    //Un panel divisor
    JSplitPane split1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    JSplitPane split2 = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
    //Arbol2 arbolContactos = new Arbol2();
    JScrollPane scroll;

    // MENU
    JMenuBar barra = new JMenuBar();
    JMenu menuAlumnos = new JMenu("Alumnos");
    JMenu menuAsignaturas = new JMenu("Asignaturas");
    JMenu menuMatriculas = new JMenu("Matriculas");
    JMenuItem menuAgregarAlumno = new JMenuItem("Agregar Alumno");
    JMenuItem menuAgregarAsignaturas = new JMenuItem("Agregar Asignaturas");
    JMenuItem menuAgregarMatriculas = new JMenuItem("Agregar Matriculas");


    //Barra de herramientas
    JToolBar barraTool = new JToolBar();
    JButton botonAbrir = new JButton();
    //Defino el panel donde va a ir la tabla de alumnos
    JPanel panelAlumnos = new JPanel(new BorderLayout());
    JPanel panelAsignaturas = new JPanel(new BorderLayout());
    JPanel panelMatriculas = new JPanel(new BorderLayout());

    static JTable tablaAlumnos;
    static JTable tablaAsignaturas;
    static JTable tablaMatriculas;
    private Vistas.VentanaPrincipal VentanaPrincipal;

    //private DAOInstituto daoInstituto = new DAOInstituto();//esto es nuevo


    public  void VentanaPrincipal(VentanaPrincipal ventanaPrincipal) {
        this.VentanaPrincipal = ventanaPrincipal;
        initGUI();
        initMenu();
        initEventos();
        initToolBar();
    }
    public VentanaPrincipal() {
        initGUI();
        initMenu();
        initEventos();
        initToolBar();
    }

    public void initGUI() {
        //Hallo el panel contenedor de la ventana
        panelContenedor = this.getContentPane();
        //Le aplico al panel contenedor un Border Layout
        panelContenedor.setLayout(layout);
        btnCentro.setVerticalAlignment(SwingConstants.TOP);
        txtSur = new JTextArea(10, 50);
        setBotonesBar();
        establecerPestanas();

        split2.add(split1);
        split2.add(txtSur);
        split2.setDividerLocation(800);
        split1.setOneTouchExpandable(true);
        //Agrego los componentes
        panelContenedor.add(pnNorte, BorderLayout.NORTH);
        panelContenedor.add(split2, BorderLayout.CENTER);
        //panelContenedor.add(pnOeste, BorderLayout.WEST);
        panelContenedor.add(pnEste, BorderLayout.EAST);  //al este
        JTabbedPane panelPestana = new JTabbedPane();

        int iconWidth = 24, iconHeight = 24;
        ImageIcon iconAlumnos = createResizedIcon("C:\\Users\\Dell\\IdeaProjects\\ProyectoInstitutoSalesiano\\src\\Vistas\\alumno.png", 24, 24);
        ImageIcon iconAsignaturas = createResizedIcon("C:\\Users\\Dell\\IdeaProjects\\ProyectoInstitutoSalesiano\\src\\Vistas\\asignaturas.png", iconWidth, iconHeight);
        ImageIcon iconMatriculas = createResizedIcon("C:\\Users\\Dell\\IdeaProjects\\ProyectoInstitutoSalesiano\\src\\Vistas\\tesis.png", iconWidth, iconHeight);

        panelPestana.addTab("Alumnos", iconAlumnos, panelAlumnos);
        panelPestana.addTab("Asignaturas", iconAsignaturas, panelAsignaturas);
        panelPestana.addTab("Matriculas", iconMatriculas, panelMatriculas);


       // JPanel panelAsignaturas = new JPanel();******
        //panelAsignaturas.add(new JLabel("asignaturas"));*********

        panelContenedor.add(panelPestana, BorderLayout.CENTER);
    }
    private ImageIcon createResizedIcon(String path, int width, int height) {
        ImageIcon originalIcon = new ImageIcon(path);
        Image resizedImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }


    public void initMenu() {
        menuAlumnos.setFont(fuenteMenu);
        barra.add(menuAlumnos);
        menuAsignaturas.setFont(fuenteMenu);
        barra.add(menuAsignaturas);
        menuMatriculas.setFont(fuenteMenu);
        barra.add(menuMatriculas);
        //
        menuAlumnos.add(menuAgregarAlumno);
        menuAgregarAlumno.setFont(fuenteMenu);
        menuAsignaturas.add(menuAgregarAsignaturas);
        menuAsignaturas.setFont(fuenteMenu);
        menuMatriculas.add(menuAgregarMatriculas);
        menuAgregarMatriculas.setFont(fuenteMenu);


        this.setJMenuBar(barra);

    }

    public void initToolBar() {
        // Cargar la imagen original
        ImageIcon iconoOriginal = new ImageIcon("C:\\Users\\Dell\\IdeaProjects\\ProyectoInstitutoSalesiano\\src\\Vistas\\abrir.png");

        // Redimensionar la imagen
        Image imagen = iconoOriginal.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
        ImageIcon iconoEscalado = new ImageIcon(imagen);

        // Asignar el icono escalado al botón
        botonAbrir.setIcon(iconoEscalado);

        // Agregar el botón a la barra de herramientas
        barraTool.add(botonAbrir);
        this.add(barraTool, BorderLayout.NORTH);
    }

    public void initEventos() {
        menuAgregarAlumno.addActionListener(e -> abrirVentanaNuevoAlumno());
        menuAgregarAsignaturas.addActionListener(e -> abrirVentanaNuevaAsignatura());
        menuAgregarMatriculas.addActionListener(e -> abrirVentanaNuevaMatricula());

        // Solo necesitamos agregar el ActionListener una vez para botonAbrir
        botonAbrir.addActionListener(e -> abrirExploradorArchivos());
    }
    public void abrirVentanaNuevoAlumno() {
        AgregarAlumnoVentana agregarAlumnoVentana = new AgregarAlumnoVentana(this);
        agregarAlumnoVentana.setVisible(true);

        agregarAlumnoVentana.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                actualizarTablaAlumnos(); // Actualizar la tabla
            }
        });

    }

    private void abrirVentanaNuevaAsignatura() {
        AgregarAsignaturaVentana agregarAsignaturaVentana = new AgregarAsignaturaVentana(this);
        agregarAsignaturaVentana.setVisible(true);

        agregarAsignaturaVentana.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                actualizarTablaAsignaturas(); // Actualizar la tabla
            }
        });
    }

    private void abrirVentanaNuevaMatricula() {
        AgregarMatriculaVentana agregarMatriculaVentana = new AgregarMatriculaVentana(this);
        agregarMatriculaVentana.setVisible(true);

        agregarMatriculaVentana.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                actualizarTablaMatriculas(); // Actualizar la tabla
            }
        });
    }

    public void abrirExploradorArchivos() {
        JFileChooser chooser = new JFileChooser("C:/Program Files/");
        FileNameExtensionFilter filter =
                new FileNameExtensionFilter("Ejecutables", "exe");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File archivo = chooser.getSelectedFile();
            try {
                Runtime.getRuntime().exec(archivo.getAbsolutePath());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void setBotonesBar(){
        //Defino un layout flow especial
        FlowLayout fl = new FlowLayout(FlowLayout.LEFT, 15, 15);
        //Se lo aplico al panel del norte
        pnNorte.setLayout(fl);
        //Al panel del norte le meto los diferentes botones
        pnNorte.add(boton2);
        pnNorte.add(boton3);

    }
    public void establecerPestanas() {
        iniciarTablaAlumnos();
        iniciarTablaAsignaturas();
        iniciarTablaMatriculas();

        // Redimensionar los iconos antes de asignarlos a las pestañas
        ImageIcon iconoAlumno = createResizedIcon("C:\\Users\\blanco.mamoi\\Downloads\\PS\\ProyectoInstitutoSalesiano\\src\\Vistas\\alumno.png", 24, 24);
        ImageIcon iconoAsignaturas = createResizedIcon("C:\\Users\\blanco.mamoi\\Downloads\\PS\\ProyectoInstitutoSalesiano\\src\\Vistas\\asignaturas.png", 24, 24);
        ImageIcon iconoMatriculas = createResizedIcon("C:\\Users\\blanco.mamoi\\Downloads\\PS\\ProyectoInstitutoSalesiano\\src\\Vistas\\tesis.png", 24, 24);

        // Asignar los iconos redimensionados a las pestañas
        pnPestannas.addTab("Alumnos", iconoAlumno, panelAlumnos);

        JPanel panelAsignaturas = new JPanel();
        panelAsignaturas.add(new JLabel("Aquí irá la tabla de asignaturas"));
        pnPestannas.addTab("Asignaturas", iconoAsignaturas, panelAsignaturas);

        JPanel panelMatriculas = new JPanel();
        panelMatriculas.add(new JLabel("Aquí irá la tabla de matriculas"));
        pnPestannas.addTab("Matriculas", iconoMatriculas, panelMatriculas);


        split1.add(pnPestannas);
    }


    private void iniciarTablaAlumnos() {
        //Se crea una Jtable a partir del modelo definido AbstractTableModel

        //JTable tablaAlumnos = new JTable();
        tablaAlumnos = new JTable(new TablaAlumnosModel());
        JScrollPane pnScroll = new JScrollPane(tablaAlumnos);
        panelAlumnos.add(pnScroll, BorderLayout.CENTER);

        //Le asigna un modo de redimensionamiento de columnas especial a la tabla
        tablaAlumnos.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        //A la tabla le voy a poner color de fuente y fondo diferente al blanco
        //tabla.setForeground(Color.white);
        //tabla.setBackground(Color.blue);

        /** Instalacion de renderers o maquillaje de celdas*/
        //Primero, hallo el modelo de columnas de la tabla
        TableColumnModel tcm = tablaAlumnos.getColumnModel();

        //Selecciones de celdas, filas, y columnas varias
        tablaAlumnos.setRowSelectionAllowed(true);
        tablaAlumnos.setColumnSelectionAllowed(true);
        tablaAlumnos.setCellSelectionEnabled(true);

    }

    private void iniciarTablaAsignaturas() {
        //Se crea una Jtable a partir del modelo definido AbstractTableModel

        tablaAsignaturas = new JTable(new TablaAsignaturasModel());
        JScrollPane pnScroll = new JScrollPane(tablaAsignaturas);
        panelAsignaturas.add(pnScroll, BorderLayout.CENTER);

        //Le asigna un modo de redimensionamiento de columnas especial a la tabla
        tablaAsignaturas.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);



        //Primero, hallo el modelo de columnas de la tabla
        TableColumnModel tcm = tablaAsignaturas.getColumnModel();

        //Selecciones de celdas, filas, y columnas varias
        tablaAsignaturas.setRowSelectionAllowed(true);
        tablaAsignaturas.setColumnSelectionAllowed(true);
        tablaAsignaturas.setCellSelectionEnabled(true);

    }

    private void iniciarTablaMatriculas() {
        //Se crea una Jtable a partir del modelo definido AbstractTableModel

        //JTable tablaAlumnos = new JTable();
        tablaMatriculas= new JTable(new TablaMatriculasModel());
        JScrollPane pnScroll = new JScrollPane(tablaMatriculas);
        panelMatriculas.add(pnScroll, BorderLayout.CENTER);

        //Le asigna un modo de redimensionamiento de columnas especial a la tabla
        tablaAlumnos.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        //A la tabla le voy a poner color de fuente y fondo diferente al blanco
        //tabla.setForeground(Color.white);
        //tabla.setBackground(Color.blue);

        /** Instalacion de renderers o maquillaje de celdas*/
        //Primero, hallo el modelo de columnas de la tabla
        TableColumnModel tcm = tablaAlumnos.getColumnModel();

        //Selecciones de celdas, filas, y columnas varias
        tablaAlumnos.setRowSelectionAllowed(true);
        tablaAlumnos.setColumnSelectionAllowed(true);
        tablaAlumnos.setCellSelectionEnabled(true);

    }

    public static ArrayList<Alumno> obtenerAlumnosDesdeBD() {
        ArrayList<Alumno> alumnos = new ArrayList<>();
        try (Connection conn = connect()) {
            // Consulta SQL para obtener los alumnos
            String query = "SELECT id, nombre, direccion, estado_matricula FROM alumno ORDER BY nombre ASC";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            // Recorrer el resultado y crear objetos Alumno
            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String direccion = rs.getString("direccion");
                String estadoMatricula = rs.getString("estado_matricula");
                Alumno alumno = new Alumno(id, nombre, direccion, estadoMatricula);
                alumnos.add(alumno);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alumnos;
    }
    public static ArrayList<Asignatura> obtenerAsignaturasDesdeBD() {
        ArrayList<Asignatura> asignaturas = new ArrayList<>();
        try (Connection conn = connect()) {
            // Consulta SQL para obtener las asignaturas
            String query = "SELECT nombre, curso FROM asignatura ORDER BY nombre ASC";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            // Recorrer el resultado y crear objetos Asignatura
            while (rs.next()) {
                //int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String curso = rs.getString("curso");
                Asignatura asignatura = new Asignatura(nombre, curso);
                asignaturas.add(asignatura);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return asignaturas;
    }

    public void actualizarTablaAlumnos() {
        ArrayList<Alumno> alumnos = obtenerAlumnosDesdeBD();

        if (tablaAlumnos == null) {
            System.err.println("Error: tablaAlumnos no ha sido inicializada.");
            return;
        }
        tablaAlumnos.validate();
        tablaAlumnos.repaint();
        tablaAlumnos.revalidate();
    }


    public void actualizarTablaAsignaturas() {
        //tablaAsignaturas.validate();
        ArrayList<Asignatura> asignaturas = obtenerAsignaturasDesdeBD();


        if (tablaAsignaturas == null) {
            System.err.println("Error: tablaAsignaturas no ha sido inicializada.");
            return;
        }
        //tablaAsignaturas.setModel(new TablaAsignaturasModel());
        tablaAsignaturas.validate();
        tablaAsignaturas.repaint();
        tablaAsignaturas.revalidate();
    }
    public void actualizarTablaMatriculas() {
        //tablaAsignaturas.validate();
        tablaMatriculas.setModel(new TablaMatriculasModel());


        if (tablaMatriculas == null) {
            System.err.println("Error: tablaMatriculas no ha sido inicializada.");
            return;
        }
        //tablaAsignaturas.setModel(new TablaAsignaturasModel());
        tablaMatriculas.validate();
        tablaMatriculas.repaint();
        tablaMatriculas.revalidate();
    }


    public static void main (String[] args){
        VentanaPrincipal ventana = new VentanaPrincipal();
        ventana.setExtendedState(JFrame.MAXIMIZED_BOTH);
        ventana.setVisible(true);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }



   }