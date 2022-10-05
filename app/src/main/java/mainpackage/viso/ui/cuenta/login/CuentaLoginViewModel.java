package mainpackage.viso.ui.cuenta.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import mainpackage.viso.herramientas.Herramientas;
import mainpackage.viso.herramientas.SharedPreferencesHelper;
import mainpackage.viso.herramientas.objetos.UsuarioAdulto;

public class CuentaLoginViewModel extends ViewModel {
    private UsuarioAdulto usuarioAdulto=null;

    public CuentaLoginViewModel() {
        this.usuarioAdulto = SharedPreferencesHelper.getUsuarioAdulto(Herramientas.mainActivity);
    }

    public UsuarioAdulto getUsuarioAdulto() {
        return usuarioAdulto;
    }

    public void setUsuarioAdulto(UsuarioAdulto usuarioAdulto) {
        this.usuarioAdulto = usuarioAdulto;
    }
}
