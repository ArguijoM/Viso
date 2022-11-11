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
import mainpackage.viso.databinding.FragmentInstruccion2Binding;
import mainpackage.viso.databinding.FragmentInstruccionBinding;

public class InstruccionFragment2 extends Fragment {
    private FragmentInstruccion2Binding binding;
    private TextView text;
    private ImageView phone,phone2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInstruccion2Binding.inflate(inflater,container, false);
        View root = binding.getRoot();

        Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.bounce);
        Animation animation2= AnimationUtils.loadAnimation(getContext(),R.anim.fragment_close_enter);
        text = root.findViewById(R.id.instruccion_text);
        phone = root.findViewById(R.id.ic_phone_hor);
        phone2 = root.findViewById(R.id.ic_phone_ver);
        text.startAnimation(animation);
        phone.startAnimation(animation2);
        phone2.startAnimation(animation2);
        return root;
    }
}