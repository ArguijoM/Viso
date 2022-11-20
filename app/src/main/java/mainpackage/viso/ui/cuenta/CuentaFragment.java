package mainpackage.viso.ui.cuenta;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import mainpackage.viso.R;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import mainpackage.viso.databinding.FragmentCuentaBinding;
import mainpackage.viso.herramientas.Herramientas;
import mainpackage.viso.herramientas.SharedPreferencesHelper;
import mainpackage.viso.herramientas.objetos.Actividad;
import mainpackage.viso.herramientas.objetos.UsuarioAdulto;
import mainpackage.viso.herramientas.objetos.UsuarioNino;
import mainpackage.viso.ui.cuenta.lista.CuentaListaFragment;

public class CuentaFragment extends Fragment {

    private FragmentCuentaBinding binding;
    private Button btn_cambiar_usuario;
    private UsuarioNino usuarioActual;
    private UsuarioAdulto usuarioAdulto;
    private ArrayList<Actividad> actividades;
    private int id;
    private ImageView profile;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCuentaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Bundle bundle = this.getArguments();
        if(bundle!=null){
            id = bundle.getInt("id");
        }
        usuarioActual = SharedPreferencesHelper.getUsuarioActual(Herramientas.mainActivity);
        actividades =SharedPreferencesHelper.getActividades(usuarioActual.getIdLocal());
        usuarioAdulto = SharedPreferencesHelper.getUsuarioAdulto(Herramientas.mainActivity);
        profile = (ImageView)root.findViewById(R.id.img_profile);
        int realizadas=0, faltantes=0;
        if(actividades==null){
            realizadas = 0;faltantes=Herramientas.TOTAL_ACT;
        }else {
            realizadas =actividades.size();faltantes=Herramientas.TOTAL_ACT-realizadas;
            String[] aux = (usuarioActual.getProfile()).split(" Y ");
            if(aux[0].equals("H")){
                String uriboy = "@drawable/ic_boy"+aux[1];
                int imageResourceboy =getResources().getIdentifier(uriboy, null, getActivity().getPackageName());
                Drawable drawableboy = getResources().getDrawable(imageResourceboy);
                profile.setImageDrawable(drawableboy);
            }else{
                String urigirl = "@drawable/ic_girl"+aux[1];
                int imageResourcegirl= getResources().getIdentifier(urigirl, null, getActivity().getPackageName());
                Drawable drawablegirl = getResources().getDrawable(imageResourcegirl);
                profile.setImageDrawable(drawablegirl);
            }
        }
        ((TextView)root.findViewById(R.id.nombre_adulto)).setText(usuarioAdulto.getNombre()+" "+usuarioAdulto.getApellido());
        ((TextView)root.findViewById(R.id.correo_adulto)).setText(usuarioAdulto.getEmail());

        ((TextView)root.findViewById(R.id.nombre)).setText("Nombre: "+ usuarioActual.getNombre());
        ((TextView)root.findViewById(R.id.apellido)).setText("Apellido: "+usuarioActual.getApellido());
        ((TextView)root.findViewById(R.id.edad)).setText("Edad: "+usuarioActual.getEdad());
        ((TextView)root.findViewById(R.id.act_realizadas)).setText(""+realizadas);
        ((TextView)root.findViewById(R.id.act_faltantes)).setText(""+faltantes);
        btn_cambiar_usuario = (Button)root.findViewById(R.id.btn_cambiar_usuario);
        btn_cambiar_usuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsuarioNino usuarioActual = SharedPreferencesHelper.getUsuarioActual(getActivity());
                SharedPreferencesHelper.updateUsuariobyId(usuarioActual);
                CuentaListaFragment fragment = new CuentaListaFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_main,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
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