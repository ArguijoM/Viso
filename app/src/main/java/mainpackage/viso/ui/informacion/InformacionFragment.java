package mainpackage.viso.ui.informacion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;

import mainpackage.viso.R;
import mainpackage.viso.databinding.FragmentInformacionBinding;
import mainpackage.viso.databinding.FragmentInformacionBinding;
import mainpackage.viso.ui.actividad.set.ActividadNFragment;
import mainpackage.viso.ui.informacion.informativa.InformativaFragment;

public class InformacionFragment extends Fragment implements View.OnClickListener {

    private InformacionViewModel slideshowViewModel;
    private FragmentInformacionBinding binding;
    LinearLayout info_item01,info_item02,info_item03,info_item04;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel = new ViewModelProvider(this).get(InformacionViewModel.class);

        binding = FragmentInformacionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        (info_item01 = (LinearLayout) root.findViewById(R.id.info_item01)).setOnClickListener(this);
        (info_item02 = (LinearLayout) root.findViewById(R.id.info_item02)).setOnClickListener(this);
        (info_item03 = (LinearLayout) root.findViewById(R.id.info_item03)).setOnClickListener(this);
        (info_item04 = (LinearLayout) root.findViewById(R.id.info_item04)).setOnClickListener(this);


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
                Bundle bundle = new Bundle();
                bundle.putInt("id",1);
                InformativaFragment fragment = new InformativaFragment();
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_main,fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
            case R.id.info_item02:
                Bundle bundle2 = new Bundle();
                bundle2.putInt("id",2);
                InformativaFragment fragment2 = new InformativaFragment();
                fragment2.setArguments(bundle2);
                FragmentManager fragmentManager2 = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction2=fragmentManager2.beginTransaction();
                fragmentTransaction2.replace(R.id.nav_host_fragment_content_main,fragment2);
                fragmentTransaction2.addToBackStack(null);
                fragmentTransaction2.commit();
                break;
            case R.id.info_item03:
                Bundle bundle3 = new Bundle();
                bundle3.putInt("id",3);
                InformativaFragment fragment3 = new InformativaFragment();
                fragment3.setArguments(bundle3);
                FragmentManager fragmentManager3 = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction3=fragmentManager3.beginTransaction();
                fragmentTransaction3.replace(R.id.nav_host_fragment_content_main,fragment3);
                fragmentTransaction3.addToBackStack(null);
                fragmentTransaction3.commit();
                break;
            case R.id.info_item04:
                Bundle bundle4 = new Bundle();
                bundle4.putInt("id",4);
                InformativaFragment fragment4 = new InformativaFragment();
                fragment4.setArguments(bundle4);
                FragmentManager fragmentManager4 = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction4=fragmentManager4.beginTransaction();
                fragmentTransaction4.replace(R.id.nav_host_fragment_content_main,fragment4);
                fragmentTransaction4.addToBackStack(null);
                fragmentTransaction4.commit();
                break;
        }
    }
}