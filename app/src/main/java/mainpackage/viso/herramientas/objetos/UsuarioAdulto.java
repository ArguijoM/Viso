package mainpackage.viso.herramientas.objetos;

public class UsuarioAdulto {
    int idServidor;
    String nombre;
    String apellido;
    String email;
    String contrasena;

    public UsuarioAdulto(String nombre, String apellido) {
        this.idServidor=0;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email="";
        this.contrasena="";
    }

    public UsuarioAdulto(int idServidor, String nombre, String apellido, String email, String contrasena) {
        this.idServidor = idServidor;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.contrasena = contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getApellido() {
        return apellido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public int getIdServidor() {
        return idServidor;
    }

    public void setIdServidor(int idServidor) {
        this.idServidor = idServidor;
    }
}
