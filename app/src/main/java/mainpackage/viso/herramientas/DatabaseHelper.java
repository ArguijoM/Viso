package mainpackage.viso.herramientas;


import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Handler;

import mainpackage.viso.herramientas.objetos.Actividad;
import mainpackage.viso.herramientas.objetos.UsuarioAdulto;
import mainpackage.viso.herramientas.objetos.UsuarioNino;

public class DatabaseHelper {
    private final static String ADD_USUARIO_URL= "https://enviomailprueba.000webhostapp.com/crud/addUsuario.php";
    private final static String ADD_NINO_URL= "https://enviomailprueba.000webhostapp.com/crud/addNino.php";
    private final static String ADD_ACTIVIDAD_URL= "https://enviomailprueba.000webhostapp.com/crud/addActividad.php";
    private final static String READ_USUARIO_URL= "https://enviomailprueba.000webhostapp.com/crud/readUsuario.php";
    private final static String READ_NINO_URL= "https://enviomailprueba.000webhostapp.com/crud/readNino.php";
    private final static String READ_ACTIVIDAD_URL= "https://enviomailprueba.000webhostapp.com/crud/readActividad.php";
    private final static String DELETE_USUARIO_URL= "https://enviomailprueba.000webhostapp.com/crud/deleteUsuario.php";
    private final static String DELETE_NINO_URL= "https://enviomailprueba.000webhostapp.com/crud/deleteNino.php";
    private final static String DELETE_ACTIVIDAD_URL= "https://enviomailprueba.000webhostapp.com/crud/deleteActividad.php";

    private RequestQueue requestQueue;
    private SQLiteHelper myDB;
    private ProgressBar progressBar;
    public DatabaseHelper(Context context, ProgressBar progressBar){
        this.progressBar = progressBar;
        this.requestQueue = Volley.newRequestQueue(context);
        this.myDB = new SQLiteHelper(Herramientas.mainActivity.getBaseContext());
    }

