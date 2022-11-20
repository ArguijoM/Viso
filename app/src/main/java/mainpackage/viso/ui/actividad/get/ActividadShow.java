package mainpackage.viso.ui.actividad.get;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import mainpackage.viso.R;
import mainpackage.viso.databinding.FragmentActividadShowBinding;
import mainpackage.viso.herramientas.Herramientas;
import mainpackage.viso.herramientas.SharedPreferencesHelper;
import mainpackage.viso.herramientas.objetos.Actividad;
import mainpackage.viso.herramientas.objetos.UsuarioNino;

public class ActividadShow extends AppCompatActivity {
    private int id;
    private ImageView img_muestra,img_tomada;
    private FragmentActividadShowBinding binding;
    private ActividadShowViewModel actividadShowViewModel;
    private TextView textView,calificacion;
    private  UsuarioNino usuarioActual;
    private ArrayList<Actividad> act;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_show);
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.usuarioActual= SharedPreferencesHelper.getUsuarioActual(Herramientas.mainActivity);
        this.act = SharedPreferencesHelper.getActividades(usuarioActual.getIdLocal());
        Intent intent = getIntent();
        if(intent!=null){
            id = intent.getIntExtra("id",0);
        }
        img_muestra = findViewById(R.id.img_muestra);
        img_tomada = findViewById(R.id.img_tomada);
        String uri = "@drawable/act_"+id;
        int imageResource = getResources().getIdentifier(uri, null, this.getPackageName());
        Drawable res = getResources().getDrawable(imageResource);
        img_muestra.setImageDrawable(res);
        img_tomada.setImageBitmap(getImagenActividad(id));
        textView = (TextView)findViewById(R.id.act_show_realizada);
        calificacion =findViewById(R.id.textView_calif);
        boolean calif =Herramientas.getEvaluacion(id,usuarioActual.getEdad(),act.get(id-1).getCalificacion());
        Log.i("Valor",""+calif);
        if(calif==true){
            //calificacion.setText(""+act.get(id-1).getCalificacion());
            calificacion.setText("VALIDA");
            calificacion.setTextColor(Color.parseColor("#00FF00"));

        }else{
            //calificacion.setText(""+act.get(id-1).getCalificacion());
            calificacion.setText("NO VALIDA");
            calificacion.setTextColor(Color.parseColor("#FF0000"));
        }
    }
    public Bitmap getImagenActividad(int id){
        if((id-1)>=0){
            id = id-1;
        }
        String imageString = act.get(id).getImagen();
        byte[] decodedString = Base64.decode(imageString, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}