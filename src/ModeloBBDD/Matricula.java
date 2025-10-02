package ModeloBBDD;

public class Matricula {
    private int id;
    private int idAlumno;
    private int idAsignatura;
    private String nombreAlumno;

    private String nombreAsignatura;
    private double nota;

    //Constructor utilizado para mostrar en la tabla de la vista
    public Matricula(int id, int idAlumno, int idAsignatura, String nombreAsignatura, double nota) {
        this.id = id;
        this.idAlumno = idAlumno;
        this.idAsignatura = idAsignatura;
        this.nombreAsignatura = nombreAsignatura;
        this.nota = nota;
    }

    public String getNombreAlumno() {
        return nombreAlumno;
    }

    public Matricula(int id, int idAlumno, String nombreAlumno, int idAsignatura, String nombreAsignatura, double nota) {
        this.id = id;
        this.idAlumno = idAlumno;
        this.idAsignatura = idAsignatura;
        this.nombreAsignatura = nombreAsignatura;
        this.nombreAlumno = nombreAlumno;
        this.nota = nota;
    }

    //Constructor utilizado sobre todo para insertar
    public Matricula(int idAlumno, int idAsignatura, double nota) {
        this.idAlumno = idAlumno;
        this.idAsignatura = idAsignatura;
        this.nota = nota;
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setIdAlumno(int idAlumno) {
        this.idAlumno = idAlumno;
    }

    public void setIdAsignatura(int idAsignatura) {
        this.idAsignatura = idAsignatura;
    }

    public void setNombreAsignatura(String nombreAsignatura) {
        this.nombreAsignatura = nombreAsignatura;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    /*public Matricula(String nombreAsignatura, double nota) {
        this.nombreAsignatura = nombreAsignatura;
        this.nota = nota;
    }*/

    public int getId() {
        return id;
    }

    public int getIdAlumno() {
        return idAlumno;
    }

    public int getIdAsignatura() {
        return idAsignatura;
    }

    public double getNota() {
        return nota;
    }

    public String getNombreAsignatura() {
        return nombreAsignatura;
    }

    @Override
    public String toString() {
        return "Matricula{ " + id + ", ID Alumno: " + idAlumno + ", ID Asignatura: " + idAsignatura + ", nombreAsignatura" + nombreAsignatura + ", Nota: " + nota + '\'' +
                '}';
    }
}