package mainpackage.viso.ui.actividad;

import androidx.appcompat.app.AppCompatActivity;
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.Console;
import java.io.IOException;
import java.util.ArrayList;

import mainpackage.viso.R;
import mainpackage.viso.herramientas.Herramientas;
import mainpackage.viso.ui.actividad.set.confirm.ActividadConfirm;

public class SplashScreen extends AppCompatActivity {
    private int id;
    //private String currentPhotoPath;
    private String currentPhotoPath2;
    private ImageView loading;
    private TextView generando;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        Intent intent = getIntent();
        if(intent!=null){
            this.id = intent.getIntExtra("id",0);
            //BORRAR ESTO
            this.currentPhotoPath2 = intent.getStringExtra("img");
            //DESCOMENTAR ESTO
            //this.currentPhotoPath = intent.getStringExtra("img");
        }
        generando = findViewById(R.id.generando_textview);
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.bounce);
        generando.startAnimation(animation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //BORRAR ESTO
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getBaseContext().getContentResolver() , Uri.parse(currentPhotoPath2));
                } catch (IOException e) {
                    e.printStackTrace();
                }


                //loading.startAnimation(animation);
                //BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                //DESCOMENTAR ESTO
                //Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath,bmOptions);


                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

                Bitmap btm_reduce = Bitmap.createScaledBitmap(rotatedBitmap, 500, Herramientas.getHeight(id), false);
                Bitmap btm_bw = Herramientas.blanco_y_negro(btm_reduce);
                Bitmap btm_new = Herramientas.recortarImagen(btm_bw);
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                btm_new.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                Intent intent2 = new Intent(SplashScreen.this, ActividadConfirm.class);
                intent2.putExtra("id", id);
                intent2.putExtra("img", byteArray);
                startActivity(intent2);

            }
        },100);

        //mostrarFoto();
/*        Intent intent2 = new Intent(this.getApplicationContext(), ActividadConfirm.class);
        intent2.putExtra("id",id);
        //intent2.putExtra("img",currentPhotoPath);
        startActivity(intent);*/
    }

    private void mostrarFoto() {
        Intent intent2 = new Intent(this.getApplicationContext(), ActividadConfirm.class);
        intent2.putExtra("id",id);
        startActivity(intent2);
    }
    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }

}