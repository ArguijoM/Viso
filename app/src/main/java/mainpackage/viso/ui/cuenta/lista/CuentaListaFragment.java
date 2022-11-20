package mainpackage.viso.ui.cuenta.lista;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import mainpackage.viso.R;
import mainpackage.viso.databinding.FragmentListaCuentasBinding;
import mainpackage.viso.herramientas.CuentaAdaptador;
import mainpackage.viso.herramientas.Herramientas;
import mainpackage.viso.herramientas.SharedPreferencesHelper;
import mainpackage.viso.herramientas.objetos.UsuarioNino;
import mainpackage.viso.ui.cuenta.CuentaFragment;
import mainpackage.viso.ui.cuenta.registro.nino.CuentaRegistroNinoFragment;


public class CuentaListaFragment extends Fragment {
    private FragmentListaCuentasBinding binding;
    private ListView lista;
    private FloatingActionButton btn_add;
    ArrayList<UsuarioNino> usuarios;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = mainpackage.viso.databinding.FragmentListaCuentasBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        lista = (ListView)root.findViewById(R.id.lista_usuarios);
        usuarios = SharedPreferencesHelper.getUsuarios();

        CuentaAdaptador adaptador = new CuentaAdaptador(getContext(),usuarios,R.layout.cuenta_item);
        lista.setAdapter(adaptador);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putInt("id",usuarios.get(position).getIdLocal());
                SharedPreferencesHelper.setUsuarioActual(Herramientas.mainActivity,usuarios.get(position));
                CuentaFragment fragment = new CuentaFragment();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_main,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        btn_add = (FloatingActionButton)root.findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CuentaRegistroNinoFragment fragment = new CuentaRegistroNinoFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_main,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return root;
    }
}
