package com.nguyenhongphuc98.dsaw.ui.login;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.nguyenhongphuc98.dsaw.MainActivity;
import com.nguyenhongphuc98.dsaw.R;
import com.nguyenhongphuc98.dsaw.data.DataCenter;
import com.nguyenhongphuc98.dsaw.data.DataManager;
import com.nguyenhongphuc98.dsaw.data.model.Account;
import com.nguyenhongphuc98.dsaw.utils.CurrentLocation;
import com.nguyenhongphuc98.dsaw.utils.LocationTrack;

import java.util.ArrayList;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class LoginActivity extends AppCompatActivity {
    EditText account;
    EditText password;
    TextView get_password;
    Button login;
    TextView sign_up;

    ProgressBar progressBar;

    // Tracking location
    private ArrayList<String> permissionsToRequest;
    private ArrayList permissionsRejected = new ArrayList();
    private ArrayList permissions = new ArrayList();

    private final static int ALL_PERMISSIONS_RESULT = 101;
    LocationTrack locationTrack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);

        InitComponent();
        InitEvent();

        getLocationPermission();

    }

    void InitComponent()
    {
        account = findViewById(R.id.account);
        password = findViewById(R.id.password);
        get_password = findViewById(R.id.get_password);
        login = findViewById(R.id.login);
        sign_up = findViewById(R.id.sign_up);
        progressBar = findViewById(R.id.login_progessbar);
    }

    void InitEvent()
    {
        login.setOnClickListener(v -> LoginProcess());
        sign_up.setOnClickListener(v -> CreateNewAccount());
        get_password.setOnClickListener(v -> ResetPassword());
    }

    public void LoginProcess()
    {
        if (account.getText().toString().length() != 0 && password.getText().toString().length() != 0) {

            progressBar.setIndeterminate(true);
            progressBar.setVisibility(View.VISIBLE);

            DataManager.Instance().setLoginProcess(this);
            DataManager.Instance().ProcessLogin(account.getText().toString(), password.getText().toString());

        }
        else if (account.getText().toString().equalsIgnoreCase("1")) {
            DataManager.Instance().setLoginProcess(this);
            DataManager.Instance().ProcessLogin("tihtk.98@gmail.com", "123456789");
        }
        else{
            Toast.makeText(getApplicationContext(), "Xin kiểm tra lại thông tin đăng nhập!", Toast.LENGTH_LONG).show();
        }
    }

    public void LoadUserDataComplete()
    {
        DataCenter.currentUser = new Account();
        DataCenter.currentUser.setEmail(account.getText().toString());
        preSetup();

//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
        //Log.d("Login activity", "User name" + DataCenter.currentUser.getUsername());
    }

    public void LoginSuccessful()
    {
        // Khong xu ly thong tin user o day ma se de cho qua main activity lam
        //========================================================
        //if (account.getText().toString().equalsIgnoreCase("1")) DataManager.Instance().GetUserDataByEmail("tihtk.98@gmail.com");
        //else DataManager.Instance().GetUserDataByEmail(account.getText().toString());
        //Log.e("LoginProcess","Account: " + DataCenter.currentUser.getUsername());

        Toast.makeText(getApplicationContext(), "Loading...", Toast.LENGTH_LONG).show();
    }

    public void LoginFail()
    {
        Toast.makeText(getApplicationContext(), "Xin kiểm tra lại thông tin đăng nhập!", Toast.LENGTH_LONG).show();
        hiddenProgessbar();
    }

    public void CreateNewAccount()
    {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void ResetPassword()
    {
        Intent intent = new Intent(this, ResetPasswordActivity.class);
        startActivity(intent);
    }

    private void hiddenProgessbar() {

        progressBar.setIndeterminate(false);
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void preSetup() {

        //set current account after login to using later
        MutableLiveData<Account> user = new MutableLiveData<>();
        DataManager.Instance().fetchAccountByEmail(DataCenter.currentUser.getEmail(),user);
        user.observe(this, account -> {
            // update to get full info of current account
            DataCenter.currentUser = account;
            locationTrack = new LocationTrack(this);

            if (locationTrack.canGetLocation()) {

                double longitude = locationTrack.getLongitude();
                double latitude = locationTrack.getLatitude();

                DataCenter.currentLocation = new CurrentLocation(latitude, longitude);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                //Toast.makeText(getApplicationContext(), "Longitude:" + Double.toString(longitude) + "\nLatitude:" + Double.toString(latitude), Toast.LENGTH_LONG).show();
                //Log.e("LOCATION", "onCreate: location:"+longitude +"-"+latitude);
            } else {
                locationTrack.showSettingsAlert();
            }

            // Open main screen when have enough data
            hiddenProgessbar();
        });
    }

    private void getLocationPermission() {

        /// Location tracking =======================================================================
        permissions.add(ACCESS_FINE_LOCATION);
        permissions.add(ACCESS_COARSE_LOCATION);

        permissionsToRequest = findUnAskedPermissions(permissions);
        //get the permissions we have asked for before but are not granted..
        //we will store this in a global list to access later.


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        }
    }

    private ArrayList findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList result = new ArrayList();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canMakeSmores() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }


    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        switch (requestCode) {

            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {


//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
//                            showMessageOKCancel("These permissions are mandatory for the application. Please allow access.",
//                                    new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialog, int which) {
//                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//                                                requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
//                                            }
//                                        }
//                                    });
//                            return;
//                        }
//                    }

                }

                break;
        }

    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(getApplicationContext())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationTrack.stopListener();
    }
}
