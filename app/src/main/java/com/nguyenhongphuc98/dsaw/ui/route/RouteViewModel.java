package com.nguyenhongphuc98.dsaw.ui.route;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nguyenhongphuc98.dsaw.data.DataManager;
import com.nguyenhongphuc98.dsaw.data.model.RouteData;
import com.nguyenhongphuc98.dsaw.data.model.TrackingStatus;

import java.util.ArrayList;
import java.util.List;

public class RouteViewModel extends ViewModel {

    private MutableLiveData<List<TrackingStatus>> mTracking;
    private MutableLiveData<RouteData> routeData;

    public RouteViewModel() {
        mTracking = new MutableLiveData<>();
        routeData = new MutableLiveData<>();
    }

    public void fetchData(String uid) {
        DataManager.Instance().fetchRouteOf(routeData,uid);
    }

    public MutableLiveData<List<TrackingStatus>> getlistTrackig() {return  mTracking; }
    public MutableLiveData<RouteData> getRoutedata() {return  routeData; }
}
