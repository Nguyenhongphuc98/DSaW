package com.nguyenhongphuc98.dsaw.ui.route;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.nguyenhongphuc98.dsaw.R;
import com.nguyenhongphuc98.dsaw.adaptor.RouteAdaptor;
import com.nguyenhongphuc98.dsaw.data.DataCenter;
import com.nguyenhongphuc98.dsaw.data.DataManager;
import com.nguyenhongphuc98.dsaw.data.model.RouteData;
import com.nguyenhongphuc98.dsaw.data.model.TrackingStatus;

import java.util.ArrayList;
import java.util.List;

public class RouteFragment extends Fragment {

    private RouteViewModel mViewModel;

    TextView tvPersonName;

    ListView lvTracking;

    RouteAdaptor adaptor;

    private String userID;

    private List<TrackingStatus> lsTracking = new ArrayList<>();

    public static RouteFragment newInstance() {
        return new RouteFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_route, container, false);
        setupView(view);
        setupAction();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(RouteViewModel.class);

//        mViewModel.getlistTrackig().observe(this, new Observer<List<TrackingStatus>>() {
//            @Override
//            public void onChanged(List<TrackingStatus> trackingStatuses) {
//                adaptor = new RouteAdaptor(getContext(),trackingStatuses);
//                lvTracking.setAdapter(adaptor);
//            }
//        });

        adaptor = new RouteAdaptor(getContext(),lsTracking);
        lvTracking.setAdapter(adaptor);

        mViewModel.getRoutedata().observe(this, new Observer<RouteData>() {
            @Override
            public void onChanged(RouteData routeData) {
                lsTracking.clear();
                for (TrackingStatus t : routeData.getStatus()) {
                    //Log.d("LOCATIONN", "onChanged: location: " + t.getLocation());
                    String location = t.getLocation().split("]")[1];
                    t.setLocation(location);
                    lsTracking.add(t);
                }

                if (lsTracking.size() == 0)
                    lsTracking.add(new TrackingStatus("Location has not update for this user yet","Notify"));

                adaptor.notifyDataSetChanged();
            }
        });

        mViewModel.fetchData(DataCenter.routeUID);
    }

    private void setupView(View view) {
        tvPersonName = view.findViewById(R.id.route_person_name_tv);
        lvTracking = view.findViewById(R.id.route_tracking_lv);

        tvPersonName.setText(DataCenter.routeUNAME);
    }

    private void setupAction() {

    }

    public void setUserID(String uid) {
        this.userID = uid;
    }
}
