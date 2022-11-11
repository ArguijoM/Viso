package mainpackage.viso.ui.actividad.instruccion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import mainpackage.viso.MainActivity;
import mainpackage.viso.R;
import mainpackage.viso.herramientas.SoundsPlayer;
import mainpackage.viso.ui.actividad.get.ActividadShow;
import mainpackage.viso.ui.actividad.set.ActividadN;

public class Instruccion extends AppCompatActivity {
    private  Button btn;
    private Fragment instruccion,instruccion2,instruccion3,instruccion4;
    private FragmentTransaction transaction;
    private SoundsPlayer sound;
    private int count = 0;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instruccion);
        Intent intent = getIntent();
        if(intent!=null){
            this.id = intent.getIntExtra("id",0);
        }
        btn = findViewById(R.id.btn_siguiente);
        btn.setEnabled(false);
        sound =new SoundsPlayer(this);

        instruccion = new InstruccionFragment();
        instruccion2 = new InstruccionFragment2();
        instruccion3 = new InstruccionFragment3();
        instruccion4 = new InstruccionFragment4();
        transaction = getSupportFragmentManager().beginTransaction();
        getSupportFragmentManager().beginTransaction().add(R.id.contenedor_fragment,instruccion).commit();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                btn.setEnabled(true);
            }
        },2000);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sound.playTapSound();
                count++;
                transaction = getSupportFragmentManager().beginTransaction();
                btn.setEnabled(false);
                switch (count){
                    case 1:
                        transaction.replace(R.id.contenedor_fragment,instruccion2);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                btn.setEnabled(true);
                            }
                        },2000);
                        break;
                    case 2:
                        transaction.replace(R.id.contenedor_fragment,instruccion3);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                btn.setEnabled(true);
                            }
                        },2000);
                        break;
                    case 3:
                        transaction.replace(R.id.contenedor_fragment,instruccion4);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                btn.setEnabled(true);
                            }
                        },2000);
                        break;
                    case 4:
                        Intent intent = new Intent(Instruccion.this, ActividadN.class);
                        intent.putExtra("id",id);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
                transaction.commit();
                transaction.addToBackStack(null);
            }
        });

    }
    @Override
    public void onBackPressed() {

    }
}