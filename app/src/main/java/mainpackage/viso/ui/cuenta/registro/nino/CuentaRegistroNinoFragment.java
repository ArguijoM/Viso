package mainpackage.viso.ui.cuenta.registro.nino;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import mainpackage.viso.R;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

import mainpackage.viso.databinding.FragmentRegistroNinoBinding;
import mainpackage.viso.herramientas.database.DatabaseHelper;
import mainpackage.viso.herramientas.Herramientas;
import mainpackage.viso.herramientas.SharedPreferencesHelper;
import mainpackage.viso.herramientas.database.VolleyCallBack;
import mainpackage.viso.herramientas.objetos.UsuarioAdulto;
import mainpackage.viso.herramientas.objetos.UsuarioNino;
import mainpackage.viso.herramientas.splashscreen.Bienvenido;
import mainpackage.viso.ui.inicio.InicioFragment;

public class CuentaRegistroNinoFragment extends Fragment implements View.OnClickListener {
    private FragmentRegistroNinoBinding binding;
    private Button btn_siguiente;
    private ImageView imageViewBoy, imageViewGirl;
    private LinearLayout layoutboy,layoutgirl;
    private ProgressBar progressBar;
    private ArrayList<UsuarioNino> usuarios;
    private int auxboy=0,auxgirl=0;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentRegistroNinoBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        (layoutboy= (LinearLayout) root.findViewById(R.id.layout_boy)).setBackground(getResources().getDrawable(R.drawable.customborder2));
        (layoutgirl = (LinearLayout) root.findViewById(R.id.layout_girl)).setBackground(getResources().getDrawable(R.drawable.customborder2));
        (imageViewBoy = (ImageView)root.findViewById(R.id.user_image_boy)).setOnClickListener(this);
        (imageViewGirl=(ImageView)root.findViewById(R.id.user_image_girl)).setOnClickListener(this);
        progressBar = (ProgressBar)root.findViewById(R.id.registro_nino_progessbar);
        progressBar.setVisibility(View.GONE);
        this.usuarios = SharedPreferencesHelper.getUsuarios();
        if(usuarios!=null) {
            String uriboy = "@drawable/ic_boy"+boyCount();
            int imageResourceboy = getResources().getIdentifier(uriboy, null, getActivity().getPackageName());
            Drawable drawableboy = getResources().getDrawable(imageResourceboy);
            imageViewBoy.setImageDrawable(drawableboy);
            String urigirl = "@drawable/ic_girl"+girlCount();
            int imageResourcegirl = getResources().getIdentifier(urigirl, null, getActivity().getPackageName());
            Drawable drawablegirl = getResources().getDrawable(imageResourcegirl);
            imageViewGirl.setImageDrawable(drawablegirl);
        }else{
            (imageViewBoy = (ImageView)root.findViewById(R.id.user_image_boy)).setImageResource(R.drawable.ic_boy1);
            (imageViewGirl=(ImageView)root.findViewById(R.id.user_image_girl)).setImageResource(R.drawable.ic_girl1);
        }
        btn_siguiente= (Button)root.findViewById(R.id.btn_next);
        btn_siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(auxboy==0 && auxgirl==0) {
                    Toast.makeText(getContext(),"Eliga un ícono",Toast.LENGTH_LONG).show();
                }else{
                    String nombreNino = ((EditText) root.findViewById(R.id.nombre_nino)).getText().toString();
                    String apellidoNino = ((EditText) root.findViewById(R.id.apellido_nino)).getText().toString();
                    String edad = ((EditText) root.findViewById(R.id.edad_nino)).getText().toString();
                    if(!nombreNino.equals("") && !apellidoNino.equals("") && !edad.equals("")) {
                        UsuarioNino usuarioNino = new UsuarioNino(nombreNino, apellidoNino, Integer.parseInt(String.valueOf(edad)));
                        if(auxboy==1){
                            usuarioNino.setProfile("H Y "+boyCount());
                        }else{
                            usuarioNino.setProfile("M Y "+girlCount());
                        }
                        UsuarioAdulto usuarioAdulto = SharedPreferencesHelper.getUsuarioAdulto(Herramientas.mainActivity);
                        usuarioNino.setIdAdulto(usuarioAdulto.getIdServidor());

                        SharedPreferencesHelper.setUsuario(usuarioNino);
                        SharedPreferencesHelper.setUsuarioActual(Herramientas.mainActivity,usuarioNino);
                        DatabaseHelper db = new DatabaseHelper(getContext(),progressBar);
                        db.addNino(new VolleyCallBack() {
                            @Override
                            public void onSuccess(String result) {
                                db.readNino(new VolleyCallBack() {
                                    @Override
                                    public void onSuccess(String result) {
                                        if((SharedPreferencesHelper.getUsuarioActual(Herramientas.mainActivity)).getIdServidor()!=0) {
                                            progressBar.setVisibility(View.GONE);
                                            Intent intent = new Intent(getContext(), Bienvenido.class);
                                            startActivity(intent);
                                        }
                                    }
                                },SharedPreferencesHelper.getUsuarioAdulto(Herramientas.mainActivity).getIdServidor());

                            }
                        },usuarioNino);

                    }else {
                        Toast.makeText(getContext(),"Ingrese la información solicitada",Toast.LENGTH_LONG).show();
                    }
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
    public void toMain(){
        InicioFragment fragment = new InicioFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment_content_main,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_image_boy:
                this.layoutboy.setBackgroundResource(R.drawable.customborder3);
                this.auxboy=1;
                this.layoutgirl.setBackgroundResource(R.drawable.customborder2);
                break;
            case R.id.user_image_girl:
                this.layoutboy.setBackgroundResource(R.drawable.customborder2);
                this.auxgirl=1;
                this.layoutgirl.setBackgroundResource(R.drawable.customborder3);
                break;
        }
    }
    public int boyCount(){
        int boy=1;
        if(usuarios==null){
            return 1;
        }else {
            for (int i = 0; i < usuarios.size(); i++) {
                String aux = usuarios.get(i).getProfile();
                String[] array = aux.split(" Y ");
                if (array[0].equals("H")) {
                    boy++;
                }
            }
        }
        return boy;
    }
    public int girlCount(){
        int girl=1;
        if(usuarios==null){
            return 1;
        }else {
            for (int i = 0; i < usuarios.size(); i++) {
                String aux = usuarios.get(i).getProfile();
                String[] array = aux.split(" Y ");
                if (array[0].equals("M")) {
                    girl++;
                }
            }
        }
        return girl;
    }
}
