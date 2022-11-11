package mainpackage.viso.ui.configuracion;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.fonts.Font;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.itextpdf.io.font.FontConstants;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.property.AreaBreakType;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import mainpackage.viso.R;
import mainpackage.viso.databinding.FragmentConfiguracionBinding;
import mainpackage.viso.herramientas.DatabaseHelper;
import mainpackage.viso.herramientas.Herramientas;
import mainpackage.viso.herramientas.SharedPreferencesHelper;
import mainpackage.viso.herramientas.VolleyCallBack;
import mainpackage.viso.herramientas.objetos.Actividad;
import mainpackage.viso.herramientas.objetos.UsuarioAdulto;
import mainpackage.viso.herramientas.objetos.UsuarioNino;
import mainpackage.viso.ui.configuracion.borrar.BorrarFragment;
import mainpackage.viso.ui.inicio.InicioFragment;


public class ConfiguracionFragment extends Fragment implements View.OnClickListener {
    private ConfiguracionViewModel configuracionViewModel;
    private FragmentConfiguracionBinding binding;
    RequestQueue requestQueue;
    TextView opcion01, opcion02,opcion03,opcion04;
    private ProgressBar progressBar;
    private static final int PERMISSION_REQUEST_CODE = 200;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        configuracionViewModel =
                new ViewModelProvider(this).get(ConfiguracionViewModel.class);
        binding = FragmentConfiguracionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        requestQueue = Volley.newRequestQueue(getContext());

        opcion01 = root.findViewById(R.id.opcion_01);
        opcion02 = root.findViewById(R.id.opcion_02);
        opcion03 = root.findViewById(R.id.opcion_03);
        opcion04 = root.findViewById(R.id.opcion_04);
        progressBar = root.findViewById(R.id.progress_bar_config);
        progressBar.setVisibility(View.GONE);

