package Vistas;


import Controlador.ControladorAlumnos;
import ModeloBBDD.Alumno;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;


public class TablaAlumnosModel extends AbstractTableModel {

    /* Indices de las columnas. Se refieren como constantes */
    public final static int NOMBRE = 0;
    public final static int DIRECCION = 1;
    public final static int ESTADOMATRICULA = 2;


    private String[] nombreColumnas = {"Nombre del alumno", "Dirección", "Estado de la matrícula"};

    private ArrayList<Alumno> alumnos;
    private ControladorAlumnos controlador = new ControladorAlumnos();

    public TablaAlumnosModel() {
        this.alumnos = new ArrayList<>();
        actualizarDatos();
    }

    // Método para actualizar los datos de la tabla
    public void actualizarDatos() {
        this.alumnos = controlador.obtenerAlumnos();
        fireTableDataChanged();//notifica a la tabla los cambios

    }


    /* Halla el numero de filas de la tabla */
    @Override
    public int getRowCount() {
        return alumnos.size();

        //return alumnos != null ? alumnos.size() : 0;
    }

    /* Halla el numero de columnas de la tabla */
    @Override
    public int getColumnCount() {
        return nombreColumnas.length;
    }

    /*Me devuelve el objeto que se encuentra en la fila y columna especificada*/
    /* En la celda especificada */
    @Override
    public Object getValueAt(int fila, int columna) {
        if (alumnos == null || fila >= alumnos.size()) {
            return null;
        }
        Alumno alumno = alumnos.get(fila);
        if (columna == NOMBRE) {
            return alumno.getNombre();
        } else if (columna == DIRECCION) {
            return alumno.getDireccion();
        } else if (columna == ESTADOMATRICULA) {
            return alumno.getEstadoMatricula();
        }

        return null;

    }


    @Override
    public String getColumnName(int columna) {
        return nombreColumnas[columna];
    }


    public void cargarDatos() {
        List<Alumno> alumnos = controlador.obtenerAlumnos();
        actualizarVistaConAlumnos(alumnos); // Método para actualizar la UI con los datos
    }

    private void actualizarVistaConAlumnos(List<Alumno> nuevosAlumnos) {
        // this.alumnos.clear(); // Limpiar la lista antes de actualizar
        this.alumnos = (ArrayList<Alumno>) nuevosAlumnos; // Actualiza la lista de alumnos
        // this.alumnos.addAll(nuevosAlumnos);
        fireTableDataChanged(); // Notifica a la tabla de los cambios
    }

    public void recargarDatos() {
        actualizarDatos(); // Vuelve a cargar los datos desde la base de datos
        fireTableDataChanged(); // Notifica a la tabla que los datos han cambiado
    }
}




