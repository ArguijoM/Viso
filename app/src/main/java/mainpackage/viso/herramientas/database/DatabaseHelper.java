package mainpackage.viso.herramientas.database;


import android.content.Context;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import mainpackage.viso.R;
import mainpackage.viso.herramientas.Herramientas;
import mainpackage.viso.herramientas.SharedPreferencesHelper;
import mainpackage.viso.herramientas.objetos.Actividad;
import mainpackage.viso.herramientas.objetos.UsuarioAdulto;
import mainpackage.viso.herramientas.objetos.UsuarioNino;

public class DatabaseHelper {
    private RequestQueue requestQueue;
    private SQLiteHelper myDB;
    private ProgressBar progressBar;
    private Context context;
    public DatabaseHelper(Context context, ProgressBar progressBar){
        this.progressBar = progressBar;
        this.requestQueue = Volley.newRequestQueue(context);
        this.myDB = new SQLiteHelper(Herramientas.mainActivity.getBaseContext());
        this.context = context;
    }

    public void addUsuario(UsuarioAdulto user, final VolleyCallBack volleyCallBack){
        progressBar.setVisibility(View.VISIBLE);
        StringRequest request = new StringRequest(
                Request.Method.POST,
                context.getString(R.string.ADD_USUARIO),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(!response.isEmpty()){
                            try {
                                Log.i("Respuest de servidor AU",response);
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray json = jsonObject.getJSONArray("estado");
                                JSONObject obj = json.getJSONObject(0);
                                Log.i("Mensaje de servidor AU",obj.getString("mensaje"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else{
                            Log.i("Respuest de servidor AU","No hay respuesta");
                        }
                        volleyCallBack.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("ERROR: Respuesta AU: ",error.toString());
                        Toast.makeText(Herramientas.mainActivity,"Ocurri?? un error, revise su conexi??n de internet",Toast.LENGTH_LONG).show();
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
        progressBar.setVisibility(View.VISIBLE);
        StringRequest request = new StringRequest(
                Request.Method.POST,
                context.getString(R.string.ADD_NINO),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(!response.isEmpty()){
                            try {
                                Log.i("Respuest de servidor AN",response);
                                JSONObject jsonObject = new JSONObject(response);
                                JSONArray json = jsonObject.getJSONArray("estado");
                                JSONObject obj = json.getJSONObject(0);
                                Log.i("Mensaje de servidor",obj.getString("mensaje"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else{
                            Log.i("Respuest de servidor AD","No hay respuesta");
                        }

                        volleyCallBack.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("ERROR: Respuesta AD: ",error.toString());
                        Toast.makeText(Herramientas.mainActivity,"Ocurri?? un error, revise su conexi??n de internet",Toast.LENGTH_LONG).show();
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
    public void addActividad(VolleyCallBack callBack,Actividad act,UsuarioNino nino){
        StringRequest request = new StringRequest(
                Request.Method.POST,
                context.getString(R.string.ADD_ACTIVIDAD),
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
                                }, nino);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else{
                            Log.i("Respuesta de servidor","No hay respuesta"+response.toString());
                        }
                        callBack.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callBack.onSuccess(error.toString());
                        Log.i("ERROR: Respuesta AA: ",error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parametros = new HashMap<String,String>();
                parametros.put("modelo",act.getImagen());
                parametros.put("local_id", String.valueOf(act.getIdLocal()));
                parametros.put("fecha", act.getFecha());
                parametros.put("calificacion", String.valueOf(act.getCalificacion()));
                parametros.put("nino_id", String.valueOf(nino.getIdServidor()));
                parametros.put("nino_id_local", String.valueOf(nino.getIdLocal()));
                return parametros;
            }
        };
        requestQueue.add(request);
    }


    public void readUsuario(final VolleyCallBack volleyCallBack,UsuarioAdulto user){
        StringRequest request = new StringRequest(
                Request.Method.POST,
                context.getString(R.string.READ_USUARIO),new Response.Listener<String>() {
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
                parametros.put("contrasena", user.getContrasena());
                return parametros;
            }
        };
        requestQueue.add(request);
    }

    public void foundUsuario(final VolleyCallBack callBack,UsuarioAdulto user){
        StringRequest request = new StringRequest(
                Request.Method.POST,
                context.getString(R.string.READ_USUARIO),new Response.Listener<String>() {
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

                    }else{
                        Log.i("Mensaje de servidor",obj.getString("mensaje"));
                    }
                    callBack.onSuccess(response);


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
                parametros.put("contrasena", user.getContrasena());
                return parametros;
            }
        };
        requestQueue.add(request);
    }

    public  void readNino(final VolleyCallBack volleyCallBack,int idUsuario){
        StringRequest request = new StringRequest(
                Request.Method.POST,
                context.getString(R.string.READ_NINO),new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Respuest de servidor RN",response);
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
                            usuarios.add(new UsuarioNino(id,nombre,apellido,edad,usuario_id,perfil));
                        }
                        SharedPreferencesHelper.updateUsuario(usuarios);
                        SharedPreferencesHelper.setUsuarioActual(Herramientas.mainActivity,usuarios.get((usuarios.size())-1));
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
                        Log.i("Error de servidor RN",error.toString());
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
                context.getString(R.string.READ_NINO),new Response.Listener<String>() {
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
                            String perfil = object.getString("perfil");
                            UsuarioNino aux = new UsuarioNino(id,nombre,apellido,edad,usuario_id,perfil);
                            usuarios.add(aux);
                        }
                        SharedPreferencesHelper.setUsuarios(usuarios);
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

    public  void readActividad(final VolleyCallBack volleyCallBack,UsuarioNino nino){
        StringRequest request = new StringRequest(
                Request.Method.POST,
                context.getString(R.string.READ_ACTIVIDAD),new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Respuest de servidor RA",response);
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
                            int nino_id_local = object.getInt("nino_id_local");
                            actividades.add(new Actividad(local_id,id, nino_id,nino_id_local,modelo,calificacion,fecha));
                        }
                        ArrayList<Actividad> acts = SharedPreferencesHelper.getActividades(nino.getIdLocal());
                        for(int i=0;i<actividades.size();i++){
                            for(int j=0;j<acts.size();j++) {
                                if (actividades.get(i).getIdLocal()==acts.get(j).getIdLocal()){
                                    acts.get(j).setIdServidor(actividades.get(i).getIdServidor());
                                }
                            }
                        }
                        SharedPreferencesHelper.updateActividades(nino.getIdLocal(),acts);
                        ArrayList<Actividad> activities = SharedPreferencesHelper.getActividades(nino.getIdLocal());
                        for(int x=0;x<activities.size();x++){
                            if(activities.get(x).getIdServidor()==0){
                                addActividad(new VolleyCallBack() {
                                    @Override
                                    public void onSuccess(String result) {

                                    }
                                }, activities.get(x), nino);
                            }
                        }
                    }else{
                        ArrayList<Actividad> activities = SharedPreferencesHelper.getActividades(nino.getIdLocal());
                        for(int k=0;k<activities.size();k++) {
                            int finalK = k;
                            addActividad(new VolleyCallBack() {
                                @Override
                                public void onSuccess(String result) {
                                    if(finalK ==activities.size()-1){
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(context,"Copia de seguridad generada",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            },activities.get(k), nino);
                        }
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
                        volleyCallBack.onSuccess(error.toString());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("id", String.valueOf(nino.getIdServidor()));
                return parametros;
            }
        };
        requestQueue.add(request);
    }
    public  void foundActividad(final VolleyCallBack callBack,int idNino){
        //Log.i("BUSCANDO ACTIVIDAD: ",""+idNino);
        StringRequest request = new StringRequest(
                Request.Method.POST,
                context.getString(R.string.READ_ACTIVIDAD),new Response.Listener<String>() {
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
                            int nino_id_local = object.getInt("nino_id_local");
                            actividades.add(new Actividad(local_id,id, nino_id,nino_id_local,modelo,calificacion,fecha));
                        }
                        SharedPreferencesHelper.addActividades(actividades);
                    }else{
                        //Log.i("Mensaje de servidor ", obj.getString("mensaje"));
                    }
                    callBack.onSuccess(response);

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
                parametros.put("id", String.valueOf(idNino));
                return parametros;
            }
        };
        requestQueue.add(request);
    }
}