package mainpackage.viso.ui.actividad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import mainpackage.viso.MainActivity;
import mainpackage.viso.R;
import mainpackage.viso.herramientas.Herramientas;
import mainpackage.viso.herramientas.SoundsPlayer;
import mainpackage.viso.ui.actividad.set.confirm.ActividadConfirm;

public class ActividadDone extends AppCompatActivity {
    private ImageView done;
    private TextView text_done;
    private SoundsPlayer sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_done);
        getSupportActionBar().hide();
        done = findViewById(R.id.done_circle);
        text_done = findViewById(R.id.done_text);
        Animation animation=AnimationUtils.loadAnimation(this,R.anim.bounce);
        Animation animation2=AnimationUtils.loadAnimation(this,R.anim.bounce);
        done.startAnimation(animation);
        text_done.startAnimation(animation2);
        sound =new SoundsPlayer(this);
        sound.playDoneSound();
        sound.playDoneSound();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ActividadDone.this, MainActivity.class);
                startActivity(intent);
            }
        },3000);

    }
}