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

public class Realizada extends AppCompatActivity {
    private ImageView done;
    private TextView text_done;
    private SoundsPlayer sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_done);
        getSupportActionBar().hide();
        //done = findViewById(R.id.done_circle);
        text_done = findViewById(R.id.done_text);
        Animation animation=AnimationUtils.loadAnimation(this,R.anim.bounce);
        //done.startAnimation(animation);
        text_done.startAnimation(animation);
        sound =new SoundsPlayer(this);
        sound.playDoneSound();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Realizada.this, MainActivity.class);
                startActivity(intent);
            }
        },2000);

    }
    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }

}