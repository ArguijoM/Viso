package mainpackage.viso.herramientas.objetos;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import mainpackage.viso.herramientas.Herramientas;

public class Actividad {
    private int id;
    private int idServidor;
    private int ninoId;
    private String path;
    private String name;
    private int puntuacion;
    private String fecha;
    private String imagen;


    public Actividad(int id,String path,String name) {
        this.id=id;
        this.idServidor=0;
        this.path=path;
        this.name =name;
        this.puntuacion = 0;
        this.fecha = Calendar.getInstance().getTime().toString();
        this.imagen = Herramientas.loadImageFromStorageString(path,name);
    }
    public Actividad(int id,int calificacion,Bitmap image) {
        this.id=id;
        this.idServidor=0;
        this.path="";
        this.name ="";
        this.puntuacion = calificacion;
        this.fecha = Calendar.getInstance().getTime().toString();
        this.imagen =crearImagenString(image);
    }
    public Actividad(int idServidor,int id,String imagen,String fecha,int puntuacion,int ninoId) {
        this.idServidor=idServidor;
        this.id=id;
        this.ninoId=ninoId;
        this.path="";
        this.name = "";
        this.puntuacion = puntuacion;
        this.fecha = fecha;
        this.imagen = imagen;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getPuntuacion() {
        return puntuacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
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
