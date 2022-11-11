package mainpackage.viso.ui.configuracion.borrar;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import mainpackage.viso.MainActivity;
import mainpackage.viso.R;
import mainpackage.viso.databinding.FragmentActividadBinding;
import mainpackage.viso.databinding.FragmentBorrarBinding;
import mainpackage.viso.herramientas.CuentaAdaptador;
import mainpackage.viso.herramientas.Herramientas;
import mainpackage.viso.herramientas.SharedPreferencesHelper;
import mainpackage.viso.herramientas.objetos.UsuarioNino;
import mainpackage.viso.ui.cuenta.CuentaFragment;
import mainpackage.viso.ui.inicio.InicioFragment;

public class BorrarFragment extends Fragment {
    private FragmentBorrarBinding binding;
    private ListView lista;
    ArrayList<UsuarioNino> usuarios;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentBorrarBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        lista = (ListView)root.findViewById(R.id.lista_usuarios);
        usuarios = SharedPreferencesHelper.getUsuarios();
        CuentaAdaptador adaptador = new CuentaAdaptador(getContext(),usuarios,R.layout.cuenta_item);
        lista.setAdapter(adaptador);
        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UsuarioNino usuario = usuarios.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Esta seguro que desea eliminar al usuario: "+usuario.getNombre()+" "+usuario.getApellido())
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                UsuarioNino usuarioActual = SharedPreferencesHelper.getUsuarioActual(Herramientas.mainActivity);
                                if(usuarioActual.getIdLocal()==usuario.getIdLocal()){
                                    SharedPreferencesHelper.deteteUsuarioActual(Herramientas.mainActivity);
                                }
                                boolean delete = SharedPreferencesHelper.deleteActividades(usuario.getIdLocal());
                                boolean deleteNino = SharedPreferencesHelper.deleteNino(usuario.getIdLocal());
                                ArrayList<UsuarioNino> usuarios = SharedPreferencesHelper.getUsuarios();
                                if(usuarios!=null){
                                    SharedPreferencesHelper.setUsuarioActual(Herramientas.mainActivity,usuarios.get(0));
                                }
                                if(deleteNino==true){
                                    Intent intent = new Intent(getActivity(), SplashScreenBorrado.class);
                                    startActivity(intent);
                                }

                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alerta = builder.create();
                alerta.setTitle("Advertencia");
                alerta.show();

            }
        });


        return  root;
    }
}