package com.nguyenhongphuc98.dsaw.ui.user;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nguyenhongphuc98.dsaw.R;
import com.nguyenhongphuc98.dsaw.data.DataCenter;
import com.nguyenhongphuc98.dsaw.data.model.City;
import com.nguyenhongphuc98.dsaw.data.model.District;
import com.nguyenhongphuc98.dsaw.data.model.Ward;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class UserFragment extends Fragment {

    private UserViewModel mViewModel;

    private TextView mTextName;
    private TextView mTextCMND;
    private TextView mTextDayofBirth;
    private TextView mTextContact;
    private Button mBtnUpdate;
    private CheckedTextView checkedTextView;
    private Spinner mSpinCity;
    private Spinner mSpinDistrict;
    private Spinner mSpinWard;

    //String[] city = { "Da Nang", "Ha Noi", "Ho Chi Minh", "Da Lat", "Quang Ngai"};
    private ArrayList<City> lsCity = new ArrayList<>();
    private ArrayAdapter<String> adCityName;
    private int cityPos = 0;

    private ArrayList<District> lsDistrict = new ArrayList<>();
    private ArrayAdapter<String> adDistrictName;
    private int districtPos = 0;

    private List<Ward> lsWard = new ArrayList<>();
    private ArrayAdapter<String> adWardName;
    private int wardPos = 0;


    final Calendar myCalendar = Calendar.getInstance();

    public static UserFragment newInstance() {
        return new UserFragment();
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            String myFormat = "dd/MM/yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            mTextDayofBirth.setText(sdf.format(myCalendar.getTime()));
        }

    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel =
                ViewModelProviders.of(this).get(UserViewModel.class);

        View root = inflater.inflate(R.layout.fragment_user, container, false);
        InitComponent(root);
        InitEvent();
        UnfocusedElement();

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO: Use the ViewModel
        RegisterDataLiveListener();
        //mViewModel.GetUser(mTextName, mTextCMND, mTextDayofBirth, mTextContact);
    }

    public void InitComponent(View view) {
        mTextName = view.findViewById(R.id.txtName);
        mTextCMND = view.findViewById(R.id.txtCMND);
        mTextDayofBirth = view.findViewById(R.id.txtDayOfBirth);
        mTextContact = view.findViewById(R.id.txtContact);
        mBtnUpdate = view.findViewById(R.id.user_update_btn);
        checkedTextView = view.findViewById(R.id.ctxAgreement);

        mSpinCity = view.findViewById(R.id.spinCity);
        mSpinDistrict = view.findViewById(R.id.spinDistrict);
        mSpinWard = view.findViewById(R.id.spinWard);
    }

    public void InitEvent()
    {
        mBtnUpdate.setOnClickListener(v -> {
            if (checkedTextView.isChecked()) {
                mViewModel.setmName(mTextName.getText().toString());
                mViewModel.setmCMND(mTextCMND.getText().toString());
                mViewModel.setmDayOfBirth(mTextDayofBirth.getText().toString());
                mViewModel.setmContact(mTextContact.getText().toString());
                mViewModel.UpdateUser(mTextName.getText().toString(), mTextCMND.getText().toString(),
                        mTextDayofBirth.getText().toString(), mTextContact.getText().toString(),
                        cityPos, districtPos, wardPos);
                Toast.makeText(getContext(), "Bạn vừa mới thay đổi thông tin cá nhân", Toast.LENGTH_LONG).show();
                UnfocusedElement();
            }
            else Toast.makeText(getContext(), "Vui lòng xác nhận cam kết", Toast.LENGTH_LONG).show();

        });

        mTextDayofBirth.setOnClickListener(v -> new DatePickerDialog(getContext(), date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show());

        checkedTextView.setOnClickListener(v -> {
            if (checkedTextView.isChecked()) checkedTextView.setChecked(false);
            else checkedTextView.setChecked(true);
        });

        mSpinCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cityPos = position;
                lsDistrict.clear();
                lsWard.clear();
                mViewModel.GetDistrictOfCity(lsCity.get(position).getCode());

                adDistrictName = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, lsDistrict);
                adDistrictName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinDistrict.setAdapter(adDistrictName);

                //Toast.makeText(getContext(), lsCity.get(position).getName() , Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mSpinDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                districtPos = position;

                mViewModel.GetWardOfDistrict(lsCity.get(cityPos).getCode(), lsDistrict.get(position).getCode());

                adWardName = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, lsWard);
                adWardName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinWard.setAdapter(adWardName);

                //Toast.makeText(getContext(), lsDistrict.get(position).getName() , Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSpinWard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                wardPos = position;

                //Toast.makeText(getContext(), lsWard.get(position), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void RegisterDataLiveListener() {
        mViewModel.getName().observe(this, s -> mTextName.setText(String.valueOf(s)));
        mViewModel.getCMND().observe(this, s -> mTextCMND.setText(String.valueOf(s)));
        mViewModel.getDayOfBirth().observe(this, s -> mTextDayofBirth.setText(String.valueOf(s)));
        mViewModel.getContact().observe(this, s -> mTextContact.setText(String.valueOf(s)));

        // add items to city spinner
        adCityName = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, lsCity);
        adCityName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinCity.setAdapter(adCityName);

        mViewModel.GetAllCity();
        mViewModel.getLsCity().observe(this, cities -> {
            lsCity.clear();
            for (City a : cities) {
                lsCity.add(a);
                Log.e("User fragment get city: ", a.getName());
            }

            if (lsCity.size() == 0)
                lsCity.add(new City());

            adCityName.notifyDataSetChanged();
            mSpinCity.setSelection(DataCenter.currentUser.getCode_city());
        });

        adDistrictName = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item);
        mViewModel.getLsDistrict().observe(this, districts -> {
            lsDistrict.clear();
            for (District a : districts)
            {
                lsDistrict.add(a);
                Log.e("User fragment get districts: ", a.getName());
            }

            if (lsDistrict.size() == 0)
                lsDistrict.add(new District());


            adDistrictName.notifyDataSetChanged();
            mSpinDistrict.setSelection(DataCenter.currentUser.getCode_district());
        });

        adWardName = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item);
        mViewModel.getLsWard().observe(this, wards -> {
            lsWard.clear();
            for (Ward a : wards)
            {
                lsWard.add(a);
                Log.e("User fragment get wards: ", a.getName());
            }

            if (lsWard.size() == 0)
                lsWard.add(new Ward());

            adWardName.notifyDataSetChanged();
            mSpinWard.setSelection(DataCenter.currentUser.getCode_ward());
        });
    }

    public void UnfocusedElement() {
        mTextName.setFocusableInTouchMode(false);
        mTextName.setFocusable(false);
        mTextName.setFocusableInTouchMode(true);
        mTextName.setFocusable(true);
        mTextCMND.setFocusableInTouchMode(false);
        mTextCMND.setFocusable(false);
        mTextCMND.setFocusableInTouchMode(true);
        mTextCMND.setFocusable(true);
        mTextDayofBirth.setFocusableInTouchMode(false);
        mTextDayofBirth.setFocusable(false);
        mTextDayofBirth.setFocusableInTouchMode(true);
        mTextDayofBirth.setFocusable(true);
        mTextContact.setFocusableInTouchMode(false);
        mTextContact.setFocusable(false);
        mTextContact.setFocusableInTouchMode(true);
        mTextContact.setFocusable(true);
    }

}
