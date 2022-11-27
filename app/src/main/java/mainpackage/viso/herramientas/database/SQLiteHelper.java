package mainpackage.viso.herramientas.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

import mainpackage.viso.herramientas.objetos.Actividad;
import mainpackage.viso.herramientas.objetos.UsuarioAdulto;
import mainpackage.viso.herramientas.objetos.UsuarioNino;

public class SQLiteHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "viso.db";//Nombre de la base de datos
    public static final String TABLE_USUARIO_NAME = "Usuario";//nombre de la tabla
    public static final String COL_USUARIO_1 = "id_servidor";//columna 1: id
    public static final String COL_USUARIO_2 = "nombre";//columna 2: nombre
    public static final String COL_USUARIO_3 = "apellido";//columna 3: escuderia
    public static final String COL_USUARIO_4 = "email";//columna 4: auto
    public static final String COL_USUARIO_5 = "contrasena";//columna 4: auto
    public static final String TABLE_NINO_NAME = "Nino";//nombre de la tabla
    public static final String COL_NINO_1 = "id_servidor";//columna 1: id
    public static final String COL_NINO_2 = "nombre";//columna 2: nombre
    public static final String COL_NINO_3 = "apellido";//columna 3: modelo
    public static final String COL_NINO_4 = "edad";//columna 4: imagen
    public static final String COL_NINO_5 = "usuario_id";//columna 4: imagen
    public static final String COL_NINO_6 = "perfil";//columna 4: imagen
    public static final String TABLE_ACTIVIDAD_NAME = "Actividad";//nombre de la tabla
    public static final String COL_ACTIVIDAD_1 = "id_local";//columna 1: id
    public static final String COL_ACTIVIDAD_2 = "id_servidor";//columna 1: id
    public static final String COL_ACTIVIDAD_3 = "id_nino";//columna 4: imagen
    public static final String COL_ACTIVIDAD_4 = "id_nino_local";//columna 4: imagen
    public static final String COL_ACTIVIDAD_5 = "imagen";//columna 2: nombre
    public static final String COL_ACTIVIDAD_6 = "calificacion";//columna 4: imagen
    public static final String COL_ACTIVIDAD_7 = "fecha";//columna 4: imagen


    //Constructor

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Creamos la base de datos
        db.execSQL("CREATE TABLE "+TABLE_USUARIO_NAME+" (id INTEGER PRIMARY KEY, id_servidor INTEGER ,nombre TEXT, apellido TEXT, email TEXT, contrasena TEXT )");
        db.execSQL("CREATE TABLE "+TABLE_NINO_NAME+" (id INTEGER PRIMARY KEY,id_servidor INTEGER ,nombre TEXT, apellido TEXT,edad INTEGER, usuario_id INTEGER, perfil TEXT)");
        db.execSQL("CREATE TABLE "+TABLE_ACTIVIDAD_NAME+" (id INTEGER PRIMARY KEY,id_local INTEGER ,id_servidor INTEGER,id_nino INTEGER,id_nino_local INTEGER,imagen LONGTEXT,calificacion INTEGER,fecha TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Eliminamos la base de datos si ya existe para evitar errores
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_USUARIO_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NINO_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_ACTIVIDAD_NAME);
        onCreate(db);//creamos la base de datos
    }

    //Función para insertar un piloto
    public boolean insertarUsuario(int idServidor,String nombre, String apellido, String email, String contrasena){
        SQLiteDatabase db = this.getWritableDatabase();
        //Valores insertados en la tabla
        ContentValues valoresUsuario = new ContentValues();
        valoresUsuario.put(COL_USUARIO_1,idServidor);
        valoresUsuario.put(COL_USUARIO_2,nombre);
        valoresUsuario.put(COL_USUARIO_3,apellido);
        valoresUsuario.put(COL_USUARIO_4,email);
        valoresUsuario.put(COL_USUARIO_5,contrasena);
        //El método "insert" devuelve "-1" si ha ocurrido un error
        long result = db.insert(TABLE_USUARIO_NAME,null,valoresUsuario);
        if(result==-1){
            return false;//error
        }else{
            return true;//exitoso
        }
    }
    //Función para insertar un auto
    public boolean insertarNino(int idServidor,String nombre, String apellido, int edad, int usuario_id, String perfil){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valoresNino = new ContentValues();
        valoresNino.put(COL_NINO_1,idServidor);
        valoresNino.put(COL_NINO_2,nombre);
        valoresNino.put(COL_NINO_3,apellido);
        valoresNino.put(COL_NINO_4,edad);
        valoresNino.put(COL_NINO_5,usuario_id);
        valoresNino.put(COL_NINO_6,perfil);
        //El método "insert" devuelve "-1" si ha ocurrido un error
        long result = db.insert(TABLE_NINO_NAME,null,valoresNino);
        if(result==-1){
            return false;//error
        }else{
            return true;//exitoso
        }
    }
    //Función para insertar un auto
    public boolean insertarActividad(int idLocal,int idServidor, int id_nino,int id_nino_local, String imagen, int calificacion, String fecha){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valoresActividad = new ContentValues();
        valoresActividad.put(COL_ACTIVIDAD_1,idLocal);
        valoresActividad.put(COL_ACTIVIDAD_2,idServidor);
        valoresActividad.put(COL_ACTIVIDAD_3,id_nino);
        valoresActividad.put(COL_ACTIVIDAD_4,id_nino_local);
        valoresActividad.put(COL_ACTIVIDAD_5, imagen);
        valoresActividad.put(COL_ACTIVIDAD_6,calificacion);
        valoresActividad.put(COL_ACTIVIDAD_7,fecha);
        //El método "insert" devuelve "-1" si ha ocurrido un error
        long result = db.insert(TABLE_ACTIVIDAD_NAME,null,valoresActividad);
        if(result==-1){
            return false;//error
        }else{
            return true;//exitoso
        }
    }
    //Función para insertar un auto
    public boolean insertarActividades(ArrayList<Actividad> actividades){
        for(int i=0;i<actividades.size();i++) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues valoresActividad = new ContentValues();
            valoresActividad.put(COL_ACTIVIDAD_1, actividades.get(i).getIdLocal());
            valoresActividad.put(COL_ACTIVIDAD_2, actividades.get(i).getIdServidor());
            valoresActividad.put(COL_ACTIVIDAD_3, actividades.get(i).getNinoId());
            valoresActividad.put(COL_ACTIVIDAD_4, actividades.get(i).getNinoIdLocal());
            valoresActividad.put(COL_ACTIVIDAD_5, actividades.get(i).getImagen());
            valoresActividad.put(COL_ACTIVIDAD_6, actividades.get(i).getCalificacion());
            valoresActividad.put(COL_ACTIVIDAD_7, actividades.get(i).getFecha());
            //El método "insert" devuelve "-1" si ha ocurrido un error
            long result = db.insert(TABLE_ACTIVIDAD_NAME, null, valoresActividad);
            if (result == -1) {
                return false;//error
            }
        }
        return true;
    }
    //Función para consultar un piloto
    public Cursor obtenerUsuario(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        //Consulta SQL
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_USUARIO_NAME+" WHERE nombre= ?",new String[]{name});

        return res;
    }
    //Función para consultar un auto
    public Cursor obtenerNino(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        //Consulta SQL
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_NINO_NAME+" WHERE nombre= ?",new String[]{name});
        Log.e("CURSOR",res.toString());
        return res;
    }
    public Cursor obtenerActividad(int idNino, int local_id){
        SQLiteDatabase db = this.getWritableDatabase();
        //Consulta SQL
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_ACTIVIDAD_NAME+" WHERE nino_id_local= ? AND local_id= ?",new String[]{String.valueOf(idNino), String.valueOf(local_id)});
        Log.i("BASE DE DATOS",res.toString());
        return res;
    }
    public Cursor obtenerActividades(int idNino){
        SQLiteDatabase db = this.getWritableDatabase();
        //Consulta SQL
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_ACTIVIDAD_NAME+" WHERE id_nino_local= ?",new String[]{String.valueOf(idNino)});
        Log.i("BASE DE DATOS",res.toString());
        return res;
    }
    //Función para consultar todos los piloto
    public Cursor obtenerTodosNinos(){
        SQLiteDatabase db = this.getWritableDatabase();
        //Consulta SQL
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_NINO_NAME,null);
        return res;
    }


    //Función para consultar todos los piloto
    public boolean actualizarUsuario(UsuarioAdulto usuarioAdulto){
        SQLiteDatabase db = this.getWritableDatabase();
        //Valores insertados en la tabla
        ContentValues valoresUsuario = new ContentValues();
        valoresUsuario.put(COL_USUARIO_1,usuarioAdulto.getIdServidor());
        valoresUsuario.put(COL_USUARIO_2,usuarioAdulto.getNombre());
        valoresUsuario.put(COL_USUARIO_3,usuarioAdulto.getApellido());
        valoresUsuario.put(COL_USUARIO_4,usuarioAdulto.getEmail());
        valoresUsuario.put(COL_USUARIO_5,usuarioAdulto.getContrasena());
        //El método "insert" devuelve "-1" si ha ocurrido un error
        long result = db.update(TABLE_USUARIO_NAME,valoresUsuario,"nombre=?", new String[]{usuarioAdulto.getNombre()});
        if(result==-1){
            return false;//error
        }else{
            return true;//exitoso
        }
    }

    public boolean actualizarNino(UsuarioNino usuarioNino){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valoresNino = new ContentValues();
        valoresNino.put(COL_NINO_1,usuarioNino.getIdServidor());
        valoresNino.put(COL_NINO_2,usuarioNino.getNombre());
        valoresNino.put(COL_NINO_3,usuarioNino.getApellido());
        valoresNino.put(COL_NINO_4,usuarioNino.getEdad());
        valoresNino.put(COL_NINO_5,usuarioNino.getIdAdulto());
        valoresNino.put(COL_NINO_6,usuarioNino.getProfile());
        //El método "insert" devuelve "-1" si ha ocurrido un error
        long result = db.update(TABLE_NINO_NAME,valoresNino,"nombre=?", new String[]{usuarioNino.getNombre()});
        if(result==-1){
            return false;//error
        }else{
            return true;//exitoso
        }
    }

    public boolean actualizarActividades(int idNino,ArrayList<Actividad> actividades){
        SQLiteDatabase db = this.getWritableDatabase();
        for(int i=0;i<actividades.size();i++) {
            ContentValues valoresActividad = new ContentValues();
            valoresActividad.put(COL_ACTIVIDAD_1, actividades.get(i).getIdLocal());
            valoresActividad.put(COL_ACTIVIDAD_2, actividades.get(i).getIdServidor());
            valoresActividad.put(COL_ACTIVIDAD_3, actividades.get(i).getNinoId());
            valoresActividad.put(COL_ACTIVIDAD_4, actividades.get(i).getNinoIdLocal());
            valoresActividad.put(COL_ACTIVIDAD_5, actividades.get(i).getImagen());
            valoresActividad.put(COL_ACTIVIDAD_6, actividades.get(i).getCalificacion());
            valoresActividad.put(COL_ACTIVIDAD_7, actividades.get(i).getFecha());
            //El método "insert" devuelve "-1" si ha ocurrido un error
            long result = db.update(TABLE_ACTIVIDAD_NAME, valoresActividad, "id_local=? AND id_nino_local=?",new String[]{String.valueOf(actividades.get(i).getIdLocal()),String.valueOf(idNino)} );
            if (result == -1) {
                return false;//error
            }
        }
        return true;//exitoso
    }
    public boolean borrarActividades(int idNino){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_ACTIVIDAD_NAME, "id_nino_local=?", new String[]{String.valueOf(idNino)});
        if (result == -1) {
            return false;//error
        }
        return true;
    }
    public boolean borrarNino(int idNino){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NINO_NAME, "id=?", new String[]{String.valueOf(idNino)});
        if (result == -1) {
            return false;//error
        }
        return true;
    }

}
