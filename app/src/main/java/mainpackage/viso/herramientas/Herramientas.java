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
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
        Bitmap newImage = copyImage(image);
//*********************************----------IZQUIERDA--------------******************************
        for (int i = 0; i < image.getWidth(); i++) {//ANCHO
            for (int j = 0; j < image.getHeight(); j++) {//ALTO
                if (image.getPixel(i,j) < (-1) && aux < 1) {
                   if (!verify(image, i, j)) {
                        aux++;
                        x1 = i;
                       // image = draw(image,i,j);
                        Log.i("Punto: ","PUNTO1: Aqui: ANCHO(" + i + ") || ALTO(" + j + ")");
                        //System.out.println("PUNTO1: Aqui: ANCHO(" + i + ") || ALTO(" + j + ")");
                    }
                }
            }
        }
//**************************************************************************************************

//*********************************----------ARRIBA--------------******************************
        aux = 0;
        for (int i = 0; i < image.getHeight(); i++) {//ALTO
            for (int j = 0; j < image.getWidth(); j++) {//ANCHO
                if (image.getPixel(j, i) < (-1) && aux < 1) {
                    if (!verify(image, j, i)) {
                        aux++;
                   // image = draw(image,j,i);

                    Log.i("Punto: ","PUNTO 2 :Aqui " + j + " || " + i);
                        // System.out.println("PUNTO 2 :Aqui " + j + " || " + i);
                        y1 = i;
                    }
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
                    if (!verify(image, i, j)) {
                        aux++;
                        x2 = i;
                    //image = draw(image,i,j);

                    Log.i("Punto: ","PUNTO 3: Aqui: ANCHO(" + i + ") || ALTO(" + j + ")");
                        //System.out.println("PUNTO 3: Aqui: ANCHO(" + i + ") || ALTO(" + j + ")");
                    }
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
                    if (!verify(image, j, i)) {
                        aux++;
                        y2 = i;
                    //image = draw(image,j,i);

                    Log.i("Punto: ","PUNTO4: Aqui: ANCHO(" + j + ") || ALTO(" + i + ")");
                        //System.out.println("PUNTO4: Aqui: ANCHO(" + j + ") || ALTO(" + i + ")");
                        break;
                    }
                }
            }
        }

//**********************************************************************************************************

        Bitmap img = Bitmap.createBitmap(image, x1, y1, (x2-x1), (y2-y1));
        img = Bitmap.createScaledBitmap(img,newImage.getWidth(),newImage.getHeight(),false);

        return img;
    }

    public static boolean verify(Bitmap image, int x, int y) {
        int aux = 4;
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
        if (count <= 5) {//ES UN PUNTO
            return true;
        } else {
            return false;
        }
    }

    public static int calificar(Bitmap img, int act){
        int resourceId1 = mainActivity.getResources().getIdentifier("act_" + act, "raw", Herramientas.mainActivity.getPackageName());
        Drawable drawable1 =mainActivity.getResources().getDrawable(resourceId1);
        int resourceId2 = mainActivity.getResources().getIdentifier("act_" + act, "drawable", Herramientas.mainActivity.getPackageName());
        Drawable drawable2 =mainActivity.getResources().getDrawable(resourceId2);

        Bitmap imgMask = Bitmap.createScaledBitmap(((BitmapDrawable) drawable1).getBitmap(),img.getWidth(),img.getHeight(),false);
        Bitmap imgOrigin  = Bitmap.createScaledBitmap(((BitmapDrawable) drawable2).getBitmap(),img.getWidth(),img.getHeight(),false);

        Log.i("IMG TOMADA: ","ANCHO: "+img.getWidth()+" ALTO: "+img.getHeight());
        Log.i("IMG ORIGINAL: ","ANCHO: "+imgOrigin.getWidth()+" ALTO: "+imgOrigin.getHeight());
        Log.i("IMG MASK: ","ANCHO: "+imgMask.getWidth()+" ALTO: "+imgMask.getHeight());

        int sum = 0;
        for (int a = 0; a <imgOrigin.getWidth(); a++) {
            for (int b = 0; b < imgOrigin.getHeight(); b++) {
                if (img.getPixel(a,b) <(-1) && imgMask.getPixel(a,b)<(-1)) {
                    sum++;
                }
                if(img.getPixel(a,b)==(-1) && imgOrigin.getPixel(a,b)==(-1)){
                    sum++;
                }
            }
        }

        Log.i("CALIFICACION"," "+sum);
        return sum;
    }
    public static int getHeight(int id){
        switch (id){
            case 1:
                return 537;
            case 2:
                return 541;
            case 3:
                return 538;
            case 4:
                return 541;
            case 5:
                return 535;
            case 6:
                return 521;
            case 7:
                return 617;
            case 8:
                return 517;
            case 9:
                return 502;
            case 10:
                return 508;
            case 11:
                return 224;
            case 12:
                return 364;
            case 13:
                return 456;
            case 14:
                return 530;
            case 15:
                return 666;
            case 16:
                return 630;
            case 17:
                return 264;
            case 18:
                return 524;
        }
        return 0;
    }

}
