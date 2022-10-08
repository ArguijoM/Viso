package mainpackage.viso.ui.actividad.get;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actividad_show);

        this.usuarioActual= SharedPreferencesHelper.getUsuarioActual(Herramientas.mainActivity);
        ArrayList<Actividad> act = usuarioActual.getActividades();
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
        Log.i("ImagenString: ",usuarioActual.getActividades().get(id-1).getImagen());
        img_tomada.setImageBitmap(getImagenActividad(id));
        textView = (TextView)findViewById(R.id.act_show_realizada);
        calificacion =findViewById(R.id.textView_calif);
        calificacion.setText(""+usuarioActual.getActividades().get(id-1).getPuntuacion());

        textView.setText(textView.getText()+" || "+act.get(id-1).getIdServidor());
    }
    public Bitmap getImagenActividad(int id){
        if((id-1)>=0){
            id = id-1;
        }
        String imageString = usuarioActual.getActividades().get(id).getImagen();
        byte[] decodedString = Base64.decode(imageString, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }
}