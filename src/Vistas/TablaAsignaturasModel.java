package Vistas;

import Controlador.ControladorAsignaturas;
import ModeloBBDD.Asignatura;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class TablaAsignaturasModel extends AbstractTableModel {

    /* Índices de las columnas. Se refieren como constantes */
    public final static int NOMBRE = 0;
    public final static int CURSO = 1;

    private String[] nombreColumnas = {"Nombre de la asignatura", "Curso"};
    private ArrayList<Asignatura> asignaturas;
   private ControladorAsignaturas controladorAsignaturas = new ControladorAsignaturas();

    public TablaAsignaturasModel() {
        this.asignaturas = new ArrayList<>();
        actualizarDatos();
    }

    // Método para actualizar los datos de la tabla
    public void actualizarDatos() {
        this.asignaturas = controladorAsignaturas.obtenerAsignaturas();           //obtenerAsignaturas();
        fireTableDataChanged(); // Notifica a la tabla los cambios
    }

    /* Obtiene el número de filas de la tabla */
    @Override
    public int getRowCount() {
        return asignaturas.size();
    }

    /* Obtiene el número de columnas de la tabla */
    @Override
    public int getColumnCount() {
        return nombreColumnas.length;
    }

    /* Devuelve el objeto que se encuentra en la fila y columna especificada */
    @Override
    public Object getValueAt(int fila, int columna) {
        if (asignaturas == null || fila >= asignaturas.size()) {
            return null;
        }
        Asignatura asignatura = asignaturas.get(fila);
        if (columna == NOMBRE) {
            return asignatura.getNombre();
        } else if (columna == CURSO) {
            return asignatura.getCurso();
        }
        return null;
    }

    @Override
    public String getColumnName(int columna) {
        return nombreColumnas[columna];
    }

    public void cargarDatos() {
        List<Asignatura> asignaturas = ControladorAsignaturas.obtenerAsignaturas();
        actualizarVistaConAsignaturas(asignaturas); // Método para actualizar la UI con los datos
    }

    private void actualizarVistaConAsignaturas(List<Asignatura> nuevasAsignaturas) {
        this.asignaturas = new ArrayList<>(nuevasAsignaturas); // Actualiza la lista de asignaturas
        fireTableDataChanged(); // Notifica a la tabla de los cambios
    }

    public void recargarDatos() {
        actualizarDatos(); // Vuelve a cargar los datos desde la base de datos
        fireTableDataChanged(); // Notifica a la tabla que los datos han cambiado
    }
}