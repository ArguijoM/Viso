package mainpackage.viso.ui.inicio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;

import mainpackage.viso.R;
import mainpackage.viso.herramientas.Herramientas;
import mainpackage.viso.herramientas.SharedPreferencesHelper;
import mainpackage.viso.herramientas.objetos.UsuarioNino;

public class InicioActivity extends AppCompatActivity {
    private PieChart pieChart;
    private int realizadas;
    ArrayList<String> valoresX = new ArrayList<>();
    ArrayList<Entry> valoresY = new ArrayList<>();
    ArrayList<Integer> colores = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        UsuarioNino usuarioActual = SharedPreferencesHelper.getUsuarioActual(Herramientas.mainActivity);
        if(usuarioActual== null){
            this.realizadas=0;
            ((TextView)findViewById(R.id.nombre_usuario)).setText("¡Hola Error!");
        }else if(usuarioActual.getActividades()==null){
            this.realizadas=99;
        }else{
            this.realizadas=usuarioActual.getActividades().size();
            ((TextView)findViewById(R.id.nombre_usuario)).setText("¡Hola "+ usuarioActual.getNombre()+"!");

        }


        pieChart = findViewById(R.id.GraficaPie);
        generarGrafica(pieChart, realizadas);
        ((TextView)findViewById(R.id.actividades_realizadas)).setText(""+ realizadas);
        ((TextView)findViewById(R.id.actividades_faltantes)).setText(""+(Herramientas.TOTAL_ACT-realizadas));

    }
    public void generarGrafica(PieChart pieChart,int realizadas){
        pieChart.setHoleRadius(40f);
        pieChart.setDrawXValues(true);
        pieChart.setDrawYValues(true);
        pieChart.setRotationEnabled(true);
        pieChart.animateXY(1500, 1500);
        valoresX.add("Realizadas");
        valoresX.add("Faltantes");
        int porcentaje= Math.round((realizadas * 100) / 26);
        valoresY.add(new Entry(porcentaje, 0));
        valoresY.add(new Entry(100-porcentaje, 1));
        colores.add(getResources().getColor(R.color.primary02));
        colores.add(getResources().getColor(R.color.grey));
        PieDataSet set = new PieDataSet(valoresY, "Resultados");
        set.setSliceSpace(5f);
        set.setColors(colores);
        PieData data = new PieData(valoresX, set);
        pieChart.setData(data);
        pieChart.highlightValues(null);
        pieChart.invalidate();
        pieChart.setDescription("");
        pieChart.setDrawLegend(false);
    }
}