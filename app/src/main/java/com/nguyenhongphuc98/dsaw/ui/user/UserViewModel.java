package com.nguyenhongphuc98.dsaw.ui.user;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.nguyenhongphuc98.dsaw.data.DataCenter;
import com.nguyenhongphuc98.dsaw.data.DataManager;

public class UserViewModel extends ViewModel {

    private MutableLiveData<String> mName = new MutableLiveData<>();
    private MutableLiveData<String> mCMND = new MutableLiveData<>();
    private MutableLiveData<String> mDayOfBirth = new MutableLiveData<>();
    private MutableLiveData<String> mContact = new MutableLiveData<>();

    public UserViewModel() {
        mName.setValue(DataCenter.userName);
        mCMND.setValue(DataCenter.identity);
        mDayOfBirth.setValue(DataCenter.birthday);
        mContact.setValue(DataCenter.phoneNumber);
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

    public void GetUser(String id)
    {
        DataManager.Instance().GetUserData(id);
    }

    public void UpdateUser(String name, String identity, String birthday, String phoneNumber)
    {
        DataManager.Instance().UpdateUser(name, identity, birthday, phoneNumber);
    }
}
