package mainpackage.viso.herramientas.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import mainpackage.viso.MainActivity;
import mainpackage.viso.R;
import mainpackage.viso.herramientas.Herramientas;
import mainpackage.viso.herramientas.SharedPreferencesHelper;
import mainpackage.viso.herramientas.SoundsPlayer;
import mainpackage.viso.herramientas.objetos.Actividad;
import mainpackage.viso.herramientas.objetos.UsuarioNino;

public class Completado extends AppCompatActivity {
    private TextView tv01,tv02;
    private SoundsPlayer sound;
    private Button btn;
    private int count =0;
    private UsuarioNino usuarioActual;
    private int bruto;
    private ImageView iv01;
    private ArrayList<Actividad>actividades;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_completado);
        getSupportActionBar().hide();

        sound = new SoundsPlayer(this);
        tv01 = findViewById(R.id.tv01_completado);
        tv02 = findViewById(R.id.tv02_completado);
        btn = findViewById(R.id.btn_completado);
        iv01 = findViewById(R.id.iv_completado);
        usuarioActual = SharedPreferencesHelper.getUsuarioActual(Herramientas.mainActivity);
        actividades = SharedPreferencesHelper.getActividades(usuarioActual.getIdLocal());
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.bounce);
        sound.playSuccessSound();
        tv01.startAnimation(animation);
        tv02.startAnimation(animation);
        iv01.startAnimation(animation);
        bruto = puntajeBruto(actividades);
        btn.setEnabled(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                btn.setEnabled(true);
            }
        },3500);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                btn.setEnabled(false);
                if(count==1){
                    tv01.setText("El puntaje bruto de "+usuarioActual.getNombre()+" es de: "+bruto);
                    iv01.setImageResource(R.drawable.vmi_table_2);
                    tv02.setText("Compara el puntaje bruto de "+usuarioActual.getNombre()+" con la tabla según su edad ("+usuarioActual.getEdad()+" años)");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            btn.setEnabled(true);
                        }
                    },3500);
                }else if(count==2){
                    tv01.setText("Si se encuentra dentro del margen");
                    tv02.setText("quiere decir que sus habilidades viso motrices están a corde con su edad");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            btn.setEnabled(true);
                        }
                    },3500);
                } else {
                    Intent intent = new Intent(Completado.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }
    public int puntajeBruto(ArrayList<Actividad>activities){
        int act=0,fallos=0;
        for(int j=0;j<activities.size();j++){
            if(fallos==3){
                return act;
            }
            if(Herramientas.getEvaluacion((j+1),usuarioActual.getEdad(),activities.get(j).getCalificacion())==false){
                fallos++;
                act = j+1;
            }
        }
        return 18;
    }
}