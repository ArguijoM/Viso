package mainpackage.viso.ui.actividad;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;

import mainpackage.viso.R;
import mainpackage.viso.databinding.FragmentActividadBinding;
import mainpackage.viso.herramientas.Herramientas;
import mainpackage.viso.herramientas.SharedPreferencesHelper;
import mainpackage.viso.herramientas.SoundsPlayer;
import mainpackage.viso.herramientas.objetos.Actividad;
import mainpackage.viso.herramientas.objetos.UsuarioNino;
import mainpackage.viso.ui.actividad.get.ActividadShow;
import mainpackage.viso.ui.actividad.instruccion.Instruccion;
import mainpackage.viso.ui.actividad.set.ActividadN;

public class ActividadFragment extends Fragment implements View.OnClickListener {

    private ActividadViewModel actividadViewModel;
    private FragmentActividadBinding binding;
    private ArrayList<Actividad> actividades;
    private SoundsPlayer sound;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        actividadViewModel = new ViewModelProvider(this).get(ActividadViewModel.class);

        binding = FragmentActividadBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        sound = new SoundsPlayer(getContext());
        int total_act=0;
        int x=0; int y=0;
        UsuarioNino usuarioActual = SharedPreferencesHelper.getUsuarioActual(Herramientas.mainActivity);
        actividades = SharedPreferencesHelper.getActividades(usuarioActual.getIdLocal());
        if(actividades==null) {
            setActividadStatus(root, 1, "Siguiente");
            for(x=2;x<=8;x++){
                setActividadStatus(root, x, "No realizado");
            }
        }else{
            total_act = actividades.size();
            for (x = 0; x <= total_act; x++) {
                setActividadStatus(root, x+1, "Realizado");
            }
            for(y=(actividades.size())+1;y<=8;y++){
                setActividadStatus(root, y, "No realizado");
            }
            if(x< Herramientas.TOTAL_ACT){
                setActividadStatus(root, x, "Siguiente");
            }
        }
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public void setActividadStatus(View root,int n,String text){
        Resources res = getResources();
        int idText = res.getIdentifier("act_"+n+"_status","id",getContext().getPackageName());
        int idImage = res.getIdentifier("act_"+n,"id",getContext().getPackageName());
        ((TextView)root.findViewById(idText)).setText(text);
        if(text.equals("Realizado")){
            ((ImageView)root.findViewById(idImage)).setImageResource(R.drawable.circle_done);
            ((ImageView)root.findViewById(idImage)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sound.playTapSound();
                    Intent intent = new Intent(getActivity(), ActividadShow.class);
                    intent.putExtra("id",n);
                    startActivity(intent);
                }
            });

        }else if(text.equals("No realizado")){
            ((ImageView)root.findViewById(idImage)).setImageResource(R.drawable.circle_init);
        }
        else if(text.equals("Siguiente")){
            ((ImageView)root.findViewById(idImage)).setImageResource(R.drawable.circle_next);
            ((ImageView)root.findViewById(idImage)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sound.playTapSound();
                    UsuarioNino usuarioActual = SharedPreferencesHelper.getUsuarioActual(Herramientas.mainActivity);
                    ArrayList<Actividad> actividades = SharedPreferencesHelper.getActividades(usuarioActual.getIdLocal());
                    Log.i("ACTIVIDADES SIZE"," "+actividades.size());
                    if(actividades.size()==0){
                        Intent intent = new Intent(getActivity(), Instruccion.class);
                        intent.putExtra("id",n);
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(getActivity(), ActividadN.class);
                        intent.putExtra("id", n);
                        startActivity(intent);
                    }


                    /*Bundle bundle = new Bundle();
                    bundle.putInt("id",n);
                    ActividadNFragment fragment = new ActividadNFragment();
                    fragment.setArguments(bundle);
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.nav_host_fragment_content_main,fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();*/
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        //Intent intent = new Intent(this,Informativa.class);
        //intent.putExtra("idPiloto",pilotos.get(position).getId());
        //intent.putExtra("idAuto",pilotos.get(position).getAuto());
        //startActivity(intent);
    }
}