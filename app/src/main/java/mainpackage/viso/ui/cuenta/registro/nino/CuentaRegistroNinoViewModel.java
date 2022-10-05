package mainpackage.viso.ui.cuenta.registro.nino;

import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import mainpackage.viso.herramientas.Herramientas;
import mainpackage.viso.herramientas.SharedPreferencesHelper;
import mainpackage.viso.herramientas.objetos.UsuarioNino;

public class CuentaRegistroNinoViewModel extends ViewModel {
    private ArrayList<UsuarioNino> usuarios = null;

    public CuentaRegistroNinoViewModel() {
        this.usuarios = SharedPreferencesHelper.getUsuarios(Herramientas.mainActivity);
    }

    public ArrayList<UsuarioNino> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ArrayList<UsuarioNino> usuarios) {
        this.usuarios = usuarios;
    }

    public int getUsuariosSize(){
        if(usuarios==null){
            return 1;
        }
        return  (this.usuarios.size())+1;
    }

    public void setUsuario(UsuarioNino usuario, Activity activity){
        ArrayList<UsuarioNino> usuarios;
        usuarios = SharedPreferencesHelper.getUsuarios(activity);
        if(usuarios==null){
            usuarios = new ArrayList<UsuarioNino>();
            usuario.setIdLocal(1);
            usuarios.add(usuario);
            SharedPreferencesHelper.setUsuarios(activity,usuarios);
            SharedPreferencesHelper.setUsuarioActual(activity,usuario);
        }else{
            usuarios.add(usuario);
            usuario.setIdLocal(usuarios.size());
            SharedPreferencesHelper.setUsuarios(activity,usuarios);
            SharedPreferencesHelper.setUsuarioActual(activity,usuario);
        }
    }
    public int boyCount(){
        int boy=1;
        if(usuarios==null){
            return 1;
        }else {
            for (int i = 0; i < usuarios.size(); i++) {
                String aux = usuarios.get(i).getProfile();
                String[] array = aux.split(" Y ");
                if (array[0].equals("H")) {
                    boy++;
                }
            }
        }
        return boy;
    }
    public int girlCount(){
        int girl=1;
        if(usuarios==null){
            return 1;
        }else {
            for (int i = 0; i < usuarios.size(); i++) {
                String aux = usuarios.get(i).getProfile();
                String[] array = aux.split(" Y ");
                if (array[0].equals("M")) {
                    girl++;
                }
            }
        }
        return girl;
    }
}
