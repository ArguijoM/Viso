package mainpackage.viso.ui.actividad.instruccion;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import mainpackage.viso.R;
import mainpackage.viso.databinding.FragmentInstruccion3Binding;
import mainpackage.viso.databinding.FragmentInstruccion4Binding;

public class InstruccionFragment4 extends Fragment {

    private FragmentInstruccion4Binding binding;
    private TextView text,text2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInstruccion4Binding.inflate(inflater,container, false);
        View root = binding.getRoot();

        Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.bounce);
        text = root.findViewById(R.id.instruccion_text);
        text2 = root.findViewById(R.id.instruccion_text2);
        text.startAnimation(animation);
        text2.startAnimation(animation);

        return root;
    }
}