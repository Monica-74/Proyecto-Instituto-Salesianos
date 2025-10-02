package Controlador;

import ModeloBBDD.DAOInstituto;
import ModeloBBDD.Matricula;

import java.util.ArrayList;

public class ControladorMatriculas {

        private static DAOInstituto daoInstituto = new DAOInstituto();
        private  static ArrayList<Matricula> listaMatricula; // Cache de alumnos en memoria

        public ControladorMatriculas() {
            listaMatricula = new ArrayList<>();
            actualizarMatriculas(); // Cargar alumnos al iniciar
        }




        // Método para obtener las matriculas desde la base de datos
        public  static ArrayList<Matricula> obtenerMatriculas() {
            return listaMatricula;
        }

        private static void actualizarMatriculas() {
            listaMatricula = new ArrayList<>(DAOInstituto.obtenerMatriculas()); // Recargar datos
        }

        public static int insertarMatricula(int idAlumno, int idAsignatura, double nota) {
            // Validar que el estado de la matrícula no sea nulo


            // Aquí construyo el objeto Alumno que voy a insertar en la base de datos
            Matricula matricula = new Matricula(idAlumno, idAsignatura, nota);
            int resultado = DAOInstituto.insertarMatricula(matricula);
            actualizarMatriculas(); // Recargar datos después de la inserción
            return resultado;
        }
    }
