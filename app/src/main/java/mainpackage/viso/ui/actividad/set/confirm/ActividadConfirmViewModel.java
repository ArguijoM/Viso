package mainpackage.viso.ui.actividad.set.confirm;

import androidx.lifecycle.ViewModel;

import mainpackage.viso.herramientas.Herramientas;
import mainpackage.viso.herramientas.SharedPreferencesHelper;
import mainpackage.viso.herramientas.objetos.UsuarioNino;

public class ActividadConfirmViewModel extends ViewModel {
    private UsuarioNino usuarioActual;

    public ActividadConfirmViewModel() {
        this.usuarioActual= SharedPreferencesHelper.getUsuarioActual(Herramientas.mainActivity);
    }

    public UsuarioNino getUsuarioActual() {
        return usuarioActual;
    }
    public int getActividadId(){
        return usuarioActual.getActividades().size();
    }

    public void setUsuarioActual(UsuarioNino usuarioActual) {
        this.usuarioActual = usuarioActual;
    }
}
