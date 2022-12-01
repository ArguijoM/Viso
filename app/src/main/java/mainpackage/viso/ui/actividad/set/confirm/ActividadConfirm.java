package mainpackage.viso.ui.actividad.set.confirm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import mainpackage.viso.R;
import mainpackage.viso.herramientas.Herramientas;
import mainpackage.viso.herramientas.SharedPreferencesHelper;
import mainpackage.viso.herramientas.SoundsPlayer;
import mainpackage.viso.herramientas.objetos.Actividad;
import mainpackage.viso.herramientas.objetos.UsuarioNino;
import mainpackage.viso.herramientas.splashscreen.Completado;
import mainpackage.viso.herramientas.splashscreen.Realizada;
import mainpackage.viso.ui.actividad.set.ActividadN;

public class ActividadConfirm extends AppCompatActivity implements View.OnClickListener {
    private int id;
    private SoundsPlayer sound;
    private Bitmap image;
    private ImageView img_muestra,img_tomada,img_ok,img_not;
    private TextView mostrar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_confirm);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sound = new SoundsPlayer(this);
        Intent intent = getIntent();
        if(intent!=null){
            id = intent.getIntExtra("id",0);
            byte[] byteArray = getIntent().getByteArrayExtra("img");
            image = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        }
        String uri = "@drawable/act_"+id;
        int imageResource = getResources().getIdentifier(uri, null,this.getPackageName());
        Drawable res = getResources().getDrawable(imageResource);
        (img_muestra = findViewById(R.id.img_muestra)).setImageDrawable(res);
        img_tomada = findViewById(R.id.img_tomada);
        img_tomada.setImageBitmap(image);
        img_ok = findViewById(R.id.btn_act_ok);
        img_ok.setOnClickListener(this);
        img_not = findViewById(R.id.btn_act_not);
        img_not.setOnClickListener(this);
        mostrar = findViewById(R.id.text_mostrar);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_act_ok:
                sound.playTapSound();
                guardarFoto();
                break;
            case R.id.btn_act_not:
                sound.playTapSound();
                Intent intent = new Intent(ActividadConfirm.this, ActividadN.class);
                intent.putExtra("id",id);
                startActivity(intent);
                break;
        }
    }
    private void guardarFoto(){
        UsuarioNino usuarioActual = SharedPreferencesHelper.getUsuarioActual(Herramientas.mainActivity);
        //Log.i("CURENT USER ACT",usuarioActual.getNombre());
        BitmapDrawable drawable = (BitmapDrawable) img_tomada.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        bitmap = Bitmap.createScaledBitmap(bitmap,500,Herramientas.getHeight(id),false);
        Log.i("IMAGEN FINAL","ANCHO "+bitmap.getWidth()+" ALTO "+bitmap.getHeight());
        int calif=Herramientas.calificar(bitmap,id);
        Actividad act = new Actividad(id,0,usuarioActual.getIdServidor(),usuarioActual.getIdLocal(),crearImagenString(bitmap),calif, Calendar.getInstance().getTime().toString());
        SharedPreferencesHelper.addActividad(act);
        if(id==18){
            sound.playSuccessSound();
            Intent intent = new Intent(ActividadConfirm.this, Completado.class);
            startActivity(intent);
        }else {
            sound.playDoneSound();
            Intent intent = new Intent(ActividadConfirm.this, Realizada.class);
            startActivity(intent);
        }
    }
    public static String crearImagenString(Bitmap image){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        //image.recycle();
        return android.util.Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}