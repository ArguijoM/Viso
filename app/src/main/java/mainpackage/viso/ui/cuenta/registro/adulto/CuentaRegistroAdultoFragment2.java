package mainpackage.viso.ui.cuenta.registro.adulto;

import android.os.Bundle;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import mainpackage.viso.R;
import mainpackage.viso.databinding.FragmentCuentaRegistroAdulto2Binding;
import mainpackage.viso.databinding.FragmentRegistroAdultoBinding;
import mainpackage.viso.herramientas.DatabaseHelper;
import mainpackage.viso.herramientas.Herramientas;
import mainpackage.viso.herramientas.SharedPreferencesHelper;
import mainpackage.viso.herramientas.VolleyCallBack;
import mainpackage.viso.herramientas.objetos.UsuarioAdulto;
import mainpackage.viso.ui.cuenta.registro.nino.CuentaRegistroNinoFragment;

public class CuentaRegistroAdultoFragment2 extends Fragment {
    private FragmentCuentaRegistroAdulto2Binding binding;
    private Button btn_siguiente;
    private String email,contrasena;
    private ProgressBar progressbar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCuentaRegistroAdulto2Binding.inflate(inflater, container, false);
        View root = binding.getRoot();
        getActivity().onBackPressed();
        Bundle bundle = this.getArguments();
        if(bundle!=null){
            email = bundle.getString("email");
            contrasena = bundle.getString("contrasena");
        }

        progressbar = (ProgressBar)root.findViewById(R.id.registro_progessbar);
        progressbar.setVisibility(View.GONE);
        btn_siguiente= (Button)root.findViewById(R.id.btn_siguiente);
        btn_siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombreAdulto = ((EditText)root.findViewById(R.id.nombre_adulto)).getText().toString();
                String apellidoAdulto = ((EditText)root.findViewById(R.id.apellido_adulto)).getText().toString();
                if(!nombreAdulto.equals("") && !apellidoAdulto.equals("")) {
                    UsuarioAdulto usuarioAdulto = new UsuarioAdulto(nombreAdulto, apellidoAdulto);
                    usuarioAdulto.setEmail(email);
                    usuarioAdulto.setContrasena(contrasena);
                    SharedPreferencesHelper.setUsuarioAdulto(usuarioAdulto);
                    UsuarioAdulto usuarioAdulto2 = SharedPreferencesHelper.getUsuarioAdulto(usuarioAdulto.getNombre());
                    SharedPreferencesHelper.setUsuarioAdulto(Herramientas.mainActivity,usuarioAdulto2);
                    if (!SharedPreferencesHelper.getUsuarioAdulto(Herramientas.mainActivity).getEmail().equals("")) {
                        DatabaseHelper db = new DatabaseHelper(getContext(),progressbar);
                        db.addUsuario(usuarioAdulto, new VolleyCallBack() {
                            @Override
                            public void onSuccess(String result) {
                                db.readUsuario(new VolleyCallBack() {
                                    @Override
                                    public void onSuccess(String result) {
                                        if((SharedPreferencesHelper.getUsuarioAdulto(Herramientas.mainActivity)).getIdServidor()!=0){
                                            progressbar.setVisibility(View.GONE);
                                            CuentaRegistroNinoFragment fragment = new CuentaRegistroNinoFragment();
                                            fragment.setArguments(bundle);
                                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                            fragmentTransaction.replace(R.id.nav_host_fragment_content_main, fragment);
                                            fragmentTransaction.addToBackStack(null);
                                            fragmentTransaction.commit();
                                        }

                                    }
                                },usuarioAdulto);
                            }
                        });
                    }
                }else{
                    Toast.makeText(getContext(),"Ingrese la información solicitada",Toast.LENGTH_LONG).show();
                }
            }
        });

        return root;
    }
}