package com.nguyenhongphuc98.dsaw.ui.admin_warning;

import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
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
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.nguyenhongphuc98.dsaw.FirebaseMessagingService;
import com.nguyenhongphuc98.dsaw.MainActivity;
import com.nguyenhongphuc98.dsaw.R;
import com.nguyenhongphuc98.dsaw.data.DataCenter;
import com.nguyenhongphuc98.dsaw.data.DataManager;
import com.nguyenhongphuc98.dsaw.data.model.City;
import com.nguyenhongphuc98.dsaw.data.model.District;
import com.nguyenhongphuc98.dsaw.data.model.Ward;
import com.nguyenhongphuc98.dsaw.data.model.Warning;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class AdminWarning extends Fragment {
    private AdminWarningViewModel mViewModel;

    Switch mSwitch;
    LinearLayout mCmndLayout;
    EditText mTextContent;
    EditText mTextCmnd;
    Button mButton;

    Spinner spinCity;
    Spinner spinDistrict;
    Spinner spinWard;

    private ArrayList<City> lsCity = new ArrayList<>();
    private ArrayAdapter<String> adCityName;
    private int cityPos = 0;

    private ArrayList<District> lsDistrict = new ArrayList<>();
    private ArrayAdapter<String> adDistrictName;
    private int districtPos = 0;

    private List<Ward> lsWard = new ArrayList<>();
    private ArrayAdapter<String> adWardName;
    private int wardPos = 0;

    public static AdminWarning newInstance() {
        return new AdminWarning();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_admin_warning, container, false);
        InitComponent(view);
        InitEvent();
        Log.d("show", "View created");

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d("show", "View created");
        super.onActivityCreated(savedInstanceState);
        createNotificationChannel();
        mViewModel = ViewModelProviders.of(this).get(AdminWarningViewModel.class);
        // TODO: Use the ViewModel
        RegisterDataLiveListener();
    }

    public void InitComponent(View view)
    {
        mSwitch = view.findViewById(R.id.stateSwitch);
        mCmndLayout = view.findViewById(R.id.cmnd);
        mTextContent = view.findViewById(R.id.contentEdit);
        mButton = view.findViewById(R.id.summit_warning_button);

        spinCity = view.findViewById(R.id.spinCity);
        spinDistrict = view.findViewById(R.id.spinDistrict);
        spinWard = view.findViewById(R.id.spinWard);
    }

    public void InitEvent()
    {
        mButton.setOnClickListener(v -> {
            Toast.makeText(getContext(),"Button is clicked",Toast.LENGTH_SHORT).show();

            mViewModel.setmContent(mTextContent.getText().toString());
            Warning warning;

            if (mTextContent.getText().toString().isEmpty())
            {
                Toast.makeText(getContext(), "Content can not be empty!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                if (mSwitch.isChecked())
                {
                    warning = new Warning("Cảnh báo nguy hiểm", mTextContent.getText().toString(), DataCenter.currentUser.getUsername(),
                            null, cityPos, districtPos - 1, wardPos - 1);

                }
                else
                {
                    warning = new Warning("Cảnh báo nguy hiểm", mTextContent.getText().toString(), DataCenter.currentUser.getUsername(),
                            null, -1, -1, -1);

                }

                mViewModel.CreateWarning(warning);

                /*Notification notification = new NotificationCompat.Builder(getContext(), "CHANNEL_ID")
                    .setSmallIcon(R.drawable.warning_icon)
                    .setContentTitle("Canh bao tu luyenprocool")
                    .setContentText(mTextContent.getText())
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .build();*/

                Log.d("show", "Warning created");

                // Get token
                // [START retrieve_current_token]
                FirebaseInstanceId.getInstance().getInstanceId()
                        .addOnCompleteListener(task -> {
                            if (!task.isSuccessful()) {
                                Log.w(TAG, "getInstanceId failed", task.getException());
                                return;
                            }

                            // Get new Instance ID token
                            String token = task.getResult().getToken();

                            // Log and toast
                            String msg = getString(R.string.msg_token_fmt, token);
                            Log.d(TAG, msg);
                            Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                            Log.d(TAG, token);

                        });
                // [END retrieve_current_token]
            }
        });

        mSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) mCmndLayout.setVisibility(View.VISIBLE);
            else mCmndLayout.setVisibility(View.GONE);
        });

        spinCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cityPos = position;
                lsDistrict.clear();
                lsWard.clear();
                mViewModel.GetDistrictOfCity(lsCity.get(position).getCode());

                adDistrictName = new ArrayAdapter(getContext(), R.layout.custom_spinner_item, lsDistrict);
                adDistrictName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinDistrict.setAdapter(adDistrictName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinDistrict.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                districtPos = position;

                mViewModel.GetWardOfDistrict(lsCity.get(cityPos).getCode(), lsDistrict.get(position).getCode());

                adWardName = new ArrayAdapter(getContext(), R.layout.custom_spinner_item, lsWard);
                adWardName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinWard.setAdapter(adWardName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinWard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                wardPos = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void RegisterDataLiveListener() {
        mViewModel.getmContent().observe(this, s -> mTextContent.setText(String.valueOf(s)));

        adCityName = new ArrayAdapter(getContext(), R.layout.custom_spinner_item, lsCity);
        adCityName.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinCity.setAdapter(adCityName);

        mViewModel.GetAllCity();
        mViewModel.getLsCity().observe(this, cities -> {
            lsCity.clear();
            for (City a : cities) {
                lsCity.add(a);
                Log.e("Warning fragment get city: ", a.getName());
            }

            if (lsCity.size() == 0)
                lsCity.add(new City());

            adCityName.notifyDataSetChanged();
        });

        adDistrictName = new ArrayAdapter(getContext(), R.layout.custom_spinner_item);
        mViewModel.getLsDistrict().observe(this, districts -> {
            lsDistrict.clear();
            for (District a : districts)
            {
                lsDistrict.add(a);
                Log.e("Warning fragment get districts: ", a.getName());
            }

            if (lsDistrict.size() == 0)
                lsDistrict.add(new District());


            adDistrictName.notifyDataSetChanged();
        });

        adWardName = new ArrayAdapter(getContext(), R.layout.custom_spinner_item);
        mViewModel.getLsWard().observe(this, wards -> {
            lsWard.clear();
            for (Ward a : wards)
            {
                lsWard.add(a);
                Log.e("Warning fragment get wards: ", a.getName());
            }

            if (lsWard.size() == 0)
                lsWard.add(new Ward());

            adWardName.notifyDataSetChanged();
            spinWard.setSelection(DataCenter.currentUser.getCode_ward());
        });
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("CHANNEL_ID", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void PushNotify()
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), "CHANNEL_ID")
                .setSmallIcon(R.drawable.warning_icon)
                .setContentTitle("Cảnh báo nguy hiểm")
                .setContentText(mTextContent.getText())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getContext());
        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, builder.build());
    }
}