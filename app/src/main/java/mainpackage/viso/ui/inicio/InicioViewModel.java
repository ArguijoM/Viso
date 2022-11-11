package mainpackage.viso.ui.inicio;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import mainpackage.viso.herramientas.DatabaseHelper;
import mainpackage.viso.herramientas.Herramientas;
import mainpackage.viso.herramientas.SQLiteHelper;
import mainpackage.viso.herramientas.SharedPreferencesHelper;
import mainpackage.viso.herramientas.objetos.Actividad;
import mainpackage.viso.herramientas.objetos.UsuarioNino;

public class InicioViewModel extends ViewModel {
    private int realizadas;
    private UsuarioNino usuarioActual;
    private ArrayList<Actividad> actividades;


    public InicioViewModel(){
        this.realizadas = 0;
        usuarioActual = SharedPreferencesHelper.getUsuarioActual(Herramientas.mainActivity);
        if(usuarioActual!=null) {
            actividades = SharedPreferencesHelper.getActividades(usuarioActual.getIdLocal());
        }else{
            actividades = new ArrayList<>();
        }

    }

    public int getRealizadas() {
        return realizadas;
    }

    public UsuarioNino getUsuarioActual() {
        return usuarioActual;
    }

    public void setRealizadas(int realizadas) {
        this.realizadas = realizadas;
    }

    public void setUsuarioActual(UsuarioNino usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    public ArrayList<Actividad> getActividades() {
        return actividades;
    }

    public void setActividades(ArrayList<Actividad> actividades) {
        this.actividades = actividades;
    }
}
