package mainpackage.viso.ui.cuenta;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import mainpackage.viso.herramientas.Herramientas;
import mainpackage.viso.herramientas.SharedPreferencesHelper;
import mainpackage.viso.herramientas.objetos.Actividad;
import mainpackage.viso.herramientas.objetos.UsuarioAdulto;
import mainpackage.viso.herramientas.objetos.UsuarioNino;

public class CuentaViewModel extends ViewModel {
    private UsuarioNino usuarioActual;
    private UsuarioAdulto usuarioAdulto;
    private ArrayList<UsuarioNino> usuarios;
    private ArrayList<Actividad> actividades;
    public CuentaViewModel() {
        this.usuarioActual= SharedPreferencesHelper.getUsuarioActual(Herramientas.mainActivity);
        this.usuarioAdulto = SharedPreferencesHelper.getUsuarioAdulto(Herramientas.mainActivity);
        this.usuarios = SharedPreferencesHelper.getUsuarios(Herramientas.mainActivity);
        this.actividades = SharedPreferencesHelper.getActividades(usuarioActual.getIdLocal());
    }

    public int getUsuariosSize() {
        return usuarios.size();
    }

    public void setUsuarios(ArrayList<UsuarioNino> usuarios) {
        this.usuarios = usuarios;
    }

    public String getUsuarioAdultoNombre() {
        return usuarioAdulto.getNombre();
    }

    public String getUsuarioAdultoApellido() {
        return usuarioAdulto.getApellido();
    }
    public UsuarioNino getUsuarioActual() {
        return usuarioActual;
    }
    public ArrayList<Actividad> getActividades(){
        return actividades;
    }
    public String getActividadName(int id){
        return actividades.get((id-1)).getName();
    }
    public String getActividadPath(int id){
        return actividades.get((id-1)).getPath();
    }
    public String getPerfil(){
        String[] aux = (this.usuarioActual.getProfile()).split(" Y ");
        return aux[0];
    }
    public String getPerfilId(){
        String[] aux = (this.usuarioActual.getProfile()).split(" Y ");
        return aux[1];
    }

    public void setUsuarioActual(UsuarioNino usuarioActual) {
        this.usuarioActual = usuarioActual;
    }


}