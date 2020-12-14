package com.nguyenhongphuc98.dsaw.ui.cases;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nguyenhongphuc98.dsaw.R;
import com.nguyenhongphuc98.dsaw.adaptor.CaseAdaptor;
import com.nguyenhongphuc98.dsaw.data.DataManager;
import com.nguyenhongphuc98.dsaw.data.model.Account;
import com.nguyenhongphuc98.dsaw.data.model.Case;
import com.nguyenhongphuc98.dsaw.data.model.TrackingStatus;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CaseFragment extends Fragment {

    private CaseViewModel mViewModel;

    private Spinner spinner;
    ArrayAdapter<CharSequence> fAdapter;

    private ListView lvCase;
    private Button btnUpdate;
    private EditText tvCmnd;
    private TextView tvSeeAll;

    ViewCaseFragment viewCaseFragment;

    CaseAdaptor adaptor;
    public List<Case> lsCases;

    String fx;

    String lastUIDInserted = "notyetinserted";

    Account tempCaseAccount;

    public static CaseFragment newInstance() {
        return new CaseFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_case, container, false);
        setupView(view);

        setupACtion();

        mViewModel = ViewModelProviders.of(this).get(CaseViewModel.class);

        adaptor = new CaseAdaptor(getContext(), lsCases);
        lvCase.setAdapter(adaptor);

        mViewModel.getLiveAccount().observe(this, account -> {

            if (account.getIdentity().equals(lastUIDInserted))
                return;

            if (account.getIdentity().equals("null")) {
                Toast.makeText(getContext(),"User not found",Toast.LENGTH_SHORT).show();
                return;
            }

           // Wait to get last location and insert case at that time
            DataManager.Instance().fetchLastLocationOfUser(mViewModel.lastLocation, account.getIdentity());

            tempCaseAccount = account;
        });

        mViewModel.getLastLocation().observe(this, trackingStatus -> {
            if (tempCaseAccount == null)
                return;

            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd - HH:mm");
            Date date = new Date();
            date.setMinutes(date.getMinutes() - 1);
            String t = dateFormat.format(date);
            Log.d("TAGGG", "onChanged: date"+ t);

            String location = "1,1";
            // Ex parten: "[10.84627,106.76992999999999]null,Vietnam"
            location = trackingStatus.getLocation().split("]")[0];
            location = location.substring(1);

            Case c = new Case(tempCaseAccount.getArea(),
                    t,
                    "zzzzzzzzzzzzzzzzzz",
                    fx,
                    "setlater",
                    location,
                    tempCaseAccount.getIdentity(),
                    tempCaseAccount.getUsername());

            DataManager.Instance().insertCase(c);
            //Log.d("CASE", "location: " + location);
            lsCases.add(c);
            adaptor.notifyDataSetChanged();
            lastUIDInserted = tempCaseAccount.getIdentity();
            tempCaseAccount = null;
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


//        mViewModel.getliveListCases().observe(this, new Observer<List<Case>>() {
//            @Override
//            public void onChanged(List<Case> cases) {
////                mViewModel.lsCases.clear();
////                for (Case c : mViewModel.getliveListCases().getValue()) {
////                    mViewModel.lsCases.add(c);
////                }
//                adaptor.notifyDataSetChanged();
//            }
//        });
    }

    private void setupView(View view) {

        viewCaseFragment = new ViewCaseFragment();

        spinner = view.findViewById(R.id.case_spinner_level);
        tvCmnd = view.findViewById(R.id.case_cmnd_tf);
        lvCase = view.findViewById(R.id.case_recently_lv);
        btnUpdate = view.findViewById(R.id.case_update_btn);
        tvSeeAll = view.findViewById(R.id.case_see_all_tv);

        ArrayAdapter<CharSequence> fAdapter = ArrayAdapter.createFromResource(getContext(),
                R.array.case_array, android.R.layout.simple_spinner_item);
        fAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(fAdapter);

        lsCases = new ArrayList<>();
    }

    private void setupACtion() {

        btnUpdate.setOnClickListener(v -> {
            if (spinner.getSelectedItemPosition() == 0) {
                Toast.makeText(getContext(),"Please choose object",Toast.LENGTH_SHORT).show();
                return;
            }

            String f = spinner.getSelectedItem().toString().split("-")[0].split(" ")[0];
            mViewModel.updateCase(tvCmnd.getText().toString(),f);
            fx = f;
        });

        tvSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Utils.replaceFragment(viewCaseFragment);
                NavHostFragment.findNavController(getParentFragment()).navigate(R.id.go_to_view_case_fragment);
            }
        });
    }
}
