package com.nguyenhongphuc98.dsaw.ui.user;

import android.app.Application;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nguyenhongphuc98.dsaw.data.DataCenter;
import com.nguyenhongphuc98.dsaw.data.DataManager;
import com.nguyenhongphuc98.dsaw.data.model.City;

import java.util.List;

public class UserViewModel extends ViewModel {

    private MutableLiveData<String> mName = new MutableLiveData<>();
    private MutableLiveData<String> mCMND = new MutableLiveData<>();
    private MutableLiveData<String> mDayOfBirth = new MutableLiveData<>();
    private MutableLiveData<String> mContact = new MutableLiveData<>();
    private MutableLiveData<List<City>> lsCity = new MutableLiveData<>();

    public UserViewModel() {
        mName.setValue(DataCenter.currentUser.getUsername());
        mCMND.setValue(DataCenter.currentUser.getIdentity());
        mDayOfBirth.setValue(DataCenter.currentUser.getBirthday());
        mContact.setValue(DataCenter.currentUser.getPhonenumber());
    }

    public MutableLiveData<String> getName() {
        return mName;
    }

    public MutableLiveData<String> getCMND() {
        return mCMND;
    }

    public MutableLiveData<String> getDayOfBirth() {
        return mDayOfBirth;
    }

    public MutableLiveData<String> getContact() {
        return mContact;
    }

    public MutableLiveData<List<City>> getLsCity() {
        return lsCity;
    }

    public void setmName(String mName) {
        this.mName.setValue(mName);
    }

    public void setmCMND(String mCMND) {
        this.mCMND.setValue(mCMND);
    }

    public void setmDayOfBirth(String mDayOfBirth) {
        this.mDayOfBirth.setValue(mDayOfBirth);
    }

    public void setmContact(String mContact) {
        this.mContact.setValue(mContact);
    }

    public void GetUser(TextView name, TextView identity, TextView birthday, TextView phonenumber)
    {
        DataManager.Instance().GetUserData(name, identity, birthday, phonenumber);
    }

    public void UpdateUser(String name, String identity, String birthday, String phoneNumber)
    {
        DataManager.Instance().UpdateUser(name, identity, birthday, phoneNumber);
    }

    public void GetAllCity()
    {
        DataManager.Instance().GetAllCity(lsCity);
    }
}
