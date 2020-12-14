package com.nguyenhongphuc98.dsaw.ui.admin_warning;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nguyenhongphuc98.dsaw.data.DataManager;
import com.nguyenhongphuc98.dsaw.data.model.City;
import com.nguyenhongphuc98.dsaw.data.model.District;
import com.nguyenhongphuc98.dsaw.data.model.Ward;
import com.nguyenhongphuc98.dsaw.data.model.Warning;

import java.util.List;

public class AdminWarningViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<String> mContent = new MutableLiveData<>();
    private MutableLiveData<String> mCMND = new MutableLiveData<>();
    private MutableLiveData<List<City>> lsCity = new MutableLiveData<>();
    private MutableLiveData<List<District>> lsDistrict = new MutableLiveData<>();
    private MutableLiveData<List<Ward>> lsWard = new MutableLiveData<>();

    public AdminWarningViewModel() {
        mContent.setValue("");
        mCMND.setValue("");
    }

    public MutableLiveData<String> getmContent() {
        return mContent;
    }

    public MutableLiveData<String> getmCMND() {
        return mCMND;
    }

    public void setmContent(String mContent) {
        this.mContent.setValue(mContent);
    }

    public void setmCMND(MutableLiveData<String> mCMND) {
        this.mCMND = mCMND;
    }


    public void CreateWarning(Warning warning)
    {
        DataManager.Instance().CreateWarning(warning);
    }

    public MutableLiveData<List<District>> getLsDistrict() {
        return lsDistrict;
    }

    public void setLsDistrict(MutableLiveData<List<District>> lsDistrict) {
        this.lsDistrict = lsDistrict;
    }

    public MutableLiveData<List<City>> getLsCity() {
        return lsCity;
    }

    public void setLsCity(MutableLiveData<List<City>> lsCity) {
        this.lsCity = lsCity;
    }

    public MutableLiveData<List<Ward>> getLsWard() {
        return lsWard;
    }

    public void setLsWard(MutableLiveData<List<Ward>> lsWard) {
        this.lsWard = lsWard;
    }

    public void GetAllCity()
    {
        DataManager.Instance().GetAllmCityWarning(lsCity);
    }

    public void GetDistrictOfCity(String codeCity)
    {
        DataManager.Instance().GetmDistrictOfCityWarning(lsDistrict, codeCity);
    }

    public void GetWardOfDistrict(String cityCode, String codeDistrict)
    {
        DataManager.Instance().GetmWardOfDistrictWarning(lsWard, cityCode, codeDistrict);
    }
}