    public void addUsuario(UsuarioAdulto user, final VolleyCallBack volleyCallBack){
        StringRequest request = new StringRequest(
                Request.Method.POST,
                ADD_USUARIO_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(!response.isEmpty()){
                            try {
                                Log.i("Respuesta de servidor",response);
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray json = jsonObject.getJSONArray("estado");
                                JSONObject obj = json.getJSONObject(0);
                                Log.i("Mensaje de servidor",obj.getString("mensaje"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            readUsuario(new VolleyCallBack() {
                                @Override
                                public void onSuccess(String result) {

                                }
                            }, user);
                        }else{
                            Log.i("Respuesta de servidor","No hay respuesta");
                        }
                        volleyCallBack.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("ERROR: Respuesta: ",error.toString());
                        volleyCallBack.onSuccess(error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String,String>();
                parametros.put("nombre",user.getNombre());
                parametros.put("apellido",user.getApellido());
                parametros.put("email",user.getEmail());
                parametros.put("contrasena",user.getContrasena());
                return parametros;
            }
        };
        requestQueue.add(request);
    }
    public void addNino(final VolleyCallBack volleyCallBack,UsuarioNino user){
        StringRequest request = new StringRequest(
                Request.Method.POST,
                ADD_NINO_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(!response.isEmpty()){
                            try {
                                Log.i("Respuesta de servidor",response);
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray json = jsonObject.getJSONArray("estado");
                                JSONObject obj = json.getJSONObject(0);
                                Log.i("Mensaje de servidor",obj.getString("mensaje"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            UsuarioAdulto usuarioAdulto = SharedPreferencesHelper.getUsuarioAdulto(Herramientas.mainActivity);
                            readNino(new VolleyCallBack() {
                                @Override
                                public void onSuccess(String result) {

                                }
                            }, usuarioAdulto.getIdServidor());
                        }else{
                            Log.i("Respuesta de servidor","No hay respuesta");
                        }
                        volleyCallBack.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("ERROR: Respuesta: ",error.toString());
                        volleyCallBack.onSuccess(error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String,String>();
                parametros.put("perfil", user.getProfile());
                parametros.put("nombre",user.getNombre());
                parametros.put("apellido",user.getApellido());
                parametros.put("edad", String.valueOf(user.getEdad()));
                parametros.put("usuario_id", String.valueOf(user.getIdAdulto()));
                return parametros;
            }
        };
        requestQueue.add(request);
    }
    public void addActividad(final VolleyCallBack volleyCallBack,Actividad act,int ninoID){
        StringRequest request = new StringRequest(
                Request.Method.POST,
                ADD_ACTIVIDAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Respuesta servidor: ",response.toString());
                        if(!response.isEmpty()){
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray json = jsonObject.getJSONArray("estado");
                                JSONObject obj = json.getJSONObject(0);
                                Log.i("Mensaje de servidor",obj.getString("mensaje"));
                                readActividad(new VolleyCallBack() {
                                    @Override
                                    public void onSuccess(String result) {

                                    }
                                }, ninoID);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else{
                            Log.i("Respuesta de servidor","No hay respuesta"+response.toString());
                        }
                        volleyCallBack.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("ERROR: Respuesta: ",error.toString());
                        volleyCallBack.onSuccess(error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String,String>();
                parametros.put("modelo",act.getImagen());
                parametros.put("local_id", String.valueOf(act.getId()));
                parametros.put("fecha", "21_12_2022");
                parametros.put("calificacion", String.valueOf(act.getPuntuacion()));
                parametros.put("nino_id", String.valueOf(ninoID));
                return parametros;
            }
        };
        requestQueue.add(request);
    }

    public void readUsuario(final VolleyCallBack volleyCallBack,UsuarioAdulto user){
        StringRequest request = new StringRequest(
                Request.Method.POST,
                READ_USUARIO_URL,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Log.i("Respuesta de servidor",response);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray json = jsonObject.getJSONArray("estado");
                    JSONObject obj = json.getJSONObject(0);
                    if(obj.getInt("estado")==1){
                        Log.i("Mensaje de servidor",obj.getString("mensaje"));
                        JSONArray jsonArray = jsonObject.getJSONArray("usuario");
                        for(int i=0; i<jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            int id = object.getInt("id");
                            String nombre = object.getString("nombre");
                            String apellido = object.getString("apellido");
                            String email = object.getString("email");
                            String contrasena= object.getString("contrasena");
                            UsuarioAdulto usuario = SharedPreferencesHelper.getUsuarioAdulto(Herramientas.mainActivity);
                            usuario.setIdServidor(id);
                            SharedPreferencesHelper.setUsuarioAdulto(Herramientas.mainActivity,usuario);
                        }
                        UsuarioAdulto usuario = SharedPreferencesHelper.getUsuarioAdulto(Herramientas.mainActivity);
                        ArrayList<UsuarioNino>usuarioNinos = SharedPreferencesHelper.getUsuarios(Herramientas.mainActivity);
                        for(int j=0;j<usuarioNinos.size();j++){
                            usuarioNinos.get(j).setIdAdulto(usuario.getIdServidor());
                            SharedPreferencesHelper.updateUsuario(Herramientas.mainActivity,usuarioNinos.get(j));
                        }

                    }else{
                        Log.i("Mensaje de servidor",obj.getString("mensaje"));
                        addUsuario(user, new VolleyCallBack() {
                            @Override
                            public void onSuccess(String result) {

                            }
                        });
                    }
                    volleyCallBack.onSuccess(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("ERROR: ",error.toString());
                        volleyCallBack.onSuccess(error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("email", user.getEmail());
                return parametros;
            }
        };
        requestQueue.add(request);
    }

    public void foundUsuario(final VolleyCallBack callBack,UsuarioAdulto user){
        StringRequest request = new StringRequest(
                Request.Method.POST,
                READ_USUARIO_URL,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Respuesta de servidor",response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray json = jsonObject.getJSONArray("estado");
                    JSONObject obj = json.getJSONObject(0);
                    if(obj.getInt("estado")==1){
                        //Log.i("Mensaje de servidor",obj.getString("mensaje"));
                        JSONArray jsonArray = jsonObject.getJSONArray("usuario");
                        for(int i=0; i<jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            int id = object.getInt("id");
                            String nombre = object.getString("nombre");
                            String apellido = object.getString("apellido");
                            String email = object.getString("email");
                            String contrasena= object.getString("contrasena");
                            UsuarioAdulto usuarioAdulto = new UsuarioAdulto(1,nombre,apellido,email,contrasena);
                            usuarioAdulto.setIdServidor(id);
                            SharedPreferencesHelper.setUsuarioAdulto(Herramientas.mainActivity,usuarioAdulto);
                        }
                        callBack.onSuccess(response);
                    }else{
                        //Log.i("Mensaje de servidor",obj.getString("mensaje"));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("ERROR: ",error.toString());
                        callBack.onSuccess(error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("email", user.getEmail());
                return parametros;
            }
        };
        requestQueue.add(request);
    }

    public  void readNino(final VolleyCallBack volleyCallBack,int idUsuario){
        StringRequest request = new StringRequest(
                Request.Method.POST,
                READ_NINO_URL,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Respuesta de servidor",response);
                ArrayList<UsuarioNino> usuarios = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray json = jsonObject.getJSONArray("estado");
                    JSONObject obj = json.getJSONObject(0);
                    if(obj.getInt("estado")==1){
                        Log.i("Mensaje de servidor",obj.getString("mensaje"));
                        JSONArray jsonArray = jsonObject.getJSONArray("ninos");
                        for(int i=0; i<jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            int id = object.getInt("id");
                            String perfil = object.getString("perfil");
                            String nombre = object.getString("nombre");
                            String apellido = object.getString("apellido");
                            int edad = object.getInt("edad");
                            int usuario_id= object.getInt("usuario_id");
                            usuarios.add(new UsuarioNino(id,perfil,nombre,apellido,edad,usuario_id));
                        }
                        ArrayList<UsuarioNino> users = SharedPreferencesHelper.getUsuarios(Herramientas.mainActivity);
                        for(int i=0; i<users.size();i++){
                            for(int j=0;j<usuarios.size();j++) {
                                if (users.get(i).getNombre().toString().equals(usuarios.get(j).getNombre().toString()) && users.get(i).getApellido().toString().equals(usuarios.get(j).getApellido().toString())){
                                    users.get(i).setIdServidor(usuarios.get(j).getIdServidor());
                                }
                            }
                        }
                        SharedPreferencesHelper.setUsuarios(Herramientas.mainActivity,users);
                    }else{
                        Log.i("Mensaje de servidor", obj.getString("mensaje"));
                        ArrayList<UsuarioNino> usuariosLocal = SharedPreferencesHelper.getUsuarios(Herramientas.mainActivity);
                        for(int i=0; i<usuariosLocal.size();i++){
                            addNino(new VolleyCallBack() {
                                @Override
                                public void onSuccess(String result) {

                                }
                            }, usuariosLocal.get(i));
                        }
                    }
                    volleyCallBack.onSuccess(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Error de servidor",error.toString());
                        volleyCallBack.onSuccess(error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("id", String.valueOf(idUsuario));
                return parametros;
            }
        };
        requestQueue.add(request);
    }
    public void foundNino(final VolleyCallBack callBack,int idUsuario){

        StringRequest request = new StringRequest(
                Request.Method.POST,
                READ_NINO_URL,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Respuesta de servidor",response);
                try {
                    ArrayList<UsuarioNino> usuarios = new ArrayList<>();
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray json = jsonObject.getJSONArray("estado");
                    JSONObject obj = json.getJSONObject(0);
                    if(obj.getInt("estado")==1){
                        //Log.i("Mensaje de servidor",obj.getString("mensaje"));
                        JSONArray jsonArray = jsonObject.getJSONArray("ninos");
                        for(int i=0; i<jsonArray.length();i++){
                            JSONObject object = jsonArray.getJSONObject(i);
                            int id = object.getInt("id");
                            String nombre = object.getString("nombre");
                            String apellido = object.getString("apellido");
                            int edad = object.getInt("edad");
                            int usuario_id= object.getInt("usuario_id");
                            UsuarioNino aux = new UsuarioNino(id,nombre,apellido,edad,usuario_id);
                            aux.setIdLocal(i+1);
                            usuarios.add(aux);
                        }
                        SharedPreferencesHelper.setUsuarios(Herramientas.mainActivity,usuarios);
                        SharedPreferencesHelper.setUsuarioActual(Herramientas.mainActivity,usuarios.get(0));
                        callBack.onSuccess(response);

                    }else{
                       // Log.i("Mensaje de servidor", obj.getString("mensaje"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Error de servidor",error.toString());
                        callBack.onSuccess(error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("id", String.valueOf(idUsuario));
                return parametros;
            }
        };
        requestQueue.add(request);
    }

    public  void readActividad(final VolleyCallBack volleyCallBack,int idNino){
        StringRequest request = new StringRequest(
                Request.Method.POST,
                READ_ACTIVIDAD_URL,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Respuesta de servidor",response);
                ArrayList<Actividad> actividades = new ArrayList<>();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray json = jsonObject.getJSONArray("estado");
                    JSONObject obj = json.getJSONObject(0);
                    if(obj.getInt("estado")==1) {
                        JSONArray jsonArray = jsonObject.getJSONArray("actividades");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            int id = object.getInt("id");
                            int local_id = object.getInt("local_id");
                            String modelo = object.getString("modelo");
                            String fecha = object.getString("fecha");
                            int calificacion = object.getInt("calificacion");
                            int nino_id = object.getInt("nino_id");
                            actividades.add(new Actividad(id,local_id, modelo, fecha, calificacion, nino_id));
                        }
                        UsuarioNino aux = SharedPreferencesHelper.getUsuarioByIdServer(Herramientas.mainActivity,idNino);
                        ArrayList<Actividad> acts = aux.getActividades();
                        for(int i=0;i<acts.size();i++){
                            for(int j=0;j<actividades.size();j++) {
                                if (acts.get(i).getId() == actividades.get(j).getId()){
                                    acts.get(i).setIdServidor(actividades.get(j).getIdServidor());
                                }
                            }
                        }
                        aux.setActividades(acts);
                        SharedPreferencesHelper.setUsuario(Herramientas.mainActivity,aux);
                        for(int i=0;i<acts.size();i++){
                            if (acts.get(i).getIdServidor() == 0){
                                addActividad(new VolleyCallBack() {
                                    @Override
                                    public void onSuccess(String result) {

                                    }
                                }, acts.get(i), aux.getIdServidor());
                            }
                        }

                    }else{
                        UsuarioNino aux = SharedPreferencesHelper.getUsuarioByIdServer(Herramientas.mainActivity,idNino);
                        ArrayList<Actividad> activities = aux.getActividades();
                        for(int k=0;k<activities.size();k++) {
                            addActividad(new VolleyCallBack() {
                                @Override
                                public void onSuccess(String result) {

                                }
                            }, activities.get(k), aux.getIdServidor());
                        }

                    }
                    volleyCallBack.onSuccess(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Error de servidor",error.toString());
                        volleyCallBack.onSuccess(error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("id", String.valueOf(idNino));
                return parametros;
            }
        };
        requestQueue.add(request);
        progressBar.setVisibility(View.GONE);
    }
    public  void foundActividad(final VolleyCallBack callBack,int idNino){
        //Log.i("BUSCANDO ACTIVIDAD: ",""+idNino);
        StringRequest request = new StringRequest(
                Request.Method.POST,
                READ_ACTIVIDAD_URL,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Respuesta de servidor",response);
                try {
                    ArrayList<Actividad> actividades = new ArrayList<>();
                  //  Log.i("SERVER_RESPONSE",response.toString());
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray json = jsonObject.getJSONArray("estado");
                    JSONObject obj = json.getJSONObject(0);
                    if(obj.getInt("estado")==1) {
                        JSONArray jsonArray = jsonObject.getJSONArray("actividades");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            int id = object.getInt("id");
                            int local_id = object.getInt("local_id");
                            String modelo = object.getString("modelo");
                            String fecha = object.getString("fecha");
                            int calificacion = object.getInt("calificacion");
                            int nino_id = object.getInt("nino_id");
                            Actividad aux = new Actividad(id,local_id,modelo,fecha,calificacion,nino_id);
                            actividades.add(aux);
                        }
                        UsuarioNino aux = SharedPreferencesHelper.getUsuarioByIdServer(Herramientas.mainActivity,idNino);
                        aux.setActividades(actividades);
                        SharedPreferencesHelper.updateUsuariobyId(Herramientas.mainActivity,aux);
                        SharedPreferencesHelper.setUsuarioActual(Herramientas.mainActivity,aux);
                        callBack.onSuccess(response);
                    }else{
                        //Log.i("Mensaje de servidor ", obj.getString("mensaje"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    callBack.onSuccess(response);
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Log.i("Error de servidor",error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("id", String.valueOf(idNino));
                return parametros;
            }
        };
        requestQueue.add(request);
        progressBar.setVisibility(View.GONE);
    }


    public void generarCopiaSeguridad(UsuarioAdulto usuarioAdulto){
        this.progressBar.setVisibility(View.VISIBLE);
        readUsuario(new VolleyCallBack() {
            @Override
            public void onSuccess(String result) {
                UsuarioAdulto usuario = SharedPreferencesHelper.getUsuarioAdulto(Herramientas.mainActivity);
                readNino(new VolleyCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        ArrayList<UsuarioNino> users = SharedPreferencesHelper.getUsuarios(Herramientas.mainActivity);
                        for(int i=0; i<users.size();i++) {
                            readActividad(new VolleyCallBack() {
                                @Override
                                public void onSuccess(String result) {
                                    progressBar.setVisibility(View.GONE);
                                }
                            },users.get(i).getIdServidor());
                        }
                    }
                }, usuario.getIdServidor());
            }
        },usuarioAdulto);



    }
    public void restaurarInformacion(UsuarioAdulto usuarioAdulto){
        this.progressBar.setVisibility(View.VISIBLE);
        foundUsuario((new VolleyCallBack() {
            @Override
            public void onSuccess(String result) {
                foundNino(new VolleyCallBack() {
                    @Override
                    public void onSuccess(String result) {
                        ArrayList<UsuarioNino> usuarios= SharedPreferencesHelper.getUsuarios(Herramientas.mainActivity);
                        for(int i=0;i<usuarios.size();i++) {
                            foundActividad(new VolleyCallBack() {
                                @Override
                                public void onSuccess(String result) {
                                    progressBar.setVisibility(View.GONE);
                                }
                            },usuarios.get(i).getIdServidor());
                        }
                    }
                },SharedPreferencesHelper.getUsuarioAdulto(Herramientas.mainActivity).getIdServidor());
            }
        }),usuarioAdulto);
    }



}