package Vistas;


import Controlador.ControladorAlumnos;
import Controlador.ControladorMatriculas;
import ModeloBBDD.Alumno;
import ModeloBBDD.Matricula;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class TablaMatriculasModel extends AbstractTableModel{

    /* Indices de las columnas. Se refieren como constantes */
    public final static int NOMBRE_ALUMNO = 0;
    public final static int NOMBRE_ASIGNATURA = 1;
    public final static int NOTA = 2;


    private String[] nombreColumnas = {"Nombre del Alumno","Nombre de la Asignatura", "Nota"};

    private ArrayList<Matricula> matriculas;
    private ControladorMatriculas controladorMatriculas;

    public TablaMatriculasModel() {
        this.controladorMatriculas = new ControladorMatriculas();
        this.matriculas = new ArrayList<>();
        actualizarDatos();
    }

    // Método para actualizar los datos de la tabla
    public void actualizarDatos() {
        this.matriculas = new ArrayList<>(controladorMatriculas.obtenerMatriculas());
        fireTableDataChanged();//notifica a la tabla los cambios
    }


    /* Halla el numero de filas de la tabla */
    @Override
    public int getRowCount() {
        return matriculas.size();
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
        if (fila >= matriculas.size()) {
            return null;
        }
        Matricula matricula = matriculas.get(fila);

        if (columna == NOMBRE_ALUMNO){
            return matricula.getNombreAlumno();
        }else if (columna == NOMBRE_ASIGNATURA) {
            return matricula.getNombreAsignatura();
        }
        else if (columna == NOTA) {
            return matricula.getNota();
        }
        return null;
    }


    @Override
    public String getColumnName (int columna){
        return nombreColumnas[columna];
    }


    public void cargarDatos() {
        List<Matricula> matriculas = controladorMatriculas.obtenerMatriculas();
        actualizarVistaConMatriculas(matriculas); // Método para actualizar la UI con los datos
    }

    private void actualizarVistaConMatriculas(List<Matricula> nuevasMatriculas) {
        this.matriculas = (ArrayList<Matricula>) nuevasMatriculas; // Actualiza la lista de alumnos
        fireTableDataChanged(); // Notifica a la tabla de los cambios
    }

    public void recargarDatos() {
        actualizarDatos(); // Vuelve a cargar los datos desde la base de datos
        fireTableDataChanged(); // Notifica a la tabla que los datos han cambiado
    }

}

