package mainpackage.viso.ui.configuracion;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
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

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.colors.DeviceRgb;
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
import java.util.ArrayList;

import mainpackage.viso.R;
import mainpackage.viso.databinding.FragmentConfiguracionBinding;
import mainpackage.viso.herramientas.database.DatabaseHelper;
import mainpackage.viso.herramientas.Herramientas;
import mainpackage.viso.herramientas.SharedPreferencesHelper;
import mainpackage.viso.herramientas.database.VolleyCallBack;
import mainpackage.viso.herramientas.objetos.Actividad;
import mainpackage.viso.herramientas.objetos.UsuarioAdulto;
import mainpackage.viso.herramientas.objetos.UsuarioNino;
import mainpackage.viso.ui.configuracion.borrar.BorrarFragment;
import mainpackage.viso.ui.cuenta.registro.adulto.Login;


public class ConfiguracionFragment extends Fragment implements View.OnClickListener {
    private FragmentConfiguracionBinding binding;
    RequestQueue requestQueue;
    TextView opcion01, opcion02,opcion03,opcion04;
    private Context context;
    private ProgressBar progressBar;
    private static final int PERMISSION_REQUEST_CODE = 200;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentConfiguracionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        requestQueue = Volley.newRequestQueue(getContext());
        context = getContext();
        opcion01 = root.findViewById(R.id.opcion_01);
        opcion02 = root.findViewById(R.id.opcion_02);
        opcion03 = root.findViewById(R.id.opcion_03);
        opcion04 = root.findViewById(R.id.opcion_04);
        progressBar = root.findViewById(R.id.progress_bar_config);
        progressBar.getIndeterminateDrawable()
                .setColorFilter(getResources().getColor(R.color.primary02), PorterDuff.Mode.SRC_IN);
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
                            progressBar.setVisibility(View.VISIBLE);
                            DatabaseHelper db = new DatabaseHelper(getContext(),progressBar);
                            ArrayList<UsuarioNino> users = SharedPreferencesHelper.getUsuarios();
                            for(int i=0; i<users.size();i++) {
                                db.readActividad(new VolleyCallBack() {
                                    @Override
                                    public void onSuccess(String result) {

                                    }
                                },users.get(i));
                            }
                            //Toast.makeText(getContext(),"No hay actividades para guardar",Toast.LENGTH_LONG).show();
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
                                UsuarioAdulto usuarioAdulto = SharedPreferencesHelper.getUsuarioAdulto(Herramientas.mainActivity);
                                if(usuarioAdulto.getIdServidor()!=0) {
                                    Intent intent = new Intent(context, Login.class);
                                    intent.putExtra("email", usuarioAdulto.getEmail());
                                    intent.putExtra("contrasena", usuarioAdulto.getContrasena());
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(getContext(),"No hay información guardada en el servidor",Toast.LENGTH_SHORT).show();
                                }
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
                                if (checkPermission()) {
                                    progressBar.setVisibility(View.VISIBLE);
                                    generatePDF(user);
                                } else {
                                    requestPermission();
                                }
                                progressBar.setVisibility(View.GONE);
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
            Table encabezado = new Table(1);
            Cell celTitle = new Cell().add(new Paragraph("\n"+"Viso").setBold().setFontSize(16).setTextAlignment(TextAlignment.CENTER));
            celTitle.setPaddingLeft(200);
            celTitle.setPaddingRight(200);
            celTitle.setBorder(null);
            encabezado.addCell(celTitle);
            encabezado.setHorizontalAlignment(HorizontalAlignment.CENTER);
            documento.add(encabezado);
            Table info = new Table(3);
            Cell info_nombre = new Cell().add(new Paragraph("Nombre: "+usuarioActual.getNombre()+" "+usuarioActual.getApellido()).setFontSize(12).setTextAlignment(TextAlignment.CENTER));
            info_nombre.setBorder(null);
            info_nombre.setPaddingLeft(20);
            info_nombre.setPaddingRight(20);

            Cell info_edad = new Cell().add(new Paragraph("Edad: "+usuarioActual.getEdad()).setFontSize(12).setTextAlignment(TextAlignment.CENTER));
            info_edad.setBorder(null);
            info_edad.setPaddingLeft(20);
            info_edad.setPaddingRight(20);
            String[] aux=usuarioActual.getProfile().split(" Y ");
            Cell info_sexo;
            if((aux[0]).equals("H")){
                info_sexo= new Cell().add(new Paragraph("Sexo: Masculino").setFontSize(12).setTextAlignment(TextAlignment.CENTER));
            }else{
                info_sexo= new Cell().add(new Paragraph("Sexo: Femenino"+usuarioActual.getProfile()).setFontSize(12).setTextAlignment(TextAlignment.CENTER));
            }
            info_sexo.setBorder(null);
            info_sexo.setPaddingLeft(20);
            info_sexo.setPaddingRight(20);
            info.addCell(info_nombre);
            info.addCell(info_edad);
            info.addCell(info_sexo);
            info.setHorizontalAlignment(HorizontalAlignment.CENTER);
            info.setPaddingBottom(200f);
            documento.add(info);

            Table acts = new Table(4);
            ArrayList<Actividad> activities = SharedPreferencesHelper.getActividades(usuarioActual.getIdLocal());
            String aciertos="",fallos="";
            for(int j=0;j<activities.size();j++){
                if(Herramientas.getEvaluacion((j+1),activities.get(j).getCalificacion())==true){
                    aciertos+=(j+1)+";";
                }else{
                    fallos+=(j+1)+";";
                }
            }

