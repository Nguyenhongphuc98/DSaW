package com.nguyenhongphuc98.dsaw.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.fragment.NavHostFragment;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Line;
import com.anychart.data.Mapping;
import com.anychart.data.Set;
import com.anychart.enums.Anchor;
import com.anychart.enums.MarkerType;
import com.anychart.enums.TooltipPositionMode;
import com.anychart.graphics.vector.Stroke;
import com.nguyenhongphuc98.dsaw.R;
import com.nguyenhongphuc98.dsaw.data.DataManager;
import com.nguyenhongphuc98.dsaw.data.model.PublicData;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private ImageButton changeAreaBtn;
    private Button mapVisualizeBtn;

    private  AnyChartView anyChartView;

    private TextView areaTv;
    private TextView updateTimeTv;

    Cartesian cartesian;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        setupView(root);
        homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        setupAction();
        setupObserver();
        setupChart();
        return root;
    }

    private void setupView(View view) {
        anyChartView = view.findViewById(R.id.home_line_chart);
        changeAreaBtn = view.findViewById(R.id.home_btn_change_area);
        mapVisualizeBtn = view.findViewById(R.id.home_btn_visualine);
        areaTv = view.findViewById(R.id.home_tv_area);
        updateTimeTv = view.findViewById(R.id.home_tv_update_time);
    }

    private void setupAction() {
        changeAreaBtn.setOnClickListener(v -> homeViewModel.changeArea());

        mapVisualizeBtn.setOnClickListener(v -> {
            Toast.makeText(getContext(),"go to visualize",Toast.LENGTH_SHORT).show();
            NavHostFragment.findNavController(getParentFragment()).navigate(R.id.go_to_mapVisualize);
        });
    }

    private void setupObserver() {
        homeViewModel.getArea().observe(this, s -> areaTv.setText(s));
    }

    public void setupChart() {

        cartesian = AnyChart.line();
        cartesian.animation(true);
        cartesian.padding(10d, 20d, 5d, 10d);

        cartesian.crosshair().enabled(true);
        cartesian.crosshair()
                .yLabel(true)
                .yStroke((Stroke) null, null, null, (String) null, (String) null);

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);

        cartesian.title("Biều đồ diễn biến dịch bệnh.");

        cartesian.yAxis(0).title("Số lượng (người)");
        cartesian.xAxis(0).title("Thời gian (ngày)");
        cartesian.xAxis(0).labels().padding(7d, 7d, 7d, 7d);
        cartesian.yAxis(0).labels().padding(7d, 0d, 7d, 3d);

        cartesian.legend().enabled(true);
        cartesian.legend().fontSize(13d);
        cartesian.legend().padding(0d, 0d, 10d, 0d);

        //fake data and presetup
        List<DataEntry> seriesData = new ArrayList<>();

        seriesData.add(new CustomDataEntry("",0,0,0));

        final Set set = Set.instantiate();

        set.data(seriesData);
        Mapping series1Mapping = set.mapAs("{ x: 'x', value: 'value' }");
        Mapping series2Mapping = set.mapAs("{ x: 'x', value: 'value2' }");
        Mapping series3Mapping = set.mapAs("{ x: 'x', value: 'value3' }");

        Line series1 = cartesian.line(series1Mapping);
        series1.name("Nhiễm");
        series1.color("#FCA903");
        series1.hovered().markers().enabled(true);
        series1.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series1.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series2 = cartesian.line(series2Mapping);
        series2.name("Tử vong");
        series2.color("#FC2003");
        series2.hovered().markers().enabled(true);
        series2.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series2.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);

        Line series3 = cartesian.line(series3Mapping);
        series3.name("Hồi phục");
        series3.color("#04D446");
        series3.hovered().markers().enabled(true);
        series3.hovered().markers()
                .type(MarkerType.CIRCLE)
                .size(4d);
        series3.tooltip()
                .position("right")
                .anchor(Anchor.LEFT_CENTER)
                .offsetX(5d)
                .offsetY(5d);


         homeViewModel.getData().observe(this, publicData -> {
             List<DataEntry> seriesData1 = new ArrayList<>();

             for (PublicData p : publicData) {
                 seriesData1.add(new CustomDataEntry(p.getUpdate_date().split("-")[2],
                         Integer.parseInt(p.getNum_confirmed()),
                         Integer.parseInt(p.getNum_death()),
                         Integer.parseInt(p.getNum_recovered())));
             }

             set.data(seriesData1);


             updateTimeTv.setText(publicData.get(publicData.size()-1).getUpdate_time());
         });
        anyChartView.setChart(cartesian);
    }

    public class CustomDataEntry extends ValueDataEntry {

        CustomDataEntry(String x, Number value, Number value2, Number value3) {
            super(x, value);
            setValue("value2", value2);
            setValue("value3", value3);
        }

    }
}