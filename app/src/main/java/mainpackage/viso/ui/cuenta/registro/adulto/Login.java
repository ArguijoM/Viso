package mainpackage.viso.ui.cuenta.registro.adulto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import mainpackage.viso.R;
import mainpackage.viso.herramientas.database.DatabaseHelper;
import mainpackage.viso.herramientas.Herramientas;
import mainpackage.viso.herramientas.database.SQLiteHelper;
import mainpackage.viso.herramientas.SharedPreferencesHelper;
import mainpackage.viso.herramientas.database.VolleyCallBack;
import mainpackage.viso.herramientas.objetos.UsuarioAdulto;
import mainpackage.viso.herramientas.objetos.UsuarioNino;
import mainpackage.viso.herramientas.splashscreen.Bienvenido;

public class Login extends AppCompatActivity {
    private Button btn_siguiente;
    private ProgressBar progressBar;
    private Context context;
    private String email,contrasena;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressBar = (ProgressBar)findViewById(R.id.login_progressbar);
        progressBar.setVisibility(View.GONE);
        Intent intent = getIntent();
        if(intent.getExtras()!=null){
            this.email=intent.getStringExtra("email");
            this.contrasena=intent.getStringExtra("contrasena");
            ((TextView)findViewById(R.id.login_head)).setText("Restaura tu información");
            ((EditText)findViewById(R.id.correo_adulto)).setText(email);
            ((EditText)findViewById(R.id.correo_adulto)).setEnabled(false);
            ((EditText)findViewById(R.id.contrasena_adulto)).setText(contrasena);
            ((EditText)findViewById(R.id.contrasena_adulto)).setEnabled(false);
            SQLiteHelper myDB = new SQLiteHelper(Herramientas.mainActivity);
            myDB.onUpgrade(myDB.getWritableDatabase(),1,2);
        }
        btn_siguiente= (Button)findViewById(R.id.btn_siguiente);
        context = this;
        btn_siguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo = ((EditText)findViewById(R.id.correo_adulto)).getText().toString();
                String contrasena = ((EditText)findViewById(R.id.contrasena_adulto)).getText().toString();
                if(!correo.equals("") && !contrasena.equals("")) {
                    UsuarioAdulto usuarioAdulto = new UsuarioAdulto(1,correo,contrasena);
                    DatabaseHelper db = new DatabaseHelper(context,progressBar);
                    progressBar.setVisibility(View.VISIBLE);
                    db.foundUsuario((new VolleyCallBack() {
                        @Override
                        public void onSuccess(String result) {
                            if((SharedPreferencesHelper.getUsuarioAdulto(Herramientas.mainActivity))!=null) {
                                db.foundNino(new VolleyCallBack() {
                                    @Override
                                    public void onSuccess(String result) {
                                        ArrayList<UsuarioNino> usuarios = SharedPreferencesHelper.getUsuarios();
                                        for (int i = 0; i < usuarios.size(); i++) {
                                            db.foundActividad(new VolleyCallBack() {
                                                @Override
                                                public void onSuccess(String result) {
                                                    progressBar.setVisibility(View.GONE);
                                                    Intent intent = new Intent(context, Bienvenido.class);
                                                    startActivity(intent);
                                                }
                                            }, usuarios.get(i).getIdServidor());
                                        }
                                    }
                                }, SharedPreferencesHelper.getUsuarioAdulto(Herramientas.mainActivity).getIdServidor());
                            }else{
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(context, "Correo y/o contraseña incorrectos", Toast.LENGTH_LONG).show();
                            }
                        }
                    }),usuarioAdulto);
                }else{
                    Toast.makeText(context,"Ingrese la información solicitada",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}