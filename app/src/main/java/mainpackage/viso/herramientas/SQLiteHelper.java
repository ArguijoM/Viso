package mainpackage.viso.herramientas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

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
    public static final String TABLE_ACTIVIDAD_NAME = "Actividad";//nombre de la tabla
    public static final String COL_ACTIVIDAD_1 = "id_servidor";//columna 1: id
    public static final String COL_ACTIVIDAD_2 = "modelo";//columna 2: nombre
    public static final String COL_ACTIVIDAD_3 = "local_id";//columna 3: modelo
    public static final String COL_ACTIVIDAD_4 = "fecha";//columna 4: imagen
    public static final String COL_ACTIVIDAD_5 = "calificacion";//columna 4: imagen
    public static final String COL_ACTIVIDAD_6 = "nino_id";//columna 4: imagen
    //Constructor

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Creamos la base de datos
        db.execSQL("CREATE TABLE "+TABLE_USUARIO_NAME+" (id INTEGER PRIMARY KEY, id_servidor INTEGER ,nombre TEXT, apellido TEXT, email TEXT, contrasena TEXT )");
        db.execSQL("CREATE TABLE "+TABLE_NINO_NAME+" (id INTEGER PRIMARY KEY,id_servidor INTEGER ,nombre TEXT, apellido TEXT,edad INTEGER, usuario_id INTEGER)");
        db.execSQL("CREATE TABLE "+TABLE_ACTIVIDAD_NAME+" (id INTEGER PRIMARY KEY, id_servidor INTEGER,modelo LONGTEXT, local_id INTEGER,fecha, TEXT,calificacion INTEGER,nino_id INTEGER)");
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
    public boolean insertarNino(int idServidor,String nombre, String apellido, int edad, int usuario_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valoresNino = new ContentValues();
        valoresNino.put(COL_NINO_1,idServidor);
        valoresNino.put(COL_NINO_2,nombre);
        valoresNino.put(COL_NINO_3,apellido);
        valoresNino.put(COL_NINO_4,edad);
        valoresNino.put(COL_NINO_5,usuario_id);
        //El método "insert" devuelve "-1" si ha ocurrido un error
        long result = db.insert(TABLE_NINO_NAME,null,valoresNino);
        if(result==-1){
            return false;//error
        }else{
            return true;//exitoso
        }
    }
    //Función para insertar un auto
    public boolean insertarActividad(int idServidor,String modelo, int local_id,String fecha, int calificacion, int nino_id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues valoresActividad = new ContentValues();
        valoresActividad.put(COL_ACTIVIDAD_1,idServidor);
        valoresActividad.put(COL_ACTIVIDAD_2,modelo);
        valoresActividad.put(COL_ACTIVIDAD_3, local_id);
        valoresActividad.put(COL_ACTIVIDAD_4,fecha);
        valoresActividad.put(COL_ACTIVIDAD_5,calificacion);
        valoresActividad.put(COL_ACTIVIDAD_6,nino_id);
        //El método "insert" devuelve "-1" si ha ocurrido un error
        long result = db.insert(TABLE_ACTIVIDAD_NAME,null,valoresActividad);
        if(result==-1){
            return false;//error
        }else{
            return true;//exitoso
        }
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
        return res;
    }
    public Cursor obtenerActividades(int idNino, int local_id){
        SQLiteDatabase db = this.getWritableDatabase();
        //Consulta SQL
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_ACTIVIDAD_NAME+" WHERE nino_id= ? AND local_id= ?",new String[]{String.valueOf(idNino), String.valueOf(local_id)});
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
}
