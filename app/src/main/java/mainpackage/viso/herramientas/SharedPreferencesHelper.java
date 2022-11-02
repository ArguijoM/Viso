package mainpackage.viso.herramientas;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import mainpackage.viso.herramientas.objetos.UsuarioAdulto;
import mainpackage.viso.herramientas.objetos.UsuarioNino;

public class SharedPreferencesHelper {
    private SQLiteHelper myDB;
    public static ArrayList<UsuarioNino> getUsuarios(Activity act){
        //Obtener usuarios guardados en SharedPreferencesHelper
        //myDB = new SQLiteHelper(Herramientas.mainActivity.getBaseContext());
        ArrayList<UsuarioNino> usuarios=null;
        SharedPreferences mPrefs = act.getPreferences(Context.MODE_PRIVATE);
        String json = mPrefs.getString("usuarios", "");
        Type type = new TypeToken< ArrayList <UsuarioNino>>() {}.getType();
        usuarios = new Gson().fromJson(json, type);
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
    public static UsuarioNino getUsuarioByIdLocal(Activity act, int id){
        //Obtener usuarios guardados en SharedPreferencesHelper
        ArrayList<UsuarioNino> usuarios=null;
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
    public static void setUsuarios(Activity act, ArrayList<UsuarioNino> usuarios){
        SharedPreferences mPrefs = act.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor2 = mPrefs.edit();
        String jsonUsers = new Gson().toJson(usuarios);
        prefsEditor2.putString("usuarios", jsonUsers).commit();
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
    public static void setUsuarioActual(Activity act, UsuarioNino usuarioActual){
        SharedPreferences sharedPreferences = act.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor shardPreferencesEditor = sharedPreferences.edit();
        String jsonUser = new Gson().toJson(usuarioActual);
        shardPreferencesEditor.putString("usuarioActual", jsonUser).commit();
    }
    public static void setUsuarioAdulto(Activity act, UsuarioAdulto usuarioAdulto){
        SharedPreferences sharedPreferences = act.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor shardPreferencesEditor = sharedPreferences.edit();
        String jsonUser = new Gson().toJson(usuarioAdulto);
        shardPreferencesEditor.putString("usuarioAdulto", jsonUser).commit();
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


}
