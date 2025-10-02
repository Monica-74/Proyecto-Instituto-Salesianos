package Controlador;


import ModeloBBDD.Alumno;
import ModeloBBDD.DAOInstituto;

import java.util.ArrayList;
import java.util.List;

//Hace de conexión entre la capa de modelo y las clases de vista
//Para ello va a definir métodos que van a ser invocados desde las vistas
//para insertar en la base de datos
//Y también va a definir métodos que van a devolver datos hacia las vistas
//desde las capas inferiores o base de datos

public class ControladorAlumnos {

    private DAOInstituto daoInstituto = new DAOInstituto();
    private ArrayList<Alumno> listaAlumnos; // Cache de alumnos en memoria

    public ControladorAlumnos() {
        listaAlumnos = new ArrayList<>();
        actualizarAlumnos(); // Cargar alumnos al iniciar
    }




    // Método para obtener los alumnos desde la base de datos
    public ArrayList<Alumno> obtenerAlumnos() {
        return listaAlumnos;
    }

    private void actualizarAlumnos() {
        listaAlumnos = new ArrayList<>(daoInstituto.obtenerAlumnos()); // Recargar datos
    }

    public int insertarAlumno(String nombre, String direccion, String estadoMatricula) {
        // Validar que el estado de la matrícula no sea nulo
        if (estadoMatricula == null || estadoMatricula.trim().isEmpty()) {
            estadoMatricula = "Provisional"; // Asignar un valor por defecto
        }

        // Aquí construyo el objeto Alumno que voy a insertar en la base de datos
        Alumno alumno = new Alumno(nombre, direccion, estadoMatricula);

        // Llamar al método del DAO para insertar el alumno
        //int resultado = DAOInstituto.insertarAlumno(alumno);
        int resultado = daoInstituto.insertarAlumno(alumno);
        return resultado;
    }
}