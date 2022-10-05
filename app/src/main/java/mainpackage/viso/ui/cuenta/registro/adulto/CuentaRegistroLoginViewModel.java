package mainpackage.viso.ui.cuenta.registro.adulto;

import androidx.lifecycle.ViewModel;

import mainpackage.viso.herramientas.Herramientas;
import mainpackage.viso.herramientas.SharedPreferencesHelper;
import mainpackage.viso.herramientas.objetos.UsuarioAdulto;

public class CuentaRegistroLoginViewModel extends ViewModel {
    private UsuarioAdulto usuarioAdulto=null;

    public CuentaRegistroLoginViewModel() {
    }

    public UsuarioAdulto getUsuarioAdulto() {
        return usuarioAdulto;
    }

    public void setUsuarioAdulto(UsuarioAdulto usuarioAdulto) {
        this.usuarioAdulto = usuarioAdulto;
    }
}
