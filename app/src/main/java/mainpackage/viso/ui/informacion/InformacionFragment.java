package mainpackage.viso.ui.informacion;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import mainpackage.viso.R;
import mainpackage.viso.databinding.FragmentInformacionBinding;
import mainpackage.viso.ui.informacion.informativa.Informativa;

public class InformacionFragment extends Fragment implements View.OnClickListener {

    private FragmentInformacionBinding binding;
    LinearLayout info_item01,info_item02,info_item03,info_item04,info_item05;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInformacionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        (info_item01 = (LinearLayout) root.findViewById(R.id.info_item01)).setOnClickListener(this);
        (info_item02 = (LinearLayout) root.findViewById(R.id.info_item02)).setOnClickListener(this);
        (info_item03 = (LinearLayout) root.findViewById(R.id.info_item03)).setOnClickListener(this);
        (info_item04 = (LinearLayout) root.findViewById(R.id.info_item04)).setOnClickListener(this);
        (info_item05 = (LinearLayout) root.findViewById(R.id.info_item05)).setOnClickListener(this);



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.info_item01:
                Intent intent = new Intent(getActivity(), Informativa.class);
                intent.putExtra("id", 1);
                startActivity(intent);
                break;
            case R.id.info_item02:

                Intent intent2 = new Intent(getActivity(), Informativa.class);
                intent2.putExtra("id", 2);
                startActivity(intent2);
                break;
            case R.id.info_item03:
                Intent intent3 = new Intent(getActivity(), Informativa.class);
                intent3.putExtra("id", 3);
                startActivity(intent3);
                break;
            case R.id.info_item04:
                Intent intent4 = new Intent(getActivity(), Informativa.class);
                intent4.putExtra("id", 4);
                startActivity(intent4);
                break;
            case R.id.info_item05:
                Intent intent5 = new Intent(getActivity(), Informativa.class);
                intent5.putExtra("id", 5);
                startActivity(intent5);
                break;
        }
    }
}