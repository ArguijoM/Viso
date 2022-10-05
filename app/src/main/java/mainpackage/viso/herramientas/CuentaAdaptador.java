package mainpackage.viso.herramientas;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import mainpackage.viso.R;
import mainpackage.viso.herramientas.objetos.UsuarioNino;

public class CuentaAdaptador extends BaseAdapter {
    private Context context;
    private ArrayList<UsuarioNino> usuarios;
    private int diseno;

    public CuentaAdaptador(Context context, ArrayList<UsuarioNino> usuarios, int diseno) {
        this.context = context;
        this.usuarios = usuarios;
        this.diseno = diseno;
    }

    @Override
    public int getCount() {
        return usuarios.size();
    }

    @Override
    public Object getItem(int position) {
        return usuarios.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vistaDiseno = convertView;
        if(vistaDiseno==null){
            //Creamos la vista
            LayoutInflater LayoutInflater = android.view.LayoutInflater.from(context);
            vistaDiseno = LayoutInflater.inflate(diseno,null);
        }
        TextView nombres = vistaDiseno.findViewById(R.id.usuario_nombre);
        ImageView imagen = vistaDiseno.findViewById(R.id.usuario_imagen);
        nombres.setText(usuarios.get(position).getNombre());
        String[] aux = (usuarios.get(position).getProfile()).split(" Y ");
        if(aux[0].equals("H")){
            String uriboy = "@drawable/ic_boy"+String.valueOf(aux[1]);
            int imageResourceboy = context.getResources().getIdentifier(uriboy, null, context.getPackageName());
            Drawable drawableboy = context.getResources().getDrawable(imageResourceboy);
            imagen.setImageDrawable(drawableboy);
        }else{
            String urigirl = "@drawable/ic_girl"+String.valueOf(aux[1]);
            int imageResourcegirl= context.getResources().getIdentifier(urigirl, null, context.getPackageName());
            Drawable drawablegirl = context.getResources().getDrawable(imageResourcegirl);
            imagen.setImageDrawable(drawablegirl);
        }

        return vistaDiseno;
    }
}
