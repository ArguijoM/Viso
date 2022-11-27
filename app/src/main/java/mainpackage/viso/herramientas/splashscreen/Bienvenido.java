package mainpackage.viso.herramientas.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import mainpackage.viso.MainActivity;
import mainpackage.viso.R;
import mainpackage.viso.herramientas.Herramientas;
import mainpackage.viso.herramientas.SharedPreferencesHelper;
import mainpackage.viso.herramientas.SoundsPlayer;
import mainpackage.viso.herramientas.objetos.UsuarioNino;

public class Bienvenido extends AppCompatActivity {

    private TextView welcome;
    private SoundsPlayer sound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenido);
        getSupportActionBar().hide();

        sound = new SoundsPlayer(this);
        UsuarioNino aux = SharedPreferencesHelper.getUsuarioActual(Herramientas.mainActivity);
        UsuarioNino usuarioNino = SharedPreferencesHelper.getUsuario(aux.getNombre());
        SharedPreferencesHelper.setUsuarioActual(Herramientas.mainActivity,usuarioNino);

        welcome = (TextView) findViewById(R.id.welcome);
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.bounce);
        welcome.startAnimation(animation);
        sound.playDoneSound();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Bienvenido.this, MainActivity.class);
                intent.putExtra("case",1);
                startActivity(intent);
            }
        },3500);
    }
    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }
}