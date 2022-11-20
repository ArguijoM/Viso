package mainpackage.viso.ui.inicio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;

import mainpackage.viso.R;
import mainpackage.viso.databinding.FragmentInicioBinding;
import mainpackage.viso.herramientas.Herramientas;
import mainpackage.viso.herramientas.SharedPreferencesHelper;
import mainpackage.viso.herramientas.objetos.Actividad;
import mainpackage.viso.herramientas.objetos.UsuarioNino;


public class InicioFragment extends Fragment {
    private PieChart pieChart;

    ArrayList<String> valoresX = new ArrayList<>();
    ArrayList<Entry> valoresY = new ArrayList<>();
    ArrayList<Integer> colores = new ArrayList<>();
    private UsuarioNino usuarioActual=null;
    private ArrayList<Actividad> actividades;

    private FragmentInicioBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentInicioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        usuarioActual = SharedPreferencesHelper.getUsuarioActual(Herramientas.mainActivity);
        if(usuarioActual != null){
            ((TextView)root.findViewById(R.id.nombre_usuario)).setText("¡Hola "+ usuarioActual.getNombre()+"!");
            actividades = SharedPreferencesHelper.getActividades(usuarioActual.getIdLocal());
            if(actividades==null){
                actividades = new ArrayList<>();
            }
        }else{
            actividades = new ArrayList<>();
            ((TextView)root.findViewById(R.id.nombre_usuario)).setText("Error");
        }

        pieChart = (PieChart)root.findViewById(R.id.GraficaPie);
        generarGrafica(pieChart, actividades.size());
        ((TextView)root.findViewById(R.id.actividades_realizadas)).setText(""+ actividades.size());
        ((TextView)root.findViewById(R.id.actividades_faltantes)).setText(""+(Herramientas.TOTAL_ACT-actividades.size()));
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
    public void generarGrafica(PieChart pieChart,int realizadas){
        pieChart.setHoleRadius(40f);
        pieChart.setDrawXValues(true);
        pieChart.setDrawYValues(true);
        pieChart.setRotationEnabled(true);
        pieChart.animateXY(1500, 1500);
        valoresX.add("Realizadas");
        valoresX.add("Faltantes");
        int porcentaje= Math.round((realizadas * 100) / Herramientas.TOTAL_ACT);
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
