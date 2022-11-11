package mainpackage.viso.herramientas.objetos;

import java.util.ArrayList;

import mainpackage.viso.herramientas.Herramientas;
import mainpackage.viso.herramientas.SQLiteHelper;

public class UsuarioNino {
    int idServidor;
    int idLocal;
    int idAdulto;
    String nombre;
    String apellido;
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
    }
    public UsuarioNino(int id,String nombre, String apellido, int edad, int usuarioAdulto) {
        this.idServidor = id;
        this.idLocal = 0;
        this.idAdulto = usuarioAdulto;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
    }
    public UsuarioNino(int id,String perfil,String nombre, String apellido, int edad, int usuarioAdulto) {
        this.idServidor = id;
        this.profile = perfil;
        this.idLocal = 0;
        this.idAdulto = usuarioAdulto;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
    }
    public UsuarioNino(int id,int idServidor,String nombre, String apellido, int edad, int usuarioAdulto, String perfil) {
        this.idLocal = id;
        this.idServidor = idServidor;
        this.profile = perfil;
        this.idAdulto = usuarioAdulto;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
    }

    public int getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(int idLocal) {
        this.idLocal = idLocal;
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

}
