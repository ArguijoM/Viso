package mainpackage.viso.herramientas;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import mainpackage.viso.herramientas.objetos.Actividad;
import mainpackage.viso.herramientas.objetos.UsuarioAdulto;
import mainpackage.viso.herramientas.objetos.UsuarioNino;

public class SharedPreferencesHelper {
    public static ArrayList<UsuarioNino> getUsuarios(Activity act){
        //Obtener usuarios guardados en SharedPreferencesHelper
        ArrayList<UsuarioNino> usuarios=null;
        SharedPreferences mPrefs = act.getPreferences(Context.MODE_PRIVATE);
        String json = mPrefs.getString("usuarios", "");
        Type type = new TypeToken< ArrayList <UsuarioNino>>() {}.getType();
        usuarios = new Gson().fromJson(json, type);
        return usuarios;
    }
    public static ArrayList<UsuarioNino> getUsuarios(){
        //Obtener usuarios guardados en SQLite
        SQLiteHelper myDB = new SQLiteHelper(Herramientas.mainActivity);
        Cursor res = myDB.obtenerTodosNinos();
        ArrayList<UsuarioNino> usuarios=new ArrayList<>();
        if(res.getCount()==0){
            return null;
        }
        while(res.moveToNext()) {
            usuarios.add(new UsuarioNino(
                    res.getInt(0),
                    res.getInt(1),
                    res.getString(2),
                    res.getString(3),
                    res.getInt(4),
                    res.getInt(5),
                    res.getString(6))
            );
        }
        return usuarios;
    }
    public static UsuarioNino getUsuarioActual(Activity act){
        //Obtener usuarios guardados en SharedPreferencesHelper
        UsuarioNino usuario = null;
        SharedPreferences mPrefs = act.getPreferences(Context.MODE_PRIVATE);
        String json = mPrefs.getString("usuarioActual", "");
        Type type = new TypeToken <UsuarioNino>() {}.getType();
        usuario = new Gson().fromJson(json, type);
        return usuario;
    }
    public static UsuarioAdulto getUsuarioAdulto(Activity act){
        //Obtener usuarios guardados en SharedPreferencesHelper
        UsuarioAdulto usuario = null;
        SharedPreferences mPrefs = act.getPreferences(Context.MODE_PRIVATE);
        String json = mPrefs.getString("usuarioAdulto", "");
        Type type = new TypeToken <UsuarioAdulto>() {}.getType();
        usuario = new Gson().fromJson(json, type);
        return usuario;
    }
    public static UsuarioAdulto getUsuarioAdulto(String nombre){
        SQLiteHelper myDB = new SQLiteHelper(Herramientas.mainActivity);
        Cursor res =  myDB.obtenerUsuario(nombre);
        UsuarioAdulto usuario = null;
        if(res.getCount()==0){
            return null;
        }
        while(res.moveToNext()) {
            usuario = new UsuarioAdulto(
                    res.getInt(0),
                    res.getInt(1),
                    res.getString(2),
                    res.getString(3),
                    res.getString(4),
                    res.getString(5)
            );
        }
        return usuario;
    }
    public static UsuarioNino getUsuario(String nombre){
        SQLiteHelper myDB = new SQLiteHelper(Herramientas.mainActivity);
        Cursor res =  myDB.obtenerNino(nombre);
        UsuarioNino usuario = null;
        if(res.getCount()==0){
            return null;
        }
        while(res.moveToNext()) {
            usuario = new UsuarioNino(
                    res.getInt(0),
                    res.getInt(1),
                    res.getString(2),
                    res.getString(3),
                    res.getInt(4),
                    res.getInt(5),
                    res.getString(6)
            );
        }
        return usuario;
    }

    public static void updateUsuario(Activity act,UsuarioNino usuarioNino){
        //Obtener usuarios guardados en SharedPreferencesHelper
        ArrayList<UsuarioNino> usuarios=null;
        SharedPreferences mPrefs = act.getPreferences(Context.MODE_PRIVATE);
        String json = mPrefs.getString("usuarios", "");
        Type type = new TypeToken< ArrayList <UsuarioNino>>() {}.getType();
        usuarios = new Gson().fromJson(json, type);
        for(int i=0;i<usuarios.size();i++) {
            if (usuarios.get(i).getNombre().toString().equals(usuarioNino.getNombre().toString())){
                usuarios.set(i,usuarioNino);
                SharedPreferences mPref = act.getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor2 = mPref.edit();
                String jsonUsers = new Gson().toJson(usuarios);
                prefsEditor2.putString("usuarios", jsonUsers).commit();
            }
        }
    }

    public static void updateUsuario(ArrayList<UsuarioNino> users){
        SQLiteHelper myDB = new SQLiteHelper(Herramientas.mainActivity);
        for(int i=0;i<users.size();i++) {
            myDB.actualizarNino(users.get(i));
        }
    }

