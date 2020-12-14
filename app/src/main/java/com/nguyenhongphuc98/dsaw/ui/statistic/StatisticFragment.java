package com.nguyenhongphuc98.dsaw.ui.statistic;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.anychart.APIlib;
import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Align;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.LegendLayout;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.nguyenhongphuc98.dsaw.R;
import com.nguyenhongphuc98.dsaw.data.model.Area;
import com.nguyenhongphuc98.dsaw.data.model.Case;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StatisticFragment extends Fragment {

    private StatisticViewModel mViewModel;

    private Spinner spinner;
    private ArrayAdapter areasAdaptor;

    private AnyChartView pieChartView;
    private Pie pieChart;

    private AnyChartView columnChartView;
    private Cartesian cartesian;

    // list name area to dropdown
    ArrayList<String> areasname = new ArrayList<>();

    public static StatisticFragment newInstance() {
        return new StatisticFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistic, container, false);
        setupView(view);
        setupAction();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(StatisticViewModel.class);

//        mViewModel.getPie().observe(this, new Observer<List<DataEntry>>() {
//            @Override
//            public void onChanged(List<DataEntry> dataEntries) {
//                APIlib.getInstance().setActiveAnyChartView(pieChartView);
//                pieChart.data(dataEntries);
//            }
//        });

        mViewModel.getListCases().observe(this, new Observer<List<Case>>() {
            @Override
            public void onChanged(List<Case> cases) {

                // bat dau dem :p
                int f0 = 0;
                int f1 = 0;
                int f2 = 0;
                int f3 = 0;

                for (Case c : cases) {
                    if (c.getF().equals("F0"))
                        f0++;
                    if (c.getF().equals("F1"))
                        f1++;
                    if (c.getF().equals("F2"))
                        f2++;
                    if (c.getF().equals("F3"))
                        f3++;
                }

                List<DataEntry> caseData;
                caseData = new ArrayList<>();
                caseData.add(new ValueDataEntry("F0 - Dương tính (" + f0 + ")", f0));
                caseData.add(new ValueDataEntry("F1 - Tiếp xúc F0 (" + f1 + ")", f1));
                caseData.add(new ValueDataEntry("F2 - Di chuyển từ vùng dịch (" + f2 + ")", f2));
                caseData.add(new ValueDataEntry("F3 - Tiếp xúc F1 (" + f3 + ")", f3));

                APIlib.getInstance().setActiveAnyChartView(pieChartView);
                pieChart.data(caseData);
            }
        });

        mViewModel.getColumn().observe(this, new Observer<List<DataEntry>>() {
            @Override
            public void onChanged(List<DataEntry> dataEntries) {
                APIlib.getInstance().setActiveAnyChartView(columnChartView);
                Column column = cartesian.column(dataEntries);
                column.tooltip()
                        .titleFormat("{%X}")
                        .position(Position.CENTER_BOTTOM)
                        .anchor(Anchor.CENTER_BOTTOM)
                        .offsetX(0d)
                        .offsetY(5d)
                        .format("${%Value}{groupsSeparator: }");
            }
        });

        mViewModel.getListAreas().observe(this, new Observer<List<Area>>() {
            @Override
            public void onChanged(List<Area> areas) {
                areasname.clear();
                areasname.add("Xem tất cả");
                for (Area a: areas) {
                    areasname.add(a.getName());
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),R.layout.support_simple_spinner_dropdown_item,areasname);
                spinner.setAdapter(adapter);
            }
        });
    }

    void setupView(View view) {
        spinner = view.findViewById(R.id.statistic_spinner_area);

        pieChartView = view.findViewById(R.id.statistic_pie_chart);
        APIlib.getInstance().setActiveAnyChartView(pieChartView);
        pieChart = AnyChart.pie();
        //pieChart.title("Biểu đồ dịch bệnh (người)");

        pieChart.labels().position("outside");

        pieChart.legend().title().enabled(true);
        pieChart.legend().title()
                .text("Các loại được xác định")
                .padding(0d, 0d, 10d, 0d);

        pieChart.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        pieChartView.setChart(pieChart);

        //column chart
        columnChartView = view.findViewById(R.id.statistic_column_chart);
        APIlib.getInstance().setActiveAnyChartView(columnChartView);
        cartesian = AnyChart.column();
        cartesian.animation(true);
        //cartesian.title("Thông tin dịch tễ");

        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("{%Value}{groupsSeparator: }");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

        cartesian.xAxis(0).title("Biểu hiện");
        cartesian.yAxis(0).title("Số lượng (người)");
        columnChartView.setChart(cartesian);
    }

    void setupAction() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String areaName = parent.getItemAtPosition(position).toString();
                if(areaName.equals("Xem tất cả"))
                {
                    mViewModel.fetchPieDataAllArea();
                }
                else {
                    for (Area a : mViewModel.getListAreas().getValue()) {
                        if (a.getName().equals(areaName)) {
                            mViewModel.fetchPieDataFor(a.getId());
                            return;
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
