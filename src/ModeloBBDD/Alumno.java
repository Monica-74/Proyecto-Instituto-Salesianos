package ModeloBBDD;

public class Alumno{
    private int id;
    private String nombre;
    private String direccion;
    private String estadoMatricula;

    public Alumno(int id, String nombre, String direccion, String estadoMatricula  ) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.estadoMatricula = estadoMatricula;
    }
    // Constructor sin id (para cuando se inserta un nuevo alumno)
    public Alumno(String nombre, String direccion, String estadoMatricula) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.estadoMatricula = estadoMatricula;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setEstadoMatricula(String estadoMatricula) {
        this.estadoMatricula = estadoMatricula;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDireccion() {
        return direccion;
    }
    public String getEstadoMatricula() {
        return estadoMatricula;
    }

    @Override
    public String toString() {
        return  id + " - " + nombre;
    }

}