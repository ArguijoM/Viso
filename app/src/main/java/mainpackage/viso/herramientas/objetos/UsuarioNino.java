package mainpackage.viso.herramientas.objetos;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class UsuarioNino {
    int idServidor;
    int idLocal;
    int idAdulto;
    String nombre;
    String apellido;
    ArrayList<Actividad> actividades;
    String profile;
    int edad;

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public UsuarioNino(String nombre, String apellido, int edad) {
        this.idServidor = 0;
        this.idLocal = 0;
        this.idAdulto = 0;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.actividades = new ArrayList<Actividad>();
    }
    public UsuarioNino(int id,String nombre, String apellido, int edad, int usuarioAdulto) {
        this.idServidor = id;
        this.idLocal = 0;
        this.idAdulto = usuarioAdulto;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.actividades = new ArrayList<Actividad>();
    }
    public UsuarioNino(int id,String perfil,String nombre, String apellido, int edad, int usuarioAdulto) {
        this.idServidor = id;
        this.profile = perfil;
        this.idLocal = 0;
        this.idAdulto = usuarioAdulto;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.actividades = new ArrayList<Actividad>();
    }

    public int getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(int idLocal) {
        this.idLocal = idLocal;
    }

    public void setActividad(Actividad act){
        this.actividades.add(act);
    }

    public ArrayList<Actividad> getActividades() {
        return actividades;
    }

    public void setActividades(ArrayList<Actividad> actividades) {
        this.actividades = actividades;
    }

    public int getIdServidor() {
        return idServidor;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setIdServidor(int idServidor) {
        this.idServidor = idServidor;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public void setIdAdulto(int idAdulto) {
        this.idAdulto = idAdulto;
    }

    public int getIdAdulto() {
        return idAdulto;
    }

    public void replaceActivityIdServer(int pos,int idServidor){
        this.actividades.get(pos).setIdServidor(idServidor);
    }

}
