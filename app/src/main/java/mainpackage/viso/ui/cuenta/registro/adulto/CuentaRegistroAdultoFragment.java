package mainpackage.viso.ui.cuenta.registro.adulto;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

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

import java.util.HashMap;
import java.util.Map;

import mainpackage.viso.R;
import mainpackage.viso.databinding.FragmentRegistroLoginBinding;
import mainpackage.viso.herramientas.Herramientas;
import mainpackage.viso.herramientas.SharedPreferencesHelper;
import mainpackage.viso.herramientas.VolleyCallBack;
import mainpackage.viso.herramientas.objetos.UsuarioAdulto;
import mainpackage.viso.ui.cuenta.registro.nino.CuentaRegistroNinoFragment;

public class CuentaRegistroAdultoFragment extends Fragment {
    private EditText email,contrasena,contrasena2;
    private Button btn_login;
    private TextView cuenta_exist;
    private ProgressBar progressBar;
    private Context context;
    private FragmentRegistroLoginBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegistroLoginBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        this.context = getContext();
        email = (EditText) root.findViewById(R.id.email);
        contrasena = (EditText) root.findViewById(R.id.contrasena);
        contrasena2 = (EditText) root.findViewById(R.id.contrasena2);
        btn_login = (Button) root.findViewById(R.id.btn_login);
        cuenta_exist = (TextView)root.findViewById(R.id.cuenta_exist);
        progressBar = (ProgressBar)root.findViewById(R.id.progress_bar_login);
        progressBar.setVisibility(View.GONE);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Contrasena 1",contrasena.getText().toString());
                Log.i("Contrasena 2",contrasena2.getText().toString());
                if(!email.getText().toString().equals("") && !contrasena.getText().toString().equals("") &&!contrasena2.getText().toString().equals("")) {
                    if (contrasena.getText().toString().equals(contrasena2.getText().toString())) {
                        progressBar.setVisibility(View.VISIBLE);
                        verifyUsuario(new VolleyCallBack() {
                            @Override
                            public void onSuccess(String result) {
                                UsuarioAdulto usuarioAdulto = SharedPreferencesHelper.getUsuarioAdulto(Herramientas.mainActivity);
                                if(usuarioAdulto!=null){
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(context, "Ya existe un usuario con este correo", Toast.LENGTH_SHORT).show();
                                    SharedPreferencesHelper.deteteAllPreference(Herramientas.mainActivity);
                                }else{
                                    progressBar.setVisibility(View.GONE);
                                    Bundle bundle = new Bundle();
                                    bundle.putString("email", String.valueOf(email.getText()));
                                    bundle.putString("contrasena", String.valueOf(contrasena.getText()));
                                    CuentaRegistroAdultoFragment2 fragment = new CuentaRegistroAdultoFragment2();                                    fragment.setArguments(bundle);
                                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                    fragmentTransaction.replace(R.id.nav_host_fragment_content_main, fragment);
                                    fragmentTransaction.addToBackStack(null);
                                    fragmentTransaction.commit();
                                }
                            }
                        },email.getText().toString());


                    } else {
                        //Alert Dialog
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("Las contraseñas no coinciden")
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                    }
                                });
                        AlertDialog alerta = builder.create();
                        alerta.setTitle("Alerta");
                        alerta.show();
                        contrasena.setText("");
                        contrasena2.setText("");
                    }
                }else{
                    Toast.makeText(getContext(),"Ingrese la información solicitada",Toast.LENGTH_LONG).show();
                }

            }
        });
        cuenta_exist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Login.class);
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public void verifyUsuario(final VolleyCallBack callBack, String emailUser){
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest request = new StringRequest(
                Request.Method.POST,
                getString(R.string.VERIFY_USUARIO),new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Respuesta de servidor",response);
                try {
                    Log.i("Respuesta de servidor",response);
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray json = jsonObject.getJSONArray("estado");
                    JSONObject obj = json.getJSONObject(0);
                    if(obj.getInt("estado")==1){
                        SharedPreferencesHelper.setUsuarioAdulto(Herramientas.mainActivity,new UsuarioAdulto(1,emailUser,""));
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
                parametros.put("email", emailUser);
                return parametros;
            }
        };
        requestQueue.add(request);
    }

}
