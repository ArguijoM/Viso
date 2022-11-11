package mainpackage.viso.ui.actividad.get;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import mainpackage.viso.herramientas.Herramientas;
import mainpackage.viso.herramientas.SharedPreferencesHelper;
import mainpackage.viso.herramientas.objetos.Actividad;
import mainpackage.viso.herramientas.objetos.UsuarioNino;

public class ActividadShowViewModel extends ViewModel {
    private UsuarioNino usuarioActual;
    private ArrayList<Actividad> actividades;

    public ActividadShowViewModel() {
        this.usuarioActual= SharedPreferencesHelper.getUsuarioActual(Herramientas.mainActivity);
        this.actividades = SharedPreferencesHelper.getActividades(usuarioActual.getIdLocal());
    }

    public UsuarioNino getUsuarioActual() {
        return usuarioActual;
    }
    public ArrayList<Actividad> getActividades(){
        return this.actividades;
    }
    public String getActividadName(int id){
        return actividades.get((id-1)).getName();
    }
    public String getActividadPath(int id){
        return actividades.get((id-1)).getPath();
    }
    public Bitmap getImagenActividad(int id){
        if((id-1)>=0){
            id = id-1;
        }
        String imageString = this.actividades.get(id).getImagen();
        byte[] decodedString = Base64.decode(imageString, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    public void setUsuarioActual(UsuarioNino usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    public int getActividadCalificacion(int id){
        return this.actividades.get(id-1).getCalificacion();
    }

}
