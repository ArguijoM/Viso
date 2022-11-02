package mainpackage.viso.ui.actividad.instruccion;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import mainpackage.viso.R;
import mainpackage.viso.databinding.FragmentInformacionBinding;
import mainpackage.viso.databinding.FragmentInstruccionBinding;

public class InstruccionFragment extends Fragment {
    private TextView text;
    private FragmentInstruccionBinding binding;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInstruccionBinding.inflate(inflater,container, false);
        View root = binding.getRoot();

        Animation animation= AnimationUtils.loadAnimation(getContext(),R.anim.bounce);
        text = root.findViewById(R.id.instruccion_text);
        text.startAnimation(animation);

        return root;
    }

}