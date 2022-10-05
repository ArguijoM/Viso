package mainpackage.viso.herramientas;


import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.media.Image;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Scanner;

import mainpackage.viso.R;
import mainpackage.viso.herramientas.objetos.Actividad;
import mainpackage.viso.herramientas.objetos.UsuarioNino;

public class Herramientas {
    public static int TOTAL_ACT = 18;
    public static Activity mainActivity;

    public static String saveToInternalStorage(Bitmap bitmapImage, String name){
        ContextWrapper cw = new ContextWrapper(mainActivity.getBaseContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("visoActivities", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,name);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    public static Bitmap loadImageFromStorage(String path,String name) {
        Bitmap b = null;
        try {
            File f=new File(path, name);
             b = BitmapFactory.decodeStream(new FileInputStream(f));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return b;
    }
    public static String loadImageFromStorageString(String path,String name) {
        Bitmap b = null;

        try {
            File f=new File(path, name);
            b = BitmapFactory.decodeStream(new FileInputStream(f));
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        b.recycle();
        return android.util.Base64.encodeToString(byteArray, Base64.DEFAULT);
    }
    public static Bitmap blanco_y_negro(Bitmap bitmap) {

        Bitmap bwBitmap = Bitmap.createBitmap( bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.RGB_565 );
        float[] hsv = new float[ 3 ];
        for( int col = 0; col < bitmap.getWidth(); col++ ) {
            for( int row = 0; row < bitmap.getHeight(); row++ ) {
                Color.colorToHSV( bitmap.getPixel( col, row ), hsv );
                if( hsv[ 2 ] > 0.5f ) {
                    bwBitmap.setPixel( col, row, 0xffffffff );
                } else {
                    bwBitmap.setPixel( col, row, 0xff000000 );
                }
            }
        }
        return bwBitmap;
    }
    public static Bitmap copyImage(Bitmap bmp1){
        Bitmap bmp2 = bmp1.copy(bmp1.getConfig(), true);
        return bmp2;
    }
    public static Bitmap recortarImagen(Bitmap image){
        int aux = 0;
        int x1 = 0, x2 = 0, y1 = 0, y2 = 0;
//*********************************----------IZQUIERDA--------------******************************
        for (int i = 0; i < image.getWidth(); i++) {//ANCHO
            for (int j = 0; j < image.getHeight(); j++) {//ALTO
                if (image.getPixel(i,j) < (-1) && aux < 1) {
                 //  if (!verify(image, i, j)) {
                        aux++;
                        x1 = i;
                       // image = draw(image,i,j);
                        Log.i("Punto: ","PUNTO1: Aqui: ANCHO(" + i + ") || ALTO(" + j + ")");
                        //System.out.println("PUNTO1: Aqui: ANCHO(" + i + ") || ALTO(" + j + ")");
                  //  }
                }
            }
        }
//**************************************************************************************************

//*********************************----------ARRIBA--------------******************************
        aux = 0;
        for (int i = 0; i < image.getHeight(); i++) {//ALTO
            for (int j = 0; j < image.getWidth(); j++) {//ANCHO
                if (image.getPixel(j, i) < (-1) && aux < 1) {
             //       if (!verify(image, j, i)) {
                        aux++;
                   // image = draw(image,j,i);

                    Log.i("Punto: ","PUNTO 2 :Aqui " + j + " || " + i);
                        // System.out.println("PUNTO 2 :Aqui " + j + " || " + i);
                        y1 = i;
               //     }
                }
            }
        }
//**************************************************************************************************

//*********************************----------DERECHA--------------******************************
        aux = 0;
        for (int i = (image.getWidth() - 1); i >= 0; i--) {//ANCHO
            for (int j = (image.getHeight() - 1); j >= 0; j--) {//ALTO
                //System.out.println("X: " + i + " || Y: " + j + " RGB: " + image.getRGB(i, j));
                if (image.getPixel(i, j) < (-1) && aux < 1) {
             //       if (!verify(image, i, j)) {
                        aux++;
                        x2 = i;
                    //image = draw(image,i,j);

                    Log.i("Punto: ","PUNTO 3: Aqui: ANCHO(" + i + ") || ALTO(" + j + ")");
                        //System.out.println("PUNTO 3: Aqui: ANCHO(" + i + ") || ALTO(" + j + ")");
             //       }
                }
            }
        }
//************************************************************************************************************
//*********************************----------ABAJO--------------******************************
        aux = 0;
        for (int i = (image.getHeight() - 1); i >= 0; i--) {//ALTO
            for (int j = (image.getWidth() - 1); j >= 0; j--) {//ANCHO
                // System.out.println("X: " + i + " || Y: " + j + " RGB: " + image.getRGB(i, j));
                if (image.getPixel(j, i) < (-1) && aux < 1) {
         //           if (!verify(image, j, i)) {
                        aux++;
                        y2 = i;
                    //image = draw(image,j,i);

                    Log.i("Punto: ","PUNTO4: Aqui: ANCHO(" + j + ") || ALTO(" + i + ")");
                        //System.out.println("PUNTO4: Aqui: ANCHO(" + j + ") || ALTO(" + i + ")");
                        break;
           //         }
                }
            }
        }

//**********************************************************************************************************

        Bitmap img = Bitmap.createBitmap(image, x1, y1, (x2-x1), (y2-y1));

        return img;
    }

    public static boolean verify(Bitmap image, int x, int y) {
        int aux = 10;
        int count = 0;
        try {
            for (int i = x - aux; i <= x + aux; i++) {
                for (int j = y - aux; j <= y + aux; j++) {
                    if (image.getPixel(i, j) < (-1)) {
                        count++;
                    }
                }
            }
        } catch (Exception e) {

        }
        if (count <= 150) {//ES UN PUNTO
            return true;
        } else {
            return false;
        }
    }
    public static Bitmap draw(Bitmap image, int x, int y) {
        int aux = 5;
        int count = 0;
        try {
            for (int i = x - aux; i <= x + aux; i++) {
                for (int j = y - aux; j <= y + aux; j++) {
                    image.setPixel( i, j, 0xff000000 );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("" + count);
        return image;
    }

    public static ArrayList<String[]> getActivityInstance(int n){
        ArrayList<String[]> aux = new ArrayList<String[]>();
        try {
            InputStream is = mainActivity.getResources().openRawResource(n);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] prueba = line.split(" ");
                aux.add(prueba);
            }
            is.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("INSTANCIA: ","LARGO: "+aux.size()+" ANCHO: "+aux.get(0).length);
        return aux;
    }

    public static ArrayList<String[]> leerInstancia(String name) {
        ArrayList<String[]> aux = new ArrayList<String[]>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(mainActivity.openFileInput(name)));
            String readLine = null;
            while ((readLine = br.readLine()) != null) {
                String[] prueba = readLine.split(" ");
                aux.add(prueba);
            }
            br.close();
        }catch (IOException ex){

        }
        return aux;
    }
    public static void generarInstancia(Bitmap image, int act) {
        try {
            UsuarioNino usuarioActual = SharedPreferencesHelper.getUsuarioActual(mainActivity);
            String name = usuarioActual.getNombre()+"_act_"+act+".txt";
            OutputStreamWriter file = new OutputStreamWriter(mainActivity.openFileOutput(name, Context.MODE_PRIVATE));
            for (int i = 0; i < image.getWidth(); i++) {
                String line = "";
                for (int j = 0; j < image.getHeight(); j++) {
                    if (image.getPixel(i, j) < (-1)) {
                        line += "1 ";
                    } else {
                        line += "0 ";
                    }
                }
                file.write(line + "\n");
            }
            file.close();
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }

}
