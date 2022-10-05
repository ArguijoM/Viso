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


public class InicioFragment extends Fragment {
    private PieChart pieChart;

    ArrayList<String> valoresX = new ArrayList<>();
    ArrayList<Entry> valoresY = new ArrayList<>();
    ArrayList<Integer> colores = new ArrayList<>();

    private InicioViewModel InicioViewModel;
    private FragmentInicioBinding binding;
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        InicioViewModel = new ViewModelProvider(this).get(InicioViewModel.class);

        binding = FragmentInicioBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        if(InicioViewModel.getUsuarioActual()== null){
            InicioViewModel.setRealizadas(0);
            ((TextView)root.findViewById(R.id.nombre_usuario)).setText("¡Hola Error!");
        }else if(InicioViewModel.getUsuarioActual().getActividades()==null){
            InicioViewModel.setRealizadas(99);
        }else{
            InicioViewModel.setRealizadas(InicioViewModel.getUsuarioActual().getActividades().size());
            ((TextView)root.findViewById(R.id.nombre_usuario)).setText("¡Hola "+ InicioViewModel.getUsuarioActual().getNombre()+"!");

        }
        pieChart = (PieChart)root.findViewById(R.id.GraficaPie);
        generarGrafica(pieChart, InicioViewModel.getRealizadas());
        ((TextView)root.findViewById(R.id.actividades_realizadas)).setText(""+ InicioViewModel.getRealizadas());
        ((TextView)root.findViewById(R.id.actividades_faltantes)).setText(""+(Herramientas.TOTAL_ACT-InicioViewModel.getRealizadas()));
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
