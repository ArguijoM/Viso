package mainpackage.viso.ui.informacion.informativa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import mainpackage.viso.R;

public class Informativa extends AppCompatActivity {
    private TextView opcion_text,opcion_text2, opcion_title;
    private ImageView opcion_image,opcion_image2;
    private LinearLayout opcion_layout;
    private int id;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informativa);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if(intent!=null){
            this.id = intent.getIntExtra("id",0);
        }
        context = this;
        opcion_text=(TextView)findViewById(R.id.info_item_text01);
        opcion_text2=(TextView)findViewById(R.id.info_item_text02);

        opcion_title = (TextView)findViewById(R.id.info_item_title);
        opcion_image = (ImageView)findViewById(R.id.info_item_image01);
        opcion_image2 = (ImageView)findViewById(R.id.info_item_image02);

        opcion_layout = (LinearLayout)findViewById(R.id.info_item_layout);

        generarInformacion(id);

    }
    public void generarInformacion(int id){
        switch (id){
            case 1:
                opcion_title.setText(R.string.informacion_item01_titulo);
                opcion_text.setText(R.string.informacion_item01_text01);
                opcion_image.setImageResource(R.drawable.ic_eye);
                opcion_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage(R.string.informacion_item01_referencia)
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alerta = builder.create();
                        alerta.setTitle("Referencia");
                        alerta.show();
                    }
                });
                break;
            case 2:
                opcion_title.setText(R.string.informacion_item02_titulo);
                opcion_text.setText(R.string.informacion_item02_text01);
                opcion_text2.setText(R.string.informacion_item02_text02);
                opcion_image.setImageResource(R.drawable.test_vmi);
                opcion_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage(R.string.informacion_item02_referencia)
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alerta = builder.create();
                        alerta.setTitle("Referencia");
                        alerta.show();
                    }
                });
                break;
            case 3:
                opcion_title.setText(R.string.informacion_item03_titulo);
                opcion_text.setText(R.string.informacion_item03_text01);
                opcion_text2.setText(R.string.informacion_item03_text02);
                opcion_image.setImageResource(R.drawable.test_bender);
                opcion_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                        builder.setMessage(R.string.informacion_item03_referencia)
                                .setCancelable(false)
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alerta = builder.create();
                        alerta.setTitle("Referencia");
                        alerta.show();
                    }
                });
                break;
            case 4:
                opcion_title.setText(R.string.informacion_item04_titulo);
                opcion_text.setText(R.string.informacion_item04_text01);
                opcion_image.setImageResource(R.drawable.ic_main_2);
                break;
        }
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}