            Cell acts_realizadas = new Cell().add(new Paragraph("Act. realizadas: "+activities.size()).setFontSize(12).setTextAlignment(TextAlignment.LEFT));
            acts_realizadas.setBorder(null);
            acts_realizadas.setPaddingRight(20);
            Cell acts_norealizadas = new Cell().add(new Paragraph("Act. faltantes: "+(Herramientas.TOTAL_ACT-activities.size())).setFontSize(12).setTextAlignment(TextAlignment.LEFT));
            acts_norealizadas.setBorder(null);
            acts_norealizadas.setPaddingRight(20);
            Cell acts_validas = new Cell().add(new Paragraph("Act. válidas: "+aciertos).setFontSize(12).setTextAlignment(TextAlignment.LEFT));
            acts_validas.setPaddingRight(20);
            acts_validas.setBorder(null);
            acts_validas.setPaddingRight(20);
            Cell acts_invalidas = new Cell().add(new Paragraph("Act. no válidas: "+fallos).setFontSize(12).setTextAlignment(TextAlignment.LEFT));
            acts_invalidas.setBorder(null);
            acts.addCell(acts_realizadas);acts.addCell(acts_norealizadas);acts.addCell(acts_validas);acts.addCell(acts_invalidas);
            acts.setHorizontalAlignment(HorizontalAlignment.CENTER);
            documento.add(acts);
            Table datos = new Table(2);
            int puntaje_bruto = Herramientas.puntajeBruto(activities,usuarioActual);
            Cell bruto = new Cell().add(new Paragraph("Puntaje bruto: "+puntaje_bruto).setFontSize(12).setTextAlignment(TextAlignment.LEFT));
            bruto.setBorder(null);
            bruto.setPaddingRight(20);
            boolean estado = Herramientas.getClasificacion(puntaje_bruto,usuarioActual.getEdad());
            Cell clasificacion;
            if(puntaje_bruto==0){
                clasificacion = new Cell().add(new Paragraph("Estado: Sin calificar").setFontSize(12).setTextAlignment(TextAlignment.LEFT));
                clasificacion.setBorder(null);
                clasificacion.setPaddingRight(20);
            }else {
                if (estado) {
                    com.itextpdf.kernel.colors.Color myColor = new DeviceRgb(0, 255, 0);
                    clasificacion = new Cell().add(new Paragraph("Estado: En rango").setFontSize(12).setTextAlignment(TextAlignment.LEFT).setFontColor(myColor));
                    clasificacion.setBorder(null);
                    clasificacion.setPaddingRight(20);
                } else {
                    com.itextpdf.kernel.colors.Color myColor = new DeviceRgb(0, 255, 0);
                    clasificacion = new Cell().add(new Paragraph("Estado: Fuera de rango").setFontSize(12).setTextAlignment(TextAlignment.LEFT).setFontColor(myColor));
                    clasificacion.setBorder(null);
                    clasificacion.setPaddingRight(20);
                }
            }
            datos.addCell(bruto);
            datos.addCell(clasificacion);
            datos.setHorizontalAlignment(HorizontalAlignment.CENTER);
            documento.add(datos);
            ArrayList<Actividad> actividades = SharedPreferencesHelper.getActividades(usuarioActual.getIdLocal());

            int i=0;
            while(i<Herramientas.TOTAL_ACT) {
                Table tabla = new Table(2);
                tabla.setHorizontalAlignment(HorizontalAlignment.CENTER);
                tabla.addCell(new Cell().add(new Paragraph("Imagen de muestra").setTextAlignment(TextAlignment.CENTER)));
                tabla.addCell(new Cell().add(new Paragraph("Imagen realizada").setTextAlignment(TextAlignment.CENTER)));
               for(int j = 0 ; j < 3; j++){
                    String uri = "@drawable/act_"+(i+1);
                    int imageResource = getResources().getIdentifier(uri, null, Herramientas.mainActivity.getPackageName());
                    Drawable drawable = getResources().getDrawable(imageResource);
                    Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    byte[] bitmapImage = byteArrayOutputStream.toByteArray();
                    ImageData data = ImageDataFactory.create(bitmapImage);
                    Image img = new Image(data);
                    if((i+1)==11 && (i+1)==17){
                        img.scaleAbsolute(200, 50);
                    }else{
                        img.scaleAbsolute(200,200);
                    }
                    tabla.addCell(new Cell().add(img.setAutoScale(true)));
                    if(i<actividades.size()) {
                        byte[] actImg = Base64.decode(actividades.get(i).getImagen(), Base64.DEFAULT);
                        ImageData act = ImageDataFactory.create(actImg);
                        Image imgact = new Image(act);
                        if((i+1)==11 && (i+1)==17){
                            imgact.scaleAbsolute(200, 50);
                        }else{
                            imgact.scaleAbsolute(200, 200);
                        }
                        tabla.addCell(new Cell().add(imgact.setAutoScale(true)));

                    }else{
                        Drawable draw = this.getResources().getDrawable(R.drawable.act_null);
                        Bitmap bit = ((BitmapDrawable)draw).getBitmap();
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        bit.compress(Bitmap.CompressFormat.PNG,100,stream);
                        byte[] bitImage = stream.toByteArray();
                        ImageData dataImg = ImageDataFactory.create(bitImage);
                        Image imgNull = new Image(dataImg);
                        if((i+1)==11 && (i+1)==17){
                            imgNull.scaleAbsolute(200, 50);
                        }else{
                            imgNull.scaleAbsolute(200, 200);
                        }
                        tabla.addCell(new Cell().add(imgNull.setAutoScale(true)));
                    }
                   i++;
                }
                documento.add(tabla);
               if(i<(Herramientas.TOTAL_ACT)) {
                   documento.add(new AreaBreak(AreaBreakType.NEXT_PAGE));
               }
            }
            documento.close();
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
