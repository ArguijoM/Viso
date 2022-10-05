package mainpackage.viso.ui.actividad;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import mainpackage.viso.herramientas.Herramientas;
import mainpackage.viso.herramientas.SharedPreferencesHelper;
import mainpackage.viso.herramientas.objetos.UsuarioNino;

public class ActividadViewModel extends ViewModel {
    private UsuarioNino usuarioActual;

    public ActividadViewModel() {
        this.usuarioActual= SharedPreferencesHelper.getUsuarioActual(Herramientas.mainActivity);
    }

    public UsuarioNino getUsuarioActual() {
        return usuarioActual;
    }

    public void setUsuarioActual(UsuarioNino usuarioActual) {
        this.usuarioActual = usuarioActual;
    }
}