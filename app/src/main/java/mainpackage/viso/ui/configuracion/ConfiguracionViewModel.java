package mainpackage.viso.ui.configuracion;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import mainpackage.viso.herramientas.Herramientas;
import mainpackage.viso.herramientas.SQLiteHelper;
import mainpackage.viso.herramientas.SharedPreferencesHelper;
import mainpackage.viso.herramientas.objetos.UsuarioAdulto;
import mainpackage.viso.herramientas.objetos.UsuarioNino;

public class ConfiguracionViewModel extends ViewModel {
    private UsuarioAdulto usuarioAdulto;
    private ArrayList<UsuarioNino> usuarios = new ArrayList<UsuarioNino>();
    private UsuarioNino usuarioActual;

    public ConfiguracionViewModel() {
        this.usuarioAdulto = SharedPreferencesHelper.getUsuarioAdulto(Herramientas.mainActivity);
        this.usuarios = SharedPreferencesHelper.getUsuarios(Herramientas.mainActivity);
        this.usuarioActual = SharedPreferencesHelper.getUsuarioActual(Herramientas.mainActivity);

    }

    public UsuarioAdulto getUsuarioAdulto() {
        return usuarioAdulto;
    }

    public void setUsuarioAdulto(UsuarioAdulto usuarioAdulto) {
        this.usuarioAdulto = usuarioAdulto;
    }

    public ArrayList<UsuarioNino> getUsuarios() {
        return usuarios;
    }

    public UsuarioNino getUsuarioActual() {
        return usuarioActual;
    }

}
