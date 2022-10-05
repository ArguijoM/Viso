package mainpackage.viso.ui.actividad.get;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
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
import mainpackage.viso.databinding.FragmentActividadShowBinding;
import mainpackage.viso.herramientas.Herramientas;
import mainpackage.viso.herramientas.objetos.Actividad;

public class ActividadShowFragment extends Fragment {
    private int id;
    private ImageView img_muestra,img_tomada;
    private FragmentActividadShowBinding binding;
    private ActividadShowViewModel actividadShowViewModel;
    private TextView textView;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        actividadShowViewModel = new ViewModelProvider(this).get(ActividadShowViewModel.class);
        binding = FragmentActividadShowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        ArrayList<Actividad> act = actividadShowViewModel.getActividades();
        Bundle bundle = this.getArguments();
        if(bundle!=null){
            this.id = bundle.getInt("id");
        }
        img_muestra = root.findViewById(R.id.img_muestra);
        img_tomada = root.findViewById(R.id.img_tomada);
        String uri = "@drawable/act_"+id;
        int imageResource = getResources().getIdentifier(uri, null, getActivity().getPackageName());
        Drawable res = getResources().getDrawable(imageResource);
        img_muestra.setImageDrawable(res);
        Log.i("ImagenString: ",actividadShowViewModel.getActividades().get(id-1).getImagen());
        img_tomada.setImageBitmap(actividadShowViewModel.getImagenActividad(id));
        textView = (TextView) root.findViewById(R.id.act_show_realizada);
        textView.setText(textView.getText()+" || "+act.get(id-1).getIdServidor());
        return root;
    }
}
