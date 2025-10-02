package ModeloBBDD;

public class Asignatura {

    private int id;
    private String nombre;
    private String curso;



    public Asignatura(int id, String nombre, String curso) {
        this.id = id;
        this.nombre = nombre;
        this.curso = curso;
    }

    public Asignatura(String nombre, String curso) {
        this.nombre = nombre;
        this.curso = curso;
    }

    public int getId() {
        return id;
    }
    public String getNombre() {
        return nombre;
    }
    public String getCurso() {
        return curso;
    }
    @Override
    public String toString() {
        return "Asignatura: " + id + ", Nombre: " + nombre + ", Curso: " + curso;
    }
}