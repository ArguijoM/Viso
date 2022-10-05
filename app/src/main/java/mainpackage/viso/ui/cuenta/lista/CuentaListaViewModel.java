package mainpackage.viso.ui.cuenta.lista;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import mainpackage.viso.herramientas.Herramientas;
import mainpackage.viso.herramientas.SharedPreferencesHelper;
import mainpackage.viso.herramientas.objetos.UsuarioNino;

public class CuentaListaViewModel extends ViewModel {
    private ArrayList<UsuarioNino> usuarios;
    public CuentaListaViewModel() {
        this.usuarios = SharedPreferencesHelper.getUsuarios(Herramientas.mainActivity);
    }

    public ArrayList<UsuarioNino> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(ArrayList<UsuarioNino> usuarios) {
        this.usuarios = usuarios;
    }
}
