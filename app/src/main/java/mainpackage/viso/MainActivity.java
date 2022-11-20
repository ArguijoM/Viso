package mainpackage.viso;

import android.os.Bundle;
import android.view.Menu;

import com.google.android.material.navigation.NavigationView;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import mainpackage.viso.databinding.ActivityMainBinding;
import mainpackage.viso.herramientas.Herramientas;
import mainpackage.viso.herramientas.SharedPreferencesHelper;
import mainpackage.viso.herramientas.objetos.UsuarioAdulto;
import mainpackage.viso.herramientas.objetos.UsuarioNino;
import mainpackage.viso.ui.cuenta.registro.adulto.CuentaRegistroAdultoFragment;
import mainpackage.viso.ui.cuenta.registro.nino.CuentaRegistroNinoFragment;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.appBarMain.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_inicio,R.id.nav_actividad, R.id.nav_cuenta, R.id.nav_informacion,R.id.nav_configuracion)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        Herramientas.mainActivity = this;
        UsuarioAdulto usuarioAdulto = SharedPreferencesHelper.getUsuarioAdulto(this);
        UsuarioNino usuarioActual=null;
        //SharedPreferencesHelper.deteteAllPreference(this);
        if(usuarioAdulto==null){
            CuentaRegistroAdultoFragment fragment = new CuentaRegistroAdultoFragment();
            FragmentManager fragmentManager = this.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.nav_host_fragment_content_main,fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }else if((usuarioActual= SharedPreferencesHelper.getUsuarioActual(this))==null){
            CuentaRegistroNinoFragment fragment= new CuentaRegistroNinoFragment();
            FragmentManager fragmentManager = this.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.nav_host_fragment_content_main,fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

        //usuarioActual.setActividades(new ArrayList<>());
        //SharedPreferencesHelper.setUsuarioActual(Herramientas.mainActivity,usuarioActual);
        //SharedPreferencesHelper.updateUsuario(Herramientas.mainActivity,usuarioActual);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }
}
