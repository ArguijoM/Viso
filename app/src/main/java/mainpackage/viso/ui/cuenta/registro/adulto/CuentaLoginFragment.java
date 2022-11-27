package mainpackage.viso.ui.cuenta.registro.adulto;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import mainpackage.viso.R;
import mainpackage.viso.databinding.FragmentRegistroAdultoBinding;
import mainpackage.viso.herramientas.database.DatabaseHelper;
import mainpackage.viso.herramientas.Herramientas;
import mainpackage.viso.herramientas.SharedPreferencesHelper;
import mainpackage.viso.herramientas.database.VolleyCallBack;
import mainpackage.viso.herramientas.objetos.UsuarioAdulto;
import mainpackage.viso.herramientas.objetos.UsuarioNino;
import mainpackage.viso.herramientas.splashscreen.Bienvenido;

public class CuentaLoginFragment extends Fragment {

    private FragmentRegistroAdultoBinding binding;
    private Button btn_siguiente;
    private ProgressBar progressBar;
    private Context context;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRegistroAdultoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        getActivity().onBackPressed();

        progressBar = (ProgressBar)root.findViewById(R.id.login_progressbar);
        progressBar.setVisibility(View.GONE);
        btn_siguiente= (Button)root.findViewById(R.id.btn_siguiente);
        context = getContext();
        btn_siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo = ((EditText)root.findViewById(R.id.correo_adulto)).getText().toString();
                String contrasena = ((EditText)root.findViewById(R.id.contrasena_adulto)).getText().toString();
                if(!correo.equals("") && !contrasena.equals("")) {
                    UsuarioAdulto usuarioAdulto = new UsuarioAdulto(1,correo,contrasena);
                    DatabaseHelper db = new DatabaseHelper(getContext(),progressBar);
                    progressBar.setVisibility(View.VISIBLE);
                    db.foundUsuario((new VolleyCallBack() {
                        @Override
                        public void onSuccess(String result) {
                            if((SharedPreferencesHelper.getUsuarioAdulto(Herramientas.mainActivity))!=null) {
                                db.foundNino(new VolleyCallBack() {
                                    @Override
                                    public void onSuccess(String result) {
                                        ArrayList<UsuarioNino> usuarios = SharedPreferencesHelper.getUsuarios();
                                        for (int i = 0; i < usuarios.size(); i++) {
                                            db.foundActividad(new VolleyCallBack() {
                                                @Override
                                                public void onSuccess(String result) {
                                                    progressBar.setVisibility(View.GONE);
                                                    Intent intent = new Intent(context, Bienvenido.class);
                                                    startActivity(intent);
                                                }
                                            }, usuarios.get(i).getIdServidor());
                                        }
                                    }
                                }, SharedPreferencesHelper.getUsuarioAdulto(Herramientas.mainActivity).getIdServidor());
                            }else{
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(getContext(), "Correo y/o contraseña incorrectos", Toast.LENGTH_LONG).show();
                            }
                        }
                    }),usuarioAdulto);
                }else{
                    Toast.makeText(getContext(),"Ingrese la información solicitada",Toast.LENGTH_LONG).show();
                }
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}
