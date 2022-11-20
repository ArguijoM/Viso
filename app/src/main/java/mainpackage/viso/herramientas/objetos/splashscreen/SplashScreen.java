package mainpackage.viso.herramientas.objetos.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import mainpackage.viso.MainActivity;
import mainpackage.viso.R;
import mainpackage.viso.herramientas.Herramientas;
import mainpackage.viso.ui.actividad.set.confirm.ActividadConfirm;

public class SplashScreen extends AppCompatActivity {
    private int id;
    private String currentPhotoPath;
    private TextView generando;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        Intent intent = getIntent();
        if(intent!=null){
            this.id = intent.getIntExtra("id",0);

            this.currentPhotoPath = intent.getStringExtra("img");
        }
        generando = findViewById(R.id.generando_textview);
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.bounce);
        generando.startAnimation(animation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    //loading.startAnimation(animation);
                    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                    //DESCOMENTAR ESTO
                    Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath,bmOptions);

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
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Ha ocurrido un error, inténtelo de nuevo", Toast.LENGTH_LONG).show();
                    Intent intent2 = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent2);
                }


            }
        },3000);

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