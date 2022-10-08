package mainpackage.viso.ui.actividad.set;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import mainpackage.viso.R;
import mainpackage.viso.herramientas.SoundsPlayer;
import mainpackage.viso.ui.actividad.SplashScreen;
import mainpackage.viso.ui.actividad.set.confirm.ActividadConfirm;
import mainpackage.viso.ui.actividad.set.confirm.ActividadConfirmFragment;

public class ActividadN extends AppCompatActivity {
    private int id;
    private Button btn_foto;
    private ImageView img_muestra;
    private String currentPhotoPath;
    private TextView mostrar;
    private SoundsPlayer sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_n);
        sound = new SoundsPlayer(this);
         Intent intent = getIntent();
        if(intent!=null){
            id = intent.getIntExtra("id",0);
        }
        img_muestra = (ImageView) findViewById(R.id.img_muestra);
        String uri = "@drawable/act_"+id;
        int imageResource = getResources().getIdentifier(uri, null,this.getPackageName());
        Drawable res = getResources().getDrawable(imageResource);
        img_muestra.setImageDrawable(res);
        btn_foto = (Button) findViewById(R.id.btn_foto);
        btn_foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sound.playTapSound();
                tomarFoto();
            }
        });
    }
    private void tomarFoto(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        //if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
        // Create the File where the photo should go
        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException ex) {
            // Error occurred while creating the File

        }
        // Continue only if the File was successfully created
        if (photoFile != null) {
            Uri photoURI = FileProvider.getUriForFile(this.getApplicationContext(),
                    "com.example.android.fileprovider",
                    photoFile);
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
            startActivityForResult(takePictureIntent, 1);
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            Intent intent = new Intent(ActividadN.this, SplashScreen.class);
            intent.putExtra("id",id);
            intent.putExtra("img",currentPhotoPath);
            startActivity(intent);
            finish();

            /*Intent intent = new Intent(this.getApplicationContext(), SplashScreen.class);
            intent.putExtra("id",id);
            intent.putExtra("img",currentPhotoPath);
            startActivity(intent);
            finish();*/
        }
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = this.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
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