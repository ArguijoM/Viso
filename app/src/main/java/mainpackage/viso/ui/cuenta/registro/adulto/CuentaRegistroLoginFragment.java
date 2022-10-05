package mainpackage.viso.ui.cuenta.registro.adulto;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import java.util.ArrayList;

import mainpackage.viso.R;
import mainpackage.viso.databinding.FragmentLoginBinding;
import mainpackage.viso.databinding.FragmentRegistroLoginBinding;
import mainpackage.viso.herramientas.DatabaseHelper;
import mainpackage.viso.herramientas.Herramientas;
import mainpackage.viso.herramientas.SQLiteHelper;
import mainpackage.viso.herramientas.SharedPreferencesHelper;
import mainpackage.viso.herramientas.VolleyCallBack;
import mainpackage.viso.herramientas.objetos.UsuarioAdulto;
import mainpackage.viso.herramientas.objetos.UsuarioNino;
import mainpackage.viso.ui.cuenta.registro.nino.CuentaRegistroNinoFragment;
import mainpackage.viso.ui.inicio.InicioFragment;

public class CuentaRegistroLoginFragment extends Fragment {
    private CuentaRegistroLoginViewModel cuentaLoginViewModel;
    private EditText email,contrasena,contrasena2;
    private Button btn_login;
    private TextView cuenta_exist;
    private ProgressBar progressBar;
    private FragmentRegistroLoginBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cuentaLoginViewModel=
                new ViewModelProvider(this).get(CuentaRegistroLoginViewModel.class);

        binding = FragmentRegistroLoginBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


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
                        Bundle bundle = new Bundle();
                        bundle.putString("email", String.valueOf(email.getText()));
                        bundle.putString("contrasena", String.valueOf(contrasena.getText()));
                        CuentaRegistroAdultoFragment fragment = new CuentaRegistroAdultoFragment();
                        fragment.setArguments(bundle);
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.nav_host_fragment_content_main, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
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
                if(!email.getText().toString().equals("") && !contrasena.getText().toString().equals("") &&!contrasena2.getText().toString().equals("")) {
                    if (contrasena.getText().toString().equals(contrasena2.getText().toString())) {
                        cuentaLoginViewModel.getUsuarioAdulto().setEmail(String.valueOf(email.getText()));
                        cuentaLoginViewModel.getUsuarioAdulto().setContrasena(String.valueOf(contrasena.getText()));
                        DatabaseHelper db = new DatabaseHelper(getContext(),progressBar);
                        progressBar.setVisibility(View.VISIBLE);
                        db.foundUsuario((new VolleyCallBack() {
                            @Override
                            public void onSuccess(String result) {
                                db.foundNino(new VolleyCallBack() {
                                    @Override
                                    public void onSuccess(String result) {
                                        ArrayList<UsuarioNino> usuarios= SharedPreferencesHelper.getUsuarios(Herramientas.mainActivity);
                                        for(int i=0;i<usuarios.size();i++) {
                                            db.foundActividad(new VolleyCallBack() {
                                                @Override
                                                public void onSuccess(String result) {
                                                    if(SharedPreferencesHelper.getUsuarioActual(Herramientas.mainActivity)==null) {
                                                        Toast.makeText(getContext(),"No existe el usuario",Toast.LENGTH_LONG).show();
                                                    }else{
                                                        progressBar.setVisibility(View.GONE);
                                                        InicioFragment fragment = new InicioFragment();
                                                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                        fragmentTransaction.replace(R.id.nav_host_fragment_content_main, fragment);
                                                        fragmentTransaction.addToBackStack(null);
                                                        fragmentTransaction.commit();
                                                    }
                                                }
                                            },usuarios.get(i).getIdServidor());
                                        }
                                    }
                                },SharedPreferencesHelper.getUsuarioAdulto(Herramientas.mainActivity).getIdServidor());
                            }
                        }),cuentaLoginViewModel.getUsuarioAdulto());
                    }else{
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

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
