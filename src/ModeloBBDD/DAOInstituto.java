package ModeloBBDD;
import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOInstituto extends ArrayList<Object> {
    private static final String URL = "jdbc:mysql://localhost:3306/instituto";
    private static final String USER = "root";
    private static final String PASSWORD = "Root1234";

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public List<Alumno> obtenerAlumnos() {
        List<Alumno> alumnos = new ArrayList<>();
        String sql = "SELECT * FROM alumnos";

        try (Connection conn = connect()) {
            String query = "SELECT id, nombre, direccion, estado_matricula FROM alumno ORDER BY nombre ASC";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String direccion= rs.getString("direccion");
                String estado_matricula = rs.getString("estado_matricula");
                Alumno alumno = new Alumno(id, nombre, direccion, estado_matricula);
                alumnos.add(alumno);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener alumnos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

        }
        return alumnos;
    }
    public List<Asignatura> obtenerAsignaturas() {
        List<Asignatura> asignaturas = new ArrayList<>();
        String sql = "SELECT * FROM asignatura"; // Ajusta el nombre de la tabla si es diferente

        try (Connection conn = connect()) {
            String query = "SELECT id, nombre, curso FROM asignatura ORDER BY nombre ASC";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nombre = rs.getString("nombre");
                String curso = rs.getString("curso");

                Asignatura asignatura = new Asignatura(id, nombre, curso);
                asignaturas.add(asignatura);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener asignaturas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

        }

        return asignaturas;
    }
    public static List<Matricula> obtenerMatriculas() {
        List<Matricula> matriculas = new ArrayList<>();
        String query = "SELECT instituto.matricula.id,  instituto.alumno.id as idAlumno, instituto.alumno.nombre as nombreAlumno," +
                " instituto.asignatura.id as idAsignatura, instituto.asignatura.nombre as nombreAsignatura, instituto.asignatura.curso, instituto.matricula.nota " +
                " from instituto.matricula" +
                " inner join instituto.alumno on instituto.matricula.idAlumno = instituto.alumno.id" +
                " inner join instituto.asignatura on instituto.matricula.idAsignatura = instituto.asignatura.id"; // Ajusta el nombre de la tabla si es diferente

        try (Connection conn = connect()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();


            while (rs.next()) {
                int id = rs.getInt("id");
                int idAlumno = rs.getInt("idAlumno");
                int idAsignatura = rs.getInt("idAsignatura");
                String nombreAlumno = rs.getString("nombreAlumno");
                String nombreAsignatura = rs.getString("nombreAsignatura");
                String curso = rs.getString("curso");
                double nota = rs.getDouble("nota");

                Matricula matricula = new Matricula (id, idAlumno, nombreAlumno, idAsignatura, nombreAsignatura, nota);
                matriculas.add(matricula);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener matrículas: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        return matriculas;
    }
    public static int insertarAlumno(Alumno alumno){
        int resultado = 0;
        try (Connection conn = connect()) {
            String query = "INSERT INTO alumno (nombre, direccion, estado_matricula) " +
                    "VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, alumno.getNombre());
            stmt.setString(2, alumno.getDireccion());
            stmt.setString(3, alumno.getEstadoMatricula());
            resultado = stmt.executeUpdate();
            System.out.println("Alumno insertado correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }

    public static int insertarAsignatura(Asignatura asignatura) {
        int resultado = 0;
        try (Connection conn = connect()) {
            String query = "INSERT INTO asignatura (nombre, curso) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
           // stmt.setInt(1, asignatura.getId());
            stmt.setString(1, asignatura.getNombre());
            stmt.setString(2, asignatura.getCurso());
            resultado =stmt.executeUpdate();
            System.out.println("Asignatura insertada correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }


    public static int insertarMatricula(Matricula matricula) {
        int resultado = 0;
        try (Connection conn = connect()) {
            String query = "INSERT INTO matricula (idAlumno,idAsignatura, nota) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, matricula.getIdAlumno());
            stmt.setInt(2, matricula.getIdAsignatura());
           // stmt.setString(3, matricula.getNombreAsignatura());
            stmt.setDouble(3, matricula.getNota());
            resultado = stmt.executeUpdate();
            System.out.println("Matricula insertada correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultado;
    }
}