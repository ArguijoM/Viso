package mainpackage.viso.herramientas.objetos.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.ArrayList;

import mainpackage.viso.MainActivity;
import mainpackage.viso.R;
import mainpackage.viso.herramientas.Herramientas;
import mainpackage.viso.herramientas.SharedPreferencesHelper;
import mainpackage.viso.herramientas.objetos.Actividad;
import mainpackage.viso.herramientas.objetos.UsuarioAdulto;
import mainpackage.viso.herramientas.objetos.UsuarioNino;
import mainpackage.viso.ui.cuenta.CuentaFragment;

public class Bienvenido extends AppCompatActivity {

    private TextView welcome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenido);
        getSupportActionBar().hide();

        UsuarioNino aux = SharedPreferencesHelper.getUsuarioActual(Herramientas.mainActivity);
        UsuarioNino usuarioNino = SharedPreferencesHelper.getUsuario(aux.getNombre());
        SharedPreferencesHelper.setUsuarioActual(Herramientas.mainActivity,usuarioNino);

        welcome = (TextView) findViewById(R.id.welcome);
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.bounce);
        welcome.startAnimation(animation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Bienvenido.this, MainActivity.class);
                startActivity(intent);
            }
        },3500);
    }
    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }
}