    public static void updateUsuariobyId(Activity act,UsuarioNino usuarioNino){
        //Obtener usuarios guardados en SharedPreferencesHelper
        ArrayList<UsuarioNino> usuarios=null;
        SharedPreferences mPrefs = act.getPreferences(Context.MODE_PRIVATE);
        String json = mPrefs.getString("usuarios", "");
        Type type = new TypeToken< ArrayList <UsuarioNino>>() {}.getType();
        usuarios = new Gson().fromJson(json, type);
        for(int i=0;i<usuarios.size();i++) {
            if (usuarios.get(i).getIdLocal()==usuarioNino.getIdLocal()){
                usuarios.set(i,usuarioNino);
                SharedPreferences mPref = act.getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor prefsEditor2 = mPref.edit();
                String jsonUsers = new Gson().toJson(usuarios);
                prefsEditor2.putString("usuarios", jsonUsers).commit();
            }
        }
    }

    public static void updateUsuariobyId(UsuarioNino usuarioNino){
        //Obtener usuarios guardados en SQLite
        SQLiteHelper myDB = new SQLiteHelper(Herramientas.mainActivity);
        Cursor res = myDB.obtenerTodosNinos();
        ArrayList<UsuarioNino> usuarios= new ArrayList<>();
        while(res.moveToNext()) {
            usuarios.add(new UsuarioNino(
                    res.getInt(0),
                    res.getInt(1),
                    res.getString(2),
                    res.getString(3),
                    res.getInt(4),
                    res.getInt(5),
                    res.getString(6))
            );
        }
        for(int i=0;i<usuarios.size();i++) {
            if (usuarios.get(i).getIdLocal()==usuarioNino.getIdLocal()){
                myDB.actualizarNino(usuarioNino);
            }
        }
    }

    public static UsuarioNino getUsuarioByIdLocal(Activity act, int id){
        //Obtener usuarios guardados en SharedPreferencesHelper
        ArrayList<UsuarioNino> usuarios=new ArrayList<>();
        SharedPreferences mPrefs = act.getPreferences(Context.MODE_PRIVATE);
        String json = mPrefs.getString("usuarios", "");
        Type type = new TypeToken< ArrayList <UsuarioNino>>() {}.getType();
        usuarios = new Gson().fromJson(json, type);
        UsuarioNino usuario = null;
        for(int i=0;i<usuarios.size();i++) {
            if (usuarios.get(i).getIdLocal() == id){
                usuario = usuarios.get(i);
            }
        }
        return usuario;
    }

    public static UsuarioNino getUsuarioByIdLocal(int id){
        //Obtener usuarios guardados en SQLite
        SQLiteHelper myDB = new SQLiteHelper(Herramientas.mainActivity);
        Cursor res = myDB.obtenerTodosNinos();
        ArrayList<UsuarioNino> usuarios=new ArrayList<>();
        while(res.moveToNext()) {
            usuarios.add(new UsuarioNino(
                    res.getInt(0),
                    res.getInt(1),
                    res.getString(2),
                    res.getString(3),
                    res.getInt(4),
                    res.getInt(5),
                    res.getString(6))
            );
        }
        UsuarioNino usuario = null;
        for(int i=0;i<usuarios.size();i++) {
            if (usuarios.get(i).getIdLocal() == id){
                usuario = usuarios.get(i);
            }
        }
        return usuario;
    }

    public static UsuarioNino getUsuarioByIdAdulto(Activity act, int id){
        //Obtener usuarios guardados en SharedPreferencesHelper
        ArrayList<UsuarioNino> usuarios=null;
        SharedPreferences mPrefs = act.getPreferences(Context.MODE_PRIVATE);
        String json = mPrefs.getString("usuarios", "");
        Type type = new TypeToken< ArrayList <UsuarioNino>>() {}.getType();
        usuarios = new Gson().fromJson(json, type);
        UsuarioNino usuario = null;
        for(int i=0;i<usuarios.size();i++) {
            if (usuarios.get(i).getIdAdulto()== id){
                usuario = usuarios.get(i);
            }
        }
        return usuario;
    }
    public static UsuarioNino getUsuarioByIdServer(Activity act, int id){
        //Obtener usuarios guardados en SharedPreferencesHelper
        ArrayList<UsuarioNino> usuarios=null;
        SharedPreferences mPrefs = act.getPreferences(Context.MODE_PRIVATE);
        String json = mPrefs.getString("usuarios", "");
        Type type = new TypeToken< ArrayList <UsuarioNino>>() {}.getType();
        usuarios = new Gson().fromJson(json, type);
        UsuarioNino usuario = null;
        for(int i=0;i<usuarios.size();i++) {
            if (usuarios.get(i).getIdServidor() == id){
                usuario = usuarios.get(i);
            }
        }
        return usuario;
    }

