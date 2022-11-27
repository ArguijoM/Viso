package mainpackage.viso.herramientas.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import mainpackage.viso.MainActivity;
import mainpackage.viso.R;
import mainpackage.viso.herramientas.SoundsPlayer;

public class Eliminado extends AppCompatActivity {
    private ImageView loading;
    private TextView generando;
    private SoundsPlayer sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen_borrado);
        getSupportActionBar().hide();

        generando = findViewById(R.id.generando_textview);
        loading = findViewById(R.id.iv_borrado);
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.bounce);
        generando.startAnimation(animation);
        sound =new SoundsPlayer(this);
        sound.playDoneSound();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Eliminado.this, MainActivity.class);
                startActivity(intent);
            }
        },3000);
    }
    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }
}