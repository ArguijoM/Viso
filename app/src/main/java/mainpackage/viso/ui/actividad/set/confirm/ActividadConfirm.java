package mainpackage.viso.ui.actividad.set.confirm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import mainpackage.viso.MainActivity;
import mainpackage.viso.R;
import mainpackage.viso.herramientas.DatabaseHelper;
import mainpackage.viso.herramientas.Herramientas;
import mainpackage.viso.herramientas.SQLiteHelper;
import mainpackage.viso.herramientas.SharedPreferencesHelper;
import mainpackage.viso.herramientas.SoundsPlayer;
import mainpackage.viso.herramientas.VolleyCallBack;
import mainpackage.viso.herramientas.objetos.Actividad;
import mainpackage.viso.herramientas.objetos.UsuarioAdulto;
import mainpackage.viso.herramientas.objetos.UsuarioNino;
import mainpackage.viso.ui.actividad.ActividadDone;
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
        Log.i("CURENT USER ACT",usuarioActual.getNombre());
        BitmapDrawable drawable = (BitmapDrawable) img_tomada.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        bitmap = Bitmap.createScaledBitmap(bitmap,500,Herramientas.getHeight(id),false);
        Log.i("IMAGEN FINAL","ANCHO "+bitmap.getWidth()+" ALTO "+bitmap.getHeight());
        int calif=Herramientas.calificar(bitmap,id);
        //Bitmap bm = Herramientas.recrearCuadrante(instancia);
        //int resourceId = Herramientas.mainActivity.getResources().getIdentifier("act_" + id, "raw", Herramientas.mainActivity.getPackageName());
        //Bitmap bm2 = Herramientas.recrearCuadrante(Herramientas.getActivityInstance(resourceId));
        //Log.i("Imagen Tomada","Ancho: "+bm.getWidth()+" Alto: "+bm.getHeight());

        //img_tomada.setImageBitmap(bm);
        //img_muestra.setImageBitmap(bm2);
        //Log.i("Imagen de muestra","Ancho: "+bm2.getWidth()+" Alto: "+bm2.getHeight());

        Actividad act = new Actividad(id,0,usuarioActual.getIdServidor(),usuarioActual.getIdLocal(),crearImagenString(bitmap),calif, Calendar.getInstance().getTime().toString());
        SharedPreferencesHelper.addActividad(act);
        sound.playDoneSound();
        //Intent intent = new Intent(ActividadConfirm.this, ActividadDone.class);
        //startActivity(intent);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(ActividadConfirm.this, ActividadDone.class);
                startActivity(intent);

            }
            },5000);

        /*String path = Herramientas.saveToInternalStorage(bitmap,name);
        Actividad act = new Actividad(id, path, name);
        usuarioActual.setActividad(act);
        SharedPreferencesHelper.setUsuarioActual(Herramientas.mainActivity,usuarioActual);
        SharedPreferencesHelper.updateUsuario(Herramientas.mainActivity,usuarioActual);
        ActividadFragment fragment = new ActividadFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment_content_main,fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();*/
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