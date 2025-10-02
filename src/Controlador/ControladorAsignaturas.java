package Controlador;

import ModeloBBDD.Asignatura;
import ModeloBBDD.DAOInstituto;
import java.util.ArrayList;

public class ControladorAsignaturas {

    private static DAOInstituto daoInstituto = new DAOInstituto();
    private static ArrayList<Asignatura> listaAsignatura;

    public ControladorAsignaturas() {
        listaAsignatura = new ArrayList<>();
        actualizarAsignaturas();
    }

    // Método para obtener las asignaturas desde la base de datos
    public static ArrayList<Asignatura> obtenerAsignaturas() {
        //return listaAsignaturas;
        return listaAsignatura;
    }

    private static void actualizarAsignaturas() {
        listaAsignatura = new ArrayList<>(daoInstituto.obtenerAsignaturas());
    }

    public static int insertarAsignatura(String nombre, String curso) {
        Asignatura asignatura = new Asignatura(nombre, curso);

        int resultado = DAOInstituto.insertarAsignatura(asignatura);
        actualizarAsignaturas(); // Recargar datos después de la inserción
        return resultado;

        }

    }


