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

import mainpackage.viso.MainActivity;
import mainpackage.viso.R;

public class Presentacion extends AppCompatActivity {
    private TextView tv;
    private ImageView iv;
    private Button btn;
    private int count=0;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida);
        getSupportActionBar().hide();
        animation= AnimationUtils.loadAnimation(this,R.anim.fadein);

        btn = findViewById(R.id.btn_presentacion);
        tv=findViewById(R.id.tv_presentacion);
        iv = findViewById(R.id.iv_presentacion);
        btn.setEnabled(false);
        tv.setText(R.string.presentacion_1);
        tv.startAnimation(animation);
        iv.setImageResource(R.drawable.ic_eye);
        iv.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                btn.setEnabled(true);
            }
        },2000);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                switch (count){
                    case 1:
                        btn.setEnabled(false);
                        tv.setText(R.string.presentacion_2);
                        tv.startAnimation(animation);
                        iv.setImageResource(R.drawable.img_presentacion);
                        iv.startAnimation(animation);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                btn.setEnabled(true);
                            }
                        },2000);
                        break;
                    case 2:
                        btn.setEnabled(false);
                        tv.setText(R.string.presentacion_3);
                        tv.startAnimation(animation);
                        iv.setImageResource(R.drawable.ic_info);
                        iv.startAnimation(animation);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                btn.setEnabled(true);
                            }
                        },2000);
                        break;
                    case 3:
                        Intent intent = new Intent(Presentacion.this, MainActivity.class);
                        intent.putExtra("case",1);
                        startActivity(intent);
                }

            }
        });
    }
    @Override
    public void onBackPressed() {

    }

}