package mainpackage.viso.herramientas.objetos;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import mainpackage.viso.herramientas.Herramientas;

public class Actividad {
    private int idLocal;
    private int idServidor;
    private int ninoId;
    private int ninoIdLocal;
    private String path;
    private String name;
    private int calificacion;
    private String fecha;
    private String imagen;

    public Actividad(int id,String path,String name) {
        this.idLocal =id;
        this.idServidor=0;
        this.path=path;
        this.name =name;
        this.calificacion = 0;
        this.fecha = Calendar.getInstance().getTime().toString();
        this.imagen = Herramientas.loadImageFromStorageString(path,name);
    }
    public Actividad(int id,int idServidor,int ninoId, int ninoIdLocal,String imagen,int puntuacion,String fecha) {
        this.idServidor=idServidor;
        this.idLocal =id;
        this.ninoId=ninoId;
        this.ninoIdLocal = ninoIdLocal;
        this.path="";
        this.name = "";
        this.calificacion = puntuacion;
        this.fecha = fecha;
        this.imagen = imagen;
    }

    public int getNinoIdLocal() {
        return ninoIdLocal;
    }

    public void setNinoIdLocal(int ninoIdLocal) {
        this.ninoIdLocal = ninoIdLocal;
    }

    public int getIdLocal() {
        return idLocal;
    }

    public void setIdLocal(int idLocal) {
        this.idLocal = idLocal;
    }

    public int getIdServidor() {
        return idServidor;
    }

    public void setIdServidor(int idServidor) {
        this.idServidor = idServidor;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
    public String getImagen(){
        return this.imagen;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public int getNinoId() {
        return ninoId;
    }

    public void setNinoId(int ninoId) {
        this.ninoId = ninoId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String crearImagenString(Bitmap image){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        //image.recycle();
        return android.util.Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
}