        opcion01.setOnClickListener(this);
        opcion02.setOnClickListener(this);
        opcion03.setOnClickListener(this);
        opcion04.setOnClickListener(this);

        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.opcion_01:
                //Generar copia de seguridad
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("¿Desea realizar una copia de seguridad?")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DatabaseHelper db = new DatabaseHelper(getContext(),progressBar);
                            progressBar.setVisibility(View.VISIBLE);
                            db.readUsuario(new VolleyCallBack() {
                                @Override
                                public void onSuccess(String result) {
                                    UsuarioAdulto usuario = SharedPreferencesHelper.getUsuarioAdulto(Herramientas.mainActivity);
                                    db.readNino(new VolleyCallBack() {
                                        @Override
                                        public void onSuccess(String result) {
                                            ArrayList<UsuarioNino> users = SharedPreferencesHelper.getUsuarios();
                                            Log.i("USUARIOS CONIFG::",""+users.size());
                                            for(int i=0; i<users.size();i++) {
                                                db.readActividad(new VolleyCallBack() {
                                                    @Override
                                                    public void onSuccess(String result) {
                                                        progressBar.setVisibility(View.GONE);
                                                        Toast.makeText(getContext(),"Copia de seguridad generada",Toast.LENGTH_SHORT).show();
                                                        ArrayList<UsuarioNino> users = SharedPreferencesHelper.getUsuarios();
                                                        SharedPreferencesHelper.setUsuarioActual(Herramientas.mainActivity,users.get(0));

                                                    }
                                                },users.get(i));
                                            }
                                        }
                                    }, usuario.getIdServidor());
                                }
                            },SharedPreferencesHelper.getUsuarioAdulto(Herramientas.mainActivity));
                        }
                    })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alerta = builder.create();
                alerta.setTitle("Alerta");
                alerta.show();
                break;
            case R.id.opcion_02:
                //Restaurarcopia de seguridad
                AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
                builder2.setMessage("Al restaurar su información perderá el progreso actual ¿Desea continuar?")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DatabaseHelper db = new DatabaseHelper(getContext(),progressBar);
                                progressBar.setVisibility(View.VISIBLE);
                                db.foundUsuario((new VolleyCallBack() {
                                    @Override
                                    public void onSuccess(String result) {
                                        db.foundNino(new VolleyCallBack() {
                                            @Override
                                            public void onSuccess(String result) {
                                                ArrayList<UsuarioNino> usuarios= SharedPreferencesHelper.getUsuarios();
                                                for(int i=0;i<usuarios.size();i++) {
                                                    db.foundActividad(new VolleyCallBack() {
                                                        @Override
                                                        public void onSuccess(String result) {
                                                            if(SharedPreferencesHelper.getUsuarioActual(Herramientas.mainActivity)==null) {
                                                                Toast.makeText(getContext(),"No existe el usuario",Toast.LENGTH_LONG).show();
                                                            }else{
                                                                progressBar.setVisibility(View.GONE);
                                                                InicioFragment fragment = new InicioFragment();
                                                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                                                                fragmentTransaction.replace(R.id.nav_host_fragment_content_main, fragment);
                                                                fragmentTransaction.addToBackStack(null);
                                                                fragmentTransaction.commit();
                                                            }
                                                        }
                                                    },usuarios.get(i).getIdServidor());
                                                }
                                            }
                                        },SharedPreferencesHelper.getUsuarioAdulto(Herramientas.mainActivity).getIdServidor());
                                    }
                                }),SharedPreferencesHelper.getUsuarioAdulto(Herramientas.mainActivity));
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alerta2 = builder2.create();
                alerta2.setTitle("Alerta");
                alerta2.show();
                break;
            case R.id.opcion_03:
                UsuarioNino user = SharedPreferencesHelper.getUsuarioActual(Herramientas.mainActivity);
                AlertDialog.Builder builder3 = new AlertDialog.Builder(getActivity());
                builder3.setMessage("Desea generar las actividades de "+user.getNombre()+"?")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                progressBar.setVisibility(View.VISIBLE);
                                if (checkPermission()) {
                                        generatePDF(user);
                                } else {
                                    requestPermission();
                                }
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alerta3 = builder3.create();
                alerta3.setTitle("Alerta");
                alerta3.show();
                break;
            case R.id.opcion_04:
                BorrarFragment fragment = new BorrarFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.nav_host_fragment_content_main, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                break;
        }
    }
    public void generatePDF(UsuarioNino usuarioActual) {
        try {
            File root = android.os.Environment.getExternalStorageDirectory();
            File dir = new File (root.getAbsolutePath() + "/download");
            dir.mkdirs();
            if(!dir.exists()) {
                dir.mkdirs();
            }
            File file = new File(dir, "Viso_"+usuarioActual.getNombre()+"_actividades.pdf");
            PdfWriter pdfWriter = new PdfWriter(file);
            PdfDocument pdfDocument = new PdfDocument(pdfWriter);
            Document documento = new Document(pdfDocument);
            pdfDocument.setDefaultPageSize(PageSize.A4);
            documento.setMargins(15,25,15,25);
            Paragraph logo = new Paragraph("Viso\n").setBold().setFontSize(16).setTextAlignment(TextAlignment.CENTER);
            documento.add(logo);
            Paragraph nombre = new Paragraph("Nombre: "+usuarioActual.getNombre()).setBold().setFontSize(14);
            documento.add(nombre);
            Paragraph apellido = new Paragraph("Apellido: "+usuarioActual.getApellido()).setBold().setFontSize(14);
            documento.add(apellido);
            Paragraph edad = new Paragraph("Edad: "+usuarioActual.getEdad()).setBold().setFontSize(14);
            documento.add(edad);
            ArrayList<Actividad> actividades = SharedPreferencesHelper.getActividades(usuarioActual.getIdLocal());
            int i=0;
            while(i<=Herramientas.TOTAL_ACT) {
                Table tabla = new Table(2);
                tabla.setHorizontalAlignment(HorizontalAlignment.CENTER);
                tabla.addCell(new Cell().add(new Paragraph("Imagen de muestra").setTextAlignment(TextAlignment.CENTER)));
                tabla.addCell(new Cell().add(new Paragraph("Imagen realizada").setTextAlignment(TextAlignment.CENTER)));
               for(int j = 0 ; j < 3; j++){
                    String uri = "@drawable/act_" +(i+1);
                    int imageResource = getResources().getIdentifier(uri, null, getActivity().getPackageName());
                    Drawable drawable = getResources().getDrawable(imageResource);
                    Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] bitmapImage = byteArrayOutputStream.toByteArray();
                    ImageData data = ImageDataFactory.create(bitmapImage);
                    Image img = new Image(data);
                    img.scaleAbsolute(200, 200);
                    tabla.addCell(new Cell().add(img.setAutoScale(false)));
                    if(i<actividades.size()) {
                        byte[] actImg = Base64.decode(actividades.get(i).getImagen(), Base64.DEFAULT);
                        ImageData act = ImageDataFactory.create(actImg);
                        Image imgact = new Image(act);
                        imgact.scaleAbsolute(200, 200);
                        tabla.addCell(new Cell().add(imgact.setAutoScale(false)));
                    }else{
                        Drawable draw = this.getResources().getDrawable(R.drawable.act_null);
                        Bitmap bit = ((BitmapDrawable)draw).getBitmap();
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bit.compress(Bitmap.CompressFormat.PNG,100,stream);
                        byte[] bitImage = stream.toByteArray();
                        ImageData dataImg = ImageDataFactory.create(bitImage);
                        Image imgNull = new Image(dataImg);
                        imgNull.scaleAbsolute(200,200);
                        tabla.addCell(new Cell().add(imgNull.setAutoScale(false)));
                    }
                    i++;
                }
                documento.add(tabla);
               if(i<Herramientas.TOTAL_ACT) {
                   documento.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
               }
            }
            documento.close();
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getContext(),"Documento generado en "+file.getAbsolutePath(),Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private boolean checkPermission() {
        // checking of permissions.
        int permission1 = ContextCompat.checkSelfPermission(getContext(), WRITE_EXTERNAL_STORAGE);
        int permission2 = ContextCompat.checkSelfPermission(getContext(), READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2 == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        // requesting permissions if not provided.
        ActivityCompat.requestPermissions(getActivity(), new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {
                boolean writeStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                if (writeStorage && readStorage) {
                    Log.i("Permission aswer: ","Permiso dado..");
                    generatePDF(SharedPreferencesHelper.getUsuarioActual(Herramientas.mainActivity));
                } else {
                    Toast.makeText(getContext(), "Permission Denegado.", Toast.LENGTH_SHORT).show();
                    Log.i("Permission aswer: ","Permiso denegado..");
                }
            }
        }
    }

}
