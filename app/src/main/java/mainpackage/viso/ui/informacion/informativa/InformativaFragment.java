package mainpackage.viso.ui.informacion.informativa;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.snackbar.Snackbar;
import com.itextpdf.kernel.geom.Line;

import mainpackage.viso.R;
import mainpackage.viso.databinding.FragmentInformativaBinding;

public class InformativaFragment extends Fragment {
    private FragmentInformativaBinding binding;
    private InformativaViewModel viewModel;
    private TextView opcion_text,opcion_text2, opcion_title;
    private ImageView opcion_image,opcion_image2;
    private LinearLayout opcion_layout;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(InformativaViewModel.class);
        binding = FragmentInformativaBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        opcion_text=(TextView)root.findViewById(R.id.info_item_text01);
        opcion_text2=(TextView)root.findViewById(R.id.info_item_text02);

        opcion_title = (TextView)root.findViewById(R.id.info_item_title);
        opcion_image = (ImageView)root.findViewById(R.id.info_item_image01);
        opcion_image2 = (ImageView)root.findViewById(R.id.info_item_image02);

        opcion_layout = (LinearLayout)root.findViewById(R.id.info_item_layout);

        Bundle datos = this.getArguments();
        int id=datos.getInt("id");
        switch (id){
            case 1:
                opcion_title.setText(R.string.informacion_item01_titulo);
                opcion_text.setText(R.string.informacion_item01_text01);
                opcion_image.setImageResource(R.drawable.ic_eye);
                opcion_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage(R.string.informacion_item01_referencia)
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alerta = builder.create();
                        alerta.setTitle("Referencia");
                        alerta.show();
                    }
                });
                break;
            case 2:
                opcion_title.setText(R.string.informacion_item02_titulo);
                opcion_text.setText(R.string.informacion_item02_text01);
                opcion_text2.setText(R.string.informacion_item02_text02);
                opcion_image.setImageResource(R.drawable.test_vmi);
                opcion_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage(R.string.informacion_item02_referencia)
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alerta = builder.create();
                        alerta.setTitle("Referencia");
                        alerta.show();
                    }
                });
                break;
            case 3:
                opcion_title.setText(R.string.informacion_item03_titulo);
                opcion_text.setText(R.string.informacion_item03_text01);
                opcion_text2.setText(R.string.informacion_item03_text02);
                opcion_image.setImageResource(R.drawable.test_bender);
                opcion_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage(R.string.informacion_item03_referencia)
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alerta = builder.create();
                        alerta.setTitle("Referencia");
                        alerta.show();
                    }
                });
                break;
            case 4:
                opcion_title.setText(R.string.informacion_item04_titulo);
                opcion_text.setText(R.string.informacion_item04_text01);
                opcion_image.setImageResource(R.drawable.ic_main_2);
                break;
        }
        return root;
    }

}
