package mainpackage.viso.ui.cuenta.login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import mainpackage.viso.R;
import mainpackage.viso.databinding.FragmentInformacionBinding;
import mainpackage.viso.databinding.FragmentLoginBinding;
import mainpackage.viso.herramientas.SharedPreferencesHelper;
import mainpackage.viso.herramientas.objetos.UsuarioAdulto;
import mainpackage.viso.ui.cuenta.registro.nino.CuentaRegistroNinoFragment;
import mainpackage.viso.ui.informacion.InformacionViewModel;

public class CuentaLoginFragment extends Fragment {
    private CuentaLoginViewModel  cuentaLoginViewModel;
    private EditText email,contrasena;
    private Button btn_login;
    private FragmentLoginBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cuentaLoginViewModel=
                new ViewModelProvider(this).get(CuentaLoginViewModel.class);

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        email = (EditText) root.findViewById(R.id.email);
        contrasena = (EditText) root.findViewById(R.id.contrasena);
        btn_login = (Button) root.findViewById(R.id.btn_login_aceptar);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



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