    public static UsuarioNino getUsuarioByIdServer(int id){
        //Obtener usuarios guardados en SQLite
        SQLiteHelper myDB = new SQLiteHelper(Herramientas.mainActivity);
        Cursor res = myDB.obtenerTodosNinos();
        ArrayList<UsuarioNino> usuarios=new ArrayList<>();
        while(res.moveToNext()) {
            usuarios.add(new UsuarioNino(
                    res.getInt(0),
                    res.getInt(1),
                    res.getString(2),
                    res.getString(3),
                    res.getInt(4),
                    res.getInt(5),
                    res.getString(6))
            );
        }
        UsuarioNino usuario = null;
        for(int i=0;i<usuarios.size();i++) {
            if (usuarios.get(i).getIdServidor() == id){
                usuario = usuarios.get(i);
            }
        }
        return usuario;
    }

    public static UsuarioNino getUsuarioByName(Activity act, String name){
        //Obtener usuarios guardados en SharedPreferencesHelper
        ArrayList<UsuarioNino> usuarios=null;
        SharedPreferences mPrefs = act.getPreferences(Context.MODE_PRIVATE);
        String json = mPrefs.getString("usuarios", "");
        Type type = new TypeToken< ArrayList <UsuarioNino>>() {}.getType();
        usuarios = new Gson().fromJson(json, type);
        UsuarioNino usuario = null;
        for(int i=0;i<usuarios.size();i++) {
            if (usuarios.get(i).getNombre().toString().equals(name)){
                usuario = usuarios.get(i);
            }
        }
        return usuario;
    }

    public static UsuarioNino getUsuarioByName(String name){
        //Obtener usuarios guardados en SQLite
        SQLiteHelper myDB = new SQLiteHelper(Herramientas.mainActivity);
        Cursor res = myDB.obtenerNino(name);
        UsuarioNino usuario=null;
        while(res.moveToNext()) {
            usuario = new UsuarioNino(
                    res.getInt(0),
                    res.getInt(1),
                    res.getString(2),
                    res.getString(3),
                    res.getInt(4),
                    res.getInt(5),
                    res.getString(6));
        }
        return usuario;
    }
    public static void setUsuarios(Activity act, ArrayList<UsuarioNino> usuarios){
        SharedPreferences mPrefs = act.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor2 = mPrefs.edit();
        String jsonUsers = new Gson().toJson(usuarios);
        prefsEditor2.putString("usuarios", jsonUsers).commit();
    }

    public static void setUsuarios(ArrayList<UsuarioNino> usuarios){
        SQLiteHelper myDB = new SQLiteHelper(Herramientas.mainActivity);
        for(int i=0;i<usuarios.size();i++){
            myDB.insertarNino(
                    usuarios.get(i).getIdServidor(),
                    usuarios.get(i).getNombre(),
                    usuarios.get(i).getApellido(),
                    usuarios.get(i).getEdad(),
                    usuarios.get(i).getIdAdulto(),
                    usuarios.get(i).getProfile()
            );
        }
    }

