package mainpackage.viso.ui.actividad.set.confirm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import mainpackage.viso.R;
import mainpackage.viso.databinding.FragmentActividadConfirmBinding;
import mainpackage.viso.herramientas.Herramientas;
import mainpackage.viso.herramientas.SharedPreferencesHelper;
import mainpackage.viso.herramientas.objetos.Actividad;
import mainpackage.viso.herramientas.objetos.UsuarioNino;
import mainpackage.viso.ui.actividad.ActividadFragment;

public class ActividadConfirmFragment extends Fragment implements View.OnClickListener {
    private ImageView img_muestra,img_tomada,img_ok,img_not;
    private int id;
    private String currentPhotoPath;
    private ProgressBar progressBar;
    private TextView mostrar;
    private FragmentActividadConfirmBinding binding;
    private ActividadConfirmViewModel actividadConfirmViewModel;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        actividadConfirmViewModel = new ViewModelProvider(this).get(ActividadConfirmViewModel.class);
        binding = FragmentActividadConfirmBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        (img_muestra = root.findViewById(R.id.img_muestra)).setImageResource(R.drawable.act_1);
        img_tomada = root.findViewById(R.id.img_tomada);
        progressBar = root.findViewById(R.id.progress_bar_act);
        progressBar.setVisibility(View.GONE);
        img_ok = root.findViewById(R.id.btn_act_ok);
        img_ok.setOnClickListener(this);
        img_not = root.findViewById(R.id.btn_act_not);
        img_not.setOnClickListener(this);
        mostrar = root.findViewById(R.id.text_mostrar);
        mostrar.setText("Ver");
        mostrar.setOnClickListener(this);

        Bundle bundle = this.getArguments();
        if(bundle!=null) {
            this.id = bundle.getInt("id");
            currentPhotoPath = bundle.getString("img");
        }
        return root;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_act_ok:
                guardarFoto();
                break;
            case R.id.btn_act_not:
                tomarFoto();
                break;
            case R.id.text_mostrar:
                progressBar.setVisibility(View.VISIBLE);
                mostrar.setText("cargando");
                mostrarFoto();
                break;
        }

        }
    private void guardarFoto(){
        UsuarioNino usuarioActual = actividadConfirmViewModel.getUsuarioActual();
        String name = "usuario_"+usuarioActual.getNombre()+"_actividad_"+id+".jpg";
        BitmapDrawable drawable = (BitmapDrawable) img_tomada.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        //Herramientas.generarInstancia(bitmap,id);

        /*String path = Herramientas.saveToInternalStorage(bitmap,name);
        Actividad act = new Actividad(id, path, name);
        usuarioActual.setActividad(act);
        SharedPreferencesHelper.setUsuarioActual(Herramientas.mainActivity,usuarioActual);
        SharedPreferencesHelper.updateUsuario(Herramientas.mainActivity,usuarioActual);
        ActividadFragment fragment = new ActividadFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment_content_main,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();*/
    }
    private void mostrarFoto() {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath,bmOptions);
        Log.i("IMAGEN","Ancho"+bitmap.getWidth()+" ALTO: "+bitmap.getHeight());
        Matrix matrix = new Matrix();
        matrix.postRotate(90);
        Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
        int resourceId = getActivity().getResources().getIdentifier("act_" + id, "raw", getActivity().getPackageName());
        ArrayList<String[]> instancia = Herramientas.getActivityInstance(resourceId);
        Bitmap btm_reduce = Bitmap.createScaledBitmap(rotatedBitmap,rotatedBitmap.getWidth()/4,rotatedBitmap.getHeight()/4,false);
        Log.i("IMAGEN","Ancho: "+btm_reduce.getWidth()+" Alto: "+btm_reduce.getHeight());
        Bitmap btm_bw = Herramientas.blanco_y_negro(btm_reduce);
        Bitmap btm_new = Herramientas.recortarImagen(btm_bw);
        //Bitmap btm_rescale = Bitmap.createScaledBitmap(btm_new,bitmap.getWidth()/5,bitmap.getHeight()/5,false);
        progressBar.setVisibility(View.GONE);
        img_tomada.setImageBitmap(btm_new);
        mostrar.setText(R.string.actividad_confirm);
    }
    private void tomarFoto(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, 1);
            }
        }
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        Log.i("FOTO PATH",currentPhotoPath);
        return image;
    }

}