    public static void setUsuario(Activity act, UsuarioNino usuario){
        //Obtener usuarios guardados en SharedPreferencesHelper
        ArrayList<UsuarioNino> usuarios=null;
        SharedPreferences mPrefs = act.getPreferences(Context.MODE_PRIVATE);
        String json = mPrefs.getString("usuarios", "");
        Type type = new TypeToken< ArrayList <UsuarioNino>>() {}.getType();
        usuarios = new Gson().fromJson(json, type);
        for(int i=0; i<usuarios.size();i++){
            if(usuarios.get(i).getIdServidor()==usuario.getIdServidor()){
                usuarios.set(i,usuario);
            }
        }
        SharedPreferences mPrefs2 = act.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor2 = mPrefs2.edit();
        String jsonUsers = new Gson().toJson(usuarios);
        prefsEditor2.putString("usuarios", jsonUsers).commit();
    }
    public static void setUsuario(UsuarioNino usuario){
        SQLiteHelper myDB = new SQLiteHelper(Herramientas.mainActivity);
        myDB.insertarNino(
                usuario.getIdServidor(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEdad(),
                usuario.getIdAdulto(),
                usuario.getProfile()
        );
    }
    public static void setUsuarioActual(Activity act, UsuarioNino usuarioActual){
        UsuarioNino aux = getUsuario(usuarioActual.getNombre());
        SharedPreferences sharedPreferences = act.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor shardPreferencesEditor = sharedPreferences.edit();
        String jsonUser = new Gson().toJson(aux);
        shardPreferencesEditor.putString("usuarioActual", jsonUser).commit();
    }
    public static void setUsuarioAdulto(Activity act, UsuarioAdulto usuarioAdulto){
        SharedPreferences sharedPreferences = act.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor shardPreferencesEditor = sharedPreferences.edit();
        String jsonUser = new Gson().toJson(usuarioAdulto);
        shardPreferencesEditor.putString("usuarioAdulto", jsonUser).commit();

        SQLiteHelper myDB = new SQLiteHelper(Herramientas.mainActivity);
        Cursor res =  myDB.obtenerUsuario(usuarioAdulto.getNombre());

        if(res.getCount()!=0){
            Log.i("setUsuarioAdulto","res!=null");
            myDB.actualizarUsuario(usuarioAdulto);
        }else{
            Log.i("setUsuarioAdulto","res=null");
            myDB.insertarUsuario(
                    usuarioAdulto.getIdServidor(),
                    usuarioAdulto.getNombre(),
                    usuarioAdulto.getApellido(),
                    usuarioAdulto.getEmail(),
                    usuarioAdulto.getContrasena()
            );
        }

    }
    public static void deteteUsuarioActual(Activity act){
        SharedPreferences sharedPreferences = act.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor shardPreferencesEditor = sharedPreferences.edit();
        shardPreferencesEditor.remove("usuarioActual");
        shardPreferencesEditor.apply();
    }
    public static void deteteAllPreference(Activity act){
        SharedPreferences sharedPreferences = act.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor shardPreferencesEditor = sharedPreferences.edit();
        shardPreferencesEditor.remove("usuarioActual");
        shardPreferencesEditor.remove("usuarioAdulto");
        shardPreferencesEditor.apply();
    }
    public static void setUsuarioAdulto(UsuarioAdulto usuarioAdulto){
        SQLiteHelper myDB = new SQLiteHelper(Herramientas.mainActivity);
        myDB.insertarUsuario(
                usuarioAdulto.getIdServidor(),
                usuarioAdulto.getNombre(),
                usuarioAdulto.getApellido(),
                usuarioAdulto.getEmail(),
                usuarioAdulto.getContrasena()
        );
    }
    public static void updateUsuarioAdulto(Activity act, UsuarioAdulto usuarioAdulto){
        //Obtener usuarios guardados en SharedPreferencesHelper
        UsuarioAdulto usuario = null;
        SharedPreferences mPrefs = act.getPreferences(Context.MODE_PRIVATE);
        String json = mPrefs.getString("usuarioAdulto", "");
        Type type = new TypeToken <UsuarioAdulto>() {}.getType();
        usuario = new Gson().fromJson(json, type);
        if (usuario.getEmail().toString().equals(usuarioAdulto.getEmail().toString())){
            SharedPreferences mPref = act.getPreferences(Context.MODE_PRIVATE);
            SharedPreferences.Editor prefsEditor2 = mPref.edit();
            String jsonUsers = new Gson().toJson(usuarioAdulto);
            prefsEditor2.putString("usuarios", jsonUsers).commit();
        }
    }

    public static ArrayList<Actividad> getActividades(int id_nino) {
        SQLiteHelper myDB = new SQLiteHelper(Herramientas.mainActivity);
        Cursor res = myDB.obtenerActividades(id_nino);
        ArrayList<Actividad> actividades=new ArrayList<>();
        while(res.moveToNext()) {
            actividades.add(new Actividad(
                            res.getInt(1),
                            res.getInt(2),
                            res.getInt(3),
                            res.getInt(4),
                            res.getString(5),
                            res.getInt(6),
                            res.getString(7)
                    )
            );
        }
        return actividades;
    }

    public static void updateActividades(int id_nino, ArrayList<Actividad> acts) {
        SQLiteHelper myDB = new SQLiteHelper(Herramientas.mainActivity);
        myDB.actualizarActividades(id_nino,acts);
    }

    public static void addActividades(ArrayList<Actividad> acts) {
        SQLiteHelper myDB = new SQLiteHelper(Herramientas.mainActivity);
        myDB.insertarActividades(acts);
    }

    public static void addActividad(Actividad act){
        SQLiteHelper myDB = new SQLiteHelper(Herramientas.mainActivity);
        myDB.insertarActividad(
                act.getIdLocal(),
                act.getIdServidor(),
                act.getNinoId(),
                act.getNinoIdLocal(),
                act.getImagen(),
                act.getCalificacion(),
                act.getFecha()
        );
    }

    public static boolean deleteActividades(int idNino){
        SQLiteHelper myDB = new SQLiteHelper(Herramientas.mainActivity);
        return myDB.borrarActividades(idNino);
    }
    public static boolean deleteNino(int idNino){
        SQLiteHelper myDB = new SQLiteHelper(Herramientas.mainActivity);
        return myDB.borrarNino(idNino);
    }


}
